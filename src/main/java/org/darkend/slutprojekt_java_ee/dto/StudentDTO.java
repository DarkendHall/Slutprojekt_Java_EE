package org.darkend.slutprojekt_java_ee.dto;


import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StudentDTO {

    private String name;
    private String email;
    private String phoneNumber;
    private String school;

    public String getName() {
        return name;
    }

    public StudentDTO setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public StudentDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public StudentDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public StudentDTO setSchool(String school) {
        this.school = school;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(email,
                that.email) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(school,
                that.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phoneNumber, school);
    }
}
