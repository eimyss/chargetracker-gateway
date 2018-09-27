package de.eimantas.edgeservice.controller;

import de.eimantas.edgeservice.client.AccountsClient;
import de.eimantas.edgeservice.client.ExpensesClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExpensesControllerTest {

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
  public void getBackendInfoKeys() throws Exception {
    mockMvc.perform(get("/expenses/backend/keys")).andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print()).andExpect(content().contentType(contentType))
        .andExpect(jsonPath("$", hasSize(greaterThan(20))));

  }

  @Test
  public void getBackendInfoForKey() throws Exception {
    mockMvc.perform(get("/expenses/backend/keys/info")).andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.build.name", is("backend-expenses")));
  }

  @Test
  public void getBackendInfoForNonExistingKey() throws Exception {
    mockMvc.perform(get("/expenses/backend/keys/eimantas")).andExpect(status().isBadRequest())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void getExpensesTypes() throws Exception {
    mockMvc.perform(get("/expenses/types")).andExpect(status().isOk())
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
