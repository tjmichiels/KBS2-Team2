import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PostcodeAPI {
    // We maken gebruik van Postcode API
    private static final String API_URL = "https://api.postcodeapi.nu/v3/lookup/";
    private static final String API_KEY = "qVP6XG4nd16lDfdUNQA3zaaktUZ1o7AlKxrz3Kv6";

    public static JSONArray findCoordinates(String postcode, int number) {
        try {
            URL url = new URL(API_URL + postcode + "/" + number);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", API_KEY);

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

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

                System.out.println("Response: " + response);
            } else {
                System.out.println("GET request failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getAddressInfo(String postcode, int number) {
        ArrayList<String> list = new ArrayList<>();
        try {
            URL url = new URL(API_URL + postcode + "/" + number);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", API_KEY);
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

                list.add(jsonResponse.getString("postcode"));
                list.add(String.valueOf(jsonResponse.getInt("number")));
                list.add(jsonResponse.getString("street"));
                list.add(jsonResponse.getString("city"));
                list.add(jsonResponse.getString("province"));

                //System.out.println("Response: " + response);
            } else {
                System.out.println("GET request failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
