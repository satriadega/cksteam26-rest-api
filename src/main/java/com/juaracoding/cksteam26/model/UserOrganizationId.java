package com.juaracoding.cksteam26.model;

import java.io.Serializable;
import java.util.Objects;

public class UserOrganizationId implements Serializable {

    private Long userId;
    private Long organizationId;

    public UserOrganizationId() {}

    public UserOrganizationId(Long userId, Long organizationId) {
        this.userId = userId;
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserOrganizationId that)) return false;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(organizationId, that.organizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, organizationId);
    }
}
