package handson;

import com.commercetools.api.client.ProjectApiRoot;
import handson.impl.ApiPrefixHelper;
import handson.impl.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;
import static handson.impl.ClientService.getStoreKey;


public class Task05c_ORDER_REPLICATE {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();

        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            final String storeKey = getStoreKey(apiClientPrefix);
            CartService cartService = new CartService(apiRoot, storeKey);

            final String orderNumber = "";

            // TODO: REPLICATE your last order

            cartService.replicateOrderByOrderNumber(orderNumber)
                    .thenAccept(cartApiHttpResponse ->
                            logger.info("cart {} created", cartApiHttpResponse.getBody().getId())
                    )
                    .exceptionally(throwable -> {
                        logger.error("Exception: {}", throwable.getMessage());
                        return null;
                    }).join();
        }
    }
}
