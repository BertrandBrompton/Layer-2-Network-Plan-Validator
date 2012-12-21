package prototypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
/**
 * This class supports synchronous SNMP Requests. 
 * @author Bertrand
 *
 */
public class SnmpUtility {

    public static int VERSION_1 = SnmpConstants.version1;
    public static int VERSION_2C = SnmpConstants.version2c;
    public static int VERSION_3 = SnmpConstants.version3;
    private int snmpVersion = SnmpUtility.VERSION_2C;
    private String host;
    private String transportProtocol = "UDP";
    private int snmpPort = 161;
    private int timeout = 3000;
    private int retry = 2;
    private Log log = LogFactory.getLog(SnmpUtility.class);

    public SnmpUtility() {
    }

    public SnmpUtility(int version) {
        this.setSnmpVersion(version);
    }

    public SnmpUtility(int version, String host) {
        this.setSnmpVersion(version);
        this.setHost(host);
    }

    /**
     * @return the snmpVersion
     */
    public int getSnmpVersion() {
        return snmpVersion;
    }

    /**
     * @param snmpVersion the snmpVersion to set
     */
    public void setSnmpVersion(int snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    public Object get(OID oid, String community) {
        Object ret = null;
        TransportMapping transport;
        try {
            if (this.getTransportProtocol().equalsIgnoreCase("UDP")) {
                transport = new DefaultUdpTransportMapping();
            } else {
                transport = new DefaultTcpTransportMapping();
            }
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            Address address = GenericAddress.parse(this.getTransportProtocol() + ":" + this.getHost() + "/" + this.getSnmpPort());
            target.setAddress(address);
            target.setVersion(this.getSnmpVersion());
            target.setTimeout(this.getTimeout());

            // create PDU
            PDU pdu = new PDU();
            pdu.setType(PDU.GET);
            pdu.addOID(new VariableBinding(oid));
            pdu.setNonRepeaters(0);

            Snmp snmp = new Snmp(transport);
            snmp.listen();

            ResponseEvent resp = snmp.send(pdu, target);
            PDU respPDU = resp.getResponse();
            Vector vbs = respPDU.getVariableBindings();
            if (vbs.size() > 0) {
                VariableBinding vb = (VariableBinding) vbs.get(0);
                ret = vb.getVariable();
            } else {
                ret = null;
            }
            snmp.close();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
        return ret;
    }

    public List<VariableBinding> getList(List<OID> oid_list, String community) {
        List<VariableBinding> ret = new ArrayList<VariableBinding>();
        TransportMapping transport;
        try {
            if (this.getTransportProtocol().equalsIgnoreCase("UDP")) {
                transport = new DefaultUdpTransportMapping();
            } else {
                transport = new DefaultTcpTransportMapping();
            }
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            Address address = GenericAddress.parse(this.getTransportProtocol() + ":" + this.getHost() + "/" + this.getSnmpPort());
            target.setAddress(address);
            target.setVersion(this.getSnmpVersion());
            target.setTimeout(this.getTimeout());

            // create the PDU
            PDU pdu = new PDU();
            pdu.setType(PDU.GET);
            //put the oids you want to get
            List<VariableBinding> ivbs = new ArrayList<VariableBinding>();
            for (OID o : oid_list) {
                ivbs.add(new VariableBinding(o));
            }
            pdu.addAll(ivbs.toArray(new VariableBinding[]{}));
            pdu.setMaxRepetitions(10);
            pdu.setNonRepeaters(0);

            Snmp snmp = new Snmp(transport);
            snmp.listen();

            // send the PDU
            ResponseEvent responseEvent = snmp.send(pdu, target);
            // extract the response PDU (could be null if timed out)
            PDU responsePDU = responseEvent.getResponse();
            Vector vbs = responsePDU.getVariableBindings();
            if (vbs.size() > 0) {
                List<OID> rec_oid = new ArrayList<OID>();
                for (int i = 0; i < vbs.size(); i++) {
                    VariableBinding v = (VariableBinding) vbs.get(i);
                    if (!rec_oid.contains(v.getOid())) {
                        rec_oid.add(v.getOid());
                        ret.add((VariableBinding) vbs.get(i));
                    }
                }
            }
            snmp.close();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
        return ret;
    }
    
	/**
	 * Do not use.
	 * @param oid
	 * @param value
	 * @param community
	 * @return
	 */
    public boolean set(OID oid, Variable value, String community) {
        boolean ret = false;
        TransportMapping transport;
        try {
            if (this.getTransportProtocol().equalsIgnoreCase("UDP")) {
                transport = new DefaultUdpTransportMapping();
            } else {
                transport = new DefaultTcpTransportMapping();
            }
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            Address address = GenericAddress.parse(this.getTransportProtocol() + ":" + this.getHost() + "/" + this.getSnmpPort());
            target.setAddress(address);
            target.setVersion(this.getSnmpVersion());
            target.setTimeout(this.getTimeout());

            // create PDU
            PDU pdu = new PDU();
            pdu.setType(PDU.SET);
            pdu.add(new VariableBinding(oid, value));
            pdu.setNonRepeaters(0);

            Snmp snmp = new Snmp(transport);
            snmp.listen();

            ResponseEvent resp = snmp.set(pdu, target);
            PDU respPDU = resp.getResponse();
            if (respPDU == null) {
                log.warn("SNMP Timeout occured.");
            } else {
                Vector vbs = respPDU.getVariableBindings();
                if (vbs.size() > 0) {
                    VariableBinding vb = (VariableBinding) vbs.get(0);
                    ret = vb.isException() ? false : true;
                } else {
                    ret = false;
                }
            }
            snmp.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.warn(ex.getMessage());
        }
        return ret;
    }

    /**
     * SNMPWalk.  
     * @param oid
     * @param community
     * @return
     */
    public List<VariableBinding> walk(OID oid, String community) {
        List<VariableBinding> ret = new ArrayList<VariableBinding>();

        PDU requestPDU = new PDU();
        requestPDU.add(new VariableBinding(oid));
        requestPDU.setType(PDU.GETNEXT);

        CommunityTarget target = new CommunityTarget(); // A CommunityTarget represents SNMP target properties for community based message processing models (SNMPv1 and SNMPv2c)
        target.setCommunity(new OctetString(community));
        Address address = GenericAddress.parse(this.getTransportProtocol() + ":" + this.getHost() + "/" + this.getSnmpPort());
        target.setAddress(address);
        target.setVersion(this.getSnmpVersion());
        target.setTimeout(this.getTimeout());

        try {
            TransportMapping transport;
            if (this.getTransportProtocol().equalsIgnoreCase("UDP")) {
                transport = new DefaultUdpTransportMapping();
            } else {
                transport = new DefaultTcpTransportMapping();
            }
            Snmp snmp = new Snmp(transport);
            transport.listen();

            boolean finished = false;

            while (!finished) {
                VariableBinding vb = null;

                ResponseEvent respEvt = snmp.send(requestPDU, target); // ResponseEvent associates a request PDU with the corresponding response and an optional user object.
                PDU responsePDU = respEvt.getResponse();
                if (responsePDU != null) {
                    vb = responsePDU.get(0);
                }

                if (responsePDU == null) {
                    finished = true;
                } else if (responsePDU.getErrorStatus() != 0) {
                    finished = true;
                } else if (vb.getOid() == null) {
                    finished = true;
                } else if (vb.getOid().size() < oid.size()) {
                    finished = true;
                } else if (oid.leftMostCompare(oid.size(), vb.getOid()) != 0) {
                    finished = true;
                } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
                    finished = true;
                } else if (vb.getOid().compareTo(oid) <= 0) {
                    finished = true;
                } else {
                    ret.add(vb);

                    // Set up the variable binding for the next entry.
                    requestPDU.setRequestID(new Integer32(0));
                    requestPDU.set(0, vb);
                }
            }
            snmp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public VariableBinding getNext(OID oid, String community) {
        VariableBinding ret = null;
        TransportMapping transport;
        try {
            //create transport
            if (this.getTransportProtocol().equalsIgnoreCase("UDP")) {
                transport = new DefaultUdpTransportMapping();
            } else {
                transport = new DefaultTcpTransportMapping();
            }
            CommunityTarget target = new CommunityTarget();
            target.setCommunity(new OctetString(community));
            Address address = GenericAddress.parse(this.getTransportProtocol() + ":" + this.getHost() + "/" + this.getSnmpPort());
            target.setAddress(address);
            target.setRetries(3);
            target.setTimeout(this.getTimeout());
            target.setVersion(this.getSnmpVersion());

            // create the PDU
            PDU pdu = new PDU();
            pdu.setType(PDU.GETNEXT);
            //put the oid you want to get
            pdu.add(new VariableBinding(oid));
            pdu.setNonRepeaters(0);

            Snmp snmp = new Snmp(transport);
            snmp.listen();

            // send the PDU
            ResponseEvent responseEvent = snmp.send(pdu, target);
            // extract the response PDU (could be null if timed out)
            PDU responsePDU = responseEvent.getResponse();
            Vector vbs = responsePDU.getVariableBindings();
            if (vbs.size() > 0) {
                ret = (VariableBinding) vbs.get(0);
            }
            snmp.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @return the transportProtocol
     */
    public String getTransportProtocol() {
        return transportProtocol;
    }

    /**
     * @param transportProtocol the transportProtocol to set
     */
    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    /**
     * @return the snmpPort
     */
    public int getSnmpPort() {
        return snmpPort;
    }

    /**
     * @param snmpPort the snmpPort to set
     */
    public void setSnmpPort(int snmpPort) {
        this.snmpPort = snmpPort;
    }

    /**
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * @return the retry
     */
    public int getRetry() {
        return retry;
    }

    /**
     * @param retry the retry to set
     */
    public void setRetry(int retry) {
        this.retry = retry;
    }
}
