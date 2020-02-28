package com.sky.standalone.http.connector.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.sky.core.service.api.configuration.HttpApiConfiguration;
import com.sky.standalone.container.node.Node;
import com.sky.standalone.http.connector.node.HttpConnectorNode;

@Configuration
@Import({
		  NettyHttpServerConfiguration.class,
		  HttpApiConfiguration.class
})
public class HttpConnectorConfiguration {

    @Bean
    public Node node() {
        return new HttpConnectorNode();
    }
    

}
