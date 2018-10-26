package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth_page_user", schema = "auth_proxy", catalog = "")
public class AuthPageUserEntity {
    private int id;
    private int pageId;
    private int userId;
    private byte shareable;
    private int viewCount;
    private int viewCountLimit;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public AuthPageUserEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "page_id")
    public int getPageId() {
        return pageId;
    }

    public AuthPageUserEntity setPageId(int pageId) {
        this.pageId = pageId;
        return this;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public AuthPageUserEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "shareable")
    public byte getShareable() {
        return shareable;
    }

    public AuthPageUserEntity setShareable(byte shareable) {
        this.shareable = shareable;
        return this;
    }

    @Basic
    @Column(name = "view_count")
    public int getViewCount() {
        return viewCount;
    }

    public AuthPageUserEntity setViewCount(int viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    @Basic
    @Column(name = "view_count_limit")
    public int getViewCountLimit() {
        return viewCountLimit;
    }

    public AuthPageUserEntity setViewCountLimit(int viewCountLimit) {
        this.viewCountLimit = viewCountLimit;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthPageUserEntity that = (AuthPageUserEntity) o;
        return id == that.id &&
                pageId == that.pageId &&
                userId == that.userId &&
                shareable == that.shareable &&
                viewCount == that.viewCount &&
                viewCountLimit == that.viewCountLimit;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, pageId, userId, shareable, viewCount, viewCountLimit);
    }
}
