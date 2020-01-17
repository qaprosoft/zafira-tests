package com.qaprosoft.zafira.domain;

import java.util.Date;

public class EmailMsg {
    private String subject;

    private String from;

    private Date time;

    private String content;

    private String contentSrc;

    public EmailMsg(String subject, String from, Date time, String content, String contentSrc) {
        this.subject = subject;
        this.from = from;
        this.time = time;
        this.content = content;
        this.contentSrc = contentSrc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentSrc() {
        return contentSrc;
    }

    public void setContentSrc(String contentSrc) {
        this.contentSrc = contentSrc;
    }

    @Override
    public String toString() {
        return "EmailMsg [subject=" + subject + ", from=" + from + ", time=" + time + ", content=" + content
                + ", contentSrc=" + contentSrc + "]";
    }

}