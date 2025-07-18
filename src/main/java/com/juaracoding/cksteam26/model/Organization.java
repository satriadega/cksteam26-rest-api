package com.juaracoding.cksteam26.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrganizationId")
    private Long organizationId;

    @Column(name = "OrganizationName", nullable = false, length = 100)
    private String organizationName;

    @Column(name = "PublicVisibility", nullable = false)
    private Boolean publicVisibility = false;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(name = "UpdatedAt")
    private Date updatedAt;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Boolean getPublicVisibility() {
        return publicVisibility;
    }

    public void setPublicVisibility(Boolean publicVisibility) {
        this.publicVisibility = publicVisibility;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
