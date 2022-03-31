package org.darkend.slutprojekt_java_ee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Service
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto implements NameDto {

    private Long id;

    @NotEmpty
    @Size(min = 5,
            message = "You need a first- and a lastname with a minimum of 2 characters each seperated by a space")
    private String fullName;

    @NotEmpty
    private String email;

    @NotNull
    private String phoneNumber;

    public Long getId() {
        return id;
    }

    public StudentDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public StudentDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public StudentDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public StudentDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String toString() {
        return "StudentDto{" +
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
        StudentDto that = (StudentDto) o;
        return Objects.equals(id, that.id) && Objects.equals(fullName,
                that.fullName) && Objects.equals(email, that.email) && Objects.equals(phoneNumber,
                that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, phoneNumber);
    }
}
