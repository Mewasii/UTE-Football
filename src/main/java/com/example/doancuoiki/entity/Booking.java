package com.example.doancuoiki.entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name="bookings")
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")	
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookingid")
    private Long bookingid;

    @Column(name="sanid", length = 200)
    private String sanid;

    @Column(name="userid", length = 200)
    private String userid;

    @Column(name="date", length = 200)
    private String date;

    @Column(name="time", length = 200)
    private String time;

    @Column(name="price", length = 200)
    private String price;
    
    @Column(name = "confirm")
    private Boolean confirm;

    // Getter & Setter
    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public Long getBookingid() {
        return bookingid;
    }

    public void setBookingid(Long bookingid) {
        this.bookingid = bookingid;
    }

    public String getSanid() {
        return sanid;
    }

    public void setSanid(String sanid) {
        this.sanid = sanid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    // Constructor mặc định
    public Booking() {}
}
