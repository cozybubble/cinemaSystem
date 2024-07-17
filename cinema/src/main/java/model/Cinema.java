package main.java.model;


/**
 * Cinema类表示一个电影厅，包含id、名称和总座位数。
 */
public class Cinema {
    private int id; // 影厅的唯一标识符
    private String name; // 电影院的名称
    private int totalSeats; // 电影院的总座位数

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }


}
