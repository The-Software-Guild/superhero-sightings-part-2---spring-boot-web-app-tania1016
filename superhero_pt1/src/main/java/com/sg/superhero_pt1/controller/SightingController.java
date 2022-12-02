package com.sg.superhero_pt1.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;

import com.sg.superhero_pt1.dao.AddressDao;
import com.sg.superhero_pt1.dao.MemberDao;
import com.sg.superhero_pt1.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sg.superhero_pt1.dao.SightingDao;

@Controller
public class SightingController {

	@Autowired
	SightingDao sightingDao;

	@Autowired
	MemberDao memberDao;

	@Autowired
	AddressDao addressDao;

	Set<ConstraintViolation<Sighting>> violations = new HashSet<>();


	@GetMapping("sightings")
	public String displaySightings(Model model) {
		//there are a couple drop-downs, so we need to "get all" for each one
		model.addAttribute("errors", violations);
		List<SightingViewDetail> sightingDetails = sightingDao.getSightingDetail();
		model.addAttribute("sightingDetails", sightingDetails);
		List<Sighting> sightings = sightingDao.getAllSightings();
		model.addAttribute("sightings", sightings);
		List<MemberViewDetail> members = memberDao.getAllMembers();
		model.addAttribute("members", members);
		List<Address> addresses = addressDao.getAllAddresses();
		model.addAttribute("addresses", addresses);
		return "sightings";
	}

	//this lets the first ten sightings be visible, and we want it to display on the homepage
	@GetMapping("homepage")
	public String getFirstTen(Model model) {
		model.addAttribute("errors", violations);
		List<Sighting> sightings = sightingDao.getLastTenSightings();
		model.addAttribute("sightings", sightings);
		return "homepage";
	}

	@PostMapping("addSighting")
	public String addSighting(HttpServletRequest request) throws ParseException {
		//get all the parameters for the fields we want to add
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		int add_id = Integer.parseInt(request.getParameter("add_id"));
		int member_id = Integer.parseInt(request.getParameter("member_id"));
		String date = request.getParameter("date");

		//create a new sighting and set them and add
		Sighting sighting = new Sighting();
		sighting.setName(name);
		sighting.setDescription(description);
		sighting.setLatitude(Double.parseDouble(latitude));
		sighting.setLongitude(Double.parseDouble(longitude));
		sighting.setAdd_id(add_id);
		sightingDao.addSighting(sighting);

		//create a new member and set the member_id
		Member member = new Member();
		member.setMember_id(member_id);

		//create this detail page to add the date in and add it to the bridge table
		SightingViewDetail svd = new SightingViewDetail();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		svd.setDate(formatter.parse((request.getParameter("date"))));
		svd.setDate(formatter.parse(date));
		sightingDao.addMemberToSighting(sighting, member, svd);

		return "redirect:/sightings";
	}


	@GetMapping("sightingDetail")
	public String sightingDetail(Integer id, Model model) {
		Sighting sighting = sightingDao.getSightingById(id);
		model.addAttribute("sighting", sighting);
		return "sightingDetail";
	}

	@GetMapping("deleteSighting")
	public String deleteSighting(Integer id) {
		sightingDao.deleteSightingById(id);
		return "redirect:/sightings";
	}

	@GetMapping("editSighting")
	public String editSighting(HttpServletRequest request, Model model) {
		int id = Integer.parseInt(request.getParameter("id"));
		Sighting sighting = sightingDao.getSightingById(id);
		//there are a couple drop-downs we want to see, so we need to "get all"
		List<MemberViewDetail> members = memberDao.getAllMembers();
		model.addAttribute("members", members);
		List<Address> addresses = addressDao.getAllAddresses();
		model.addAttribute("addresses", addresses);
		model.addAttribute("sighting", sighting);
		return "editSighting";
	}

	@PostMapping("editSighting")
	public String performEditSighting(HttpServletRequest request) throws ParseException {
		//get all the parameters we want to update, set them, and then update the sighting and bridge table
		int id = Integer.parseInt(request.getParameter("id"));
		int member_id = Integer.parseInt(request.getParameter("member_id"));
		Sighting sighting = sightingDao.getSightingById(id);
		Member member = memberDao.getMemberById(member_id);
		sighting.setName(request.getParameter("name"));
		sighting.setDescription(request.getParameter("description"));
		sighting.setLatitude(Double.parseDouble(request.getParameter("latitude")));
		sighting.setLongitude(Double.parseDouble(request.getParameter("longitude")));
		sighting.setAdd_id(Integer.parseInt(request.getParameter("add_id")));
		member.setMember_id(Integer.parseInt(request.getParameter("member_id")));

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SightingViewDetail svd = new SightingViewDetail();
		svd.setDate(formatter.parse((request.getParameter("date"))));

		sightingDao.updateSighting(sighting);
		sightingDao.updateMemberSighting(sighting, member, svd);

		return "redirect:/sightings";
	}
}
