package org.darkend.slutprojekt_java_ee.dto;

import java.util.Objects;

public class PrincipalDTO implements NameDTO {

    private Long id;
    private String fullName;

    public Long getId() {
        return id;
    }

    public PrincipalDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public PrincipalDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Override
    public String toString() {
        return "PrincipalDTO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalDTO that = (PrincipalDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }
}
