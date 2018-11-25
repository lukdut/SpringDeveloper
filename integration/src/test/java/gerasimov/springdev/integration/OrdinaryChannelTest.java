package gerasimov.springdev.integration;

import gerasimov.springdev.integration.model.Order;
import gerasimov.springdev.integration.service.OrderInputGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static gerasimov.springdev.integration.IntegrationApplication.VIP_THRESHOLD;
import static gerasimov.springdev.integration.util.TestOrderProvider.onePosOrder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdinaryChannelTest {
    @Autowired
    private OrderInputGateway inputGateway;

    @Autowired
    @Qualifier("ordinaryOrders")
    private AbstractMessageChannel vipOrders;

    @Test
    @DirtiesContext
    public void emptyOrderLoads() throws Exception {
        inputGateway.receiveOrder(new Order());
        Assert.assertEquals(0, vipOrders.getSendRate().getCount());
    }

    @Test
    @DirtiesContext
    public void vipOrderLoads() throws Exception {
        inputGateway.receiveOrder(onePosOrder(1, VIP_THRESHOLD + 1));
        Assert.assertEquals(0, vipOrders.getSendRate().getCount());
    }

    @Test
    @DirtiesContext
    public void casualOrderLoads() throws Exception {
        inputGateway.receiveOrder(onePosOrder(1, 10));
        Assert.assertEquals(1, vipOrders.getSendRate().getCount());
    }
}
