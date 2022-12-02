package com.sg.superhero_pt1.model;

import java.util.Objects;

public class Member {
    private int member_id;

    private String member_name;

    private String description;

    private int powers_id;

    public int getMember_id(){
        return member_id;
    }
    public void setMember_id(int member_id){
        this.member_id = member_id;
    }

    public String getMember_name(){
        return member_name;
    }
    public void setMember_name(String member_name){
        this.member_name = member_name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public int getPowers_id(){
        return powers_id;
    }

    public void setPowers_id(int powers_id) {
        this.powers_id = powers_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return getMember_id() == member.getMember_id() && getPowers_id() == member.getPowers_id() && Objects.equals(getMember_name(), member.getMember_name()) && Objects.equals(getDescription(), member.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMember_id(), getMember_name(), getDescription(), getPowers_id());
    }
}
