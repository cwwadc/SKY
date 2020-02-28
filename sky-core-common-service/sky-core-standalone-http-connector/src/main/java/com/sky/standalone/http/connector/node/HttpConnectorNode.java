package com.sky.standalone.http.connector.node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sky.standalone.container.component.LifecycleComponent;
import com.sky.standalone.container.node.AbstractNode;
import com.sky.standalone.http.connector.netty.NettyEmbeddedContainer;

public class HttpConnectorNode extends AbstractNode {

    @Override
    public String name() {
        return "SKY - HTTP API Connector";
    }

    @Override
    public String application() {
        return "Sky-HTTP API Connector";
    }

    @Override
    public Map<String, Object> metadata() {
        Map<String, Object> metadata = new HashMap<>();
        return metadata;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<Class<? extends LifecycleComponent>> components() {
    	
        final List<Class<? extends LifecycleComponent>> components = new ArrayList<>();
        components.add(NettyEmbeddedContainer.class);
        components.addAll(super.components());
        
        return components;
    }
}
