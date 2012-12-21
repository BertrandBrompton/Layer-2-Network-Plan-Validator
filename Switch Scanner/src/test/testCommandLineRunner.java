package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prototypes.CommandLineRunner;
import prototypes.SshRunner;

public class testCommandLineRunner {
	CommandLineRunner clr;
	SshRunner sr;
	@Before
	public void setUp() throws Exception {
		clr = new CommandLineRunner();
	}
	
	/*
	 * The lack of exceptions thrown indicate it was a success.
	 */
	@Test
	public void testRunPingLocalhost(){
		clr.run("ping 127.0.0.1");
	}
	@Test
	public void testNetstatMinusR(){
		clr.run("netstat -r");
	}
	// Seems to freeze up if the command fails. Perhaps there is a process lock?
	@Test
	public void testSnmpWalk(){
		sr.connect();
		//clr.run();
	}	
}
