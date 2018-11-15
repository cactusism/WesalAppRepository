package com.shaden.wesal;

public class notifications {
    String subject;
    String body;
    String time;
    String notId;

    public String getNotId() {
        return notId;
    }

    public void setNotId(String notId) {
        this.notId = notId;
    }

    public notifications() {
    }

    public notifications(String subject, String body, String time) {
        this.subject = subject;
        this.body = body;
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
