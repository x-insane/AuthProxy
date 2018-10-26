package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "auth_token", schema = "auth_proxy", catalog = "")
public class AuthTokenEntity {
    private int tokenId;
    private int userId;
    private Timestamp createTime;
    private Timestamp expireTime;

    @Id
    @Column(name = "token_id")
    public int getTokenId() {
        return tokenId;
    }

    public AuthTokenEntity setTokenId(int tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public AuthTokenEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public AuthTokenEntity setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    @Basic
    @Column(name = "expire_time")
    public Timestamp getExpireTime() {
        return expireTime;
    }

    public AuthTokenEntity setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthTokenEntity that = (AuthTokenEntity) o;
        return tokenId == that.tokenId &&
                userId == that.userId &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(expireTime, that.expireTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tokenId, userId, createTime, expireTime);
    }
}
