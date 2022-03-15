package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
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

    @ManyToMany(fetch = javax.persistence.FetchType.LAZY, targetEntity = SubjectEntity.class)
    private List<SubjectEntity> subject;

    @NotEmpty
    @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public List<SubjectEntity> getSubject() {
        return subject;
    }

    public TeacherEntity setSubject(List<SubjectEntity> subject) {
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
