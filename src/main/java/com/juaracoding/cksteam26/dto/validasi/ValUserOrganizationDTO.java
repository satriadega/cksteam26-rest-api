package com.juaracoding.cksteam26.dto.validasi;

import java.util.List;

public class ValUserOrganizationDTO {
    private String organizationName;
    private List<String> addMember;
    private List<String> removeMember;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<String> getAddMember() {
        return addMember;
    }

    public void setAddMember(List<String> addMember) {
        this.addMember = addMember;
    }

    public List<String> getRemoveMember() {
        return removeMember;
    }

    public void setRemoveMember(List<String> removeMember) {
        this.removeMember = removeMember;
    }
}
