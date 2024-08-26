package logback.kafka;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;
import com.github.danielwegener.logback.kafka.keying.KeyingStrategy;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * Used key format [host-port-application] for kafka
 */
public class KafkaAggregateLogsKeyingStrategy extends ContextAwareBase implements KeyingStrategy<Object>, LifeCycle {
    private byte[] kafkaKeyHash = null;
    private boolean errorWasShown = false;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
        String hostName ="";
        try {
            hostName = Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        final String hostPort = context.getProperty("port");
        final String applicationId = context.getProperty("applicationId");

        if (hostName == null || hostPort == null || applicationId == null) {
            if (!errorWasShown) {
                addError("Hostname/hostport/applicationId could not be found in context. HostNamePartitioningStrategy will not work.");
                errorWasShown = true;
            }
        } else {
            String keys = hostName + "-" + hostPort + "-" + applicationId;
            System.out.println("keys ====>"+keys);
            kafkaKeyHash = ByteBuffer.allocate(4).putInt(keys.hashCode()).array();
        }
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        errorWasShown = false;
    }

    @Override
    public boolean isStarted() {
        return true;
    }

    @Override
    public byte[] createKey(Object o) {

        return kafkaKeyHash;
    }
/*    @Override
    public byte[] createKey(ILoggingEvent iLoggingEvent) {
        String msg = iLoggingEvent.getLevel().toString() +iLoggingEvent.getLoggerName();
        return ByteBuffer.allocate(4).putInt(msg.hashCode()).array();
    }*/
}
