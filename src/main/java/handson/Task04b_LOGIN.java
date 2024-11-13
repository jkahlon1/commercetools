package handson;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.Address;
import com.commercetools.api.models.common.AddressBuilder;
import com.commercetools.api.models.customer.AnonymousCartSignInMode;
import com.commercetools.api.models.order.OrderState;
import handson.impl.*;
import io.vrap.rmf.base.client.ApiHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;
import static handson.impl.ClientService.getStoreKey;


public class Task04b_LOGIN {

    private static final Logger log = LoggerFactory.getLogger(Task04b_LOGIN.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();
        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            final String storeKey = getStoreKey(apiClientPrefix);

            CartService cartService = new CartService(apiRoot, storeKey);
            CustomerService customerService = new CustomerService(apiRoot, storeKey);

            // TODO: Fetch a channel if your inventory mode will not be NONE
            //
            final String cartId = "";
            final String customerKey = "ct-customer";
            final String customerEmail = "ct@example.com";

            //  TODO: LOGIN customer or signup, if not found
            //
            customerService.loginCustomer(
                    customerEmail,
                    "password",
                    cartId,
                    AnonymousCartSignInMode.USE_AS_NEW_ACTIVE_CUSTOMER_CART
                )
                .exceptionally(ex -> {
                    logger.info("exception: {}", ex.getMessage());
                    try {
                        return customerService.createCustomer(
                            customerEmail,
                            "password",
                            cartId
                        ).get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenAccept(customerSignInResult -> {
                    logger.info("Login successful {}", customerSignInResult.getBody().getCustomer().getEmail());
                }).join();

//            // TODO: ADD shipping address from customer profile
//            // TODO Add a new address if no default shipping address found
//            //
//            customerService
//                .getCustomerByKey(customerKey)
//                .thenApply(ApiHttpResponse::getBody)
//                .thenApply(customer -> customer.getAddresses().stream()
//                    .filter(address -> address.getId().equals(customer.getDefaultShippingAddressId()))
//                    .findFirst()
//                )
//                .thenAccept(optionalAddress -> {
//                    Address shippingAddress = optionalAddress.orElseGet(() -> AddressBuilder.of()
//                        .firstName("commercetools")
//                        .lastName("Customer")
//                        .country("DE")
//                        .key(customerKey + "-default")
//                        .build()
//                    );
//                    if(!optionalAddress.isPresent()) {
//                        try {
//                            logger.info("Customer address added and set as default billing and shipping address:"
//                                + customerService.addDefaultShippingAddressToCustomer(customerKey, shippingAddress)
//                                .get().getBody().getEmail()
//                            );
//                        } catch (Exception e) {throw new RuntimeException(e);}
//                    }
//
//                    cartService.getCartById(cartId)
//                        .thenComposeAsync(cartApiHttpResponse -> cartService.setShippingAddress(cartApiHttpResponse, shippingAddress))
//                        .thenComposeAsync(cartService::setShipping)
//                        .thenComposeAsync(cartService::recalculate)
//                        .thenAccept(cartApiHttpResponse -> {
//                            logger.info("cart updated with shipping info {}", cartApiHttpResponse.getBody().getId());
//                        })
//                        .exceptionally(throwable -> {
//                            logger.error("Exception: {}", throwable.getMessage());
//                            return null;
//                        }).join();
//                })
//                .exceptionally(ex -> {
//                    logger.error("Error retrieving customer: {}", ex.getMessage());
//                    return null;
//                }).join();
        }
    }
}
