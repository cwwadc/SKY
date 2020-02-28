package com.sky.standalone.container.node;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.sky.base.lang.sequence.UUID;
import com.sky.standalone.container.component.LifecycleComponent;


public interface Node extends LifecycleComponent<Node> {

    String ID = UUID.toString(UUID.random());

    /**
     * Returns the node name in a human format
     *
     * @return The node name in a human format
     */
    String name();

    /**
     * Returns the node name in a technical format
     *
     * @return The node name in a technical format
     */
    String application();

    /**
     * Returns the node id.
     *
     * @return The node id.
     */
    default String id() {
        return ID;
    }

    default List<Class<? extends LifecycleComponent>> components() {
        return Collections.emptyList();
    }

    default String hostname() {
        return "";
    }

    default Map<String, Object> metadata() {
        return Collections.emptyMap();
    }
}
