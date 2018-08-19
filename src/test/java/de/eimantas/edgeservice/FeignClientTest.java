package de.eimantas.edgeservice;

import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.client.OverviewClient;
import de.eimantas.edgeservice.dto.AccountOverView;
import de.eimantas.edgeservice.dto.ExpenseDTO;
import feign.RequestInterceptor;
import feign.RequestTemplate;
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
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles("test")
public class FeignClientTest {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


    private MockMvc mockMvc;

    @Autowired
    private ExpensesClient client;


    @SuppressWarnings("rawtypes")
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private OverviewClient overviewClient;

    @Autowired
    private WebApplicationContext webApplicationContext;



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

        // TODO FIX TESTS!!!!!!!

        KeycloakAuthenticationToken details =   Mockito.mock(KeycloakAuthenticationToken.class);
        Mockito.when(details.getPrincipal()).thenReturn("eyJraWQiOiJvZHcwY2oxaEljMzRZRDBzV2RxUFNrVmFiZWppNVhfX3lMd0xISF8wUzdVIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULnIyd2c2em9XZ2JiYTY4MTNHVU5JV2d5LXRHdG9vRS1zUlh4ZjAzTFlmWTgiLCJpc3MiOiJodHRwczovL2Rldi00NjMwMDgub2t0YXByZXZpZXcuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiYXBpOi8vZGVmYXVsdCIsImlhdCI6MTUyNjMxMDM3NywiZXhwIjoxNTI2MzEzOTc3LCJjaWQiOiIwb2FlcHd6OXlrZU5hU3VXSzBoNyIsInVpZCI6IjAwdWVxNDVjbDVwUGRtaTdEMGg3Iiwic2NwIjpbIm9wZW5pZCIsImVtYWlsIl0sInN1YiI6ImVpbXlzc0BnbWFpbC5jb20ifQ.UegFsGzvnI3B8TfutyazK6voHRYfaswPZRv7k2TWksKDc7oDdg_5TJ31SBVroY6DqjUp8ZqTG4i3JpPyCwYMtcFzs5_U4cdP2P4FEORW1TjBeGgx1yL_h-YMLBhLF-dRLFstudQKBVNLDFFw5g6SPkPKVXbPaWOSCLBt0zWH7dWQEG9dJQZvsq_OYZKxvfTLSzqU1ejOsZrlWWFW5Q0jme495j_BZrIFlNPnFC35qyzjVbEkri2D6CWB3-gIEjN4wJw7LAYbfQZO7RSAi8YlUHbOLivzSXNRtE2so4cZqSrkWH3Ysjb5c5kCdyP29sb-nSoKhmdAJN59UpT01fdqKA");
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    @Ignore
    public void clientConnects() {
       ResponseEntity response  =  client.getUserExpenses();
        assertNotNull(response);
        logger.info(response.toString());


    }


    @Test
    @Ignore
    public void getToken() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "0oaepwz9ykeNaSuWK0h7");
        params.add("username", "eimyss@gmail.com");
        params.add("password", "rasas");

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("0oaepwz9ykeNaSuWK0h7","secret"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());


        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        logger.info(jsonParser.parseMap(resultString).get("access_token").toString());


    }


    @Test
    @Ignore
    public void searchClientConnects() {
        ResponseEntity response  =  client.searchExpenses("AMC");
        assertNotNull(response);

        logger.info(response.toString());


    }

    @Test
    @Ignore
    public void addClientExpense() throws IOException {

        ExpenseDTO exp = new ExpenseDTO();
        exp.setName("uploaded");
        exp.setCategory("test");
        exp.setBetrag(BigDecimal.TEN);
        exp.setOrt("Mainz");


        String bookmarkJson = json(exp);

        ResponseEntity response  =  client.postExpense(exp);
        assertNotNull(response);

        logger.info(response.toString());
        assertThat(response.getStatusCode(),equalTo(status().isCreated()));

    }




    @Test
    @Ignore
    public void clientSearchConnects() {
       ResponseEntity response  =  client.getAllExpenses();
        assertNotNull(response);

        logger.info(response.toString());


    }


    @Test
    @Ignore
    public void clientOverviewConnects() {

        AccountOverView response = overviewClient.readOverview(482);

        assertNotNull(response);
        assertNotNull(response.getTotal());
        assertNotNull(response.getRefAccount());
        logger.info("response: " + response.toString());



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

                if (authentication != null && authentication.getDetails() instanceof KeycloakAuthenticationToken) {
                    KeycloakAuthenticationToken details = (KeycloakAuthenticationToken) authentication.getDetails();
                    template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE,  ((KeycloakAuthenticationToken) authentication).getAccount().getKeycloakSecurityContext().getTokenString()));
                }
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
