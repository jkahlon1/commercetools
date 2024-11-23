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


            // TODO: Perform cart operations: add products to a new anonymous cart
            //
            storeService.getCurrentStore()
                    .thenComposeAsync(storeApiHttpResponse ->
                            cartService.createAnonymousCart(storeApiHttpResponse, "AAR-34", 3L, supplyChannelKey, distChannelKey))
                    .thenAccept(cartApiHttpResponse -> logger.info("cart created {}", cartApiHttpResponse.getBody().getId()))
                    .exceptionally(throwable -> {
                        logger.error("Exception: {}", throwable.getMessage());
                        return null;
                    }).join();

//            // TODO Add a new shipping address
//            //
//            Address shippingAddress = AddressBuilder.of()
//                    .firstName("commercetools")
//                    .lastName("Customer")
//                    .country("DE")
//                    .key("default-address")
//                    .build();
//
//            cartService.getCartById(cartId)
//                    .thenComposeAsync(cartApiHttpResponse -> cartService.addShippingAddress(cartApiHttpResponse, shippingAddress))
//                    .thenAccept(cartApiHttpResponse -> {
//                        logger.info("cart updated with shipping info {}", cartApiHttpResponse.getBody().getId());
//                    })
//                    .exceptionally(throwable -> {
//                        logger.error("Exception: {}", throwable.getMessage());
//                        return null;
//                    }).join();

//            // TODO Freeze cart
//            //
//            cartService.getCartById(cartId)
//                    .thenComposeAsync(cartService::freezeCart)
//                    .thenAccept(cartApiHttpResponse -> logger.info("cart is now in frozen state {}", cartApiHttpResponse.getBody().getId()))
//                    .exceptionally(throwable -> {
//                        logger.error("Exception: {}", throwable.getMessage());
//                        return null;
//                    }).join();

//            // TODO Unfreeze cart
//            //
//            cartService.getCartById(cartId)
//                    .thenComposeAsync(cartService::unfreezeCart)
//                    .thenAccept(cartApiHttpResponse -> logger.info("cart is now in active state {}", cartApiHttpResponse.getBody().getId()))
//                    .exceptionally(throwable -> {
//                        logger.error("Exception: {}", throwable.getMessage());
//                        return null;
//                    }).join();

//            // TODO Recalculate cart
//            //
//            cartService.getCartById(cartId)
//                    .thenComposeAsync(cartService::recalculate)
//                    .thenAccept(cartApiHttpResponse -> logger.info("cart has been recalculated {}", cartApiHttpResponse.getBody().getId()))
//                    .exceptionally(throwable -> {
//                        logger.error("Exception: {}", throwable.getMessage());
//                        return null;
//                    }).join();
        }
    }
}
