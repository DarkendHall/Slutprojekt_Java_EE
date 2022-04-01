package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.Entity;

@Entity
public class TeacherEntity extends PersonEntity {

    @Override
    public TeacherEntity setId(Long id) {
        return (TeacherEntity) super.setId(id);
    }

    @Override
    public TeacherEntity setFirstName(String firstName) {
        return (TeacherEntity) super.setFirstName(firstName);
    }

    @Override
    public TeacherEntity setLastName(String lastName) {
        return (TeacherEntity) super.setLastName(lastName);
    }

    @Override
    public String toString() {
        return "TeacherEntity{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TeacherEntity)
            return super.equals(o);
        else
            return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
