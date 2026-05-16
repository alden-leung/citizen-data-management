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

                String address =
                        fields.get(3)
                                .replace("\"", "")
                                .trim();

                int age =
                        Integer.parseInt(fields.get(4).trim());

                boolean resident =
                        fields.get(5)
                                .trim()
                                .equalsIgnoreCase("Resident");

                int district =
                        Integer.parseInt(fields.get(6).trim());

                char gender =
                        fields.get(7)
                                .trim()
                                .equalsIgnoreCase("Male")
                                ? 'M'
                                : 'F';

                Citizen citizen = new Citizen(
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

        } // end of try

        catch (IOException e) {

            System.out.println(
                    "Error reading file: "
                            + e.getMessage()
            );

        }

        return citizens;

    } // end of CSV File Convert


    // CSV Parsing
    private static List<String> parseCSVLine(String line) {

        List<String> fields = new ArrayList<>();

        StringBuilder current = new StringBuilder();

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


    // Count Male Citizens
    public static long countMales(List<Citizen> citizens) {

        return citizens.stream()
                .filter(c -> c.getGender() == 'M')
                .count();

    } // end of countMales


    // Count Female Citizens
    public static long countFemales(List<Citizen> citizens) {

        return citizens.stream()
                .filter(c -> c.getGender() == 'F')
                .count();

    } // end of countFemales


    // Count Residents
    public static long countResidents(List<Citizen> citizens) {

        return citizens.stream()
                .filter(Citizen::isResident)
                .count();

    } // end of countResidents


    // Count Senior Citizens
    public static long countSeniorCitizens(List<Citizen> citizens) {

        return citizens.stream()
                .filter(c -> c.getAge() >= 60)
                .count();

    } //countSeniorCitizens


    // Average Age
    public static double getAverageAge(List<Citizen> citizens) {

        return citizens.stream()
                .mapToInt(Citizen::getAge)
                .average()
                .orElse(0);

    } //getAverageAge


    // Youngest Citizen
    public static Citizen getYoungestCitizen(List<Citizen> citizens) {

        return citizens.stream()
                .min(Comparator.comparingInt(Citizen::getAge))
                .orElse(null);

    } //getYoungestCitizen


    // Oldest Citizen
    public static Citizen getOldestCitizen(List<Citizen> citizens) {

        return citizens.stream()
                .max(Comparator.comparingInt(Citizen::getAge))
                .orElse(null);

    } //getOldestCitizen


    // Population Per District
    public static Map<Integer, Long> getPopulationPerDistrict(List<Citizen> citizens) {

        return citizens.stream()
                .collect(Collectors.groupingBy(
                        Citizen::getDistrict,
                        Collectors.counting()
                ));

    } // end of getPopulationPerDistrict

}