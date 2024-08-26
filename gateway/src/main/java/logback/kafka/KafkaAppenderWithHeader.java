package logback.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.github.danielwegener.logback.kafka.KafkaAppender;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * add the headers to the kafka producer record
 * @param <E>
 */
public class KafkaAppenderWithHeader<E> extends KafkaAppender<ILoggingEvent> {
    protected void append(ILoggingEvent e) {
        final byte[] payload = encoder.encode(e);
        final byte[] key = keyingStrategy.createKey(e);

        final Long timestamp = isAppendTimestamp() ? getTimestamp(e) : null;

        //add headers for kafka
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("level",e.getLevel().toString().getBytes()));
        Map<String, String> copyOfPropertyMap = encoder.getContext().getCopyOfPropertyMap();
        if(copyOfPropertyMap != null){
            copyOfPropertyMap.entrySet().forEach(c-> headers.add(new RecordHeader(c.getKey(), c.getValue().getBytes())));
        }
        //add headers for kafka

        final ProducerRecord<byte[], byte[]> record = new ProducerRecord<>(topic, partition, timestamp, key, payload, headers);

        final Producer<byte[], byte[]> producer = lazyProducer.get();
        if (producer != null) {
            deliveryStrategy.send(lazyProducer.get(), record, e, failedDeliveryCallback);
        } else {
            failedDeliveryCallback.onFailedDelivery(e, null);
        }
    }
}
