package com.sg.superhero_pt1.dao;

import java.util.List;

import com.sg.superhero_pt1.model.Member;
import com.sg.superhero_pt1.model.Sighting;
import com.sg.superhero_pt1.model.SightingViewDetail;

public interface SightingDao {

    Sighting getSightingById(int id);
    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
    List<Sighting> getLastTenSightings();
    List<SightingViewDetail> getSightingDetail();
    void addMemberToSighting(Sighting sighting, Member member, SightingViewDetail svd);
    void updateMemberSighting(Sighting sighting, Member member, SightingViewDetail svd);
}
