package org.darkend.slutprojekt_java_ee.dto;

import java.util.Objects;

public class PrincipalDTO {

    private String name;
    private String school;

    public String getName() {
        return name;
    }

    public PrincipalDTO setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public PrincipalDTO setSchool(String school) {
        this.school = school;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalDTO that = (PrincipalDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(school, that.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, school);
    }
}
