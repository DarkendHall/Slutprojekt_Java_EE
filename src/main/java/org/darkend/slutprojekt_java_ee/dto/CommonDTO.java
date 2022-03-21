package org.darkend.slutprojekt_java_ee.dto;

public class CommonDTO {

    public static String generateFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    public static String generateFirstName(NameDTO dto) {
        String[] names = dto.getFullName()
                .split(" ");
        checkLength(names);
        return names[0];

    }

    public static String generateLastName(NameDTO dto) {
        String[] names = dto.getFullName()
                .split(" ");
        checkLength(names);
        return names[1];
    }

    private static void checkLength(String[] names) {
        if (names.length != 2)
            throw new IllegalArgumentException("Please provide first- and lastname");
    }
}
