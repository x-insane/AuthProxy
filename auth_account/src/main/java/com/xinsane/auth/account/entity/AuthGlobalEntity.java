package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "auth_global", schema = "auth_proxy", catalog = "")
public class AuthGlobalEntity {
    private int authId;
    private String authName;
    private String authDescription;
    private String authParent;
    private String authChildren;
    private String authKey;

    @Override
    public String toString() {
        return "AuthGlobalEntity{" +
                "authId=" + authId +
                ", authName='" + authName + '\'' +
                ", authDescription='" + authDescription + '\'' +
                ", authParent='" + authParent + '\'' +
                ", authChildren='" + authChildren + '\'' +
                ", authKey='" + authKey + '\'' +
                '}';
    }

    @Id
    @Column(name = "auth_id")
    public int getAuthId() {
        return authId;
    }

    public AuthGlobalEntity setAuthId(int authId) {
        this.authId = authId;
        return this;
    }

    @Basic
    @Column(name = "auth_key")
    public String getAuthKey() {
        return authKey;
    }

    public AuthGlobalEntity setAuthKey(String authKey) {
        this.authKey = authKey;
        return this;
    }

    @Basic
    @Column(name = "auth_name")
    public String getAuthName() {
        return authName;
    }

    public AuthGlobalEntity setAuthName(String authName) {
        this.authName = authName;
        return this;
    }

    @Basic
    @Column(name = "auth_description")
    public String getAuthDescription() {
        return authDescription;
    }

    public AuthGlobalEntity setAuthDescription(String authDescription) {
        this.authDescription = authDescription;
        return this;
    }

    @Basic
    @Column(name = "auth_parent")
    public String getAuthParent() {
        return authParent;
    }

    public AuthGlobalEntity setAuthParent(String authParent) {
        this.authParent = authParent;
        return this;
    }

    @Basic
    @Column(name = "auth_children")
    public String getAuthChildren() {
        return authChildren;
    }

    public AuthGlobalEntity setAuthChildren(String authChildren) {
        this.authChildren = authChildren;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthGlobalEntity that = (AuthGlobalEntity) o;
        return authId == that.authId &&
                Objects.equals(authName, that.authName) &&
                Objects.equals(authDescription, that.authDescription) &&
                Objects.equals(authParent, that.authParent) &&
                Objects.equals(authChildren, that.authChildren);
    }

    @Override
    public int hashCode() {

        return Objects.hash(authId, authName, authDescription, authParent, authChildren);
    }
}
