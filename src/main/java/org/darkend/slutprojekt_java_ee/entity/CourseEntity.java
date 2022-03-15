package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2)
    private String name;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Student.class)
    private Student students;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TeacherEntity.class)
    private TeacherEntity teacherEntity;

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

    public Student getStudents() {
        return students;
    }

    public CourseEntity setStudents(Student students) {
        this.students = students;
        return this;
    }
}
