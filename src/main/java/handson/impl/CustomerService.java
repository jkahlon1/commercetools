package handson.impl;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.AddressBuilder;
import com.commercetools.api.models.customer.*;
import com.commercetools.api.models.customer_group.CustomerGroup;
import com.commercetools.api.models.customer_group.CustomerGroupResourceIdentifierBuilder;
import io.vrap.rmf.base.client.ApiHttpResponse;

import java.util.concurrent.CompletableFuture;

/**
 * This class provides operations to work with {@link Customer}s.
 */
public class CustomerService {

    final ProjectApiRoot apiRoot;

    public CustomerService(final ProjectApiRoot client) {
        this.apiRoot = client;
    }

    public CompletableFuture<ApiHttpResponse<Customer>> getCustomerByKey(String customerKey) {
        return
                apiRoot
                        .customers()
                        .withKey(customerKey)
                        .get()
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
                                            .country(country)
                                            .build()
                                )
                                .defaultShippingAddress(0)
                                )
                .execute();
    }

    public CompletableFuture<ApiHttpResponse<CustomerToken>> createEmailVerificationToken(
            final ApiHttpResponse<CustomerSignInResult> customerSignInResultApiHttpResponse,
            final long timeToLiveInMinutes
    ) {

        final Customer customer = customerSignInResultApiHttpResponse.getBody().getCustomer();

        return
                apiRoot
                        .customers()
                        .emailToken()
                        .post(
                                customerCreateEmailTokenBuilder -> customerCreateEmailTokenBuilder
                                    .id(customer.getId())
                                    .ttlMinutes(timeToLiveInMinutes)
                        )
                        .execute();
    }

    public CompletableFuture<ApiHttpResponse<CustomerToken>> createEmailVerificationToken(final Customer customer, final long timeToLiveInMinutes) {

        return
                apiRoot
                        .customers()
                        .emailToken()
                        .post(
                                customerCreateEmailTokenBuilder -> customerCreateEmailTokenBuilder
                                        .id(customer.getId())
                                        .ttlMinutes(timeToLiveInMinutes)
                        )
                        .execute();
    }

    public CompletableFuture<ApiHttpResponse<Customer>> verifyEmail(final ApiHttpResponse<CustomerToken> customerTokenApiHttpResponse) {

        final CustomerToken customerToken = customerTokenApiHttpResponse.getBody();

        return
                apiRoot
                        .customers()
                        .emailConfirm()
                        .post(
                                customerEmailVerifyBuilder ->customerEmailVerifyBuilder
                                        .tokenValue(customerToken.getValue())
                        )
                        .execute();
    }

    public CompletableFuture<ApiHttpResponse<Customer>> verifyEmail(final CustomerToken customerToken) {


        return
                apiRoot
                        .customers()
                        .emailConfirm()
                        .post(
                                customerEmailVerifyBuilder ->customerEmailVerifyBuilder
                                    .tokenValue(customerToken.getValue())
                                )
                        .execute();
    }

    public CompletableFuture<ApiHttpResponse<CustomerGroup>> getCustomerGroupByKey(String customerGroupKey) {
        return
                apiRoot
                        .customerGroups()
                        .withKey(customerGroupKey)
                        .get()
                        .execute();
    }

    public CompletableFuture<ApiHttpResponse<Customer>> assignCustomerToCustomerGroup(
            final String customerKey,
            final String customerGroupKey) {

        return getCustomerByKey(customerKey)
                .thenComposeAsync(customerApiHttpResponse ->
                        apiRoot.customers()
                        .withKey(customerKey)
                        .post(
                                customerUpdateBuilder -> customerUpdateBuilder
                                        .version(customerApiHttpResponse.getBody().getVersion())
                                        .plusActions(
                                                customerUpdateActionBuilder -> customerUpdateActionBuilder
                                                        .setCustomerGroupBuilder()
                                                        .customerGroup(customerGroupResourceIdentifierBuilder -> customerGroupResourceIdentifierBuilder.key(customerGroupKey))
                                        )
                        )
                        .execute()
                );
    }

}
