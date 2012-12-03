package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import datastore.Datastore;

public class testDatastore {
	
	Datastore ds;
	
	@Before
	public void setUp() throws Exception {
		ds = new Datastore();
		ds.createDb();
	}
	
	@Test
	public void testCreateExampleNode(){
		ds.createExampleNodes();
		assertEquals(ds.getRootNode().getProperty("Switch Name"), "CISCO ROOT1");
	}
	@Test
	public void testCreateStarNetworkOfNodes(){
		
	}
}
