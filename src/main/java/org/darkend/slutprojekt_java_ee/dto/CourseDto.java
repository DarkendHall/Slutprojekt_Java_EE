package org.darkend.slutprojekt_java_ee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    private Long id;

    @NotEmpty
    @Size(min = 2)
    private String name;

    @NotEmpty
    @Valid
    private List<StudentDto> students;

    @NotNull
    @Valid
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
        return "CourseDto{" +
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
        CourseDto courseDto = (CourseDto) o;
        return Objects.equals(id, courseDto.id) && Objects.equals(name,
                courseDto.name) && Objects.equals(students, courseDto.students) && Objects.equals(
                teacher, courseDto.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students, teacher);
    }
}
