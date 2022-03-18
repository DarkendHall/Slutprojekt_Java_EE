package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class PrincipalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2)
    private String firstName;

    @NotEmpty
    @Size(min = 2)
    private String lastName;

    @OneToOne(fetch = FetchType.LAZY)
    private SchoolEntity school;

    public Long getId() {
        return id;
    }

    public PrincipalEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PrincipalEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PrincipalEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SchoolEntity getSchool() {
        return school;
    }

    public PrincipalEntity setSchool(SchoolEntity school) {
        this.school = school;
        return this;
    }

    @Override
    public String toString() {
        return "PrincipalEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", school=" + school +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalEntity that = (PrincipalEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName,
                that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(school,
                that.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, school);
    }
}
