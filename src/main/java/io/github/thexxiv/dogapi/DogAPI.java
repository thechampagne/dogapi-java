/*
 * Copyright 2022 XXIV
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.thexxiv.dogapi;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dog API client
 *
 * @author XXIV
 */
public class DogAPI {

    private static String getRequest(String endpoint) throws IOException {
        URL url = new URL(String.format("https://dog.ceo/api/%s", endpoint));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader reader;
        if (100 <= connection.getResponseCode() && connection.getResponseCode() <= 399) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        StringBuilder string = new StringBuilder();
        String output;
        while ((output = reader.readLine()) != null) {
            string.append(output);
        }
        return string.toString();
    }

    /**
     * DISPLAY SINGLE RANDOM IMAGE FROM ALL DOGS COLLECTION
     *
     * @return random dog image
     * @throws DogAPIException if something went wrong
     */
    public static String randomImage() {
        try {
            String response = getRequest("breeds/image/random");
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            return data.get("message").getAsString();
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }

    /**
     * DISPLAY MULTIPLE RANDOM IMAGES FROM ALL DOGS COLLECTION
     *
     * @param imagesNumber number of images
     * @return multiple random dog image `NOTE` ~ Max number returned is 50
     * @throws DogAPIException if something went wrong
     */
    public static List<String> multipleRandomImages(int imagesNumber) {
        try {
            String response = getRequest(String.format("breeds/image/random/%d", imagesNumber));
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            List<String> list = new ArrayList<>();
            for (JsonElement i : data.get("message").getAsJsonArray())
                list.add(i.getAsString());
            return list;
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }

    /**
     * RANDOM IMAGE FROM A BREED COLLECTION
     *
     * @param breed breed name
     * @return random dog image from a breed, e.g. hound
     * @throws DogAPIException if something went wrong
     */
    public static String randomImageByBreed(String breed) {
        try {
            String response = getRequest(String.format("breed/%s/images/random", breed.trim()));
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            return data.get("message").getAsString();
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }

    /**
     * MULTIPLE IMAGES FROM A BREED COLLECTION
     *
     * @param breed breed name
     * @param imagesNumber number of images
     * @return multiple random dog image from a breed, e.g. hound
     * @throws DogAPIException if something went wrong
     */
    public static List<String> multipleRandomImagesByBreed(String breed, long imagesNumber) {
        try {
            String response = getRequest(String.format("breed/%s/images/random/%d", breed.trim(), imagesNumber));
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            List<String> list = new ArrayList<>();
            for (JsonElement i : data.get("message").getAsJsonArray())
                list.add(i.getAsString());
            return list;
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }

    /**
     * ALL IMAGES FROM A BREED COLLECTION
     *
     * @param breed breed name
     * @return list all the images from a breed, e.g. hound
     * @throws DogAPIException if something went wrong
     */
    public static List<String> imagesByBreed(String breed) {
        try {
            String response = getRequest(String.format("breed/%s/images", breed.trim()));
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            List<String> list = new ArrayList<>();
            for (JsonElement i : data.get("message").getAsJsonArray())
                list.add(i.getAsString());
            return list;
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }

    /**
     * SINGLE RANDOM IMAGE FROM A SUB BREED COLLECTION
     *
     * @param breed breed name
     * @param subBreed sub_breed name
     * @return random dog image from a sub-breed, e.g. Afghan Hound
     * @throws DogAPIException if something went wrong
     */
    public static String randomImageBySubBreed(String breed, String subBreed) {
        try {
            String response = getRequest(String.format("breed/%s/%s/images/random", breed.trim(), subBreed.trim()));
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            return data.get("message").getAsString();
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }

    /**
     * MULTIPLE IMAGES FROM A SUB-BREED COLLECTION
     *
     * @param breed breed name
     * @param subBreed sub_breed name
     * @param imagesNumber number of images
     * @return multiple random dog images from a sub-breed, e.g. Afghan Hound
     * @throws DogAPIException if something went wrong
     */
    public static List<String> multipleRandomImagesBySubBreed(String breed, String subBreed, long imagesNumber) {
        try {
            String response = getRequest(String.format("breed/%s/%s/images/random/%d", breed.trim(), subBreed.trim(), imagesNumber));
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            List<String> list = new ArrayList<>();
            for (JsonElement i : data.get("message").getAsJsonArray())
                list.add(i.getAsString());
            return list;
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }

    /**
     * LIST ALL SUB-BREED IMAGES
     *
     * @param breed breed name
     * @param subBreed sub_breed name
     * @return list of all the images from the sub-breed
     * @throws DogAPIException if something went wrong
     */
    public static List<String> imagesBySubBreed(String breed, String subBreed) {
        try {
            String response = getRequest(String.format("breed/%s/%s/images", breed.trim(), subBreed.trim()));
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            List<String> list = new ArrayList<>();
            for (JsonElement i : data.get("message").getAsJsonArray())
                list.add(i.getAsString());
            return list;
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }

    /**
     * LIST ALL BREEDS
     *
     * @return map of all the breeds as keys and sub-breeds as values if it has
     * @throws DogAPIException if something went wrong
     */
    public static Map<String, List<String>> breedsList() {
        try {
            String response = getRequest("breeds/list/all");
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            Map<String, List<String>> map = new HashMap<>();
            for (Map.Entry<String,JsonElement> entry : data.get("message").getAsJsonObject().entrySet()) {
                String key = entry.getKey();
                List<String> valuesList = new ArrayList<>();
                for (JsonElement value : entry.getValue().getAsJsonArray())
                    valuesList.add(value.getAsString());
                map.put(key, valuesList);
            }
            return map;
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }

    /**
     * LIST ALL SUB-BREEDS
     *
     * @param breed breed name
     * @return list of all the sub-breeds from a breed if it has sub-breeds
     * @throws DogAPIException if something went wrong
     */
    public static List<String> subBreedsList(String breed) {
        try {
            String response = getRequest(String.format("breed/%s/list", breed.trim()));
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(response, JsonElement.class);
            JsonObject data = json.getAsJsonObject();
            if (!data.get("status").getAsString().equals("success")) {
                throw new Exception(data.get("message").getAsString());
            }
            List<String> list = new ArrayList<>();
            for (JsonElement i : data.get("message").getAsJsonArray())
                list.add(i.getAsString());
            if (list.isEmpty())
                throw new DogAPIException("the breed does not have sub-breeds");
            return list;
        } catch (JsonParseException ex) {
            throw new DogAPIException("Something went wrong while reading json: " + ex.getMessage());
        } catch (Exception ex) {
            throw new DogAPIException(ex.getMessage());
        }
    }
}