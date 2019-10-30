package com.example.lab3;

public class MessageSend extends Message {
    public boolean isSent = true;

    public MessageSend(String user, String message, long id) {
        super(user, message, id);
    }

    @Override
    public boolean isSend() {
        return isSent;
    }

    @Override
    public boolean isReceived() {
        return !isSent;
    }

}