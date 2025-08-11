package com.juaracoding.cksteam26.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "UserOrganization")
@IdClass(UserOrganizationId.class)
public class UserOrganization {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private final Date createdAt = new Date();
    @Id
    @Column(name = "UserId", nullable = true)
    private Long userId;
    @Id
    @Column(name = "OrganizationId", nullable = false)
    private Long organizationId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", insertable = false, updatable = false, nullable = true,
            foreignKey = @ForeignKey(name = "fk-userorganization-user"))
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrganizationId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk-userorganization-organization"))
    private Organization organization;
    @Column(name = "OrganizationOwner", nullable = false)
    private Boolean organizationOwner = false;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = (user != null) ? user.getId() : null;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
        this.organizationId = (organization != null) ? organization.getId() : null;
    }


    public Boolean getOrganizationOwner() {
        return organizationOwner;
    }

    public void setOrganizationOwner(Boolean organizationOwner) {
        this.organizationOwner = organizationOwner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
