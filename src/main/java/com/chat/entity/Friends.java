package com.chat.entity;

public class Friends {
    private Integer id;
    private String name;
    private String status;

    public Friends() {
    }

    public Friends(Integer id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    /**
     * 获取
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "Friends{id = " + id + ", name = " + name + ", status = " + status + "}";
    }
}
