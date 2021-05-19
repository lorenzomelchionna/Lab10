package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		INGRESSO,
		USCITA,
		TRACIMAZIONE,
		IRRIGAZIONE
	}
	
	private LocalDate date;
	private Flow flow;
	private EventType type;
	
	public Event(LocalDate date, Flow flow, EventType type) {
		super();
		this.date = date;
		this.flow = flow;
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int compareTo(Event other) {
		
		if(this.date.equals(other.date)) {
			if(this.type == EventType.INGRESSO)
				return -1;
			else
				return 1;
		} else {
			return this.date.compareTo(other.date);
		}
		
	}
	
}
