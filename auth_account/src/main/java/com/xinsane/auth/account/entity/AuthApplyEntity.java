package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "auth_apply", schema = "auth_proxy", catalog = "")
public class AuthApplyEntity {
    private int applyId;
    private Integer siteId;
    private Integer pageId;
    private int userId;
    private String notifyEmail;
    private String notifyPhone;
    private Timestamp createTime;
    private int status;
    private Integer handleUserId;
    private Timestamp handleTime;

    @Id
    @Column(name = "apply_id")
    public int getApplyId() {
        return applyId;
    }

    public AuthApplyEntity setApplyId(int applyId) {
        this.applyId = applyId;
        return this;
    }

    @Basic
    @Column(name = "site_id")
    public Integer getSiteId() {
        return siteId;
    }

    public AuthApplyEntity setSiteId(Integer siteId) {
        this.siteId = siteId;
        return this;
    }

    @Basic
    @Column(name = "page_id")
    public Integer getPageId() {
        return pageId;
    }

    public AuthApplyEntity setPageId(Integer pageId) {
        this.pageId = pageId;
        return this;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public AuthApplyEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "notify_email")
    public String getNotifyEmail() {
        return notifyEmail;
    }

    public AuthApplyEntity setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
        return this;
    }

    @Basic
    @Column(name = "notify_phone")
    public String getNotifyPhone() {
        return notifyPhone;
    }

    public AuthApplyEntity setNotifyPhone(String notifyPhone) {
        this.notifyPhone = notifyPhone;
        return this;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public AuthApplyEntity setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public AuthApplyEntity setStatus(int status) {
        this.status = status;
        return this;
    }

    @Basic
    @Column(name = "handle_user_id")
    public Integer getHandleUserId() {
        return handleUserId;
    }

    public AuthApplyEntity setHandleUserId(Integer handleUserId) {
        this.handleUserId = handleUserId;
        return this;
    }

    @Basic
    @Column(name = "handle_time")
    public Timestamp getHandleTime() {
        return handleTime;
    }

    public AuthApplyEntity setHandleTime(Timestamp handleTime) {
        this.handleTime = handleTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthApplyEntity that = (AuthApplyEntity) o;
        return applyId == that.applyId &&
                userId == that.userId &&
                status == that.status &&
                Objects.equals(siteId, that.siteId) &&
                Objects.equals(pageId, that.pageId) &&
                Objects.equals(notifyEmail, that.notifyEmail) &&
                Objects.equals(notifyPhone, that.notifyPhone) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(handleUserId, that.handleUserId) &&
                Objects.equals(handleTime, that.handleTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applyId, siteId, pageId, userId, notifyEmail, notifyPhone, createTime, status, handleUserId, handleTime);
    }
}
