package com.sg.superhero_pt1.dao;

import com.sg.superhero_pt1.model.Member;
import com.sg.superhero_pt1.model.MemberViewDetail;
import com.sg.superhero_pt1.model.Organization;
import java.util.List;

public interface MemberDao {

    Member addMember(Member member);
    void addMemberToOrg(Member member, Organization organization);
    List<MemberViewDetail> getAllMembers();
    List<Member> getAll();
    Member getMemberById(int id);
    List<Member> getAllMembersAtSighting(int sightingId);
    List<Member> getAllMembersInOrganization(int org_id);
    void deleteMemberById(int member_id);
    void updateMember(Member member);
    void updateMemberOrg(Member member, Organization org);

}

