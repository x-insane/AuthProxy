package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth_page", schema = "auth_proxy", catalog = "")
public class AuthPageEntity {
    private int pageId;
    private int siteId;
    private String pagePath;
    private int authType;

    @Id
    @Column(name = "page_id")
    public int getPageId() {
        return pageId;
    }

    public AuthPageEntity setPageId(int pageId) {
        this.pageId = pageId;
        return this;
    }

    @Basic
    @Column(name = "site_id")
    public int getSiteId() {
        return siteId;
    }

    public AuthPageEntity setSiteId(int siteId) {
        this.siteId = siteId;
        return this;
    }

    @Basic
    @Column(name = "page_path")
    public String getPagePath() {
        return pagePath;
    }

    public AuthPageEntity setPagePath(String pagePath) {
        this.pagePath = pagePath;
        return this;
    }

    @Basic
    @Column(name = "auth_type")
    public int getAuthType() {
        return authType;
    }

    public AuthPageEntity setAuthType(int authType) {
        this.authType = authType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthPageEntity that = (AuthPageEntity) o;
        return pageId == that.pageId &&
                siteId == that.siteId &&
                authType == that.authType &&
                Objects.equals(pagePath, that.pagePath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pageId, siteId, pagePath, authType);
    }
}
