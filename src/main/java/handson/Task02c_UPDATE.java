package handson;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.product.Product;
import handson.impl.ApiPrefixHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;

public class Task02c_UPDATE {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_DEV_CLIENT_PREFIX.getPrefix();
        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            Product product = apiRoot.products()
                    .withKey("")
                    .get()
                    .executeBlocking().getBody();

            // TODO: UPDATE product categories
            //

        }
    }
}

