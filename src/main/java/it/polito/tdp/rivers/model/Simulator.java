package it.polito.tdp.rivers.model;

import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {
	
	private Model model;
	
	public Simulator() {
		this.model = new Model();
	}
	
	private PriorityQueue<Event> queue;
	
	//input
	private float k;
	private float fmed;
	private River fiume;
	
	//modello mondo
	private float Q;
	private float C;
	private List<Flow> fIn;
	private float fOut;
	private float fOutMin;
	
	//output
	private int nGiorniDisservizio;
	private float Cmed;

	//set input
	public void setK(float k) {
		this.k = k;
	}
	
	public void setFmed(float fmed) {
		this.fmed = fmed;
	}
	
	public void setFiume(River fiume) {
		this.fiume = fiume;
	}
	
	//get output
	public int getnGiorniDisservizio() {
		return nGiorniDisservizio;
	}
	public float getCmed() {
		return Cmed/this.fIn.size();
	}
	
	public void run() {
		
		this.queue = new PriorityQueue<Event>();
		
		//stato iniziale
		this.Q = this.k*this.fmed*30*24*3600;
		this.C = this.Q/2;
		this.fOutMin = (float)0.8*this.fmed;
		this.nGiorniDisservizio = 0;
		this.Cmed = 0;
		
		//eventi iniziali
		this.model.riempiFiume(fiume);
		this.fIn = fiume.getFlows();
		for(Flow f : fIn)
			this.queue.add(new Event(f.getDay(), f, EventType.INGRESSO));
		
		//ciclo simulazione
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
		
	}

	private void processEvent(Event e) {
		
		switch(e.getType()) {
		case INGRESSO:
			this.C += e.getFlow().getFlow();
			
			if(this.C > this.Q)
				this.queue.add(new Event(e.getDate(), e.getFlow(), EventType.TRACIMAZIONE));
			
			int p = (int)(Math.random()*100);
			if(p<5)
				this.queue.add(new Event(e.getDate(), e.getFlow(), EventType.IRRIGAZIONE));
			else
				this.queue.add(new Event(e.getDate(), e.getFlow(), EventType.USCITA));
			
			break;
			
		case USCITA:
			if(this.C < this.fOutMin) {
				this.nGiorniDisservizio++;
				this.C = 0;
				this.Cmed += this.C;
			} else {
				this.C -= this.fOutMin;
				this.Cmed += this.C;
			}
			
			break;
			
		case TRACIMAZIONE:
			float diff = this.C-this.Q;
			this.C -= diff;
			
			break;
			
		case IRRIGAZIONE:
			this.fOut = 10*this.fOutMin;
			
			if(this.fOut > this.C) {
				this.nGiorniDisservizio++;
				this.fOut = this.C;
				this.C = 0;
				this.Cmed += this.C;
			} else {
				this.C -= this.fOut;
				this.Cmed += this.C;
			}
			
			break;
			
		}
		
	}
	
}
