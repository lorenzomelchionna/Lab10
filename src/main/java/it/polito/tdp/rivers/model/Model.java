package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	RiversDAO dao;
	
	public Model() {
		super();
		this.dao = new RiversDAO();
	}
	
	public List<River> getFiumi() {
		return dao.getAllRivers();
	}
	
	public List<LocalDate> getDate(River fiume) {
		return dao.getDate(fiume);
	}
	
	public void riempiFiume(River fiume) {
		dao.setPortataMedia(fiume);
		dao.setFlows(fiume);
	}
	
}
