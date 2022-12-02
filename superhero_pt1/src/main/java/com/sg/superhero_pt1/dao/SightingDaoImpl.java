package com.sg.superhero_pt1.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sg.superhero_pt1.model.Member;
import com.sg.superhero_pt1.model.SightingViewDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sg.superhero_pt1.model.Sighting;

@Repository
public class SightingDaoImpl implements SightingDao{


	@Autowired
	JdbcTemplate jdbc;


	@Override
	public Sighting getSightingById(int id) {
		try {
			final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sightings WHERE sighting_id = ?";
			Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
			return sighting;
		} catch(DataAccessException ex) {
			return null;
		}
	}

	//this lets us get all the sighting columns
	@Override
	public List<Sighting> getAllSightings() {
		final String GET_ALL_SIGHTINGS = "SELECT * FROM sightings";
		return jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
	}

	//this lets us get all the columns we want to display at the bottom, allowing it to be "custom"
	//we included the date because it is part of the bridge table MemberSighting
	@Override
	public List<SightingViewDetail> getSightingDetail() {
		final String sql = "SELECT sightings.sighting_id, sightings.sighting_name, sightings.description, sightings.latitude, sightings.longitude, address.city, member.member_name, memberSighting.date FROM sightings " +
				"JOIN memberSighting ON sightings.sighting_id=memberSighting.sighting_id " +
				"JOIN member ON memberSighting.member_id=member.member_id " +
				"JOIN address ON sightings.add_id=address.add_id ORDER BY sighting_id DESC";
		return jdbc.query(sql, new SightingMapper2());
	}

	//this is for our homepage, so we can see the latest 10 sightings
	//we order in descending order based on the sighting_id with a limit of 10 viewable at a time
	@Override
	public List<Sighting> getLastTenSightings() {
		final String GET_ALL_SIGHTINGS = "SELECT * FROM sightings ORDER BY sighting_id DESC LIMIT 10";
		return jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
	}

	@Override
	@Transactional
	public Sighting addSighting(Sighting sighting) {
		final String INSERT_SIGHTING = "INSERT INTO sightings(sighting_name, description, latitude, longitude, add_id) "
				+ "VALUES(?,?,?,?,?)";
		jdbc.update(INSERT_SIGHTING,
				sighting.getName(),
				sighting.getDescription(),
				sighting.getLatitude(),
				sighting.getLongitude(),
				sighting.getAdd_id());


		int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		sighting.setId(newId);
		return sighting;
	}

	//this allows us to add the elements into the MemberSighting bridge table
	//it has 3 parameters because it needs to grab the "getters" from three different areas
	@Override
	public void addMemberToSighting(Sighting sighting, Member member, SightingViewDetail svd) {
		final String sql = "INSERT INTO memberSighting(member_id, sighting_id, date) " +
				"VALUES(?,?,?)";
		jdbc.update(sql,
				member.getMember_id(),
				sighting.getId(),
				svd.getDate());

	}

	@Override
	public void updateSighting(Sighting sighting) {
		//we don't want the user to be able to update the sighting_name because it is acting as the "primary key" in the bridge table
		//ideally we wouldn't update any part of the bridge table but this instance is odd
		final String UPDATE_SIGHTING = "UPDATE sightings SET description = ?, latitude = ?, longitude = ?, add_id =? "
				+ "WHERE sighting_id = ?";
		jdbc.update(UPDATE_SIGHTING,
				sighting.getDescription(),
				sighting.getLatitude(),
				sighting.getLongitude(),
				sighting.getAdd_id(),
				sighting.getId());

	}

	//this allows us to update the new information into the bridge table
	@Override
	public void updateMemberSighting(Sighting sighting, Member member, SightingViewDetail svd) {
		final String sql = "UPDATE memberSighting SET member_id = ?, date = ? " +
				"WHERE sighting_id = ?";
		jdbc.update(sql,
				member.getMember_id(),
				svd.getDate(),
				sighting.getId());


	}

	@Override
	@Transactional
	public void deleteSightingById(int id) {
		//delete from the bridge table first
		final String DELETE_MEMBERSIGHTING_SIGHTINGS = "DELETE FROM memberSighting WHERE sighting_id = ?";
		jdbc.update(DELETE_MEMBERSIGHTING_SIGHTINGS, id);

		//then we can delete the actual sighting
		final String DELETE_SIGHTING = "DELETE FROM sightings WHERE sighting_id = ?";
		jdbc.update(DELETE_SIGHTING, id);

	}


	public static final class SightingMapper implements RowMapper <Sighting> {

		@Override
		public Sighting mapRow(ResultSet rs, int index) throws SQLException {
			Sighting sighting = new Sighting();
			sighting.setId(rs.getInt("sighting_id"));
			sighting.setName(rs.getString("sighting_name"));
			sighting.setDescription(rs.getString("description"));
			sighting.setLatitude(rs.getDouble("latitude"));
			sighting.setLongitude(rs.getDouble("longitude"));
			sighting.setAdd_id(rs.getInt("add_id"));
			return sighting;
		}
	}

	public static final class SightingMapper2 implements RowMapper <SightingViewDetail> {
		//we created a second Mapper because we wanted it to implement the exact rows we wanted in the html
		@Override
		public SightingViewDetail mapRow(ResultSet rs, int index) throws SQLException {
			SightingViewDetail svd = new SightingViewDetail();
			svd.setSighting_id(rs.getInt("sighting_id"));
			svd.setSighting_name(rs.getString("sighting_name"));
			svd.setDescription(rs.getString("description"));
			svd.setLatitude(rs.getDouble("latitude"));
			svd.setLongitude(rs.getDouble("longitude"));
			svd.setCity(rs.getString("city"));
			svd.setMember_name(rs.getString("member_name"));
			svd.setDate(rs.getDate("date"));
			return svd;
		}
	}
}
