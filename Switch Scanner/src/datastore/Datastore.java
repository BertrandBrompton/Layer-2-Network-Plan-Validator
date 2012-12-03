package datastore;
/*
 * http://docs.neo4j.org/chunked/stable/tutorials-java-embedded-hello-world.html
 */
import java.io.File;
import java.io.IOException;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

import scanner.Switch;

public class Datastore {
	private static final String DB_PATH = "target/neo4j-hello-db"; // what does this do?
	private GraphDatabaseService graphDb;
	private Node firstNode;
	private Node secondNode;
	private Relationship relationship;
	/*
	 *  KNOWS represents SNMP detecting another switch. It does not make assumptions on the exact relationship.
	 */
	private static enum RelTypes implements RelationshipType
	{
		// TODO: Does this relationship adequately portray the relationship I want (as opposed to have)
	    KNOWS
	}
	
	/**
	 * 
	 */
	public void createDb(){
		/*
		 * The next step is to start the database server. 
		 * Note that if the directory given for the database doesn’t already exist, it will be created.
		 * Note that starting a database server is an expensive operation...
		 * so don’t start up a new instance every time you need to interact with the database! 
		 * The instance can be shared by multiple threads. Transactions are thread confined.
		 */
		clearDb();
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
		registerShutdownHook( graphDb );
		
	}
    private void clearDb()
    {
        try
        {
            FileUtils.deleteRecursively( new File( DB_PATH ) );
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
    }
    
    /**
     * Only used for testing purposes.
     */
    public void createExampleNodes(){
		Transaction tx = graphDb.beginTx();
		try
		{
		    /*
		     * The first node is the root switch of the search.
		     */
			firstNode = graphDb.createNode();
			//TODO: Make it such that the data set for the root node is actually from the parameters passed in.
				firstNode.setProperty( "Switch Name", "CISCO ROOT1" );
				firstNode.setProperty( "IP ADDR", "x.x.x.x" );
				firstNode.setProperty( "MAC ADDR", "0f0f0f0f" );
			/*
			 * Second example node.	
			 */
				
			//TODO: Make it such that this switch is actually known or connected to the root switch.
			secondNode = graphDb.createNode();
			// TODO: Get the second switches actual information.
	            secondNode.setProperty( "Switch Name", "CISCO ROOT2" );
				secondNode.setProperty( "IP ADDR", "x.x.x.x" );
				secondNode.setProperty( "MAC ADDR", "0f0f0f0f" );
				
	        relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
	        relationship.setProperty( "Link Type", "ACCESS, " );
	        
	        System.out.println("First Node's Switch Name: " + firstNode.getProperty( "Switch Name" ) );
            System.out.println("It's link type is: " + relationship.getProperty( "Link Type" ) );
            //System.out.print( secondNode.getProperty( "message" ) );
	        
	        //greeting = ( (String) firstNode.getProperty( "message" ) )
            //        + ( (String) relationship.getProperty( "message" ) )
            //        + ( (String) secondNode.getProperty( "message" ) );
	        
		    tx.success(); // You need to mark the transaction as a success as the final line of code in the try-finally block. http://docs.neo4j.org/chunked/stable/transactions-interaction.html
		}
		finally
		{
		    tx.finish();
		}
    }
    /**
     * Shuts down the database.
     */
    public void shutDown()
    {
        System.out.println();
        System.out.println( "Shutting down database ..." );
        // START SNIPPET: shutdownServer
        graphDb.shutdown();
        // END SNIPPET: shutdownServer
    }
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    
    /**
     * Note that deleting a node which still has relationships when the transaction commits will fail. 
     * This is to make sure relationships always have a start node and an end node.
     */
    public void removeData()
    {
        Transaction tx = graphDb.beginTx();
        try
        {
            firstNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            firstNode.delete();
            secondNode.delete();
            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }
    
    public Node getRootNode(){
		return firstNode;
    	
    }
    // This function maybe in the wrong place, or at least not well designed since:
    // Ideally datastore should handle how to add relationships
    // But either way, this breaks encapsulation, so persistence and logic is blended
    public void storeSwitch(Switch network_switch){
    	
    }
}




