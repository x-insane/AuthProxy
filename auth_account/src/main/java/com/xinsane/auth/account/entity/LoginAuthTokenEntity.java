package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "login_auth_token", schema = "auth_proxy", catalog = "")
public class LoginAuthTokenEntity {
    private int tokenId;
    private String tokenCode;
    private int userId;
    private Timestamp expireTime;

    @Id
    @Column(name = "token_id")
    public int getTokenId() {
        return tokenId;
    }

    public LoginAuthTokenEntity setTokenId(int tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    @Basic
    @Column(name = "token_code")
    public String getTokenCode() {
        return tokenCode;
    }

    public LoginAuthTokenEntity setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
        return this;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public LoginAuthTokenEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "expire_time")
    public Timestamp getExpireTime() {
        return expireTime;
    }

    public LoginAuthTokenEntity setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginAuthTokenEntity that = (LoginAuthTokenEntity) o;
        return tokenId == that.tokenId &&
                userId == that.userId &&
                Objects.equals(tokenCode, that.tokenCode) &&
                Objects.equals(expireTime, that.expireTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tokenId, tokenCode, userId, expireTime);
    }
}
