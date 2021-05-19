package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public List<LocalDate> getDate(River fiume) {
		
		final String sql = "SELECT MIN(DAY) AS MIN, MAX(DAY) AS MAX "
				+ "FROM  flow f "
				+ "WHERE f.river=?";
		
		LocalDate dataI;
		LocalDate dataF;
		
		List<LocalDate> date = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, fiume.getId());
			
			ResultSet res = st.executeQuery();
			
			if(res.next()) {
				dataI = res.getDate("MIN").toLocalDate();
				dataF = res.getDate("MAX").toLocalDate();
			
				date.add(dataI);
				date.add(dataF);
			}
			
			conn.close();
			return date;
			
		} catch(SQLException e) {
			throw new RuntimeException("SQL Error");
		}
		
	}
	
	public void setPortataMedia(River fiume) {
		
		final String sql = "SELECT AVG(flow) as avg "
				+ "FROM flow f "
				+ "WHERE f.river=?";
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, fiume.getId());
			
			ResultSet res = st.executeQuery();
			
			if(res.next()) {
				fiume.setFlowAvg(res.getDouble("avg"));
			}
			
			conn.close();

		} catch(SQLException e) {
			throw new RuntimeException("SQL Error");
		}
		
	}
	
	public void setFlows(River fiume) {
		
		final String sql = "SELECT f.flow as f, f.day as d "
				+ "FROM flow f "
				+ "WHERE f.river=?";
		
		List<Flow> flows = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, fiume.getId());
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				flows.add(new Flow(res.getDate("d").toLocalDate(), res.getDouble("f"), fiume));
			}
			
			conn.close();
			
			fiume.setFlows(flows);

		} catch(SQLException e) {
			throw new RuntimeException("SQL Error");
		}
		
	}
	
}
