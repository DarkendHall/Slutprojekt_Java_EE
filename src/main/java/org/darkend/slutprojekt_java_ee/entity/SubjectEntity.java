package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2)
    private String name;

    @ManyToMany(fetch = javax.persistence.FetchType.LAZY, targetEntity = TeacherEntity.class)
    private List<TeacherEntity> teacher;

    public Long getId() {
        return id;
    }

    public SubjectEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SubjectEntity setName(String name) {
        this.name = name;
        return this;
    }

    public List<TeacherEntity> getTeacher() {
        return teacher;
    }

    public SubjectEntity setTeacher(List<TeacherEntity> teacher) {
        this.teacher = teacher;
        return this;
    }
}
