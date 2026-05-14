package prog2.finalgroup;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MyProgramUtility {

    // CSV File Convert
    public static List<Citizen> loadCitizens(String filePath) {

        List<Citizen> citizens = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                List<String> fields = parseCSVLine(line);

                if (fields.size() < 8) {
                    continue;
                }

                String firstName = fields.get(0).trim();
                String lastName = fields.get(1).trim();
                String fullName = firstName + " " + lastName;
                String email = fields.get(2).trim();
                String address = fields.get(3).replace("\"", "").trim();
                int age = Integer.parseInt(fields.get(4).trim());
                boolean resident = fields.get(5).trim().equalsIgnoreCase("Resident");
                int district = Integer.parseInt(fields.get(6).trim());
                char gender = fields.get(7).trim().equalsIgnoreCase("Male") ? 'M' : 'F';

                Citizen citizen = new Citizen (
                        fullName,
                        email,
                        address,
                        age,
                        resident,
                        district,
                        gender
                );

                citizens.add(citizen);

            } // end of while

        } // end of try loop
        catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return citizens;
    } // end of CSV File Convert

    // CSV Parsing
    private static List<String> parseCSVLine(String line) {

        List<String> fields = new ArrayList<>();
        StringBuilder current  = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                insideQuotes = !insideQuotes;
            }

            else if (ch == ',' && !insideQuotes) {
                fields.add(current.toString());
                current.setLength(0);
            }

            else {
                current.append(ch);
            }
        } // end of for

        fields.add(current.toString());
        return fields;
    } // end of CSV Parsing

} // end of MyProgramUtility
