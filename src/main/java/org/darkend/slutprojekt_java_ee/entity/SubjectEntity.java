package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<TeacherEntity> teachers;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<CourseEntity> courses;

    public Long getId() {
        return id;
    }

    public SubjectEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SubjectEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Set<TeacherEntity> getTeachers() {
        return teachers;
    }

    public SubjectEntity setTeachers(Set<TeacherEntity> teachers) {
        this.teachers = teachers;
        return this;
    }

    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public SubjectEntity setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
        return this;
    }
}