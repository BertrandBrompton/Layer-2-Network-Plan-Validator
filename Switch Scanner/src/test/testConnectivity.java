package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prototypes.Connectivity;

public class testConnectivity {
	Connectivity c;
	String host;
	@Before
	public void setUp() throws Exception {
		c = new Connectivity();
		
	}
	@Test
	public void testInternetConnection(){
		host = "http://www.google.com";
		Boolean result = c.connectInternet(host);
		assertTrue(result);
	}
	@Test
	public void testCheckBasicOnGoogle(){
		host = "www.google.com";
		Boolean result = c.checkBasic(host);
		assertTrue(result);
	}
	@Test
	public void testCheckAdvancedOnGoogle(){
		host = "www.google.com";
		Boolean result = c.checkAdvanced(host); //Takes upto 10 ~ seconds if it occurs problems.
		assertTrue(result);
	}
	@Test
	public void testCheckBasicOnISLSwitch(){
		host = "islsw03";
		Boolean result = c.checkBasic(host);
		assertTrue(result);
	}
	@Test
	public void testCheckAdvancedOnISLSwitch(){
		host = "islsw03";
		Boolean result = c.checkAdvanced(host);
		assertTrue(result);
	}

}
