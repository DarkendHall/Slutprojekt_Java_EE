package org.darkend.slutprojekt_java_ee.dto;

import java.util.Objects;

public class PrincipalDTO {

    private Long id;
    private String name;
    private String school;

    public Long getId() {
        return id;
    }

    public PrincipalDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PrincipalDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getSchool() {
        return school;
    }

    public PrincipalDTO setSchool(String school) {
        this.school = school;
        return this;
    }

    @Override
    public String toString() {
        return "PrincipalDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", school='" + school + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrincipalDTO that = (PrincipalDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name,
                that.name) && Objects.equals(school, that.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, school);
    }
}
