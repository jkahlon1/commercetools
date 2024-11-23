package handson.impl;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.CartResourceIdentifierBuilder;
import com.commercetools.api.models.common.Address;
import com.commercetools.api.models.common.AddressBuilder;
import com.commercetools.api.models.customer.*;
import com.commercetools.api.models.customer_group.CustomerGroup;
import io.vrap.rmf.base.client.ApiHttpResponse;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * This class provides operations to work with {@link Customer}s.
 */
public class CustomerService {

    final ProjectApiRoot apiRoot;
    final String storeKey;

    public CustomerService(final ProjectApiRoot apiRoot, final String storeKey) {
        this.apiRoot = apiRoot;
        this.storeKey = storeKey;
    }

    public CompletableFuture<ApiHttpResponse<Customer>> getCustomerByKey(String customerKey) {
        return
                apiRoot
                        .inStore(storeKey)
                        .customers()
                        .withKey(customerKey)
                        .get()
                        .execute();
    }

    public CompletableFuture<ApiHttpResponse<CustomerSignInResult>> createCustomer(
            final String email,
            final String password,
            final String anonymousCartId) {

        return apiRoot
            .inStore(storeKey)
                .customers()
                .post(
                        customerDraftBuilder -> customerDraftBuilder
                                .email(email)
                                .password(password)
                                .key("ct-" + System.nanoTime())
                                .anonymousCart(CartResourceIdentifierBuilder.of().id(anonymousCartId).build())
                )
                .execute();
    }

    public CompletableFuture<ApiHttpResponse<CustomerSignInResult>> createCustomer(
            final String email,
            final String password,
            final String customerKey,
            final String firstName,
            final String lastName,
            final String country) {

        return apiRoot
            .inStore(storeKey)
            .customers()
            .post(
                customerDraftBuilder -> customerDraftBuilder
                    .email(email)
                    .password(password)
                    .firstName(firstName)
                    .lastName(lastName)
                    .key(customerKey)
                    .addresses(
                        AddressBuilder.of()
                            .key(customerKey + "-default-address")
                            .firstName(firstName)
                            .lastName(lastName)
                            .country(country)
                            .build()
                    )
                    .defaultShippingAddress(0)
//                    .stores(StoreResourceIdentifierBuilder.of().key(storeKey).build())
                )
                .execute();
    }

    public CompletableFuture<ApiHttpResponse<CustomerSignInResult>> loginCustomer(
            final String customerEmail,
            final String password) {
        CustomerSignin customerSignin = CustomerSigninBuilder.of()
                .email(customerEmail)
                .password(password)
                .build();
        return apiRoot
                .inStore(storeKey)
                .login()
                .post(customerSignin)
                .execute();
    }

    public CompletableFuture<ApiHttpResponse<CustomerSignInResult>> loginCustomer(
            final String customerEmail,
            final String password,
            final String anonymousCartId,
            final AnonymousCartSignInMode anonymousCartSignInMode) {

        return
                null;
    }

    public CompletableFuture<ApiHttpResponse<CustomerToken>> createEmailVerificationToken(
            final ApiHttpResponse<CustomerSignInResult> customerSignInResultApiHttpResponse,
            final long timeToLiveInMinutes
    ) {

        final Customer customer = customerSignInResultApiHttpResponse.getBody().getCustomer();

        return
            apiRoot
                .inStore(storeKey)
                .customers()
                .emailToken()
                .post(
                    customerCreateEmailTokenBuilder -> customerCreateEmailTokenBuilder
                        .id(customer.getId())
                        .ttlMinutes(timeToLiveInMinutes)
                )
                .execute();
    }

    public CompletableFuture<ApiHttpResponse<Customer>> addAddressToCustomer(
            final String customerKey,
            final Address address) {

        return
                null;
    }

}
