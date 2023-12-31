import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    private static void addToLog(String logMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bitacora.txt", true))) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            writer.write(formattedDateTime + " - " + logMessage);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static class InvalidPokemonNameException extends Exception {
        public InvalidPokemonNameException(String message) {
            super(message);
        }
    }

    private static void validatePokemonName(String name, String[] validNames) throws InvalidPokemonNameException {
        for (String pokemonName : validNames) {
            if (pokemonName.equalsIgnoreCase(name)) {
                return; // Nombre válido
            }
        }

        // Si llegamos aquí, el nombre no es válido
        throw new InvalidPokemonNameException("Nombre de Pokémon no válido: " + name);
    }
    public static void main(String[] args) {
        try {

            String[] searchOptions = {"Nombre", "Tipo", "Generación", "EggGroup"};
            String searchCriteria = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona cómo buscar al Pokémon:",
                    "Búsqueda de Pokémon",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    searchOptions,
                    searchOptions[0]
            );


            String[] allPokemonNames = PokemonApi.getPokemonNames();
            Thread typeThread = null;
            Thread generationThread = null;
            Thread eggGroupThread = null;
            AtomicReference<String> pokemonSeleccionado = new AtomicReference<>("");
            String selectedName = null;

            switch (searchCriteria) {
                case "Nombre":
                    boolean nombreValido = false;
                    do {
                        try {
                            selectedName = JOptionPane.showInputDialog(null, "Escriba el nombre del pokemon");
                            validatePokemonName(selectedName, allPokemonNames);
                            nombreValido = true;
                        } catch (InvalidPokemonNameException e) {
                            JOptionPane.showMessageDialog(null, "El nombre ingresado no es válido. Inténtelo de nuevo.");
                            addToLog("Nombre no válido ingresado: " + selectedName);
                        }
                    } while (!nombreValido);

                    pokemonSeleccionado.set(selectedName.toLowerCase());
                    break;

                case "Tipo":
                    try {
                        String[] pokemonTypes = PokemonApi.getPokemonTypes();
                        String selectedType = (String) JOptionPane.showInputDialog(
                                null,
                                "Selecciona un tipo:",
                                "Tipos de Pokémon",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                pokemonTypes,
                                pokemonTypes[0]
                        );
                        System.out.println("Tipo seleccionado: " + selectedType);

                        typeThread = new Thread(() -> {
                            JOptionPane loadingDialog = new JOptionPane("Obteniendo Pokémon...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                            JDialog dialog = loadingDialog.createDialog("Cargando...");
                            dialog.setModal(false);
                            dialog.setVisible(true);

                            ArrayList<String> pokemonsByType = new ArrayList<>();
                            for (String name : allPokemonNames) {
                                PokeInfo pokeInfo = new PokeInfo();
                                try {
                                    String typeInfo = pokeInfo.getPokemonType(name);
                                    if (typeInfo.contains(selectedType)) {
                                        pokemonsByType.add(name);
                                        System.out.println(name); // Imprimir en consola los Pokémon
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    addToLog("Error al obtener información del tipo del Pokémon: " + name);
                                    JOptionPane.showMessageDialog(null, "Error al obtener información del tipo del Pokémon: " + name);
                                }
                            }

                            if (!pokemonsByType.isEmpty()) {
                                String selectedPokemon = (String) JOptionPane.showInputDialog(
                                        null,
                                        "Selecciona un Pokémon:",
                                        "Pokémon con tipo " + selectedType,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        pokemonsByType.toArray(),
                                        pokemonsByType.get(0)
                                );
                                pokemonSeleccionado.set(selectedPokemon);
                            } else {
                                JOptionPane.showMessageDialog(null, "No se encontraron Pokémon con el tipo " + selectedType);
                            }
                        });
                        typeThread.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        addToLog("Error al obtener los tipos de Pokémon desde el API");
                        JOptionPane.showMessageDialog(null, "Error al obtener los tipos de Pokémon desde el API");
                    }
                    break;


                case "Generación":
                    String[] pokemonGenerations = PokemonApi.getPokemonGenerations();
                    String selectedGeneration = (String) JOptionPane.showInputDialog(
                            null,
                            "Selecciona una generación:",
                            "Generaciones de Pokémon",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            pokemonGenerations,
                            pokemonGenerations[0]
                    );
                    System.out.println("Generación seleccionada: " + selectedGeneration);

                    generationThread = new Thread(() -> {
                        JOptionPane loadingDialog = new JOptionPane("Obteniendo Pokémon...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                        JDialog dialog = loadingDialog.createDialog("Cargando...");
                        dialog.setModal(false);
                        dialog.setVisible(true);

                        ArrayList<String> pokemonsByGeneration = new ArrayList<>();
                        for (String name : allPokemonNames) {
                            PokeInfo pokeInfo = new PokeInfo();
                            String genInfo = pokeInfo.getPokemonGeneration(name);
                            if (genInfo.equals("Generation: " + selectedGeneration)) {

                                pokemonsByGeneration.add(name);
                            }
                        }

                        dialog.dispose();

                        if (!pokemonsByGeneration.isEmpty()) {
                            String selectedPokemon = (String) JOptionPane.showInputDialog(null, "Selecciona un Pokémon:", "Pokémon de la generación " + selectedGeneration, JOptionPane.QUESTION_MESSAGE, null, pokemonsByGeneration.toArray(), pokemonsByGeneration.get(0));
                            pokemonSeleccionado.set(selectedPokemon);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontraron Pokémon de la generación " + selectedGeneration);
                        }
                    });
                    generationThread.start();
                    break;

                case "EggGroup":
                    String[] pokemonEggGroups = PokemonApi.getPokemonEggGroups();
                    String selectedEggGroup = (String) JOptionPane.showInputDialog(
                            null,
                            "Selecciona un grupo de huevo:",
                            "Grupos de Huevo de Pokémon",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            pokemonEggGroups,
                            pokemonEggGroups[0]
                    );
                    System.out.println("Grupo de Huevo seleccionado: " + selectedEggGroup);

                    eggGroupThread = new Thread(() -> {
                        JOptionPane loadingDialog = new JOptionPane("Obteniendo Pokémon...", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                        JDialog dialog = loadingDialog.createDialog("Cargando...");
                        dialog.setModal(false);
                        dialog.setVisible(true);

                        ArrayList<String> pokemonsByEggGroup = new ArrayList<>();
                        for (String name : allPokemonNames) {
                            PokeInfo pokeInfo = new PokeInfo();
                            String eggGroupInfo = pokeInfo.getPokemonEggGroup(name);
                            if (eggGroupInfo.contains(selectedEggGroup)) {
                                pokemonsByEggGroup.add(name);
                                System.out.println(name); // Imprimir en consola los Pokémon
                            }
                        }

                        dialog.dispose();

                        if (!pokemonsByEggGroup.isEmpty()) {
                            String selectedPokemon = (String) JOptionPane.showInputDialog(null, "Selecciona un Pokémon:", "Pokémon del grupo de huevo " + selectedEggGroup, JOptionPane.QUESTION_MESSAGE, null, pokemonsByEggGroup.toArray(), pokemonsByEggGroup.get(0));
                            pokemonSeleccionado.set(selectedPokemon);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontraron Pokémon del grupo de huevo " + selectedEggGroup);
                        }
                    });
                    eggGroupThread.start();
                    break;

                default:
                    System.out.println("Opción no válida");
                    break;
            }

            // Cierre del switch


            if (typeThread != null) {
                try {
                    typeThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (generationThread != null) {
                try {
                    generationThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (eggGroupThread != null) {
                try {
                    eggGroupThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Conectar los threads

            System.out.println("Pokémon seleccionado: " + pokemonSeleccionado.get());
            String selectedPokemon = pokemonSeleccionado.get();

            String type = "";
            String abilities = "";
            String moves = "";

            if (!selectedPokemon.isEmpty()) {
                PokeInfo pokeInfo = new PokeInfo();
                type = pokeInfo.getPokemonType(selectedPokemon);
                abilities = pokeInfo.getPokemonAbilities(selectedPokemon);
                moves = pokeInfo.getPokemonMoves(selectedPokemon);

            }


            String[] abilitiesArray = abilities.split(", ");
            for (int i = 0; i < abilitiesArray.length; i++) {
                abilitiesArray[i] = abilitiesArray[i].replaceAll("Abilities: ", "");
            }

            String[] movesArray = moves.split(", ");
            for (int i = 0; i < movesArray.length; i++) {
                movesArray[i] = movesArray[i].replaceAll("Moves: ", "");
            }

            String selectedAbility = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona una habilidad:",
                    "Habilidades del Pokémon",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    abilitiesArray,
                    abilitiesArray[0]
            );

            String selectedMove = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona un movimiento:",
                    "Movimientos del Pokémon",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    movesArray,
                    movesArray[0]
            );
            String moveProperties = PokeInfo.getMoveProperties(selectedMove);
            String moveDescription = PokeInfo.getMoveDescription(selectedMove);

            String preEvolution = "";
            String spritePreEvolutionURL = "";
            //Obtener pre evolución + sprite
            preEvolution = PokeInfo.getPokemonPreEvolution(selectedPokemon);
            spritePreEvolutionURL = PokeInfo.getPokemonFrontSprite(preEvolution);

            String pokedex = PokeInfo.pokedexEntry(selectedPokemon);
            String cleanPokedex = pokedex.replaceAll("\n", " ");
            cleanPokedex = cleanPokedex.replaceAll("\f", "<br>");
            cleanPokedex = "<html>" + cleanPokedex + "</html>";

            PokemonInfoDisplay.displayInfo(selectedPokemon, type, selectedAbility, selectedMove,
                    PokeInfo.getPokemonArtwork(selectedPokemon), moveProperties, moveDescription,
                    preEvolution, spritePreEvolutionURL, cleanPokedex);

        /*
        PokemonInfoDisplay es lo que se debe actualizar para el diseño
        SelectedPokemon es el nombre
        Type el tipo del pokemon
        selectedAbility la habilidad que agregó el usuario
        selectedMove el movimiento
        con el getPokemonArtwork se saca la imagen
        moveProperties es el ataque, tipo y accuracy del movimiento
        moveDescription la descripcion

         */
        }catch (Exception e){
            e.printStackTrace();
            addToLog("Error al conectar con el API:" + e.getMessage());
            JOptionPane.showMessageDialog(null,"Error al conectar con el API");
        }
    }

}
