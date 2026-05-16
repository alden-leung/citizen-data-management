package prog2.finalgroup;

import java.util.*;
import java.util.stream.Collectors;

public class CitizenStatistics {

    // Count Male Citizens
    public static long countMales(List<Citizen> citizens) {

        return citizens.stream()
                .filter(c -> c.getGender() == 'M')
                .count();

    }


    // Count Female Citizens
    public static long countFemales(List<Citizen> citizens) {

        return citizens.stream()
                .filter(c -> c.getGender() == 'F')
                .count();

    }


    // Count Residents
    public static long countResidents(List<Citizen> citizens) {

        return citizens.stream()
                .filter(Citizen::isResident)
                .count();

    }


    // Count Senior Citizens
    public static long countSeniorCitizens(List<Citizen> citizens) {

        return citizens.stream()
                .filter(c -> c.getAge() >= 60)
                .count();

    }


    // Average Age
    public static double getAverageAge(List<Citizen> citizens) {

        return citizens.stream()
                .mapToInt(Citizen::getAge)
                .average()
                .orElse(0);

    }


    // Youngest Citizen
    public static Citizen getYoungestCitizen(List<Citizen> citizens) {

        return citizens.stream()
                .min(Comparator.comparingInt(Citizen::getAge))
                .orElse(null);

    }


    // Oldest Citizen
    public static Citizen getOldestCitizen(List<Citizen> citizens) {

        return citizens.stream()
                .max(Comparator.comparingInt(Citizen::getAge))
                .orElse(null);

    }


    // Population Per District
    public static Map<Integer, Long> getPopulationPerDistrict(List<Citizen> citizens) {

        return citizens.stream()
                .collect(Collectors.groupingBy(
                        Citizen::getDistrict,
                        Collectors.counting()
                ));

    }

}