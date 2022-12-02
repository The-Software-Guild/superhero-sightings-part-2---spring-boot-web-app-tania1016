package com.sg.superhero_pt1.model;


import java.util.Objects;

public class Organization {

    private int org_id;
    private String org_name;
    private String org_description;
    private String phone;
    private int add_id;


    @Override
    public String toString() {
        return "Organization{" + "org_id=" + org_id + ", org_name=" + org_name + ", org_description=" + org_description + ", phone=" + phone + ",add_id" + add_id + '}';
    }

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_description() {
        return org_description;
    }

    public void setOrg_description(String org_description) {
        this.org_description = org_description;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone) { this.phone = phone;}

    public int getAdd_id(){ return add_id; }

    public void setAdd_id(int add_id) { this.add_id = add_id; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;
        Organization that = (Organization) o;
        return getOrg_id() == that.getOrg_id() && getAdd_id() == that.getAdd_id() && Objects.equals(getOrg_name(), that.getOrg_name()) && Objects.equals(getOrg_description(), that.getOrg_description()) && Objects.equals(getPhone(), that.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrg_id(), getOrg_name(), getOrg_description(), getPhone(), getAdd_id());
    }
}


