package com.heythere.user.model;

import com.heythere.user.dto.UpdateUserProfileRequestDto;
import com.heythere.user.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Unique
    private String email;
    private String name;
    private String img;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Follower> followers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Subscriber> subscribers = new ArrayList<>();

    @Builder
    public User(Long id,
                @Unique String email,
                String name,
                String img,
                String password,
                List<Follower> followers,
                List<Subscriber> subscribers) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.img = img;
        this.password = password;
        this.followers = followers;
        this.subscribers = subscribers;
    }

    public User addFollower(final Follower follower) {
        followers.add(follower);
        follower.setUser(this);
        return this;
    }

    public User addSubscriber(final Subscriber subscriber) {
        subscribers.add(subscriber);
        subscriber.setUser(this);
        return this;
    }

    public User updateImg(final String img) {
        this.img = img;
        return this;
    }

    public User update(final UpdateUserProfileRequestDto payload) {
        this.email = payload.getEmail();
        this.name = payload.getName();
        this.password = payload.getPassword();
        return this;
    }
}
