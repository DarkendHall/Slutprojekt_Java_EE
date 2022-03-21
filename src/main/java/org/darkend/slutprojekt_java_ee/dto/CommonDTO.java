package org.darkend.slutprojekt_java_ee.dto;

public class CommonDTO {

    public static String generateFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    public static String generateFirstName(StudentDTO dto) {
        String[] names = dto.getFullName()
                .split(" ");
        if (names.length != 2)
            throw new IllegalArgumentException("Please provide first- and lastname");
        return names[0];

    }

    public static String generateLastName(StudentDTO dto) {
        String[] names = dto.getFullName()
                .split(" ");
        if (names.length != 2)
            throw new IllegalArgumentException("Please provide first- and lastname");
        return names[1];
    }

    private void throwException() {

    }
}
