import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class PokeInfo {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getPokemonType(String pokemonName) {
        try {
            String apiURL = properties.getProperty("base_api_url") + "pokemon/" + pokemonName;
            URL url = new URL(apiURL);
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
            String apiURL = properties.getProperty("base_api_url") + "pokemon-species/" + pokemonName;
            URL url = new URL(apiURL);
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
            String apiURL = properties.getProperty("base_api_url") + "pokemon-species/" + pokemonName;
            URL url = new URL(apiURL);
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
            String apiURL = properties.getProperty("base_api_url") + "pokemon/" + pokemonName;
            URL url = new URL(apiURL);
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
            String apiURL = properties.getProperty("base_api_url") + "pokemon/" + pokemonName;
            URL url = new URL(apiURL);
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
            String apiURL = properties.getProperty("base_api_url") + "pokemon/" + pokemonName;
            URL url = new URL(apiURL);
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

    public static String getMoveProperties(String moveName) {
        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(input);

            URL url = new URL(properties.getProperty("base_api_url") + "move/" + moveName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);

            String type = jsonObject.getAsJsonObject("type").get("name").getAsString();
            String power = jsonObject.has("power") ? String.valueOf(jsonObject.get("power").getAsInt()) : "";
            String accuracy = jsonObject.has("accuracy") ? String.valueOf(jsonObject.get("accuracy").getAsInt()) : "";

            return "Type: " + type + ", Power: " + power + ", Accuracy: " + accuracy;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getMoveDescription(String moveName) {
        try {
            Properties properties = new Properties();
            try (InputStream input = PokeInfo.class.getClassLoader().getResourceAsStream("config.properties")) {
                properties.load(input);
            }

            String moveURL = properties.getProperty("base_api_url") + "move/" + moveName.toLowerCase().trim();

            URL url = new URL(moveURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = new Gson().fromJson(response.toString(), JsonObject.class);
            String description = jsonObject.getAsJsonArray("effect_entries")
                    .get(0).getAsJsonObject()
                    .get("effect").getAsString();

            return description;
        } catch (Exception e) {
            e.printStackTrace();
            return "Descripción no disponible";
        }
    }

}