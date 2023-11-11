import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PokeInfo {
    public String getPokemonType(String pokemonName) {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + pokemonName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);

            JsonArray types = jsonObject.getAsJsonArray("types");
            String primaryType = types.get(0).getAsJsonObject().getAsJsonObject("type").get("name").getAsString();
            String secondaryType = (types.size() > 1) ? types.get(1).getAsJsonObject().getAsJsonObject("type").get("name").getAsString() : "None";

            if (secondaryType.equals("None")) {
                return "Primary Type: " + primaryType;
            } else {
                return "Primary Type: " + primaryType + ", Secondary Type: " + secondaryType;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    public String getPokemonEggGroup(String pokemonName) {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon-species/" + pokemonName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);
            JsonArray eggGroups = jsonObject.getAsJsonArray("egg_groups");

            if (eggGroups.size() > 0) {
                String eggGroup = eggGroups.get(0).getAsJsonObject().get("name").getAsString();
                return "Egg Group: " + eggGroup;
            } else {
                return "No defined Egg Group";
            }
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public String getPokemonGeneration(String pokemonName) {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon-species/" + pokemonName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);
            JsonObject generationInfo = jsonObject.getAsJsonObject("generation");
            String generationName = generationInfo.get("name").getAsString();

            return "Generation: " + generationName;
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public String getPokemonAbilities(String pokemonName) {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + pokemonName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);
            JsonArray abilities = jsonObject.getAsJsonArray("abilities");
            StringBuilder abilitiesList = new StringBuilder();

            for (JsonElement element : abilities) {
                JsonObject ability = element.getAsJsonObject().getAsJsonObject("ability");
                String abilityName = ability.get("name").getAsString();
                abilitiesList.append(abilityName).append(", ");
            }

            return "Abilities: " + abilitiesList.toString();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public String getPokemonMoves(String pokemonName) {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + pokemonName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);
            JsonArray moves = jsonObject.getAsJsonArray("moves");
            StringBuilder movesList = new StringBuilder();

            for (JsonElement element : moves) {
                JsonObject move = element.getAsJsonObject().getAsJsonObject("move");
                String moveName = move.get("name").getAsString();
                movesList.append(moveName).append(", ");
            }

            return "Moves: " + movesList.toString();
        } catch (Exception e) {
            return "Unknown";
        }
    }
    public static String getPokemonArtwork(String pokemonName) {
        try {
            URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + pokemonName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);
            String frontDefaultImageURL = jsonObject.getAsJsonObject("sprites").get("other")
                    .getAsJsonObject().getAsJsonObject("official-artwork").get("front_default").getAsString();

            return frontDefaultImageURL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }



}