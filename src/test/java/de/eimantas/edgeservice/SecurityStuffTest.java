package de.eimantas.edgeservice;

import de.eimantas.edgeservice.Utils.SecurityUtils;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import static org.junit.Assert.assertNotNull;


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
  public void parseDate() throws IOException {

    String jsonString = "2018-10-12T02:49:01";
    String full = "2018-10-12T18:15:46.196";

    LocalDateTime dt = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String test =  dt.format(formatter);
    logger.info("String is: " +  test);
    LocalDateTime date =   LocalDateTime.parse(test, formatter);
    assertNotNull(date);
    logger.info("test parsed date:" + date);

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
    keys.forEachRemaining(key -> logger.info((String) key));

  }


  @Test
  public void getTokenByName() throws IOException, JSONException {

    String token = SecurityUtils.getValueFromToken("token_type");
    assertNotNull(token);
    logger.info("test token:" + token);

  }

}
