package com.xinsane.auth.account.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "config", schema = "auth_proxy", catalog = "")
public class ConfigEntity {
    private int id;
    private String key;
    private String value;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public ConfigEntity setId(int id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "name")
    public String getKey() {
        return key;
    }

    public ConfigEntity setKey(String name) {
        this.key = name;
        return this;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public ConfigEntity setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigEntity that = (ConfigEntity) o;
        return id == that.id &&
                Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, key, value);
    }
}
