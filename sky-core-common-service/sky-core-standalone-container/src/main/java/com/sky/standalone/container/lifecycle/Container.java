package com.sky.standalone.container.lifecycle;

import com.sky.standalone.container.component.LifecycleComponent;
import com.sky.standalone.container.node.Node;

public interface Container extends LifecycleComponent<Container> {

    Node node();
}
