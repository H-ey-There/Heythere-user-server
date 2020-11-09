package com.heythere.user.model;

import com.heythere.user.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Follower extends BaseTimeEntity {
    @Id
    @Column(name = "follower_id")
    private Long id;

    @Unique
    private String email;
    private String name;
    private String img;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Follower(Long id, @Unique String email, String name, String img, User user) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.img = img;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
