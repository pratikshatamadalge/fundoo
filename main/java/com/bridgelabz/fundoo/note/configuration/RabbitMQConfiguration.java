package com.bridgelabz.fundoo.note.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.fundoo.utility.EmailSender;

@Configuration
public class RabbitMQConfiguration {

	String routingKey = "key";
	String queueExhcnage = "queue";

	@Bean
	Queue queue() {
		return new Queue(routingKey, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(queueExhcnage);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter messageListenerAdapter) {

		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(routingKey);
		container.setMessageListener(messageListenerAdapter);
		return container;
	}

	@Bean
	EmailSender sender() {
		return new EmailSender();
	}

	@Bean
	MessageListenerAdapter listenerAdapter(EmailSender emailSender) {
		return new MessageListenerAdapter(emailSender, "emailSender");
	}

}
