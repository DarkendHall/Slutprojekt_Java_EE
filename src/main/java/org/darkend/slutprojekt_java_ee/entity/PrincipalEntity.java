package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.Entity;

@Entity
public class PrincipalEntity extends PersonEntity {

    @Override
    public PrincipalEntity setId(Long id) {
        return (PrincipalEntity) super.setId(id);
    }

    @Override
    public PrincipalEntity setFirstName(String firstName) {
        return (PrincipalEntity) super.setFirstName(firstName);
    }

    @Override
    public PrincipalEntity setLastName(String lastName) {
        return (PrincipalEntity) super.setLastName(lastName);
    }

    @Override
    public String toString() {
        return "PrincipalEntity{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PrincipalEntity)
            return super.equals(o);
        else
            return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
