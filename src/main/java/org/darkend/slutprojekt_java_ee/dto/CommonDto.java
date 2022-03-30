package org.darkend.slutprojekt_java_ee.dto;

import org.darkend.slutprojekt_java_ee.exceptions.InvalidNameException;

public class CommonDto {

    public static String generateFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    public static String generateFirstName(NameDto dto) {
        String[] names = dto.getFullName()
                .split(" ");
        checkLength(names);
        return names[0];
    }

    public static String generateLastName(NameDto dto) {
        String[] names = dto.getFullName()
                .split(" ");
        checkLength(names);
        return names[1];
    }

    private static void checkLength(String[] names) {
        if (names.length != 2)
            throw new InvalidNameException("Please provide first- and lastname");
    }
}
