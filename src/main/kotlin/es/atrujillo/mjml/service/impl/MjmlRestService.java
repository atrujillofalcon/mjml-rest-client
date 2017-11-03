package es.atrujillo.mjml.service.impl;

import es.atrujillo.mjml.model.mjml.MjmlRequest;
import es.atrujillo.mjml.model.mjml.MjmlResponse;
import es.atrujillo.mjml.rest.BasicAuthRestClient;
import es.atrujillo.mjml.rest.RestClient;
import es.atrujillo.mjml.service.definition.MjmlAuthConf;
import es.atrujillo.mjml.service.definition.MjmlService;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MjmlRestService implements MjmlService {

    private static final String TRANSPILE_RENDER_MJML_PATH = "/render";

    @Override
    public String transpileMjmlToHtml(@NotNull String mjmlBody, @NotNull MjmlAuthConf authConf) {
        RestClient<MjmlRequest> restClient = new BasicAuthRestClient<>(authConf.getMjmlApiEndpoint().toString(),
                authConf.getMjmlApplicationId(), authConf.getMjmlApplicationSecretKey());

        MjmlRequest request = new MjmlRequest(mjmlBody);

        return Optional.of(restClient.post(request, TRANSPILE_RENDER_MJML_PATH,
                new ParameterizedTypeReference<MjmlResponse>() {
                }, null, null))
                .filter(mjmlResponseEntity -> mjmlResponseEntity.getStatusCode().is2xxSuccessful())
                .map(HttpEntity::getBody)
                .map(MjmlResponse::getHtml)
                .orElse("");
    }
}
