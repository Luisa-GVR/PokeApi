import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class PokemonAPI {
    private static final String BASE_API_URL = "https://pokeapi.co/api/v2/";

    static String[] getPokemonNames() {
        ArrayList<String> names = new ArrayList<>();
        try {
            URL url = new URL(BASE_API_URL + "pokemon?limit=1000");
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
            URL url = new URL(BASE_API_URL + "type");
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
            URL url = new URL(BASE_API_URL + "generation");
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
            URL url = new URL(BASE_API_URL + "egg-group");
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