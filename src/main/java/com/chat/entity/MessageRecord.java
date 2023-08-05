package com.chat.entity;

public class MessageRecord {
    private int id;
    private String sender;
    private String receiver;
    private String content;
    private String createTime;

    public MessageRecord() {
    }

    public MessageRecord(int id, String sender, String receiver, String content, String createTime) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.createTime = createTime;
    }

    // Setters and getters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    // Override toString() method for better output format

    @Override
    public String toString() {
        return "MessageRecord{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}

/*MessageRecord messageRecord1 = new MessageRecord(1, "Alice", "Bob", "Hello Bob, how are you today?", String.parse("2023-06-08T10:00:00"));
MessageRecord messageRecord2 = new MessageRecord(2, "Bob", "Alice", "Hi Alice, I am doing fine, thanks for asking.", String.parse("2023-06-08T10:01:00"));
MessageRecord messageRecord3 = new MessageRecord(3, "Carol", "Alice", "Hello Alice, have you finished your project?", String.parse("2023-06-08T10:02:00"));

// You can set and get the fields of a MessageRecord object like this:
messageRecord1.setSender("David");
System.out.println(messageRecord1.getSender()); // Output: "David"*/