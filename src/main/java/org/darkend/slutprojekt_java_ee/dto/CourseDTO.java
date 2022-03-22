package org.darkend.slutprojekt_java_ee.dto;

import java.util.List;
import java.util.Objects;

public class CourseDTO {

    private Long id;
    private String name;
    private List<StudentDTO> students;
    private TeacherDTO teacher;

    public Long getId() {
        return id;
    }

    public CourseDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CourseDTO setName(String name) {
        this.name = name;
        return this;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public CourseDTO setStudents(List<StudentDTO> students) {
        this.students = students;
        return this;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public CourseDTO setTeacher(TeacherDTO teacher) {
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
        CourseDTO courseDTO = (CourseDTO) o;
        return Objects.equals(id, courseDTO.id) && Objects.equals(name,
                courseDTO.name) && Objects.equals(students, courseDTO.students) && Objects.equals(
                teacher, courseDTO.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students, teacher);
    }
}
