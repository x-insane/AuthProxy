package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth_token_site", schema = "auth_proxy", catalog = "")
public class AuthTokenSiteEntity {
    private int id;
    private int tokenId;
    private int siteId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public AuthTokenSiteEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "token_id")
    public int getTokenId() {
        return tokenId;
    }

    public AuthTokenSiteEntity setTokenId(int tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    @Basic
    @Column(name = "site_id")
    public int getSiteId() {
        return siteId;
    }

    public AuthTokenSiteEntity setSiteId(int siteId) {
        this.siteId = siteId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthTokenSiteEntity that = (AuthTokenSiteEntity) o;
        return id == that.id &&
                tokenId == that.tokenId &&
                siteId == that.siteId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, tokenId, siteId);
    }
}
