package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth_global_user", schema = "auth_proxy", catalog = "")
public class AuthGlobalUserEntity {
    private int id;
    private int authId;
    private int userId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public AuthGlobalUserEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "auth_id")
    public int getAuthId() {
        return authId;
    }

    public AuthGlobalUserEntity setAuthId(int authId) {
        this.authId = authId;
        return this;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public AuthGlobalUserEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthGlobalUserEntity that = (AuthGlobalUserEntity) o;
        return id == that.id &&
                authId == that.authId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, authId, userId);
    }
}
