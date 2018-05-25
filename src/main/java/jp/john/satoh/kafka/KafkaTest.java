package jp.john.satoh.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Arrays;
import java.util.Properties;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class KafkaTest {

    public static class KafkaSender {
        private KafkaProducer<Long, Message> producer;
        private String topic = "jjug-2018";

        KafkaSender(Properties properties) {
            producer = new KafkaProducer<>(properties);
        }

        void send(String message) {
            Message dto = new Message();
            dto.message = message;

            producer.send(new ProducerRecord<>(
                    topic,
                    System.currentTimeMillis(),
                    dto
            ), (recordMetadata, e) -> {
                if (e != null) System.out.println(e.getMessage());
                if (recordMetadata != null) System.out.println(recordMetadata);
            });
            producer.flush();
        }
    }

    public static void main(String[] args) {

        String kafkaEndpoint = "127.0.0.1:9092";
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaEndpoint);

        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        KafkaSender kafkaSender = new KafkaSender(properties);
        Stream.of(args).forEachOrdered(kafkaSender::send);
    }
}
