package lamp.support.kairosdb;

import org.kairosdb.client.Client;
import org.kairosdb.client.HttpClient;

import java.net.MalformedURLException;

public class KairosdbClient {

	private Client client;

	public KairosdbClient(KairosdbProperties kairosdbProperties) throws MalformedURLException {
		this.client = new HttpClient(kairosdbProperties.getUrl());
	}


}
