package com.sg.superhero_pt1.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MemberSighting {

    private int member_id;
    private int sighting_id;
    private Date date;
    private Sighting sighting;
    private List<Member> members;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getSighting_id() {
        return sighting_id;
    }

    public void setSighting_id(int sighting_id) {
        this.sighting_id = sighting_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Sighting getSighting() {
        return sighting;
    }

    public void setSighting(Sighting sighting) {
        this.sighting = sighting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberSighting that = (MemberSighting) o;

        if (member_id != that.member_id) return false;
        if (sighting_id != that.sighting_id) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(sighting, that.sighting)) return false;
        return Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        int result = member_id;
        result = 31 * result + sighting_id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (sighting != null ? sighting.hashCode() : 0);
        result = 31 * result + (members != null ? members.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberSighting{" +
                "member_id=" + member_id +
                ", sighting_id=" + sighting_id +
                ", date=" + date +
                ", sighting=" + sighting +
                ", members=" + members +
                '}';
    }
}
