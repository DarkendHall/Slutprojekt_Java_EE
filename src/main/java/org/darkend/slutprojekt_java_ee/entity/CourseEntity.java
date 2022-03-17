package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2)
    private String name;

    @NotEmpty
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<StudentEntity> students = new HashSet<>();

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    private TeacherEntity teacher;

    public Long getId() {
        return id;
    }

    public CourseEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CourseEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Set<StudentEntity> getStudents() {
        return students;
    }

    public CourseEntity setStudents(Set<StudentEntity> students) {
        this.students = students;
        return this;
    }

    public TeacherEntity getTeacher() {
        return teacher;
    }

    public CourseEntity setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name,
                that.name) && Objects.equals(students, that.students) && Objects.equals(teacher,
                that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, students, teacher);
    }
}
