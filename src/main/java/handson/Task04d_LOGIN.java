package handson;

import com.commercetools.api.client.ProjectApiRoot;
import handson.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;
import static handson.impl.ClientService.getStoreKey;


public class Task04d_LOGIN {

    private static final Logger log = LoggerFactory.getLogger(Task04d_LOGIN.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();
        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            final String storeKey = getStoreKey(apiClientPrefix);

            CartService cartService = new CartService(apiRoot, storeKey);
            CustomerService customerService = new CustomerService(apiRoot, storeKey);

            final String cartId = "";
            final String customerKey = "ct-customer";
            final String customerEmail = "ct@example.com";


            //  TODO: LOGIN customer or signup, if not found
            //




//            // TODO: GET default shipping address from customer profile
//            // TODO Optionally add a new address and save it as default for the customer
//            //
//            customerService
//                    .getCustomerByKey(customerKey)
//                    .thenApply(ApiHttpResponse::getBody)
//                    .thenApply(customer -> customer.getAddresses().stream()
//                            .filter(address -> address.getId().equals(customer.getDefaultShippingAddressId()))
//                            .findFirst()
//                    )
//                    .thenAccept(optionalAddress -> {
//                        Address shippingAddress = optionalAddress.orElseGet(() -> AddressBuilder.of()
//                                .firstName("First")
//                                .lastName("Tester")
//                                .country("DE")
//                                .key(customerKey + "-default")
//                                .build()
//                        );
//                        if(!optionalAddress.isPresent()) {
//                            try {
//                                logger.info("Customer address added and set as default billing and shipping address:"
//                                        + customerService.addAddressToCustomer(customerKey, shippingAddress)
//                                        .get().getBody().getEmail()
//                                );
//                            } catch (Exception e) {throw new RuntimeException(e);}
//                        }
//                        // TODO: UPDATE cart shipping address
//                        // TODO: SET default shipping method
//                        // TODO: RECALCULATE cart
//                    })
//                    .exceptionally(ex -> {
//                        logger.error("Error retrieving customer: {}", ex.getMessage());
//                        return null;
//                    }).join();

        }
    }
}
