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







        // TEKENEN SCHERM
        Scherm s = new Scherm(databasenaam);

        // Deze graag als commentaar laten want we hebben beperkte API calls
        s.setListOfCoordinates(CreateFile.getListOfCoordinates(postcodesEnHuisnummers));
    }
}