package de.eimantas.edgeservice.client;

import de.eimantas.edgeservice.EdgeServiceApplication;
import de.eimantas.edgeservice.Helper.RequestHelper;
import de.eimantas.edgeservice.Utils.SecurityUtils;
import de.eimantas.edgeservice.dto.ExpenseDTO;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.DEFAULT)
public class ExpensesClientTest {

  private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


  private MockMvc mockMvc;

  @Autowired
  private ExpensesClient client;

  @Autowired
  private ExpensesRootlessClient rootlessClient;


  @SuppressWarnings("rawtypes")
  private HttpMessageConverter mappingJackson2HttpMessageConverter;

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

  }


  @Test
  public void testGetAllExpenses() {
    ResponseEntity response = client.getAllExpenses();
    assertNotNull(response);
    logger.info(response.toString());

  }


  @Test
  public void testGetExpensesInPeriod() {

    Date from = Date.from(LocalDate.now().minus(Period.ofMonths(3)).atStartOfDay().toInstant(ZoneOffset.UTC));
    Date to = Date.from(LocalDate.now().plus(Period.ofMonths(3)).atStartOfDay().toInstant(ZoneOffset.UTC));

    SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd");

    logger.info(formatted.format(from));
    logger.info(formatted.format(to));


    ResponseEntity<List> response = client.getExpensesInPeriod(formatted.format(from), formatted.format(to));
    assertNotNull(response);
    assertNotNull(response.getBody());
    logger.info(response.toString());

  }


  @Test
  @Ignore
  public void testPopulateExpenses() {
    ResponseEntity response = client.populateExpenses();
    assertNotNull(response);
    logger.info(response.toString());

  }


  @Test
  public void testServerInfoLinks() {
    ResponseEntity<Object> response = rootlessClient.getServerInfo();
    assertNotNull(response);
    logger.info("response : " + response.toString());
    assertNotNull(response.getBody());
    assertNotNull(((LinkedHashMap) response.getBody()).get("_links"));
    logger.info("Links: " + ((LinkedHashMap) response.getBody()).get("_links"));

  }


  @Test
  public void getActuallInfo() throws IOException {
    ResponseEntity<Object> response = rootlessClient.getServerInfo();
    assertNotNull(response);
    logger.info("response : " + response.toString());
    assertNotNull(response.getBody());
    assertNotNull(((LinkedHashMap) response.getBody()).get("_links"));
    logger.info("Links: " + ((LinkedHashMap) response.getBody()).get("_links"));

    LinkedHashMap links = (LinkedHashMap) ((LinkedHashMap) response.getBody()).get("_links");

    assertNotNull(links);
    Set<String> keys = links.keySet();
    assertNotNull(keys);

    LinkedHashMap values = (LinkedHashMap) links.get("info");
    assertNotNull(values);

    String url = (String) values.get("href");
    assertNotNull(url);
    logger.info("value of href: " + url);

    String content = RequestHelper.getInfoFromUrl(url);

    assertNotNull(content);
    logger.info("content of url: " + content);

  }


  @Test
  public void addClientExpense() throws IOException {

    ExpenseDTO exp = new ExpenseDTO();
    exp.setName("Integration");
    exp.setCategory("STEUER");
    exp.setBetrag(BigDecimal.valueOf(50));
    exp.setOrt("Intellij");
    exp.setAccountId(2L);
    exp.setValid(true);
    String bookmarkJson = json(exp);

    ResponseEntity response = client.postExpense(exp);
    assertNotNull(response);
    assertNotNull(response.getBody());
    logger.info(response.toString());

  }

  @Test
  public void testGetUserExpenses() {
    ResponseEntity<List> response = client.getUserExpenses();
    assertNotNull(response);
    assertNotNull(response.getBody());
    logger.info(response.toString());

  }


  @Test
  public void testGetExpensesByAccountId() {
    ResponseEntity<List> response = client.getExpensesForAccount(1);
    assertNotNull(response);
    assertNotNull(response.getBody());
    logger.info(response.toString());

  }


  @Test
  public void getGetExpenseTypes() {
    ResponseEntity<List> response = client.getExpenseTypes();
    assertNotNull(response);
    assertNotNull(response.getBody());
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
