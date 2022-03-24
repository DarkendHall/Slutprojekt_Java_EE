package org.darkend.slutprojekt_java_ee.dto;

import java.util.Objects;

public class RoleDto {

    private Long id;
    private String role;

    public Long getId() {
        return id;
    }

    public RoleDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRole() {
        return role;
    }

    public RoleDto setRole(String role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(id, roleDto.id) && Objects.equals(role, roleDto.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
