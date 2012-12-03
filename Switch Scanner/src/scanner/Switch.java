package scanner;

import java.util.Hashtable;

/**
 * This class holds all the switch data for a single switch. Considering refactoring to a Factory design pattern.
 * It facilitates having a store_switch(Switch network_switch) in Datastore.java . 
 * To facilitate that, it would need dynamic class inspection of some kind.
 * @author Bertrand
 *
 */
public class Switch {
	/*
	 * The Switch class will need to store:
	 * 		> switch connectivity information
	 * 		> switch information
	 * 		> port-switch mappings
	 * 
	 */
	Hashtable<String, String> ht = new Hashtable<String, String>();
	
	public Switch(){		
	}
	
	public void add(String key, String value){
		ht.put(key, value);
	}
	
}
