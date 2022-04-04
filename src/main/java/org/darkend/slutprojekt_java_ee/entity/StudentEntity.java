package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class StudentEntity extends PersonEntity {

    @NotEmpty
    private String email;

    @NotNull
    private String phoneNumber;

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

    @Override
    public StudentEntity setId(Long id) {
        return (StudentEntity) super.setId(id);
    }

    @Override
    public StudentEntity setFirstName(String firstName) {
        return (StudentEntity) super.setFirstName(firstName);
    }

    @Override
    public StudentEntity setLastName(String lastName) {
        return (StudentEntity) super.setLastName(lastName);
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StudentEntity that = (StudentEntity) o;
        return Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, phoneNumber);
    }
}
