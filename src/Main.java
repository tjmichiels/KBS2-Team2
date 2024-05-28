import org.json.JSONArray;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    public static void main(String[] args) {


        ArrayList<Object[]> postcodesEnHuisnummers = new ArrayList<>();
        String databasenaam = "test";
        String databaseurl = "jdbc:mysql://localhost:3306/" + databasenaam;
        try {
            JDBC dbconn = new JDBC(databaseurl, "root", "");
            String query = "SELECT klanten.postcode, klanten.huisnummer \n" +
                    "FROM klanten \n" +
                    "JOIN `order` ON klanten.klant_id = `order`.klant_id;";
//            String query = "SELECT klanten.postcode, klanten.huisnummer \n" +
//                    "FROM klanten \n" +
//                    "JOIN `order` ON klanten.klant_id = `order`.klant_id \n" +
//                    "WHERE `order`.delivered = 'No';";
            ResultSet rs = JDBC.executeSQL(dbconn.getConn(), query);
            int i = 0;
            while (rs.next()) {
                String postcode = rs.getString("postcode");
                int huisnr = rs.getInt("huisnummer");
                postcodesEnHuisnummers.add(new Object[]{postcode, huisnr});
                i++;
                wait(500);
            }
            dbconn.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        postcodesEnHuisnummers.add(new Object[]{"3605KW", 1180});
//        postcodesEnHuisnummers.add(new Object[]{"3995DP", 130});
//        postcodesEnHuisnummers.add(new Object[]{"3543AJ", 80});
//        postcodesEnHuisnummers.add(new Object[]{"1211DZ", 1});
//        postcodesEnHuisnummers.add(new Object[]{"1211CB", 10});
//        postcodesEnHuisnummers.add(new Object[]{"1217ET", 62});
//        postcodesEnHuisnummers.add(new Object[]{"1315HD", 5});
//        postcodesEnHuisnummers.add(new Object[]{"1315BP", 36});
//        postcodesEnHuisnummers.add(new Object[]{"3621ZA", 1});
        // Deze graag als commentaar laten want we hebben beperkte API calls
        //CreateFile.createTSPFile("route", postcodesEnHuisnummers);










        // IMPLEMENTATIE LIN KERNIGHAN
//        File folder = new File("data/");
//        File[] listOfFiles = folder.listFiles();
//        for (int i = 0; i < listOfFiles.length; i++) {
//            String name = listOfFiles[i].getName();
//            if (listOfFiles[i].isFile() && name.substring(name.length() - 3).equalsIgnoreCase("tsp")) {
//                System.out.println("  [" + i + "] " + listOfFiles[i].getName());
//            }
//        }
//        Scanner scanner = new Scanner(System.in);
//        int idx;
//        do {
//            System.out.print("Select the dataset to test: ");
//            idx = scanner.nextInt();
//        } while(idx >= listOfFiles.length || idx < 0);
//        // Read the file
//        Interpreter in = new Interpreter(listOfFiles[idx]);
//        // Create the instance of the problem
//        LinKernighan lk = new LinKernighan(in.getCoordinates(), in.getIds());
//        // Time keeping
//        long start;
//        start = System.currentTimeMillis();
//        // Shpw the results even if shutdown
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            public void run() {
//                System.out.printf("The solution took: %dms\n", System.currentTimeMillis()-start);
//                System.out.println("The solution is: ");
//                System.out.println(lk);
//            }
//        });
//        lk.runAlgorithm();

//        System.out.println(lk.getDistance()); // totale afstand
//        System.out.println(Arrays.toString(lk.tour)); // de int[] met de volgorde van de adressen



        // TEKENEN SCHERM
        Scherm s = new Scherm(databasenaam);

        // Deze graag als commentaar laten want we hebben beperkte API calls
        s.setListOfCoordinates(CreateFile.getListOfCoordinates(postcodesEnHuisnummers));
    }
}