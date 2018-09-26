package de.eimantas.edgeservice;

import de.eimantas.edgeservice.Utils.SecurityUtils;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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
