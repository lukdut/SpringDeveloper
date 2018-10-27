package gerasimov.springdev.nosqllibrary.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Endpoint(id = "lastcreation")
public class LastAddedBookTimeEndpoint {
    private AtomicReference<AddedBookInfo> lastBook = new AtomicReference<>();

    void created() {
        lastBook.set(new AddedBookInfo());
    }

    @ReadOperation
    public AddedBookInfo get() {
        return lastBook.get();
    }

    public static class AddedBookInfo {
        private final LocalDateTime timestamp = LocalDateTime.now();

        AddedBookInfo() {
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }
    }
}
