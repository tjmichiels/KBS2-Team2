import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CreateFile {

    public static void createTSPFile(String name, ArrayList<int[]> listOfCoordinates) {
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
                fileInformation += "NODE_COORD_SECTION" + "\n";
                for (int i = 0; i < listOfCoordinates.size(); i++) { // ArrayList doorlopen
                    int[] coords = listOfCoordinates.get(i);
                    fileInformation += i + 1 + " " + coords[0] + " " + coords[1] + "\n";
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
