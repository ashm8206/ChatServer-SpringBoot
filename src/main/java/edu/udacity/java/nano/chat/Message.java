package edu.udacity.java.nano.chat;

/**
 * WebSocket message model
 */
public class Message {
    private String author;
    private String text;
    private String type;
    private Integer onlineCount;

    public Message(String author, String text, String type) {
        this.author = author;
        this.text = text;
        this.type = type;
        this.onlineCount = null;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }
}
