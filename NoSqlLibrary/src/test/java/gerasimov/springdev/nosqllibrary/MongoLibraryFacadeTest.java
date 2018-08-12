package gerasimov.springdev.nosqllibrary;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import gerasimov.springdev.nosqllibrary.facade.LibraryFacade;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoLibraryFacadeTest {

    @Autowired
    LibraryFacade libraryFacade;

    @Test
    @Ignore
    public void test() {
        Assert.assertTrue(libraryFacade.findBook("none").length() < 5);
        libraryFacade.addBook("book", Collections.singletonList("author"), Collections.singletonList("genre"));
        Assert.assertTrue(libraryFacade.findBook("book").contains("author"));
        Assert.assertTrue(libraryFacade.showBookInfo("book").contains("genre"));
    }

    @Configuration
    static class SpringTestConfiguration {
        @Bean
        public MongoTemplate mongoTemplate() throws IOException {
            String ip = "localhost";
            int port = 27017;

            IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                    .net(new Net(ip, port, Network.localhostIsIPv6())).build();

            MongodStarter starter = MongodStarter.getDefaultInstance();
            MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
            //Caused by: java.io.IOException: Could not start process: <EOF>
            mongodExecutable.start();
            return new MongoTemplate(new MongoClient(ip, port), "test");
        }
    }
}
