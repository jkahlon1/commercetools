package handson;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.category.Category;
import com.commercetools.api.models.product.*;
import handson.impl.ApiPrefixHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static handson.impl.ClientService.createApiClient;

public class Task02a_PRODUCT_SEARCH {

    public static void main(String[] args) throws Exception {

// TODO UPDATE: Product projection in Store
        final String apiClientPrefix = ApiPrefixHelper.API_DEV_CLIENT_PREFIX.getPrefix();

        try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {
            Logger logger = LoggerFactory.getLogger("commercetools");

            Category furnitureCategory = apiRoot.categories()
                    .withKey("home-decor")
                    .get()
                    .execute().get().getBody();

            // params found in the product projection search https://docs.commercetools.com/api/projects/products-search#search-productprojections
            ProductProjectionPagedSearchResponse productProjectionPagedSearchResponse = null;
                // TODO Get all products

                // TODO Restrict on category home-decor

                // TODO Get all Facets for Enum color and finish
                // Make sure the attributes are searchable

                // TODO Add price as a range facet

                // TODO Give price range on products with no effect on facets
                // .withFilter("variants.price.centAmount:range (1000 to 100000)")

                // TODO: with effect on facets

                // TODO: Simulate click on facet White:#FFFFFF or Golden Rod:#DAA520 from attribute color


            int size = productProjectionPagedSearchResponse.getResults().size();
            logger.info("No. of products: " + size);
            List<ProductProjection> result =  productProjectionPagedSearchResponse.getResults().subList(0, size);
            System.out.println("products searched: ");
            result.forEach((r) -> System.out.println(r.getKey()));

            logger.info("Facet count: " + productProjectionPagedSearchResponse.getFacets().values().size());
            logger.info("Facets: " + productProjectionPagedSearchResponse.getFacets().values().keySet());
            for (String facet: productProjectionPagedSearchResponse.getFacets().values().keySet()){
                FacetResult facetResult = productProjectionPagedSearchResponse.getFacets().withFacetResults(FacetResultsAccessor::asFacetResultMap).get(facet);
                if (facetResult instanceof RangeFacetResult) {
                    logger.info("{} ranges: {}", facet ,((RangeFacetResult)facetResult).getRanges().size());
                    logger.info("Facet counts: {}", ((RangeFacetResult)facetResult).getRanges().stream().map(facetResultRange -> facetResultRange.getFromStr() + " to " + facetResultRange.getToStr() + ": " + facetResultRange.getCount()).collect(Collectors.toList()));
                }
                else if (facetResult instanceof TermFacetResult) {
                    logger.info("{} terms: {}", facet, ((TermFacetResult)facetResult).getTerms().size());
                    logger.info("Facet counts: {}", ((TermFacetResult)facetResult).getTerms().stream().map(facetResultTerm -> facetResultTerm.getTerm() + ": " + facetResultTerm.getCount()).collect(Collectors.joining(", ")));
                }
            }

        }
    }
}
