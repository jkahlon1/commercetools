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

            // filter from product projection query response

            // the effective filter from the search response
            // params found in the product projection search https://docs.commercetools.com/api/projects/products-search#search-productprojections
            ProductProjectionPagedSearchResponse productProjectionPagedSearchResponse = apiRoot
                // TODO Get all products
                .productProjections().search()
                .get()
                .withStaged(false)

                // TODO Restrict on category home-decor
                .withMarkMatchingVariants(true)
                .withFilterQuery("categories.id:\"" + furnitureCategory.getId() + "\"")

                // TODO Get all Facets for Enum color and finish
                // Make sure the attributes are searchable

                .withFacet("variants.attributes.color.en-US as Color-EN")
                .addFacet("variants.attributes.color.de-DE as Color-DE")
                .addFacet("variants.attributes.finish.en-US as Finish-EN")
                .addFacet("variants.attributes.finish.de-DE as Finish-DE")
                .addFacet("variants.price.centAmount:range (1000 to 10000),(10000 to 100000),(100000 to *) as Prices")

                // TODO Give price range on products with no effect on facets
                // .withFilter("variants.price.centAmount:range (1000 to 100000)")

                // TODO: with effect on facets
                // .addFilterQuery("variants.price.centAmount:range (1000 to 100000)")
                // .addFilterQuery("variants.attributes.color.en-US:\"Golden Rod:#DAA520\"")

                // TODO: Simulate click on facet White:#FFFFFF or Golden Rod:#DAA520 from attribute color
                // .withFilterFacets("variants.attributes.color.en-US:\"Golden Rod:#DAA520\"")
                .executeBlocking()
                .getBody();

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
