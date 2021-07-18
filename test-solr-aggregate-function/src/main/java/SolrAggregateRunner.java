import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * 
 * @author ARIJT BASU
 *
 */

public class SolrAggregateRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * Here we need to fetch the Minimum and Maximum Salary from the Employee index where attendence of the employee in the month >19
		 */
		
		// Create the client of Solr using SOLRJ
		final LBHttpSolrClient solrClient = new LBHttpSolrClient.Builder()
				.withBaseSolrUrl("http://localhost:8983/solr/employee").build();
		
		//Create Solr Query for Statistical data
		SolrQuery query = new SolrQuery();
		query.setQuery("attendence:[19 TO *]");
		query.add("stats.field","sal");
		query.set("stats", "true");
		query.setRows(0);
		
		QueryResponse response;
		try {
			//Query to Solr
			response = solrClient.query(query);
			//Parse the Solr Response and fetch/print the aggregate Function Values
			System.out.println("Response in String ::"+response.toString());
			System.out.println("Max Salary ::"+response.getFieldStatsInfo().get("sal").getMax().toString());
			System.out.println("Min Salary ::"+response.getFieldStatsInfo().get("sal").getMin().toString());
			System.out.println("Count of Employee whose attendence >19 ::"+response.getFieldStatsInfo().get("sal").getCount().toString());
		}
		catch (SolrServerException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
