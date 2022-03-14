package com.example.demo.websocket;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqConfig {

  @Bean
  public Queue queue() {
    return new Queue("channel.queue");
  }

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange("channel.exchange");
  }

  @Bean
  public Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with("msg.*");
  }

  @Bean
  public ConnectionFactory connectionFactory() {
    RabbitConnectionFactoryBean f = new RabbitConnectionFactoryBean();
    f.setHost("localhost");
    f.setUsername("guest");
    f.setPassword("guest"); // by default
    return new CachingConnectionFactory(f.getRabbitConnectionFactory());
  }

  @Bean
  public MessageConverter jackson2JsonMessageConverter(){
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate messageTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
    RabbitTemplate t = new RabbitTemplate(connectionFactory);
    t.setRoutingKey("channel.queue");
    t.setMessageConverter(messageConverter);
    return t;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectionFactory,
      SimpleRabbitListenerContainerFactoryConfigurer configurer,
      MessageConverter messageConverter) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    factory.setMessageConverter(messageConverter);
    factory.setRecoveryInterval(10000L);
    return factory;
  }


}
