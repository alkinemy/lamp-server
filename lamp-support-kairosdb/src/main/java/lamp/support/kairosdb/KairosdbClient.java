package lamp.support.kairosdb;

import org.kairosdb.client.Client;
import org.kairosdb.client.HttpClient;
import org.kairosdb.client.builder.QueryBuilder;
import org.kairosdb.client.response.QueryResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class KairosdbClient {

	private Client client;

	public KairosdbClient(KairosdbProperties kairosdbProperties) throws MalformedURLException {
		this.client = new HttpClient(kairosdbProperties.getUrl());
	}


	public QueryResponse query(QueryBuilder builder) throws URISyntaxException, IOException {
		return client.query(builder);
	}

}
