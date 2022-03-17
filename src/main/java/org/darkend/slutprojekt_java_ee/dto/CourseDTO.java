package org.darkend.slutprojekt_java_ee.dto;

import java.util.Objects;
import java.util.Set;

public class CourseDTO {

    private String name;
    private Set<String> students;
    private String teacher;
    private String school;

    public String getName() {
        return name;
    }

    public CourseDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Set<String> getStudents() {
        return students;
    }

    public CourseDTO setStudents(Set<String> students) {
        this.students = students;
        return this;
    }

    public String getTeacher() {
        return teacher;
    }

    public CourseDTO setTeacher(String teacher) {
        this.teacher = teacher;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public CourseDTO setSchool(String school) {
        this.school = school;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDTO courseDTO = (CourseDTO) o;
        return Objects.equals(name, courseDTO.name) && Objects.equals(students,
                courseDTO.students) && Objects.equals(teacher, courseDTO.teacher) && Objects.equals(
                school, courseDTO.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, students, teacher, school);
    }
}
