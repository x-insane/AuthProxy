package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth_site_user", schema = "auth_proxy", catalog = "")
public class AuthSiteUserEntity {
    private int id;
    private int siteId;
    private int userId;
    private boolean shareable;
    private boolean manageable;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public AuthSiteUserEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "site_id")
    public int getSiteId() {
        return siteId;
    }

    public AuthSiteUserEntity setSiteId(int siteId) {
        this.siteId = siteId;
        return this;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public AuthSiteUserEntity setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "shareable")
    public boolean getShareable() {
        return shareable;
    }

    public AuthSiteUserEntity setShareable(boolean shareable) {
        this.shareable = shareable;
        return this;
    }

    @Basic
    @Column(name = "manageable")
    public boolean getManageable() {
        return manageable;
    }

    public AuthSiteUserEntity setManageable(boolean manageable) {
        this.manageable = manageable;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthSiteUserEntity that = (AuthSiteUserEntity) o;
        return id == that.id &&
                siteId == that.siteId &&
                userId == that.userId &&
                shareable == that.shareable &&
                manageable == that.manageable;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, siteId, userId, shareable, manageable);
    }
}
