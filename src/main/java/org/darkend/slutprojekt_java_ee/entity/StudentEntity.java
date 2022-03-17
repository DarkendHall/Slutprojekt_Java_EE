package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
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

    @NotNull
    private String phoneNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private SchoolEntity school;

    @ManyToMany(mappedBy = "students", cascade = CascadeType.PERSIST)
    private Set<CourseEntity> courses = new HashSet<>();

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

    public StudentEntity setCourses(Set<CourseEntity> course) {
        this.courses = course;
        return this;
    }

    public Set<CourseEntity> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName,
                that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email,
                that.email) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(school,
                that.school) && Objects.equals(courses, that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phoneNumber, school, courses);
    }
}
