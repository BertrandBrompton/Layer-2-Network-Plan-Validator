import cli.cliParser;
import config.Config;
import scanner.L2Scanner;
import datastore.Datastore;


public class SwitchScanner {

	/**
	 * @param args 
	 */
	public static void main(String[] args) {

		// Scan a single switch.
		// TODO: Make it such that it takes CLI args properly
		
		/*
		 *  Algorithm for switch scanning
		 *  -------------------------------
		 *  Loop(){
		 *  Detect if switch is available
		 *  Detect which MIBs are compatible
		 *  Pick appropriate MIB to use
		 *  Extract data
		 *  Work out relationship
		 *  Send data to datastore
		 *  
		 *  }
		 */
		
		cliParser clip = new cliParser(args); // This should control the execution of the application.
		
		Config config = new Config();
		
		L2Scanner l2s = new L2Scanner();

		// TODO: Actually make it such that the switch information is passed into the datastore.
		Datastore ds = new Datastore();
		ds.createDb();
		ds.removeData();
		ds.shutDown();
	}
}
