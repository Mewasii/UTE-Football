package com.example.doancuoiki.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username", length = 100, nullable = false)
    private String username;

    @Column(name="password", length = 100, nullable = false)
    private String password;

    @Column(name="email", length = 200)
    private String email;

    @Column(name="phone", length = 15)
    private String phone;

    @Column(name="status")
    private int status;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = new Date(); 
        }
        if (images == null || images.isEmpty()) {
            images = "icon_login.jpg";  
        }
    }
    
    @Column(name = "images", length = 500)
    private String images;

    public User() {}

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
}
