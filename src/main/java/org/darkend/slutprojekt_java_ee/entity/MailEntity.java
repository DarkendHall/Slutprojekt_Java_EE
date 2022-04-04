package org.darkend.slutprojekt_java_ee.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class MailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String msg;
    @OneToMany(cascade = CascadeType.ALL)
    private List<RecipientEntity> recipients = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public MailEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public MailEntity setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<RecipientEntity> getRecipients() {
        return recipients;
    }

    public MailEntity setRecipients(List<RecipientEntity> recipients) {
        this.recipients = recipients;
        return this;
    }

    @Override
    public String toString() {
        return "MailEntity{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", recipients=" + recipients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailEntity that = (MailEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(msg, that.msg) && Objects.equals(
                recipients, that.recipients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, msg, recipients);
    }
}
