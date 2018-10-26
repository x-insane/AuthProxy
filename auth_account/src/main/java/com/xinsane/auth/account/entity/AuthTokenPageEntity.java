package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth_token_page", schema = "auth_proxy", catalog = "")
public class AuthTokenPageEntity {
    private int id;
    private int tokenId;
    private int pageId;
    private int viewCount;
    private int viewCountLimit;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public AuthTokenPageEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "token_id")
    public int getTokenId() {
        return tokenId;
    }

    public AuthTokenPageEntity setTokenId(int tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    @Basic
    @Column(name = "page_id")
    public int getPageId() {
        return pageId;
    }

    public AuthTokenPageEntity setPageId(int pageId) {
        this.pageId = pageId;
        return this;
    }

    @Basic
    @Column(name = "view_count")
    public int getViewCount() {
        return viewCount;
    }

    public AuthTokenPageEntity setViewCount(int viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    @Basic
    @Column(name = "view_count_limit")
    public int getViewCountLimit() {
        return viewCountLimit;
    }

    public AuthTokenPageEntity setViewCountLimit(int viewCountLimit) {
        this.viewCountLimit = viewCountLimit;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthTokenPageEntity that = (AuthTokenPageEntity) o;
        return id == that.id &&
                tokenId == that.tokenId &&
                pageId == that.pageId &&
                viewCount == that.viewCount &&
                viewCountLimit == that.viewCountLimit;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, tokenId, pageId, viewCount, viewCountLimit);
    }
}
