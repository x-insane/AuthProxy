package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user_invitation", schema = "auth_proxy", catalog = "")
public class UserInvitationEntity {
    private int inviteId;
    private String inviteCode;
    private int userId;
    private byte bindChild;
    private String authGlobal;
    private String authSite;
    private String authPage;
    private Timestamp createTime;
    private Timestamp expireTime;
    private int status;

    @Id
    @Column(name = "invite_id")
    public int getInviteId() {
        return inviteId;
    }

    public UserInvitationEntity setInviteId(int inviteId) {
        this.inviteId = inviteId;
        return this;
    }

    @Basic
    @Column(name = "invite_code")
    public String getInviteCode() {
        return inviteCode;
    }

    public UserInvitationEntity setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public UserInvitationEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "bind_child")
    public byte getBindChild() {
        return bindChild;
    }

    public UserInvitationEntity setBindChild(byte bindChild) {
        this.bindChild = bindChild;
        return this;
    }

    @Basic
    @Column(name = "auth_global")
    public String getAuthGlobal() {
        return authGlobal;
    }

    public UserInvitationEntity setAuthGlobal(String authGlobal) {
        this.authGlobal = authGlobal;
        return this;
    }

    @Basic
    @Column(name = "auth_site")
    public String getAuthSite() {
        return authSite;
    }

    public UserInvitationEntity setAuthSite(String authSite) {
        this.authSite = authSite;
        return this;
    }

    @Basic
    @Column(name = "auth_page")
    public String getAuthPage() {
        return authPage;
    }

    public UserInvitationEntity setAuthPage(String authPage) {
        this.authPage = authPage;
        return this;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public UserInvitationEntity setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    @Basic
    @Column(name = "expire_time")
    public Timestamp getExpireTime() {
        return expireTime;
    }

    public UserInvitationEntity setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public UserInvitationEntity setStatus(int status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInvitationEntity that = (UserInvitationEntity) o;
        return inviteId == that.inviteId &&
                userId == that.userId &&
                bindChild == that.bindChild &&
                status == that.status &&
                Objects.equals(inviteCode, that.inviteCode) &&
                Objects.equals(authGlobal, that.authGlobal) &&
                Objects.equals(authSite, that.authSite) &&
                Objects.equals(authPage, that.authPage) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(expireTime, that.expireTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(inviteId, inviteCode, userId, bindChild, authGlobal, authSite, authPage, createTime, expireTime, status);
    }
}
