package org.example.resources;

import com.hivemq.spi.services.BlockingSubscriptionStore;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Path("/plugin")
public class SubscriptionResource {

    private final BlockingSubscriptionStore subscriptionStore;

    @Inject
    public SubscriptionResource(final BlockingSubscriptionStore subscriptionStore) {
        this.subscriptionStore = subscriptionStore;
    }

    @POST
    @Path("/serve")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getClients(List<String> topics) {
        log.info("Received request for topics: {}", topics);
        try {
            List<Set<String>> clientIds = topics.stream()
                                                .map(subscriptionStore::getSubscribers)
                                                .collect(Collectors.toList());
            log.info("Found {} subscriber(-s): [{}]", clientIds.size(), clientIds);
            return Response.ok()
                           .entity(clientIds.toString())
                           .build();
        } catch (Exception e) {
            log.error("Oophs, problem during processing", e);
            return Response.serverError().build();
        }
    }

}
