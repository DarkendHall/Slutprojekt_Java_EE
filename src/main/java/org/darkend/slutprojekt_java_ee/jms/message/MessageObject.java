package org.darkend.slutprojekt_java_ee.jms.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class MessageObject implements Serializable {

    private UUID id;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;

    public MessageObject() {
    }

    public MessageObject(UUID id, String message, LocalDateTime localDateTime) {
        this.id = id;
        this.message = message;
        this.localDateTime = localDateTime;
    }

    public UUID getId() {
        return id;
    }

    public MessageObject setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageObject setMessage(String message) {
        this.message = message;
        return this;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public MessageObject setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        return this;
    }

    @Override
    public String toString() {
        return "MessageObject{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
