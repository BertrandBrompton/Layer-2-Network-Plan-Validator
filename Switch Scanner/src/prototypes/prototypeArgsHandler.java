package prototypes;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;


public class prototypeArgsHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		/*
		 * Config settings
		 */
		Boolean writeToFile = false; 
		
		CommandLineParser parser = new PosixParser();
		try {
			CommandLine cmd = parser.parse(createOptions(), args);
			if(cmd.hasOption("i")){
				// interactive mode.
			} else
			if(cmd.hasOption("c") && cmd.hasOption("a")){
			    System.out.println("-c and -a are mutually exclusive.");
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( "l2npv", createOptions(), true);				
			} else if(cmd.hasOption("c")) {
				System.out.println("Lead Project Developer: Bertrand Brompton. beb2@aber.ac.uk");
			    //L2Scanner l2s = new L2Scanner();
			    //l2s.snmpGet(args[1], args[2], args[3]);
			} 
			if (cmd.hasOption("v")) {
				System.out.println("Version number: 0.01");
			}
			if (cmd.hasOption("f")) {
				writeToFile = true;
				System.out.println(cmd.getOptionValue("f"));
			}
			if (cmd.hasOption("s")) {
				// scan switch
			}
			if (cmd.hasOption("a")){
				// span layer 2 topology
			} else {
			    System.out.println("No options found");
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( "l2npv", createOptions(), true);				
			}
		} catch (ParseException exp) {
			System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
		} finally {
			
		}
	}
	private static Options createOptions() {
		/*
		 * Definitions stage
		 */
	    Options mOptions = new Options();
	    OptionGroup og = new OptionGroup();
	    
	    mOptions.addOption("i", false, "interactive mode. Ignores any params used.");

	    OptionBuilder.hasArgs(3);
	    OptionBuilder.withArgName("hostname> <community> <oid");
	    OptionBuilder.withDescription("scans a single switch; does not span. Mutually exclusive with -a");
	    Option my_s = OptionBuilder.create("s");

	    OptionBuilder.hasArgs(3);
	    OptionBuilder.withArgName("hostname> <community> <oid");
	    OptionBuilder.withDescription("spans switch topology. Mutually exclusive with -s");
	    Option my_a = OptionBuilder.create("a");
	    

	    og.addOption(my_a);
	    og.addOption(my_s);	  
	    mOptions.addOptionGroup(og);
	    
	    OptionBuilder.hasArg(true);
	    OptionBuilder.withArgName("filename");
	    OptionBuilder.withDescription("prints to file; else follows default datastore procedure");
	    mOptions.addOption(OptionBuilder.create("f"));

	    OptionBuilder.hasArg(true);
	    OptionBuilder.withArgName("filename");
	    OptionBuilder.withDescription("use properties file; for Mac OSX compliance"); // doing
	    mOptions.addOption(OptionBuilder.create("p"));
	    
	    mOptions.addOption("d", false, "debug mode");
	    mOptions.addOption("v", false, "prints version and exit");
	    mOptions.addOption("q", false, "quiet mode");
	    mOptions.addOption("c", false, "prints contact information and exit");
	    return mOptions;
	}
}
