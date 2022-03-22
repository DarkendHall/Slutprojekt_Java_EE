package org.darkend.slutprojekt_java_ee.dto;

import java.util.List;
import java.util.Objects;

public class SchoolDTO {

    private Long id;
    private String name;
    private String city;
    private String address;
    private PrincipalDTO principal;
    private List<StudentDTO> students;
    private List<CourseDTO> courses;
    private List<TeacherDTO> teachers;

    public Long getId() {
        return id;
    }

    public SchoolDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SchoolDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SchoolDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public SchoolDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public PrincipalDTO getPrincipal() {
        return principal;
    }

    public SchoolDTO setPrincipal(PrincipalDTO principal) {
        this.principal = principal;
        return this;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public SchoolDTO setStudents(List<StudentDTO> students) {
        this.students = students;
        return this;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public SchoolDTO setCourses(List<CourseDTO> courses) {
        this.courses = courses;
        return this;
    }

    public List<TeacherDTO> getTeachers() {
        return teachers;
    }

    public SchoolDTO setTeachers(List<TeacherDTO> teachers) {
        this.teachers = teachers;
        return this;
    }

    @Override
    public String
    toString() {
        return "SchoolDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", principal=" + principal +
                ", students=" + students +
                ", courses=" + courses +
                ", teachers=" + teachers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolDTO schoolDTO = (SchoolDTO) o;
        return Objects.equals(id, schoolDTO.id) && Objects.equals(name,
                schoolDTO.name) && Objects.equals(city, schoolDTO.city) && Objects.equals(address,
                schoolDTO.address) && Objects.equals(principal, schoolDTO.principal) && Objects.equals(
                students, schoolDTO.students) && Objects.equals(courses,
                schoolDTO.courses) && Objects.equals(teachers, schoolDTO.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, address, principal, students, courses, teachers);
    }
}
