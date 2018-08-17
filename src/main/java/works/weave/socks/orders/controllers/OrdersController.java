package works.weave.socks.orders.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import works.weave.socks.orders.config.OrdersConfigurationProperties;
import works.weave.socks.orders.entities.Item;
import works.weave.socks.orders.repositories.CustomerOrderRepository;
import works.weave.socks.orders.services.AsyncGetService;

@RestController
public class OrdersController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrdersConfigurationProperties config;

	@Autowired
	private AsyncGetService asyncGetService;

	@Autowired
	private CustomerOrderRepository customerOrderRepository;

	@Value(value = "${http.timeout:5}")
	private long timeout;

	private String parseId(String href) {
		Pattern idPattern = Pattern.compile("[\\w-]+$");
		Matcher matcher = idPattern.matcher(href);
		if (!matcher.find()) {
			throw new IllegalStateException("Could not parse user ID from: " + href);
		}
		return matcher.group(0);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/amount")
	public @ResponseBody String getAmount() {
		return "100";
	}

	private float calculateTotal(List<Item> items) {
		float amount = 0F;
		float shipping = 4.99F;
		amount += items.stream().mapToDouble(i -> i.getQuantity() * i.getUnitPrice()).sum();
		amount += shipping;
		return amount;
	}
}
