package handson;

import com.commercetools.api.client.ProjectApiRoot;
import handson.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;
import static handson.impl.ClientService.getStoreKey;


public class Task04c_CART {

    private static final Logger log = LoggerFactory.getLogger(Task04c_CART.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String supplyChannelKey = "boston-store-channel";
        final String distChannelKey = "boston-store-channel";
        final String cartId = "";

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();
        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");
            final String storeKey = getStoreKey(apiClientPrefix);

            CartService cartService = new CartService(apiRoot, storeKey);
            StoreService storeService = new StoreService(apiRoot, storeKey);


//            // TODO: Perform cart operations:
//            //  TODO Create an anonymous cart and add a product to it
//            //
//            storeService.getCurrentStore()
//                    .thenComposeAsync(storeApiHttpResponse ->
//                            cartService.createAnonymousCart(storeApiHttpResponse, "AAR-34", 3L, supplyChannelKey, distChannelKey))
//                    .thenAccept(cartApiHttpResponse -> logger.info("cart created {}", cartApiHttpResponse.getBody().getId()))
//                    .exceptionally(throwable -> {
//                        logger.error("Exception: {}", throwable.getMessage());
//                        return null;
//                    }).join();

            // TODO UPDATE cartId variable above

            // TODO ADD a shipping address to the cart
            //

            // TODO Recalculate cart
            //

            // TODO Freeze cart
            //


//            // TODO Unfreeze cart
//            //
//            cartService.getCartById(cartId)
//                    .thenComposeAsync(cartService::unfreezeCart)
//                    .thenAccept(cartApiHttpResponse -> logger.info("cart is now in active state {}", cartApiHttpResponse.getBody().getId()))
//                    .exceptionally(throwable -> {
//                        logger.error("Exception: {}", throwable.getMessage());
//                        return null;
//                    }).join();

        }
    }
}
