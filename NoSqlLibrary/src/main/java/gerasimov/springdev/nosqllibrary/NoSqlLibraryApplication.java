package gerasimov.springdev.nosqllibrary;

import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class NoSqlLibraryApplication extends AbstractReactiveMongoConfiguration {
    //add book --authors author --genres genre
    public static void main(String[] args) {
        SpringApplication.run(NoSqlLibraryApplication.class, args);
    }


    @Override
    public com.mongodb.reactivestreams.client.MongoClient reactiveMongoClient() {
        return MongoClients.create();
    }

    @Override
    protected String getDatabaseName() {
        return "library";
    }
}
