package handson;

import com.commercetools.api.client.ProjectApiRoot;
import handson.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;
import static handson.impl.ClientService.getStoreKey;


public class Task05b_ORDEREDITS {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();

        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            final String storeKey = getStoreKey(apiClientPrefix);
            OrderService orderService = new OrderService(apiRoot, storeKey);

            final String orderNumber = "";
            final String orderEditKey = "";

            // TODO: CREATE staged order update action for OrderEdit

            // final StagedOrderUpdateAction stagedOrderUpdateAction = StagedOrderUpdateActionBuilder.of()
            //        .build();

            // TODO: GET order and Create an Order Edit to update it

            // TODO: update orderEditKey above

//            //  TODO: Apply OrderEdit
//            //
//            orderService.getOrderEditByKey(orderEditKey)
//                    .thenComposeAsync(orderService::applyOrderEdit)
//                    .thenAccept(orderEditApiHttpResponse ->
//                            logger.info("orderEdit {} ", orderEditApiHttpResponse.getBody().getResult().getType())
//                    )
//                    .exceptionally(throwable -> {
//                        logger.error("Exception: {}", throwable.getMessage());
//                        return null;
//                    }).join();
        }
    }
}
