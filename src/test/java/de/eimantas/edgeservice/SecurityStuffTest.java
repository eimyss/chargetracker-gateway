package de.eimantas.edgeservice;

import de.eimantas.edgeservice.Utils.SecurityUtils;
import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.client.OverviewClient;
import de.eimantas.edgeservice.dto.AccountOverView;
import de.eimantas.edgeservice.dto.ExpenseDTO;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


public class SecurityStuffTest {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());



    @Before
    public void setup() throws Exception {


    }

    @Test
    public void getString() throws IOException {

      String jsonString = SecurityUtils.getJsonString();
        assertNotNull(jsonString);
        logger.info("test content:" + jsonString);

    }


    @Test
    public void getToken() throws IOException, JSONException {

        String token = SecurityUtils.getOnlyToken();
        assertNotNull(token);
        logger.info("test token:" + token);

    }


    @Test
    public void getTokenKeys() throws IOException, JSONException {

        Iterator keys = SecurityUtils.getTokenInfo();
        assertNotNull(keys);
        keys.forEachRemaining(key -> logger.info((String)key) );

    }


    @Test
    public void getTokenByName() throws IOException, JSONException {

        String token = SecurityUtils.getValueFromToken("token_type");
        assertNotNull(token);
        logger.info("test token:" + token);

    }

}
