package com.example.lab3;

public abstract class Message {
    public String user;
    public String message;
    public long id;
   // public boolean isSend;

    public Message(String message, String user, long id) {
        this.user = user;
        this.message = message;
        this.id = id;
        //this.isSend = isSend;
    }

    public String getUser() {
        return this.user;
    }
    public String getMessage() {
        return this.message;
    }
    public long getId(){
        return this.id;
    }

    public abstract boolean isSend();
    public abstract boolean isReceived();
}
