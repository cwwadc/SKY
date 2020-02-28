package com.sky.core.service.api.utils;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RealtimeTranscationReceiver {

	/*
	 * @RabbitListener(bindings = @QueueBinding( value = @Queue(value =
	 * "rabbit.queue.synchronizedTrading", durable = "true"), exchange
	 * = @Exchange(name = "", durable = "true", type = "topic"), key = "order.*" ) )
	 */
    @RabbitHandler
    public void onOrderMessage(@Payload String order, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        Long delivertTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(delivertTag, false);
    }
}
