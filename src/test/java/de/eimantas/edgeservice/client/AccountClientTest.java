package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.EdgeServiceApplication;
import de.eimantas.edgeservice.Utils.SecurityUtils;
import de.eimantas.edgeservice.dto.*;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.DEFAULT)
public class AccountClientTest {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


    private MockMvc mockMvc;

    @Autowired
    private AccountsClient client;


    @SuppressWarnings("rawtypes")
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private AccountDTO got;


    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        logger.info("setting up appllication");
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

    }


    @Test
    public void testSaveAccount() throws IOException {

        AccountDTO acc = new AccountDTO();
        acc.setName("uploaded");
        acc.setBank("test");
        acc.setBusinessAccount(true);
        String bookmarkJson = json(acc);

        ResponseEntity<AccountDTO> response  =  client.postAccount(acc);
        assertNotNull(response);
        assertNotNull(response.getBody());
        got = response.getBody();
        assertNotNull(got.getId());
        assertNotNull(got.getUser());
        logger.info(got.toString());
    }


    @Test
    public void testGetAllAccounts() {
        ResponseEntity<List<AccountDTO>> response  =  client.getAccountList();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotEquals(response.getBody(),"");
        assertThat(response.getBody().size(),is(greaterThan(0)));
        logger.info(response.toString());

    }




    @Test
    public void testGlobalOverview() {
        ResponseEntity<AllAccountsOverViewDTO> response  =  client.getGlobalOverview();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotEquals(response.getBody(),"");
        logger.info(response.toString());

    }



    @Configuration
    @EnableAutoConfiguration
    @EnableFeignClients
    @EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
    protected static class TestApplication {

        public static void main(String[] args) {
            SpringApplication.run(EdgeServiceApplication.class, args);

        }


        @Component
        public class UserFeignClientInterceptor implements RequestInterceptor {
            private static final String AUTHORIZATION_HEADER = "Authorization";
            private static final String BEARER_TOKEN_TYPE = "Bearer";

            @Override
            public void apply(RequestTemplate template) {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                Authentication authentication = securityContext.getAuthentication();

                    template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, SecurityUtils.getOnlyToken()));

            }
        }
    }


    @SuppressWarnings("unchecked")
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


}
