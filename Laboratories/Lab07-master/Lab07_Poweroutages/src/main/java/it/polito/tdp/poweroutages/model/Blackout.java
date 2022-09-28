package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.util.Calendar;

public class Blackout {

	private LocalDateTime data_inizio;
	private LocalDateTime data_fine;
	private int oreDisservizio;
	private int customersAffected;
	/**
	 * @param data_inizio
	 * @param data_fine
	 * @param oreDisservizio
	 * @param customersAffected
	 */
	public Blackout(LocalDateTime data_inizio, LocalDateTime data_fine, int customersAffected) {
		super();
		this.data_inizio = data_inizio;
		this.data_fine = data_fine;
		this.oreDisservizio = 0;
		this.customersAffected = customersAffected;
	}
	public LocalDateTime getData_inizio() {
		return data_inizio;
	}
	public void setData_inizio(LocalDateTime data_inizio) {
		this.data_inizio = data_inizio;
	}
	public LocalDateTime getData_fine() {
		return data_fine;
	}
	public void setData_fine(LocalDateTime data_fine) {
		this.data_fine = data_fine;
	}
	
	public int getCustomersAffected() {
		return customersAffected;
	}
	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}
	
	public int getOreDisservizio() {
		
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(data_inizio.getYear(), data_inizio.getMonthValue(), 
				data_inizio.getDayOfMonth(), data_inizio.getHour(), data_inizio.getMinute());
		Calendar secondDate = Calendar.getInstance();
		secondDate.set(data_fine.getYear(), data_fine.getMonthValue(), 
				data_fine.getDayOfMonth(), data_fine.getHour(), data_fine.getMinute());
		
		return this.oreDisservizio = (int) ((secondDate.getTimeInMillis()-firstDate.getTimeInMillis())/1000)/60/60;
	}
	@Override
	public String toString() {
		return data_fine.getYear()+" "+data_inizio.toString()+" "+data_fine.toString()+" "+this.getOreDisservizio()+" "+customersAffected+"\n";
	}
	
	
	
}

/*SELECT customers_affected, date_event_began,  date_event_finished, 
       TIMEDIFF(date_event_finished, date_event_began) AS blackoutHours
FROM poweroutages 
WHERE nerc_id = 3
*/