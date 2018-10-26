package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "auth_proxy", catalog = "")
public class UserEntity {
    private int userId;
    private byte userType;
    private String username;
    private String password;
    private String email;
    private String phone;
    private int inviterId;
    private int parentId;
    private Timestamp createTime;

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userType=" + userType +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", inviterId=" + inviterId +
                ", parentId=" + parentId +
                ", createTime=" + createTime +
                '}';
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getUserId() {
        return userId;
    }

    public UserEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "user_type")
    public byte getUserType() {
        return userType;
    }

    public UserEntity setUserType(byte userType) {
        this.userType = userType;
        return this;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public UserEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Basic
    @Column(name = "inviter_id")
    public int getInviterId() {
        return inviterId;
    }

    public UserEntity setInviterId(int inviterId) {
        this.inviterId = inviterId;
        return this;
    }

    @Basic
    @Column(name = "parent_id")
    public int getParentId() {
        return parentId;
    }

    public UserEntity setParentId(int parentId) {
        this.parentId = parentId;
        return this;
    }

    @Basic
    @Column(name = "create_time", insertable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public UserEntity setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId &&
                userType == that.userType &&
                inviterId == that.inviterId &&
                parentId == that.parentId &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, userType, username, password, email, phone, inviterId, parentId, createTime);
    }
}
