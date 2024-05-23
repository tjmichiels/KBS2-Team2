import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CreateFile {

    public static void createTSPFile(String name, ArrayList<Object[]> postcodesEnHuisnummers) {
        ArrayList<double[]> listOfCoordinates = new ArrayList<>();

        ArrayList<String> postcodes = new ArrayList<>();
        ArrayList<String> huisnummers = new ArrayList<>();
        ArrayList<String> straten = new ArrayList<>();
        ArrayList<String> steden = new ArrayList<>();
        ArrayList<String> provincies = new ArrayList<>();
        for (int i = 0; i < postcodesEnHuisnummers.size(); i++) {
            String postcode = (String) postcodesEnHuisnummers.get(i)[0];
            int huisnummer = (int) postcodesEnHuisnummers.get(i)[1];
            JSONArray coordinates = PostcodeAPI.findCoordinates(postcode, huisnummer);
            double longitude = coordinates.getDouble(0);
            double latitude = coordinates.getDouble(1);
            listOfCoordinates.add(new double[]{longitude, latitude});


            ArrayList<String> addressInfo = PostcodeAPI.getAddressInfo(postcode, huisnummer);
            postcodes.add(addressInfo.get(0));
            huisnummers.add(addressInfo.get(1));
            straten.add(addressInfo.get(2));
            steden.add(addressInfo.get(3));
            provincies.add(addressInfo.get(4));
        }

        String filePath = "data/" + name + ".tsp";
        try {
            File myObj = new File(filePath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                String fileInformation = "";
                fileInformation += "NAME: " + name + "\n";
                fileInformation += "COMMENT: Gegenereerd door de applicatie van KBS team 2" + "\n";
                fileInformation += "TYPE: TSP" + "\n";
                fileInformation += "DIMENSION: " + listOfCoordinates.size() + "\n";
                fileInformation += "EDGE_WEIGHT_TYPE: EUC_2D" + "\n";
                fileInformation += "NODE_COORD_SECTION" + "\n\n";
                for (int i = 0; i < listOfCoordinates.size(); i++) { // ArrayList doorlopen
                    double[] coords = listOfCoordinates.get(i);
                    fileInformation += i + 1 + " " + coords[0] + " " + coords[1] +
                            "\n# " + straten.get(i) + " " + huisnummers.get(i) + ", " + postcodes.get(i) + " " +
                            steden.get(i) + " " + provincies.get(i);
                    if (i != listOfCoordinates.size() - 1) {
                        fileInformation += "\n\n";
                    }
                }
                WriteToFile(filePath, fileInformation);
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

//    public static void createTSPFile(String name, ArrayList<double[]> listOfCoordinates) {
//        String filePath = "data/" + name + ".tsp";
//        try {
//            File myObj = new File(filePath);
//            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
//                String fileInformation = "";
//                fileInformation += "NAME: " + name + "\n";
//                fileInformation += "COMMENT: Gegenereerd door de applicatie van KBS team 2" + "\n";
//                fileInformation += "TYPE: TSP" + "\n";
//                fileInformation += "DIMENSION: " + listOfCoordinates.size() + "\n";
//                fileInformation += "EDGE_WEIGHT_TYPE: EUC_2D" + "\n";
//                fileInformation += "NODE_COORD_SECTION" + "\n";
//                for (int i = 0; i < listOfCoordinates.size(); i++) { // ArrayList doorlopen
//                    double[] coords = listOfCoordinates.get(i);
//                    fileInformation += i + 1 + " " + coords[0] + " " + coords[1] + "\n";
//                }
//                WriteToFile(filePath, fileInformation);
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }

    private static void WriteToFile(String filePath, String text) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(text);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
