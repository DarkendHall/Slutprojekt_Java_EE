package org.darkend.slutprojekt_java_ee.dto;

import java.util.Objects;
import java.util.Set;

public class TeacherDTO {

    private String name;
    private Set<String> courses;

    public String getName() {
        return name;
    }

    public TeacherDTO setName(String firstName, String lastName) {
        this.name = firstName + " " + lastName;
        return this;
    }

    public Set<String> getCourses() {
        return courses;
    }

    public TeacherDTO setCourses(Set<String> courses) {
        this.courses = courses;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherDTO that = (TeacherDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, courses);
    }
}
