package org.darkend.slutprojekt_java_ee.dto;

import java.util.List;
import java.util.Objects;

public class CourseDto {

    private Long id;
    private String name;
    private List<StudentDto> students;
    private TeacherDto teacher;

    public Long getId() {
        return id;
    }

    public CourseDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CourseDto setName(String name) {
        this.name = name;
        return this;
    }

    public List<StudentDto> getStudents() {
        return students;
    }

    public CourseDto setStudents(List<StudentDto> students) {
        this.students = students;
        return this;
    }

    public TeacherDto getTeacher() {
        return teacher;
    }

    public CourseDto setTeacher(TeacherDto teacher) {
        this.teacher = teacher;
        return this;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                ", teacher=" + teacher +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseDto courseDTO = (CourseDto) o;
        return Objects.equals(id, courseDTO.id) && Objects.equals(name,
                courseDTO.name) && Objects.equals(students, courseDTO.students) && Objects.equals(
                teacher, courseDTO.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students, teacher);
    }
}
