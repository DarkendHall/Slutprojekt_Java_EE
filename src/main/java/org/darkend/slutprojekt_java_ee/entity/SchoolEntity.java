package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class SchoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String city;

    @NotEmpty
    private String address;

    @OneToOne(targetEntity = PrincipalEntity.class)
    private PrincipalEntity principal;

    @NotNull
    @OneToMany
    private List<StudentEntity> students = new ArrayList<>();

    @NotNull
    @OneToMany
    private List<CourseEntity> courses = new ArrayList<>();

    @NotNull
    @ManyToMany
    private List<TeacherEntity> teachers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public SchoolEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SchoolEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SchoolEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public SchoolEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public PrincipalEntity getPrincipal() {
        return principal;
    }

    public SchoolEntity setPrincipal(PrincipalEntity principal) {
        this.principal = principal;
        return this;
    }

    public List<StudentEntity> getStudents() {
        return students;
    }

    public SchoolEntity setStudents(List<StudentEntity> students) {
        this.students = students;
        return this;
    }

    public List<CourseEntity> getCourses() {
        return courses;
    }

    public SchoolEntity setCourses(List<CourseEntity> courses) {
        this.courses = courses;
        return this;
    }

    public List<TeacherEntity> getTeachers() {
        return teachers;
    }

    public SchoolEntity setTeachers(List<TeacherEntity> teachers) {
        this.teachers = teachers;
        return this;
    }

    @Override
    public String toString() {
        return "SchoolEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", principal=" + principal +
                ", students=" + students +
                ", courses=" + courses +
                ", teachers=" + teachers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolEntity that = (SchoolEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name,
                that.name) && Objects.equals(city, that.city) && Objects.equals(address,
                that.address) && Objects.equals(principal, that.principal) && Objects.equals(students,
                that.students) && Objects.equals(courses, that.courses) && Objects.equals(teachers,
                that.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, address, principal, students, courses, teachers);
    }
}
