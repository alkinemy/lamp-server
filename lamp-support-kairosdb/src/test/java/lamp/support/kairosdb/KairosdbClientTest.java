package lamp.support.kairosdb;

import org.junit.Test;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.builder.TimeUnit;
import org.kairosdb.client.builder.grouper.TagGrouper;
import org.kairosdb.client.response.QueryResponse;

public class KairosdbClientTest {

	@Test
	public void test() throws Exception {
		HttpClient client = new HttpClient("http://localhost:8080");
		QueryBuilder queryBuilder = QueryBuilder.getInstance();

			queryBuilder
				.setStart(5, TimeUnit.SECONDS)
//				.setEnd(1,)
				.addMetric("server.mem.actualFree")
			.addGrouper(new TagGrouper("host"))
			;
		QueryResponse response = client.query(queryBuilder);
		System.out.println(response.getBody());

	}
}