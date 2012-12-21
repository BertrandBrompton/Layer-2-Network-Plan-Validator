package prototypes;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.junit.Test;

public class Connectivity {
	/**
	 * 	
	 * @param host
	 * @return
	 */
	public Boolean checkBasic(String host){
		Boolean success = false;
		Socket socket = null;
		try{
			socket = new Socket(host, 80); // This does not explicitly tests port 80. It is just to avoid getting false negatives from Fire-wall blocking other ports.
			success = true;
		} catch (UnknownHostException e) {
			success = false;
			e.printStackTrace();
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		} finally {
			if (socket != null){ 
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		return success;		
	}
	
	/**
	 * An improved method for checking if connectivity is possible. 
	 * 
	 * Problems:
	 * 	bordet.blogspot.co.uk/2006/07/icmp-and-inetaddressisreachable.html
	 * 	www.coderanch.com/t/206934/sockets/java/InetAdress-isReachable-Ping-Permissions
	 * @param host
	 * @return
	 */
	public Boolean checkAdvanced(String host){
		try {
			return InetAddress.getByName(host).isReachable(10000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false; // Multiple returns. Bad style?		
	}
	
	@Test public Boolean connectInternet(String host){
		Boolean success = false;
		try {
			URL url = new URL(host);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			assertEquals(HttpURLConnection.HTTP_OK, conn.getResponseCode());
			success = true;
		} catch (IOException e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
}
