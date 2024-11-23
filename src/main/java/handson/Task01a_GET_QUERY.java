package handson;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.project.Project;
import com.commercetools.api.models.tax_category.TaxCategory;
import com.commercetools.api.models.tax_category.TaxCategoryPagedQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import handson.impl.ApiPrefixHelper;
import io.vrap.rmf.base.client.ApiHttpResponse;
import io.vrap.rmf.base.client.error.NotFoundException;
import io.vrap.rmf.base.client.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static handson.impl.ClientService.createApiClient;


public class Task01a_GET_QUERY {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_DEV_CLIENT_PREFIX.getPrefix();

        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {

            Logger logger = LoggerFactory.getLogger("commercetools");

            // TODO: GET project info
            //

            Project project = apiRoot.get().executeBlocking().getBody();
            logger.info("Project key: {}", project.getKey());

            // TODO CHECK if a Shipping Method exists
            //
            try {
                int statusCode = apiRoot.shippingMethods()
                        .withKey("standard")
                        .head()
                        .executeBlocking().getStatusCode();
                logger.info("Response: " + statusCode);
            }
            catch (NotFoundException nfe) {
                logger.info("Shipping method not found " + nfe.getStatusCode());
            }
            catch (Exception e) {
                logger.info("Shipping method not found " + e.getMessage());
            }

            // TODO CHECK if a Shipping Method exists asynchronously
            //
            apiRoot.shippingMethods()
                    .withKey("standard-delivery")
                    .head()
                    .execute()
                    .thenAccept((apiHttpResponse) -> {
                        int statusCode = apiHttpResponse.getStatusCode();
                        logger.info("Response: " + statusCode);
                    })
                    .exceptionally(throwable -> {logger.info("Shipping method not found " + throwable.getMessage());
                        return null;
                    }).join();


            // TODO: GET tax categories
            //

            TaxCategoryPagedQueryResponse taxCategoryPagedQueryResponse = apiRoot
                    .taxCategories()
                    .get()
                    .executeBlocking().getBody();
            if (taxCategoryPagedQueryResponse != null && taxCategoryPagedQueryResponse.getResults() != null) {
                logger.info("Tax categories: {}",
                        taxCategoryPagedQueryResponse.getResults()
                                .stream()
                                .map(TaxCategory::getKey)
                                .collect(Collectors.toList())
                );
            } else {
                logger.warn("No tax categories found.");
            }


            // TODO Get Tax category by Key
            //
            apiRoot.taxCategories()
                .withKey("standard-tax")
                .get()
                .execute()
                .thenApply(ApiHttpResponse::getBody)
                .thenAccept(taxCategory -> {
                    logger.info("Tax category ID: {}", taxCategory.getId());
                    try {
                        System.out.println(JsonUtils.prettyPrint(JsonUtils.toJsonString(taxCategory)));
                    } catch (JsonProcessingException ignored) { }
                })
                .exceptionally(throwable -> {
                    logger.error("Exception: {}", throwable.getMessage());
                    return null;
                }).join();

        }
    }
}