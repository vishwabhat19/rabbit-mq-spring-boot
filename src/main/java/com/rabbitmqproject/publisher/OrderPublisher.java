package com.rabbitmqproject.publisher;

import com.rabbitmqproject.config.MessagingConfig;
import com.rabbitmqproject.dto.Order;
import com.rabbitmqproject.dto.OrderStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderPublisher
{
	@Autowired
	private RabbitTemplate template;

	@PostMapping("/{restuarantName}")
	public String bookOrder(@RequestBody Order order, @PathVariable String restuarantName) {
		order.setOrderId(UUID.randomUUID().toString());
		OrderStatus orderStatus = new OrderStatus(order, "IN PROCESS", "Order placed successfully in "+restuarantName);
		template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, orderStatus);
		return "Success!";
	}
}
