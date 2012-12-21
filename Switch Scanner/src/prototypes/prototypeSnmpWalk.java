package prototypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
	// http://stackoverflow.com/questions/3629972/programmatically-enable-disable-port-on-network-switch
	// gives clue to OID of a port for MIB RFC 1213

public class prototypeSnmpWalk {
	public static void main(String[] args){
		System.out.println("Running...");
		
		System.out.print("Enter the community string: ");
		//String community = System.console().readLine();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		String community = null;
		Connectivity cc = new Connectivity();
		//cc.checkConnectivity("islsw03");
		//cc.connectivity("islsw03");
		//cc.connectInternet();
		try {
			community = bufferedReader.readLine();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		// A VariableBinding is an association of a object instance identifier (OID) and the instance's value (Variable).
		// http://www.alvestrand.no/objectid/1.3.6.1.2.1.1.html
		System.out.println(community);
		SnmpUtility su = new SnmpUtility(SnmpUtility.VERSION_1, "islsw03");
		List<VariableBinding> vbs = su.walk(new OID("1.3.6.1.2.1.1"),community); // If this returns nothing, it is completely silent. 
		for (VariableBinding vb : vbs) {
			System.out.println(vb.getOid() + ":=" + vb.getVariable().toString());
		}

		System.out.println("Closing...");
	}
}
