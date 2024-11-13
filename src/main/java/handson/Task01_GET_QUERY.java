package handson;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.project.Project;
import handson.impl.ApiPrefixHelper;
import handson.impl.ClientService;
import io.vrap.rmf.base.client.utils.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static handson.impl.ClientService.createApiClient;


public class Task01_GET_QUERY {

    private static final Logger log = LoggerFactory.getLogger(Task01_GET_QUERY.class);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

            final String apiClientPrefix = ApiPrefixHelper.API_DEV_CLIENT_PREFIX.getPrefix();

            // TODO add API Client in dev.properties
            // TODO implement ClientService#createApiClient(String prefix)}
            //

            try (ProjectApiRoot apiRoot = createApiClient(apiClientPrefix)) {

                Logger logger = LoggerFactory.getLogger("commercetools");

                Project projectDetails = apiRoot.get().executeBlocking().getBody();
                logger.info("Project key: {}", projectDetails.getKey());
                logger.info("JSON Response: {}", JsonUtils.prettyPrint(JsonUtils.toJsonString(projectDetails)));

                // TODO CHECK if a shipping method exists
                //

                // TODO: GET tax categories
                //

                // TODO: GET a Tax category by Key
                //

            }
    }
}
