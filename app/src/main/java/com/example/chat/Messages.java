package com.example.chat;

public class Messages {
    private String sender;
    private String reciever;
    private String content;

    public Messages(){

    }

    public Messages(String sender, String reciever, String content) {
        this.sender = sender;
        this.reciever = reciever;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
