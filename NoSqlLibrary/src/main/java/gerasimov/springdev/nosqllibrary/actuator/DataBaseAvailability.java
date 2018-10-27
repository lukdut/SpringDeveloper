package gerasimov.springdev.nosqllibrary.actuator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;

import java.net.Socket;

@Service
public class DataBaseAvailability extends AbstractHealthIndicator {

    private final String host;
    private final int port;

    public DataBaseAvailability(@Value("${spring.data.mongodb.host}") String host, @Value("${spring.data.mongodb.port}") int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        if (serverListening(host, port)) {
            builder.up();
        } else {
            builder.down();
        }
    }

    public boolean serverListening(String host, int port) {
        try (Socket ignore = new Socket(host, port)) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
