package org.darkend.slutprojekt_java_ee.dto;

import java.util.Objects;

public class SchoolDTO {

    private String name;
    private String city;
    private String address;
    private String principal;

    public String getName() {
        return name;
    }

    public SchoolDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SchoolDTO setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public SchoolDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPrincipal() {
        return principal;
    }

    public SchoolDTO setPrincipal(String principal) {
        this.principal = principal;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolDTO schoolDTO = (SchoolDTO) o;
        return Objects.equals(name, schoolDTO.name) && Objects.equals(city,
                schoolDTO.city) && Objects.equals(address, schoolDTO.address) && Objects.equals(
                principal, schoolDTO.principal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, address, principal);
    }
}
