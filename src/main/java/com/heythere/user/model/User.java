package com.heythere.user.model;

import com.heythere.user.shared.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String email;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "target_user_id")
    private User user;

    @OneToMany(mappedBy = "user")
    private List<User> subscriber = new ArrayList<>();

    @Builder
    public User(Long id, String email, String name, User user, List<User> subscriber) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.user = user;
        this.subscriber = subscriber;
    }
}
