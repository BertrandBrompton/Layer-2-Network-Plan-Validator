package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import scanner.L2Scanner;

public class testL2Scanner {
	
	L2Scanner l2s;
	
	@Before
	public void setUp() throws Exception {
		l2s = new L2Scanner();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSnmpGetOnTestData(){
		fail("Not yet implemented");	
	}
	
	@Test
	public void testSnmpQueryOfRootSwitch(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testSnmpQueryOfRootSwitchGetsCorrectInfo(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testSnmpQuerySecondSwitch(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testConnectivityBasic(){
		fail("Not yet implemented");
	}
	
	// What does it mean by advanced? Does it mean use non-ICMP based methods of identifying network connectivity issues?
	@Test
	public void testConnectivityAdvanced(){
		fail("Not yet implemented");
	}
	@Test
	public void testFindSwitchPortWithMultipleNodesAttached(){
		fail("Not yet implemented");
	}
	@Test
	public void testFind(){
		fail("Not yet implemented"); // What is this unit test meant to actually test?
	}
	@Test
	public void testMacAddressToSwitchPortResolution(){
		fail("Not yet implemented");
	}
	@Test
	public void testSpanOverVLAN(){
		fail("Not yet implemented");
	}
	
	// It maybe the case that the software's algorithm causes it to ignore loops - we may want to know of their existence.
	// This is the case with Spanning Tree Protocol.
	// There could, perhaps, be a "sanity check" to see if any loops appear at all - if none apear, this is a strange signal.
	@Test
	public void testDetectsLoopsOfVariousKinds(){
		fail("Not yet implemented");
	}
	@Test
	public void testActsIntelligentlyWhenItFindsANodeThatIsNotASwitch(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testActsIntelligentlyWhenItFindsNoSwitchesAttached(){
		fail("Not yet implemented");
	}
	
	// This is a failover situation and needs to be reported as it will cause an error in the result data.
	@Test
	public void testActsIntelligentlyWhenSwitchDoesNotRespondToSNMP(){
		fail("Not yet implemented");
	}
	// This is important and should be done before any spanning is ever done.
	@Test
	public void testDetectsInfiniteLoop(){
		fail("Not yet implemented");
	}
	@Test
	public void testDetectsCablingAnomaly(){
		fail("Not yet implemented");
	}
	
	// Problem Description - If it detects a switch which should not exist, 
	// it should not query it in a way that causes the server to be blocked as malicious software.
	@Test
	public void testDetectsWhenItHasReachedISNetwork(){
		fail("Not yet implemented");
	}
	
	// This is essential to ensuring that it gets the correct data
	// TODO: There will need to be a test suite of tests like this that check other MIBs
	@Test
	public void testGetsCorrectDataFromMibRFC1213(){
		
	}
	
	// It is possible that a router would give back SNMP information. 
	// The software needs to be intelligent enough not to span any backbone links.
	@Test
	public void testCanIdentifyDiffBetweenSwitchAndRouter(){
		
	}
	
	@Test
	public void testDoesNotSpanPastRouterBoundary(){
		
	}
	
	// It needs to be ascertained how the software interacts with wireless access points.
	@Test
	public void testDoesNotSpanWireless(){
		
	}
}
