package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth_site", schema = "auth_proxy", catalog = "")
public class AuthSiteEntity {
    private int siteId;
    private String siteName;
    private String siteUrl;
    private String siteDescription;

    @Id
    @Column(name = "site_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getSiteId() {
        return siteId;
    }

    public AuthSiteEntity setSiteId(int siteId) {
        this.siteId = siteId;
        return this;
    }

    @Basic
    @Column(name = "site_name")
    public String getSiteName() {
        return siteName;
    }

    public AuthSiteEntity setSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    @Basic
    @Column(name = "site_url")
    public String getSiteUrl() {
        return siteUrl;
    }

    public AuthSiteEntity setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
        return this;
    }

    @Basic
    @Column(name = "site_description")
    public String getSiteDescription() {
        return siteDescription;
    }

    public AuthSiteEntity setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthSiteEntity that = (AuthSiteEntity) o;
        return siteId == that.siteId &&
                Objects.equals(siteName, that.siteName) &&
                Objects.equals(siteUrl, that.siteUrl) &&
                Objects.equals(siteDescription, that.siteDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(siteId, siteName, siteUrl, siteDescription);
    }
}
