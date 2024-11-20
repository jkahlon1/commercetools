package handson;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product_search.*;
import com.commercetools.api.models.search.*;
import handson.impl.ApiPrefixHelper;
import io.vrap.rmf.base.client.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;
import static handson.impl.ClientService.getStoreKey;


public class Task04a_PRODUCT_SEARCH {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();
        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            final String storeKey = getStoreKey(apiClientPrefix);

            // TODO GET store ID

            // TODO GET products from your store using Product Search, sort by min price
            ProductPagedSearchResponse productPagedSearchResponse = null;

            System.out.println(JsonUtils.prettyPrint(JsonUtils.toJsonString(productPagedSearchResponse)));
        }
    }
}
