package microservices.book.logs;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class LogsConsumer {
    @KafkaListener(topics = "logs")
    public void log(ConsumerRecord<byte[], byte[]> record,
                    @Header("level") String level,
                    @Header("applicationId") String appId) {
        byte[] messageBytes = record.value();
        String msg = new String(messageBytes);
        Marker marker = MarkerFactory.getMarker(appId);
        switch (level) {
            case "INFO" -> log.info(marker, msg);
            case "ERROR" -> log.error(marker, msg);
            case "WARN" -> log.warn(marker, msg);
        }
    }
}
