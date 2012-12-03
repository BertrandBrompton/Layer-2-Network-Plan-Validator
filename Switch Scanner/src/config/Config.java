package config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class Config {
	private Hashtable<String, String> conf;
	private static final String CONFIG_FILE_PATH = "./src/config/config.txt";
	/**
	 * Default Constructor
	 */
	public Config(){
		conf = new Hashtable<String, String>();
		readConfigFile();		
	}
	
	/**
	 * Reads the config text file into a datastructure.
	 * @return
	 */
	public Boolean readConfigFile(){
		Boolean success = false;
		try {
			
			// TODO: Default config file path needs testing
			// TODO: Write out a default config file
			
			FileReader filereader = new FileReader(CONFIG_FILE_PATH);
			BufferedReader bufferedreader = new BufferedReader(filereader);
			String line;
			StringTokenizer stringtokenizer;
			while((line = bufferedreader.readLine()) != null ){
				/*
				 * "All human knowledge takes the form of interpretation" - Walter Benjamin
				 */
				stringtokenizer = new StringTokenizer(line,"=");
				String key = stringtokenizer.nextToken();
				String value = stringtokenizer.nextToken();
				//TODO: Enhance this so that we could have better organised config files. See Samba config files as an example.
				conf.put(key, value);			
			}
			success = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return success;		
	}
	
	/**
	 * Facilitates getting the value from the config file. Conceals the internal workings from other classes.
	 * @param config_key  
	 * @return 
	 */
	public String get(String config_key){
		return conf.get(config_key);		
	}
}
