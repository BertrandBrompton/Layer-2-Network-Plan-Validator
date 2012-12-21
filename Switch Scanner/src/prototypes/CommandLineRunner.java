package prototypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineRunner {
	/**
	 * Runs a command on the command line. 
	 * http://stackoverflow.com/questions/8496494/running-command-line-in-java
	 * Indepth explanation: http://www.linglom.com/2007/06/06/how-to-run-command-line-or-execute-external-application-from-java/
	 * @param command
	 */
	public void run(String command){
		// "java -jar map.jar time.rel test.txt debug" as an example:
		// http://stackoverflow.com/questions/8496494/running-command-line-in-java
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			 
            String line=null;

            while((line=input.readLine()) != null) {
                System.out.println(line);
            }

            int exitVal = p.waitFor();
            System.out.println("Exited with error code "+exitVal);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
