package works.weave.socks.orders.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import works.weave.socks.orders.config.OrdersConfigurationProperties;
import works.weave.socks.orders.entities.CustomerOrder;
import works.weave.socks.orders.entities.Item;
import works.weave.socks.orders.repositories.CustomerOrderRepository;

@RestController
public class OrdersController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrdersConfigurationProperties config;

	@Autowired
	private CustomerOrderRepository customerOrderRepository;

	@Value(value = "${http.timeout:5}")
	private long timeout;

	@PostMapping("/amount")
	public @ResponseBody String postAmount(@RequestBody List<Item> items) {
		return Float.toString(calculateTotal(items));
	}

	@PostMapping("/save")
	public @ResponseBody CustomerOrder postSaveOrder(@RequestBody CustomerOrder order) {
		CustomerOrder savedOrder = customerOrderRepository.save(order);
		return savedOrder;
	}

	private float calculateTotal(List<Item> items) {
		float amount = 0F;
		float shipping = 4.99F;
		amount += items.stream().mapToDouble(i -> i.getQuantity() * i.getUnitPrice()).sum();
		amount += shipping;
		return amount;
	}
}
