package com.example.test.user.entity;

import com.example.test.group.entity.Group;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String passwd;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    protected User() {/*no-op*/}

    public User(Builder builder) {
        this.loginId = builder.loginId;
        this.passwd = builder.passwd;
        this.group = builder.group;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
        if (!passwordEncoder.matches(credentials, passwd)) {
            throw new IllegalArgumentException("Bad credential!");
        }
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPasswd() {
        return passwd;
    }

    public Group getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("loginId", loginId)
                .append("group", group)
                .toString();
    }

    public static class Builder {
        private String loginId;
        private String passwd;
        private Group group;

        public Builder loginId(String loginId) {
            this.loginId = loginId;
            return this;
        }

        public Builder passwd(String passwd) {
            this.passwd = passwd;
            return this;
        }

        public Builder group(Group group) {
            this.group = group;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
