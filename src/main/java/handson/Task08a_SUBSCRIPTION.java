package handson;


import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.subscription.*;
import handson.impl.ApiPrefixHelper;
import io.vrap.rmf.base.client.ApiHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;

/**
 * Create a subscription for customer change requests.
 *
 */
public class Task08a_SUBSCRIPTION {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_DEV_CLIENT_PREFIX.getPrefix();

        Logger logger = LoggerFactory.getLogger("commercetools");

        final ProjectApiRoot client = createApiClient(apiClientPrefix);

        client
            .subscriptions()
            .post(
                subscriptionDraftBuilder -> subscriptionDraftBuilder
                    .key("mhOrderPlacedSubscription")
                    .destination(
                            //for GCP Pub/Sub topic
                        GoogleCloudPubSubDestinationBuilder.of()
                            .projectId("ct-support")
                            .topic("mh-dev-topic")
                            .build()
                    )
                    .addMessages(
                        messageSubscriptionBuilder -> messageSubscriptionBuilder
                          .resourceTypeId(MessageSubscriptionResourceTypeId.CUSTOMER) // https://docs.commercetools.com/api/types#referencetype
                          .types("CustomerCreated") // https://docs.commercetools.com/api/message-types
                            .build()
                    )

            ).execute()
            .thenApply(ApiHttpResponse::getBody)
            .handle((subscription, exception) -> {
                if (exception == null) {
                    logger.info("Subscription ID: " + subscription.getId());
                    return subscription;
                }
                logger.error("Exception: " + exception.getMessage());
                return null;
            }).thenRun(() -> client.close());
    }
}
