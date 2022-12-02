package com.sg.superhero_pt1.dao;


import com.sg.superhero_pt1.model.MemberSighting;



import java.util.List;

public interface MemberSightingDao {

     MemberSighting getMemberSightingByIds(int sighting_id, int member_id);

    MemberSighting getMemberSightingByMemberId(int member_id); // UNUSED METHOD ***
    MemberSighting getMemberSightingBySightingId(int sighting_id );// UNUSED METHOD ***
    List<MemberSighting> getAllMemberSightings();
    MemberSighting addMemberSighting(MemberSighting memberSighting);
    void updateMemberSighting(MemberSighting memberSighting);
    void deleteMemberSightingByMemberId(int member_id); // Work in Progress ***
    void deleteMemberSightingBySightingId(int sighting_id); // Work in Progress ***
    List<MemberSighting> getMostRecent();

    void deleteMemberSightings( int sighting_id, int member_id);
}
