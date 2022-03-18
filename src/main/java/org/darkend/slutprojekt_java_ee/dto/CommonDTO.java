package org.darkend.slutprojekt_java_ee.dto;

public class CommonDTO {

    public static String generateFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }

    public static String generateFirstName(StudentDTO dto) {
        String[] names = dto.getFullName()
                .split(" ");
        try {
            return names[0];
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Please provide first- and lastname");
        }
    }

    public static String generateLastName(StudentDTO dto) {
        String[] names = dto.getFullName()
                .split(" ");
        try {
            return names[1];
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Please provide first- and lastname");
        }
    }
}
