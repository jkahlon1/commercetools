package handson;

import com.commercetools.api.client.ProjectApiRoot;
import handson.impl.ApiPrefixHelper;
import handson.impl.StoreService;
import io.vrap.rmf.base.client.ApiHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;
import static handson.impl.ClientService.getStoreKey;


public class Task04a_QUERY_PRODUCTS {

    private static final Logger log = LoggerFactory.getLogger(Task04a_QUERY_PRODUCTS.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();
        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");
            final String storeKey = getStoreKey(apiClientPrefix);

            StoreService storeService = new StoreService(apiRoot, storeKey);

            // TODO: GET the products in the store
            //
            storeService.getProductsInCurrentStore()
                    .thenApply(ApiHttpResponse::getBody)
                    .thenAccept(productsInStorePagedQueryResponse -> {
                            logger.info("{} products in the store", productsInStorePagedQueryResponse.getResults().size());
                            productsInStorePagedQueryResponse.getResults().forEach(productsInStore -> {
                                logger.info(productsInStore.getProduct().getObj().getKey());
                                logger.info("MasterVariant Sku {}", productsInStore.getProduct().getObj().getMasterData().getCurrent().getMasterVariant().getSku());
                                productsInStore.getProduct().getObj().getMasterData().getCurrent().getVariants().forEach(variant ->
                                        logger.info("Variant Sku : {}", variant.getSku())
                                );
                            });
                        }
                    )
                    .exceptionally(throwable -> {
                        logger.error("Exception: {}", throwable.getMessage());
                        return null;
                    }).join();

        }
    }
}
