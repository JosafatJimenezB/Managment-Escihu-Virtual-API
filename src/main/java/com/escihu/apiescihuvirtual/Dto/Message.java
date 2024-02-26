package com.escihu.apiescihuvirtual.Dto;

import lombok.Builder;

@Builder
public class Message {
    public String message;
    public Object object;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", object=" + object +
                '}';
    }
}