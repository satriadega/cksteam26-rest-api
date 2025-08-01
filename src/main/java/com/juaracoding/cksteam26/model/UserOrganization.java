package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "UserOrganization")
@IdClass(UserOrganizationId.class)
public class UserOrganization {

    @Id
    @Column(name = "UserId", nullable = false)
    private Long userId;

    @Id
    @Column(name = "OrganizationId", nullable = false)
    private Long organizationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk-userorganization-user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrganizationId", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk-userorganization-organization"))
    private Organization organization;

    @Column(name = "OrganizationOwner", nullable = false)
    private Boolean organizationOwner = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Date createdAt = new Date();

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
