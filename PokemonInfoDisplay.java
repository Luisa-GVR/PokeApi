import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
public class PokemonInfoDisplay {
    public static String firstCapitalLetter(String palabra) {
        // Verifica si el String no es nula
        if (palabra != null && !palabra.isEmpty()) {
            // Convierte la primera letra a mayúscula
            return palabra.substring(0, 1).toUpperCase() + palabra.substring(1);
        } else {
            // Retorna el String original si es nula o vacía
            return palabra;
        }
    }
    private static JLabel fontSize(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Comics Sans MS", Font.BOLD, size)); // Puedes ajustar el tamaño según tus necesidades
        return label;
    }

    public static Dimension resizer(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.getWidth() * 0.35);
        int maxHeight = (int) (screenSize.getHeight() * 0.8);

        return new Dimension(maxWidth, maxHeight);
    }

    public static Dimension spriteResizer(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.getWidth() * 0.04);
        int maxHeight = (int) (screenSize.getWidth() * 0.04);

        return new Dimension(maxWidth, maxHeight);
    }

    public static Dimension typeResizer(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.getWidth() * 0.05);
        int maxHeight = (int) (screenSize.getWidth() * 0.05);;

        return new Dimension(maxWidth, maxHeight);
    }

    public static Dimension iconResizer(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.getWidth() * 0.17);
        int maxHeight = (int) (screenSize.getWidth() * 0.17);

        return new Dimension(maxWidth, maxHeight);
    }

    private static void addToLog(String logMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bitacoraPokemonConsultado.txt", true))) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            writer.write(formattedDateTime + " - " + logMessage);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    public static void displayInfo(String selectedPokemon, String type, String selectedAbility,
                                   String selectedMove, String imageUrl, String moveProperties, String moveDescription,
                                   String preEvolution, String spritePreEvolutionURL, String pokedex) {
        JFrame frame = new JFrame("Información del Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = resizer();
        Dimension pokemonDimension = spriteResizer();
        Dimension typeResizer = typeResizer();
        Dimension iconResizer = iconResizer();

        //imagene, resized
        String urlBackground = backgroundCheck(type);
        ImageIcon typeBackground = new ImageIcon(urlBackground);
        Image resized = typeBackground.getImage().getScaledInstance(dimension.width, dimension.height, Image.SCALE_SMOOTH);
        ImageIcon resIcon = new ImageIcon(resized);
        JLabel backgroundTypeLabel = new JLabel(resIcon);

        String urlType;
        //Hace especial si no hay properties
        if (!moveProperties.equals("")) {
            urlType = typeCheck(moveProperties);
        } else {
            urlType = "iconosTipos/especial.png";
        }

        ImageIcon typeIcon = new ImageIcon(urlType);
        Image resizedTypeIcon = typeIcon.getImage().getScaledInstance(typeResizer.width, typeResizer.height, Image.SCALE_SMOOTH);
        ImageIcon resIconLabel = new ImageIcon(resizedTypeIcon);
        JLabel typeIconLabel = new JLabel(resIconLabel);


        frame.setSize(backgroundTypeLabel.getPreferredSize().width + 20, backgroundTypeLabel.getPreferredSize().height + 80);
        frame.setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();

        JLabel imageLabel = new JLabel();
        try {
            URL imageURL = new URL(imageUrl);
            BufferedImage img = ImageIO.read(imageURL);
            Image resizedIcon = img.getScaledInstance(iconResizer.width, iconResizer.height, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(resizedIcon);
            imageLabel.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        moveDescription = "<html>"+ moveDescription + "<html>";
        moveDescription =addLineBreaks(moveDescription, 8);


        //mas texto que nada
        JLabel nameLabel = fontSize(firstCapitalLetter(selectedPokemon), (int) (dimension.height * 0.05));
        JLabel typeLabel = fontSize(firstCapitalLetter(type), (int) (dimension.height * 0.0235));
        JLabel abilityLabel = fontSize(firstCapitalLetter(selectedAbility), (int) (dimension.height * 0.02));
        JLabel moveLabel = fontSize(firstCapitalLetter(selectedMove), (int) (dimension.height * 0.035));
        JLabel moveDescLabel = fontSize(firstCapitalLetter(moveDescription), (int) (dimension.height * 0.020));
        JLabel powerLabel = fontSize(firstCapitalLetter(powerCheck(moveProperties)), (int) (dimension.height * 0.02));
        JLabel accLabel = fontSize(firstCapitalLetter(accCheck(moveProperties)), (int) (dimension.height * 0.02));
        JLabel pokedexLabel = fontSize(firstCapitalLetter(addLineBreaks(pokedex,15)), (int) (dimension.height * 0.015));

        // Establecer los límites de los componentes
        backgroundTypeLabel.setBounds(0, 0,
                backgroundTypeLabel.getPreferredSize().width, backgroundTypeLabel.getPreferredSize().height);


        imageLabel.setBounds((int) (dimension.width * 0.30), (int) (dimension.height * 0.12),
                imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height);

        typeIconLabel.setBounds((int) (dimension.width * 0.1), (int) (dimension.height * 0.55),
                typeIconLabel.getPreferredSize().width, typeIconLabel.getPreferredSize().height);

        nameLabel.setBounds((int) (dimension.width * 0.2), (int) (dimension.height * 0.030),
                nameLabel.getPreferredSize().width + 100, nameLabel.getPreferredSize().height);

        //centrar el label type
        if(type.contains("Secondary")){
            typeLabel.setBounds((int) (dimension.width*0.2), (int) (dimension.height*0.475),
                    typeLabel.getPreferredSize().width+100, typeLabel.getPreferredSize().height);
        } else {typeLabel.setBounds((int) (dimension.width*0.35), (int) (dimension.height*0.475),
                    typeLabel.getPreferredSize().width+100, typeLabel.getPreferredSize().height);
        }


        abilityLabel.setBounds((int) (dimension.width*0.65), (int)(dimension.height*0.837),
                abilityLabel.getPreferredSize().width, abilityLabel.getPreferredSize().height+50);

        moveLabel.setBounds((int) (dimension.width*0.3), (int) (dimension.height*0.52),
                (int) (moveLabel.getPreferredSize().width*1.2), moveLabel.getPreferredSize().height);

        moveDescLabel.setBounds((int) (dimension.width*0.26), (int) (dimension.height*0.57),
                moveDescLabel.getPreferredSize().width, moveDescLabel.getPreferredSize().height);

        pokedexLabel.setBounds((int) (dimension.width*0.06), (int) (dimension.height*0.9),
                (int) (pokedexLabel.getPreferredSize().width*1.1), (int) (pokedexLabel.getPreferredSize().height*1.1));

        accLabel.setBounds((int) (dimension.width*0.1), (int) (dimension.height*0.86),
                accLabel.getPreferredSize().width, accLabel.getPreferredSize().height);

        powerLabel.setBounds((int) (dimension.width*0.3), (int) (dimension.height*0.86),
                powerLabel.getPreferredSize().width, powerLabel.getPreferredSize().height);


        // Añadir los componentes al JLayeredPane con diferentes capas
        layeredPane.add(backgroundTypeLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(imageLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(typeIconLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(nameLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(typeLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(abilityLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(moveLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(moveDescLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(powerLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(pokedexLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(accLabel, JLayeredPane.PALETTE_LAYER);

        //Pre evolución
        if (!preEvolution.equals("") && !spritePreEvolutionURL.equals("")) {
            // rectángulo :)
            JPanel roundedRectanglePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;

                    int width = getWidth() - 1;
                    int height = getHeight() - 1;
                    int arcWidth = 40;
                    int arcHeight = 40;

                    RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, width, height, arcWidth, arcHeight);
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fill(roundedRectangle);
                    g2d.setColor(Color.black);
                    g2d.draw(roundedRectangle);
                }
            };

            roundedRectanglePanel.setOpaque(false);
            roundedRectanglePanel.setBounds((int) (dimension.width*0.16), (int) (dimension.height*0.11),
                    (int) (dimension.width*0.40), (int) (dimension.height*0.04));


            JLabel preEvoName = new JLabel("Evolves from: " + firstCapitalLetter(preEvolution));
            preEvoName.setBounds((int) (dimension.width*0.18),(int) (dimension.height*0.12), preEvoName.getPreferredSize().width+40, preEvoName.getPreferredSize().height);
            layeredPane.add(preEvoName, JLayeredPane.PALETTE_LAYER);

            JLabel spriteLabel = new JLabel();
            try {
                URL spriteURL = new URL(spritePreEvolutionURL);
                BufferedImage img = ImageIO.read(spriteURL);
                Image resizedIcon = img.getScaledInstance(pokemonDimension.width,pokemonDimension.height, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(resizedIcon);
                spriteLabel.setIcon(imageIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }

            spriteLabel.setBounds((int) (dimension.width*0.05), (int) (dimension.height*0.08), spriteLabel.getPreferredSize().width,spriteLabel.getPreferredSize().height);

            //circulo
            CirclePanel circlePanel = new CirclePanel();
            circlePanel.setBounds(spriteLabel.getX(), spriteLabel.getY(),
                    spriteLabel.getPreferredSize().width, spriteLabel.getPreferredSize().height);
            circlePanel.setOpaque(false);

            layeredPane.add(roundedRectanglePanel, JLayeredPane.PALETTE_LAYER);
            layeredPane.add(spriteLabel, JLayeredPane.PALETTE_LAYER);
            layeredPane.add(circlePanel, JLayeredPane.PALETTE_LAYER);

        }

        //boton de guardado
        JButton saveButton = new JButton("Save");
        saveButton.setBounds((int) (backgroundTypeLabel.getPreferredSize().width / 2.25),
                backgroundTypeLabel.getPreferredSize().height + 5, (int) (dimension.width*0.15), (int) (dimension.height*0.05));
        saveButton.addActionListener(e -> {
            saveButton.setVisible(false);
            saveLayeredPaneAsImage(layeredPane);
            saveButton.setVisible(true);
        });

        layeredPane.add(saveButton, JLayeredPane.DEFAULT_LAYER);

        layeredPane.setPreferredSize(new Dimension(backgroundTypeLabel.getPreferredSize().width, backgroundTypeLabel.getPreferredSize().height));

        frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
        frame.setVisible(true);

        String logMessage = "Consultado Pokémon: " + selectedPokemon + ", Tipo: " + type +
                ", Habilidad: " + selectedAbility + ", Movimiento: " + selectedMove +
                ", Pokedex: " + pokedex;
        addToLog(logMessage);

    }

    private static String addLineBreaks(String moveDescription, int wordsPerLine) {
        String[] words = moveDescription.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            result.append(words[i]).append(" ");

            if ((i + 1) % wordsPerLine == 0) {
                result.append("<br>");
            }
        }

        return result.toString();
    }



    private static void saveLayeredPaneAsImage(JLayeredPane layeredPane) {
        BufferedImage image = new BufferedImage(layeredPane.getWidth(), layeredPane.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        layeredPane.printAll(g2d);

        g2d.dispose();

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getName().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }

            try {
                ImageIO.write(image, "png", fileToSave);
                System.out.println("Image saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String backgroundCheck(String tipo) {
        Pattern pattern = Pattern.compile("Primary Type: (\\w+)(, Secondary Type: \\d+)?");
        Matcher matcher = pattern.matcher(tipo);

        if (matcher.find()) {
            String maintType = matcher.group(1);
            return "fondosTipos/" + maintType + ".png";
        } else {
            return "";
        }
    }


    public static String typeCheck(String tipo){

        Pattern pattern = Pattern.compile("Type: (\\w+), Power: \\d+, Accuracy: \\d+");
        Matcher matcher = pattern.matcher(tipo);

        if (matcher.find()) {
            return "iconosTipos/" + (matcher.group(1)) + ".png";
        } else {
            return "";
        }

    }

    public static String powerCheck(String power) {
        Pattern pattern = Pattern.compile("Type: \\w+, Power: (\\d+), Accuracy: \\d+");
        Matcher matcher = pattern.matcher(power);

        if (matcher.find()) {
            String powerValue = matcher.group(1);
            return "Power: " + powerValue;
        } else {
            return "";
        }
    }

    public static String accCheck(String acc) {
        Pattern pattern = Pattern.compile("Type: \\w+, Power: \\d+, Accuracy: (\\d+)");
        Matcher matcher = pattern.matcher(acc);

        if (matcher.find()) {
            String accuracyValue = matcher.group(1);
            return "Accuracy: " + accuracyValue;
        } else {
            return "";
        }
    }

    //Solo es para que se vea bonito la pre evolución jaja
    public static class CirclePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int diameter = Math.min(getWidth(), getHeight());
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;

            Graphics2D g2d = (Graphics2D) g;
            float thick = 5.0f;
            g2d.setStroke(new BasicStroke(thick));
            Color colorOutline = new Color(20, 0, 20);
            g2d.setColor(colorOutline);
            g2d.drawOval(x, y, diameter, diameter);
            g2d.setColor(Color.WHITE);
            g2d.fillOval(x, y, diameter, diameter);
        }
    }

}

