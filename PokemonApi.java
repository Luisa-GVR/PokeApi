import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.JOptionPane;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

class PokemonApi {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static String[] getPokemonNames() {
        ArrayList<String> names = new ArrayList<>();
        try {
            String apiURL = properties.getProperty("base_api_url") + properties.getProperty("pokemon_names_endpoint");
            URL url = new URL(apiURL);
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
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (var element : results) {
                JsonObject pokemon = element.getAsJsonObject();
                String name = pokemon.get("name").getAsString();
                names.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names.toArray(new String[0]);
    }

    static String[] getPokemonTypes() {
        ArrayList<String> types = new ArrayList<>();
        try {
            String apiURL = properties.getProperty("base_api_url") + properties.getProperty("pokemon_types_endpoint");
            URL url = new URL(apiURL);
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
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (var element : results) {
                JsonObject type = element.getAsJsonObject();
                String typeName = type.get("name").getAsString();
                types.add(typeName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return types.toArray(new String[0]);
    }

    static String[] getPokemonGenerations() {
        ArrayList<String> generations = new ArrayList<>();
        try {
            String apiURL = properties.getProperty("base_api_url") + properties.getProperty("generation_endpoint");
            URL url = new URL(apiURL);
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
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (var element : results) {
                JsonObject generation = element.getAsJsonObject();
                String generationName = generation.get("name").getAsString();
                generations.add(generationName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return generations.toArray(new String[0]);
    }


    static String[] getPokemonEggGroups() {
        ArrayList<String> eggGroups = new ArrayList<>();
        try {
            String apiURL = properties.getProperty("base_api_url") + properties.getProperty("egg_group_endpoint");
            URL url = new URL(apiURL);
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
            JsonArray results = jsonObject.getAsJsonArray("results");

            for (var element : results) {
                JsonObject eggGroup = element.getAsJsonObject();
                String eggGroupName = eggGroup.get("name").getAsString();
                eggGroups.add(eggGroupName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eggGroups.toArray(new String[0]);
    }


}