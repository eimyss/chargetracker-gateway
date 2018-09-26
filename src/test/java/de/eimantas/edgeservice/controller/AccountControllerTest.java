package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.Utils.SecurityUtils;
import de.eimantas.edgeservice.client.AccountsClient;
import de.eimantas.edgeservice.client.ExpensesClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Arrays;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountControllerTest {

  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
  private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  private MockMvc mockMvc;

  @SuppressWarnings("rawtypes")
  private HttpMessageConverter mappingJackson2HttpMessageConverter;


  @Autowired
  private ExpensesClient expensesClient;

  @Inject
  private AccountsClient accountsClient;

  @Autowired
  private WebApplicationContext webApplicationContext;
  private Principal mockPrincipal;

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {

    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

    assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
  }

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();

  }


  @Test
  @Ignore
  public void getAccountList() throws Exception {

    String token = SecurityUtils.getOnlyToken();
    logger.info("Token: " + token);
    String AUTHORIZATION_HEADER = "Authorization";
    String BEARER_TOKEN_TYPE = "Bearer";


    mockPrincipal = Mockito.mock(KeycloakAuthenticationToken.class);
    OidcKeycloakAccount securityAcc = Mockito.mock(OidcKeycloakAccount.class);
    KeycloakSecurityContext context = Mockito.mock(KeycloakSecurityContext.class);
    Mockito.when(securityAcc.getKeycloakSecurityContext()).thenReturn(context);
    Mockito.when(context.getTokenString()).thenReturn(token);
    //Mockito.when(((KeycloakAuthenticationToken) mockPrincipal).getAccount().getKeycloakSecurityContext().getTokenString()).thenReturn(token);
    Mockito.when(((KeycloakAuthenticationToken) mockPrincipal).getAccount()).thenReturn(securityAcc);


    mockMvc.perform(get("/account/list").header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, token)).with(authentication((Authentication) mockPrincipal)))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print()).andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$", hasSize(greaterThan(2))));
  }


  @SuppressWarnings("unchecked")
  protected String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }

}
