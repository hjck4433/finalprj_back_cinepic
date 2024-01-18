package com.kh.cinepic.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table
@ToString
@NoArgsConstructor
public class ChatRoom {
    @Id
    @Column(name="room_id")
    private String roomId;
    @Column(name="room_name")
    private String roomName;
    @Column(name="created_at")
    private LocalDateTime createdAt;
}
