import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
    public static void main(String[] args) {
        ArrayList<Object[]> postcodesEnHuisnummers = new ArrayList<>();
        postcodesEnHuisnummers.add(new Object[]{"3605KW", 1180});
        postcodesEnHuisnummers.add(new Object[]{"3995DP", 130});
        postcodesEnHuisnummers.add(new Object[]{"3543AJ", 80});
        postcodesEnHuisnummers.add(new Object[]{"1211DZ", 1});
        postcodesEnHuisnummers.add(new Object[]{"1211CB", 10});
        postcodesEnHuisnummers.add(new Object[]{"1217ET", 62});
        postcodesEnHuisnummers.add(new Object[]{"1315HD", 5});
        postcodesEnHuisnummers.add(new Object[]{"1315BP", 36});
        postcodesEnHuisnummers.add(new Object[]{"3621ZA", 1});

//        ArrayList<double[]> coordinatesList = new ArrayList<>();
//        for (int i = 0; i < postcodesEnHuisnummers.size(); i++) {
//            String postcode = (String) postcodesEnHuisnummers.get(i)[0];
//            int huisnummer = (int) postcodesEnHuisnummers.get(i)[1];
//            JSONArray coordinates = PostcodeAPI.findCoordinates(postcode, huisnummer);
//            double longitude = coordinates.getDouble(0);
//            double latitude = coordinates.getDouble(1);
//            coordinatesList.add(new double[]{longitude, latitude});
//        }

        // IMPLEMENTATIE POSTCODE API
//        JSONArray coordinates = PostcodeAPI.findCoordinates("3608TH", 72);
//        double longitude = coordinates.getDouble(0);
//        double latitude = coordinates.getDouble(1);
//        ArrayList<String> addressInfo = PostcodeAPI.getAddressInfo("3608TH", 72);
//        System.out.println(addressInfo);

//        ArrayList<int[]> coordinatesList = new ArrayList<>();
//
//        coordinatesList.add(new int[]{1, 2});
//        coordinatesList.add(new int[]{3, 4});
//        coordinatesList.add(new int[]{5, 6});

        CreateFile.createTSPFile("route", postcodesEnHuisnummers);








        // IMPLEMENTATIE LIN KERNIGHAN
        File folder = new File("data/");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String name = listOfFiles[i].getName();
            if (listOfFiles[i].isFile() && name.substring(name.length() - 3).equalsIgnoreCase("tsp")) {
                System.out.println("  [" + i + "] " + listOfFiles[i].getName());
            }
        }
        Scanner scanner = new Scanner(System.in);
        int idx;
        do {
            System.out.print("Select the dataset to test: ");
            idx = scanner.nextInt();
        } while(idx >= listOfFiles.length || idx < 0);
        // Read the file
        Interpreter in = new Interpreter(listOfFiles[idx]);
        // Create the instance of the problem
        LinKernighan lk = new LinKernighan(in.getCoordinates(), in.getIds());
        // Time keeping
        long start;
        start = System.currentTimeMillis();
        // Shpw the results even if shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.printf("The solution took: %dms\n", System.currentTimeMillis()-start);
                System.out.println("The solution is: ");
                System.out.println(lk);
            }
        });
        lk.runAlgorithm();



        // TEKENEN SCHERM
//        Scherm s = new Scherm("routebepaling");
    }
}