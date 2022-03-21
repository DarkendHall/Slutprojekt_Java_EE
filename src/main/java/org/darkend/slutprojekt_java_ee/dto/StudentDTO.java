package org.darkend.slutprojekt_java_ee.dto;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StudentDTO implements NameDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public StudentDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public StudentDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public StudentDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public StudentDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(fullName,
                that.fullName) && Objects.equals(email, that.email) && Objects.equals(phoneNumber,
                that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, phoneNumber);
    }
}
