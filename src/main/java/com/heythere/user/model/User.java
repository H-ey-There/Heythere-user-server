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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targer_user_id", referencedColumnName = "user_id")
    private User targetUser;

    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "target_user_id")
    private List<User> subscribers = new ArrayList<>();

    @Builder
    public User(Long id, String email, String name, User targetUser, List<User> subscribers) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.targetUser = targetUser;
        this.subscribers = subscribers;
    }
}
