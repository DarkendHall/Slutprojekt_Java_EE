package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Student> students = new HashSet<>();

    public Set<Student> getStudents() {
        return students;
    }

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

    public SchoolEntity setStudents(Set<Student> students) {
        this.students = students;
        return this;
    }
}
