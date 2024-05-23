import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoordinateFinder {
    // We maken gebruik van Postcode API
    private static final String API_URL = "https://api.postcodeapi.nu/v3/lookup/";
    private static final String API_KEY = "qVP6XG4nd16lDfdUNQA3zaaktUZ1o7AlKxrz3Kv6";

    public static JSONArray findCoordinates(String postcode, int number) {

        try {
            // Construct the full URL
            URL url = new URL(API_URL + postcode + "/" + number);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Set the request header with the API key
            connection.setRequestProperty("X-Api-Key", API_KEY);

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // If the response code is 200 (HTTP.OK), read the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                // Read the response line by line
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Extract the coordinates
                if (jsonResponse.has("location")) {
                    JSONObject location = jsonResponse.getJSONObject("location");
                    if (location.has("coordinates")) {
                        JSONArray coordinates = location.getJSONArray("coordinates");
                        double longitude = coordinates.getDouble(0);
                        double latitude = coordinates.getDouble(1);
                        //System.out.println("Coordinates: Longitude = " + longitude + ", Latitude = " + latitude);
                        return coordinates;
                    } else {
                        System.out.println("Coordinates not found in the response.");
                    }
                } else {
                    System.out.println("Location not found in the response.");
                }

                // Print the response
                System.out.println("Response: " + response);
            } else {
                System.out.println("GET request failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
