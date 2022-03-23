package org.darkend.slutprojekt_java_ee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SchoolDto {

    private Long id;
    private String name;
    private String city;
    private String address;
    private PrincipalDto principal;
    private List<StudentDto> students;
    private List<CourseDto> courses;
    private List<TeacherDto> teachers;

    public Long getId() {
        return id;
    }

    public SchoolDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SchoolDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SchoolDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public SchoolDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public PrincipalDto getPrincipal() {
        return principal;
    }

    public SchoolDto setPrincipal(PrincipalDto principal) {
        this.principal = principal;
        return this;
    }

    public List<StudentDto> getStudents() {
        return students;
    }

    public SchoolDto setStudents(List<StudentDto> students) {
        this.students = students;
        return this;
    }

    public List<CourseDto> getCourses() {
        return courses;
    }

    public SchoolDto setCourses(List<CourseDto> courses) {
        this.courses = courses;
        return this;
    }

    public List<TeacherDto> getTeachers() {
        return teachers;
    }

    public SchoolDto setTeachers(List<TeacherDto> teachers) {
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
        SchoolDto schoolDTO = (SchoolDto) o;
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
