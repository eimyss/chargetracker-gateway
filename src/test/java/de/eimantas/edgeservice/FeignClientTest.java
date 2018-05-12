package de.eimantas.edgeservice;

import de.eimantas.edgeservice.client.ExpensesClient;
import de.eimantas.edgeservice.dto.AccountOverView;
import feign.RequestInterceptor;
import feign.RequestTemplate;
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
import org.springframework.hateoas.Resources;
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
    private OAuth2ClientContext oauth2ClientContext;

    @Test
    public void clientConnects() {
        assertNotNull(client.readExpenses());
        ArrayList content = new ArrayList<>(client.readExpenses().getContent());
        assertThat(content.size(),greaterThan(0));

        logger.info("eimantas test");
        content.forEach(e -> logger.info(e.toString()));

    }


    @Test
    public void clientOverviewConnects() {

        OAuth2AuthenticationDetails details =   Mockito.mock(OAuth2AuthenticationDetails.class);
        Mockito.when(details.getTokenValue()).thenReturn("eyJraWQiOiJvZHcwY2oxaEljMzRZRDBzV2RxUFNrVmFiZWppNVhfX3lMd0xISF8wUzdVIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULkMxaFZuV2FKT0xHY1otME5JUTJqVTBtNFVvU3VoZzlCV3lDaUdoaV9HVDgiLCJpc3MiOiJodHRwczovL2Rldi00NjMwMDgub2t0YXByZXZpZXcuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiYXBpOi8vZGVmYXVsdCIsImlhdCI6MTUyNjE0MTQ1MiwiZXhwIjoxNTI2MTQ1MDUyLCJjaWQiOiIwb2FlcHd6OXlrZU5hU3VXSzBoNyIsInVpZCI6IjAwdWVxNDVjbDVwUGRtaTdEMGg3Iiwic2NwIjpbIm9wZW5pZCIsImVtYWlsIl0sInN1YiI6ImVpbXlzc0BnbWFpbC5jb20ifQ.VTlKI98UebKg6GlClv58cnX25x8FXeE5xnjtZoJAxOlOYWwxXfDvYSndQ7_nHrhgSVVfoNE_zkG9w1PWOGlavhZuwtaD2wKRSaW13p79nUcLQAIzkRgNI0mCR5tgjWZmPwvNjbRqEfLC8Q3NMBuGyYFJxKDTz4wrBbauQ1YvoCVzNPkD8kBx2ibX7sfn98VmTUuo096AJgxaz6HLcWwEM0e7PRViUPiBUm5PU2doR8vpjCZabTFOAAnJGxNuITw2yZlZOxp3LBcxKyxf_mSDmA5c6IxRyDNSC4CfdQe6J2sGrzQEnuHmuiaayynK6_cSo9tfIa0pWlxQe6OHkzLzmA");
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getDetails()).thenReturn(details);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Resources<AccountOverView> response = client.readOverview(482);

        assertNotNull(response);
        ArrayList content = new ArrayList<>(response.getContent());

        logger.info("eimantas test");
        content.forEach(e -> logger.info(e.toString()));

    }


    @Configuration
    @EnableAutoConfiguration
    @EnableFeignClients
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
