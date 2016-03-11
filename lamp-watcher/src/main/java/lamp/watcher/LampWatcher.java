package lamp.watcher;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import scala.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class LampWatcher {

	private static final String PID_FILE_NAME = "lamp-watcher.pid";

	public static void main(String[] args) throws Exception {
		SpringApplication springApplication = new SpringApplication(LampWatcher.class);
		springApplication.setBanner(new LampWatcherBanner());
		springApplication.addListeners(new ApplicationPidFileWriter(PID_FILE_NAME));
		springApplication.run(args);


		SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("Hello World");
		JavaStreamingContext streamingContext = new JavaStreamingContext(conf, Durations.seconds(60));
		int numThreads = 1;
		Map<String, Integer> topicMap = new HashMap<>();
		String[] topics = {"metrics"};
		for (String topic: topics) {
			topicMap.put(topic, numThreads);
		}

//		Map<String, String> kafkaParams = new HashMap<>();
//		kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:2181");
//		kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, "spark");

		JavaPairReceiverInputDStream<String, String> kafkaStream =
			KafkaUtils.createStream(streamingContext,
				"localhost:2181", "spark", topicMap);

		JavaDStream<String> lines = kafkaStream.map(new Function<Tuple2<String, String>, String>() {
			@Override
			public String call(Tuple2<String, String> tuple2) {
				return "Xyz"; // tuple2._1();
			}
		});

		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public Iterable<String> call(String x) {
				return Arrays.asList(x);
			}
		});

		JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
			new PairFunction<String, String, Integer>() {
				@Override
				public Tuple2<String, Integer> call(String s) {
					return new Tuple2<>(s, 1);
				}
			}).reduceByKey(new Function2<Integer, Integer, Integer>() {
			@Override
			public Integer call(Integer i1, Integer i2) {
				return i1 + i2;
			}
		});

		wordCounts.print();
		streamingContext.start();
		streamingContext.awaitTermination();
	}

}
