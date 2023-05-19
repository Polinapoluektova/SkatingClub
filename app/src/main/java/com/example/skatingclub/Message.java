package com.example.skatingclub;

import java.util.Date;

// объект, содержащий в себе имя пользователя, время отправки сообщения и само сообщение,
// хренящееся в бд и выводщееся на экран
public class Message {
    private String text;
    private String senderId;
    private String receiverId;

    public Message() {
    }

    public Message(String text, String senderId, String receiverId) {
        this.text = text;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}