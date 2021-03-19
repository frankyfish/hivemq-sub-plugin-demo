/*
 * Copyright 2015 dc-square GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.example.plugin;

import com.hivemq.spi.services.rest.RESTService;
import com.hivemq.spi.services.rest.listener.HttpListener;
import org.example.callbacks.*;
import com.hivemq.spi.PluginEntryPoint;
import com.hivemq.spi.callback.events.OnConnectCallback;
import com.hivemq.spi.callback.registry.CallbackRegistry;
import org.example.resources.SubscriptionResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * This is the main class of the plugin, which is instanciated during the HiveMQ start up process.
 *
 * @author Christian Götz
 */
public class HelloWorldMainClass extends PluginEntryPoint {

    Logger log = LoggerFactory.getLogger(HelloWorldMainClass.class);

    private final RESTService restService;

    /**
     * This is the injected constructor.
     */
    @Inject
    public HelloWorldMainClass(final RESTService restService) {
        this.restService = restService;
    }

    /**
     * This method is executed after the instanciation of the whole class. It is used to initialize
     * the implemented callbacks and make them known to the HiveMQ core.
     */
    @PostConstruct
    public void postConstruct() {
        log.info("Starting http listener");
        HttpListener listener
                = new HttpListener(
                "sub-plugin-demo",
                "0.0.0.0",
                9362);
        restService.addListener(listener);
        restService.addJaxRsResources(SubscriptionResource.class);
    }
}
