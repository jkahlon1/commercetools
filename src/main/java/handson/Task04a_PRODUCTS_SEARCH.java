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


public class Task04a_PRODUCTS_SEARCH {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        final String apiClientPrefix = ApiPrefixHelper.API_STORE_CLIENT_PREFIX.getPrefix();
        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            final String storeKey = getStoreKey(apiClientPrefix);

            ProductPagedSearchResponse productPagedSearchResponse = null;

            productPagedSearchResponse = apiRoot
                    .products()
                    .search()
                    .post(
                        ProductSearchRequestBuilder.of()
                            .query(SearchFullTextExpressionBuilder.of()
                                .fullText(SearchFullTextValueBuilder.of()
                                    .field("name")
                                    .value("bed frame")
                                    .language("en-US")
                                    .mustMatch(SearchMatchType.ANY)
                                    .build())
                                .build()
                            )
                                .sort(
                                    SearchSortingBuilder.of()
                                        .field("variants.prices.centAmount")
                                        .mode(SearchSortMode.MAX)
                                        .order(SearchSortOrder.ASC)
                                        .build()
                                )
                                .productProjectionParameters(ProductSearchProjectionParamsBuilder.of()
                                    .build())
                                .markMatchingVariants(true)
                            .facets(
                                ProductSearchFacetDistinctExpressionBuilder.of()
                                    .distinct(
                                        ProductSearchFacetDistinctValueBuilder.of()
                                            .name("Color")
                                            .field("variants.attributes.color")
                                            .fieldType(SearchFieldType.LTEXT)
                                            .language("en-US")
                                            .level(ProductSearchFacetCountLevelEnum.VARIANTS)
                                            .scope(ProductSearchFacetScopeEnum.ALL)
                                            .build()
                                    )
                                    .build()
                            )
                            .limit(0)
                        .build()
                    )
                    .executeBlocking().getBody();

//            productPagedSearchResponse = apiRoot
//                    .products()
//                    .search()
//                    .post(
//                            ProductSearchRequestBuilder.of()
//                                    .query(
//                                            SearchExactExpressionBuilder.of()
//                                                    .exact(
//                                                            SearchAnyValueBuilder.of()
//                                                                    .field("stores")
//                                                                    .value("65279443-d3fd-4fd2-ba4a-360de7668dfa")
//                                                                    .fieldType(SearchFieldType.SET_REFERENCE)
//                                                                    .build()
//                                                    )
//                                                    .build()
//                                    )
//                                    .productProjectionParameters(ProductSearchProjectionParamsBuilder.of()
//                                            .storeProjection("nagesh-store")
//                                            .build())
//                                    .build()
//                    )
//                    .executeBlocking().getBody();

//            productPagedSearchResponse = apiRoot
//                .products()
//                .search()
//                .post(
//                    ProductSearchRequestBuilder.of()
//                        .query(
//                            SearchAndExpressionBuilder.of()
//                                .and(Arrays.asList(
//                                    SearchExactExpressionBuilder.of()
//                                        .exact(
//                                            SearchAnyValueBuilder.of()
//                                                .field("stores")
//                                                .value("65279443-d3fd-4fd2-ba4a-360de7668dfa")
//                                                .fieldType(SearchFieldType.SET_REFERENCE)
//                                                .build()
//                                        )
//                                        .build(),
//                                    SearchExactExpressionBuilder.of()
//                                        .exact(
//                                            SearchAnyValueBuilder.of()
//                                                .field("variants.attributes.color")
//                                                .value("Ivory:#FFFFF0")
////                                                .value("White:#FFFFFF")
//                                                .fieldType(SearchFieldType.LTEXT)
//                                                .language("en-US")
//                                                .build()
//                                        )
//                                        .build()))
//                            .build()
//
//                        )
//                        .markMatchingVariants(true)
//                            .productProjectionParameters(ProductSearchProjectionParamsBuilder.of()
//                                .storeProjection("nagesh-store")
//                                .build())
//                        .build()
//                )
//                .executeBlocking().getBody();


//            productPagedSearchResponse = apiRoot
//                    .products()
//                    .search()
//                    .post(
//                        ProductSearchRequestBuilder.of()
//                            .query(
//                                SearchExactExpressionBuilder.of()
//                                    .exact(
//                                        SearchAnyValueBuilder.of()
//                                            .field("stores")
//                                            .value("65279443-d3fd-4fd2-ba4a-360de7668dfa")
//                                            .fieldType(SearchFieldType.SET_REFERENCE)
//                                            .build()
//                                    )
//                                    .build()
//                            )
//                            .productProjectionParameters(ProductSearchProjectionParamsBuilder.of()
//                                .storeProjection("nagesh-store")
//                                .build())
//
//                            .build()
//                    )
//                    .executeBlocking().getBody();


            System.out.println(JsonUtils.prettyPrint(JsonUtils.toJsonString(productPagedSearchResponse)));
        }
    }
}
