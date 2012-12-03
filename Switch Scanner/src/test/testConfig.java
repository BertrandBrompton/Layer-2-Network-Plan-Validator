package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import config.Config;

public class testConfig {
	Config config;
	
	@Before
	public void setUp() throws Exception {
		config = new Config();
	}

	@Test
	public void testReadConfigFile(){		
		config.readConfigFile();
		assertEquals(config.get("language"),"en");
	}
	@Test
	public void testRespondsIntelligentlyWhenItDoesntFindAConfigFile(){
		fail("Not yet implemented!");
	}
}
