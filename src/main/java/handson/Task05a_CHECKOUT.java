package handson;

import com.commercetools.api.client.ProjectApiRoot;
import handson.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;
import static handson.impl.ClientService.getStoreKey;


public class Task05a_CHECKOUT {

    private static final Logger log = LoggerFactory.getLogger(Task05a_CHECKOUT.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();
        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            final String storeKey = getStoreKey(apiClientPrefix);

            CartService cartService = new CartService(apiRoot, storeKey);
            CustomerService customerService = new CustomerService(apiRoot, storeKey);
            OrderService orderService = new OrderService(apiRoot, storeKey);
            PaymentService paymentService = new PaymentService(apiRoot, storeKey);

            final String cartId = "";
            final String customerKey = "ct-customer";
            final String customerEmail = "ct@example.com";
            final String orderNumber = "";


//            // TODO ADD Payment to the cart
//            //
//            cartService.getCartById(cartId)
//                    .thenComposeAsync(cartApiHttpResponse ->
//                            paymentService.createPaymentAndAddToCart(
//                                    cartApiHttpResponse.getBody(),
//                                    "We_Do_Payments",
//                                    "CREDIT_CARD",
//                                    "we_pay_73636" + Math.random(),    // Must be unique.
//                                    "pay82626" + Math.random())                  // Must be unique.
//                    )
//                    .thenAccept(cartApiHttpResponse -> logger.info("cart updated with payment {}", cartApiHttpResponse.getBody().getId()))
//                    .exceptionally(throwable -> {
//                        logger.error("Exception: {}", throwable.getMessage());
//                        return null;
//                    }).join();

            // TODO: Place the order
            // TODO: Set order status to CONFIRMED
            // customerService.loginCustomer(customerEmail, "password") // Sign in and find last customer cart
            // or
            // cartService.getCartById(cartId)      // Get cart by Id

        }
    }
}
