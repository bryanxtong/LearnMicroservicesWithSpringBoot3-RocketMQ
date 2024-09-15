package microservices.book.logs;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RocketMQMessageListener(nameServer ="localhost:9876",consumerGroup = "logs", topic = "logs", selectorType = SelectorType.TAG )
public class LogsConsumer implements RocketMQListener<Message> {
    public void onMessage(Message message) {
        String level = message.getProperty("level");
        String appId = message.getProperty("applicationId");
        Marker marker = MarkerFactory.getMarker(appId);
        String msg = new String(message.getBody());
        switch (level) {
            case "INFO" -> log.info(marker, msg);
            case "ERROR" -> log.error(marker, msg);
            case "WARN" -> log.warn(marker, msg);
        }
    }
}
