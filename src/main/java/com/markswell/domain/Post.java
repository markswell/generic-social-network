package com.markswell.domain;

import lombok.Data;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "POSTS")
@Data
@NoArgsConstructor
public class Post {

    public Post(String text, User user) {
        this.text = text;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "post_text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "dateTime")
    private LocalDateTime dateTime;

    @PrePersist
    private void preSave() {
        this.dateTime = LocalDateTime.now();
    }

}
