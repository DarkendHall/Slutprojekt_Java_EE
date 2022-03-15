package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2)
    private String firstName;

    @NotEmpty
    @Size(min = 2)
    private String lastName;

    @NotEmpty
    private String email;

    @Size(min = 10, max = 10)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolEntity school;

    @NotEmpty
    @OneToMany(mappedBy = "students", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseEntity> course = new HashSet<>();

    public Long getId() {
        return id;
    }

    public StudentEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public StudentEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public StudentEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public StudentEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public StudentEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public SchoolEntity getSchool() {
        return school;
    }

    public StudentEntity setSchool(SchoolEntity school) {
        this.school = school;
        return this;
    }

    public StudentEntity setCourse(Set<CourseEntity> course) {
        this.course = course;
        return this;
    }

    public Set<CourseEntity> getCourse() {
        return course;
    }
}