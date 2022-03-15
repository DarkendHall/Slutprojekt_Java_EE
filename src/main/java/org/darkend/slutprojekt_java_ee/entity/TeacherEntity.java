package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2)
    private String firstName;

    @NotEmpty
    @Size(min = 2)
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<SubjectEntity> subject;

    @NotEmpty
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseEntity> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public TeacherEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public TeacherEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public TeacherEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<SubjectEntity> getSubject() {
        return subject;
    }

    public TeacherEntity setSubject(Set<SubjectEntity> subject) {
        this.subject = subject;
        return this;
    }

    public Set<CourseEntity> getCourses() {
        return courses;
    }

    public TeacherEntity setCourses(Set<CourseEntity> courses) {
        this.courses = courses;
        return this;
    }
}
