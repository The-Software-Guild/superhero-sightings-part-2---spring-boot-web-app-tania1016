package com.sg.superhero_pt1.dao;

import com.sg.superhero_pt1.model.Member;
import com.sg.superhero_pt1.model.MemberSighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import com.sg.superhero_pt1.model.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MemberSightingDaoImpl implements MemberSightingDao {

    @Autowired
    JdbcTemplate jdbc;


    @Override
    public MemberSighting getMemberSightingByMemberId(int member_id) {
        try {
            final String SELECT_MEMBER_SIGHTING_BY_MEMBER_ID = "SELECT * FROM memberSighting WHERE member_id = ?";
            MemberSighting memberSighting = jdbc.queryForObject(SELECT_MEMBER_SIGHTING_BY_MEMBER_ID, new MemberSightingMapper(), member_id);

            return memberSighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public MemberSighting getMemberSightingBySightingId(int sighting_id) {
        try {
            final String SELECT_MEMBER_SIGHTING_BY_SIGHTING_ID = "SELECT * FROM memberSighting WHERE sighting_id = ?";
            MemberSighting memberSighting = jdbc.queryForObject(SELECT_MEMBER_SIGHTING_BY_SIGHTING_ID, new MemberSightingMapper(), sighting_id);
            memberSighting.setSighting(addSightingToMemberSighting(memberSighting));
            memberSighting.setMembers(addMemberToMemberSighting(memberSighting));
            return memberSighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<MemberSighting> getAllMemberSightings() {
        final String GET_ALL_MEMBER_SIGHTINGS = "SELECT * FROM memberSighting";
        return jdbc.query(GET_ALL_MEMBER_SIGHTINGS, new MemberSightingMapper());
    }


    @Override
    @Transactional
    public MemberSighting addMemberSighting(MemberSighting memberSighting) {
        final String INSERT_MEMBER_SIGHTING = "INSERT INTO memberSighting(sighting_id, member_id, date) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_MEMBER_SIGHTING,
                memberSighting.getSighting_id(),
                memberSighting.getMember_id(),
                memberSighting.getDate());

        return memberSighting;
    }

    @Override
    public void updateMemberSighting(MemberSighting memberSighting) {
        final String UPDATE_MEMBER_SIGHTING = "UPDATE memberSighting SET date = ? "
                + "WHERE sighting_id = ? AND member_id=? ";
        jdbc.update(UPDATE_MEMBER_SIGHTING,
                memberSighting.getDate(),
                memberSighting.getSighting_id(),
                memberSighting.getMember_id());


    }

    @Override
    public void deleteMemberSightingByMemberId(int member_id) {

    }

    @Override
    public void deleteMemberSightingBySightingId(int sighting_id) {

    }

    @Override
    @Transactional
    public void deleteMemberSightings( int sighting_id, int member_id){
        final String DELETE_MEMBER_SIGHTING = "DELETE FROM memberSighting WHERE sighting_id = ? AND member_id= ? ";
        jdbc.update(DELETE_MEMBER_SIGHTING, sighting_id, member_id);

    }
    @Override
   public MemberSighting getMemberSightingByIds(int sighting_id, int member_id){
        try {
            final String SELECT_MEMBER_SIGHTING_BY_IDS = "SELECT * FROM memberSighting WHERE sighting_id = ? AND member_id= ? ";
            MemberSighting memberSighting = jdbc.queryForObject(SELECT_MEMBER_SIGHTING_BY_IDS, new MemberSightingMapper(), sighting_id, member_id);
             memberSighting.setSighting(addSightingToMemberSighting(memberSighting));
             memberSighting.setMembers(addMemberToMemberSighting(memberSighting));
            return memberSighting;
        } catch (DataAccessException ex) {
            return null;
        }
}
@Override
    public List<MemberSighting> getMostRecent() {
        final String SELECT_MOST_RECENT = "SELECT * FROM memberSighting ORDER BY date DESC LIMIT 10";

        List<MemberSighting> memberSightings = jdbc.query(SELECT_MOST_RECENT, new MemberSightingMapper());

        for (MemberSighting s : memberSightings) {
            s.setSighting(addSightingToMemberSighting(s));
        }

        return memberSightings;

    }

    private Sighting addSightingToMemberSighting(MemberSighting memberSighting) {
        final String ADD_SIGHTING_TO_MEMBER_SIGHTING = "SELECT sightings.* FROM sightings " +
                "JOIN memberSighting ON sightings.sighting_Id = memberSighting.sighting_Id " +
                "WHERE sightings.sighting_Id = ?";
        return jdbc.queryForObject(ADD_SIGHTING_TO_MEMBER_SIGHTING, new SightingDaoImpl.SightingMapper(), memberSighting.getSighting_id());
    }

    private List<Member> addMemberToMemberSighting(MemberSighting memberSighting) {
        final String ADD_MEMBER_TO_MEMBER_SIGHTING = "SELECT member.* FROM member " +
                "JOIN memberSighting ON member.member_Id = memberSighting.member_Id " +
                "WHERE memberSighting.member_Id = ?";

            List<Member> membersList =   jdbc.query(ADD_MEMBER_TO_MEMBER_SIGHTING, new MemberDaoImpl.MemberMapper(), memberSighting.getMember_id());
        return membersList ;
    }


    public static final class MemberSightingMapper implements RowMapper<MemberSighting> {

        @Override
        public MemberSighting mapRow(ResultSet rs, int index) throws SQLException {
            MemberSighting memberSighting = new MemberSighting();
            memberSighting.setMember_id(rs.getInt("member_id"));
            memberSighting.setSighting_id(rs.getInt("sighting_id"));
            memberSighting.setDate(rs.getDate("date"));
            return memberSighting;

        }
    }
}

