package com.bezbednost.email;

import com.bezbednost.models.Consumer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="token",nullable = false)
    private String token;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expired_at",nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="consumer_id",nullable = false)
    private Consumer consumer;

    public ConfirmationToken(String token, LocalDateTime createdAt,LocalDateTime expiredAt,Consumer consumer){

        this.token=token;
        this.createdAt=createdAt;
        this.expiredAt=expiredAt;
        this.consumer=consumer;
    }
}
