package handson;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.Reference;
import handson.impl.ApiPrefixHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;


public class Task06a_CUSTOMOBJECTS {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();
        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            // TODO: CREATE a custom object
            // container: cross-sell-upsell-info,
            // key: the product key
            // cross-sell: references to products
            //

            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("crossSell", Arrays.asList(
                (Reference.productBuilder().id("product-id").build()),
                Reference.productBuilder().id("product-id").build()));
            jsonObject.put("upSell", Arrays.asList(
                Reference.productBuilder().id("product-id").build(),
                Reference.productBuilder().id("product-id").build()));

            // TODO: CREATE a custom object
            // client.customObjects().post().execute()


            // TODO: ADD attribute as a reference type key-value-document
            // Update the Product Type to add a reference type attribute .
            // Update Products by storing a reference to the Custom Object created in the above step.
            //
        }
    }
}
