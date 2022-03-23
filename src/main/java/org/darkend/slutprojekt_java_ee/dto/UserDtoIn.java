package org.darkend.slutprojekt_java_ee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDtoIn {

    private Long id;
    private String username;
    private String password;
    private List<String> roles;

    public Long getId() {
        return id;
    }

    public UserDtoIn setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDtoIn setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDtoIn setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public UserDtoIn setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String toString() {
        return "UserDtoIn{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDtoIn userDtoIn = (UserDtoIn) o;
        return Objects.equals(id, userDtoIn.id) && Objects.equals(username,
                userDtoIn.username) && Objects.equals(password, userDtoIn.password) && Objects.equals(
                roles, userDtoIn.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, roles);
    }
}
