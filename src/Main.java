import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
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
        // Deze graag als commentaar laten want we hebben beperkte API calls
        //CreateFile.createTSPFile("route", postcodesEnHuisnummers);







        // TEKENEN SCHERM
        Scherm s = new Scherm("test");

        // Deze graag als commentaar laten want we hebben beperkte API calls
        //s.setListOfCoordinates(CreateFile.getListOfCoordinates(postcodesEnHuisnummers));
    }
}