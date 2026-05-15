/*
This Class is just for testing if the Data CSV is loading. It will read the
data.csv file using the MyProgramUtility class, and displays the loaded data.
 */

package prog2.finalgroup;

import java.util.List;

public class TestDataLoader {

    public static void main(String[] args) {

        String filePath = "res/data.csv";

        List<Citizen> citizens = MyProgramUtility.loadCitizens(filePath);

        System.out.println("Total Citizens Loaded: " + citizens.size());

        System.out.println();

        for (Citizen citizen : citizens) {
            System.out.println(citizen);
        }

    } // end of main

} // end of TestDataLoader
