package org.darkend.slutprojekt_java_ee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailDto {
    private Long id;
    private String msg;

    public Long getId() {
        return id;
    }

    public MailDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public MailDto setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return "MailDto{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailDto that = (MailDto) o;
        return Objects.equals(id, that.id) && Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, msg);
    }
}
