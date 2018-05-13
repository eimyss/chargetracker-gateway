package de.eimantas.edgeservice;

import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.client.OverviewClient;
import de.eimantas.edgeservice.dto.AccountOverView;
import de.eimantas.edgeservice.dto.Expense;
import de.eimantas.edgeservice.dto.ExpensesResponse;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles("test")
public class FeignClientTest {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExpensesClient client;

    @Autowired
    private OverviewClient overviewClient;

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    @Before
    public void setup() throws Exception {

        logger.info("setting up appllication");

        OAuth2AuthenticationDetails details =   Mockito.mock(OAuth2AuthenticationDetails.class);
        Mockito.when(details.getTokenValue()).thenReturn("eyJraWQiOiJvZHcwY2oxaEljMzRZRDBzV2RxUFNrVmFiZWppNVhfX3lMd0xISF8wUzdVIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULmQzakhPMTZqNjNwSmRPTGZxRl9jM2FxMUs0V1JyR2MyOFNLaF80OVlwMGciLCJpc3MiOiJodHRwczovL2Rldi00NjMwMDgub2t0YXByZXZpZXcuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiYXBpOi8vZGVmYXVsdCIsImlhdCI6MTUyNjIyNzY2OSwiZXhwIjoxNTI2MjMxMjY5LCJjaWQiOiIwb2FlcHd6OXlrZU5hU3VXSzBoNyIsInVpZCI6IjAwdWVxNDVjbDVwUGRtaTdEMGg3Iiwic2NwIjpbImVtYWlsIiwib3BlbmlkIl0sInN1YiI6ImVpbXlzc0BnbWFpbC5jb20ifQ.By3ihpp2UX75YhLSpzWJiTZBLEM8cYTB8yKHBKjaPScddE5AqTQ3er3Du1plrRP0Yxon98dy0epGVurnMBEN6SpbuSz_xrIctpgXAe_4aN5_8p8pV27uEgZ49FzHtUdm_vQu861dizhAPXV3w3l-bNqm1QlbYQGUI7T4liLLiXYaTdBEpee7qQ5I47IO0m21BoeGsvM4yejNtU0_-cKD9dJpHvLG5wN4SCDRkjNhlnu5uvq6MgjYDxpbFD5WdbY_w7Q86Kt96bUuBpSUG8KB4RP3bsG-KSMpH8bn6BT4BrG0plY3tS9DpG8QsKoPfLMXcyQiscCCO1wPsRPOeBPa8A");
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }


    @Test
    public void clientConnects() {
        Collection<Expense> response  =  client.readExpenses();
        assertNotNull(response);
        ArrayList content = new ArrayList<>(response);

        logger.info(response.toString());
        assertThat(content.size(),greaterThan(0));

        logger.info("eimantas test");
        content.forEach(e -> logger.info(e.toString()));

    }


    @Test
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

                if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                    template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, details.getTokenValue()));
                }
            }
        }
    }



}
