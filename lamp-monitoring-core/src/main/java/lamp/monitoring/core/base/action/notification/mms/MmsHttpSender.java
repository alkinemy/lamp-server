package lamp.monitoring.core.base.action.notification.mms;

import lamp.monitoring.core.base.action.notification.NotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.IOException;

@Slf4j
public class MmsHttpSender implements NotificationSender<MmsMessage> {

	private static final SpelExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

	private MmsSenderProperties properties;

	public MmsHttpSender(MmsSenderProperties properties) {
		this.properties = properties;
	}

	public void send(MmsMessage mmsMessage) throws IOException {
		log.debug("MmsHttpNotifier send : {}", mmsMessage);
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpUriRequest request = null;
			if ("post".equalsIgnoreCase(properties.getMethod())) {
				Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(properties.getContent(), new TemplateParserContext("#{", "}"));
				StandardEvaluationContext evaluationContext = new StandardEvaluationContext(mmsMessage);
				String data = expression.getValue(evaluationContext, String.class);

				HttpPost httpPost = new HttpPost(properties.getUrl());
				httpPost.setEntity(new StringEntity(data, ContentType.create(properties.getContentType(), properties.getCharset())));
				request = httpPost;
			} else if ("get".equalsIgnoreCase(properties.getMethod())) {
				HttpGet httpGet = new HttpGet(properties.getUrl());
				request = httpGet;
			}

			try (CloseableHttpResponse response = httpclient.execute(request)) {
				int statusCode = response.getStatusLine().getStatusCode();
			}
		}
	}
}
