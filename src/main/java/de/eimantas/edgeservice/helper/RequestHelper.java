package de.eimantas.edgeservice.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHelper {


  public static String getInfoFromUrl(String urladdress) throws IOException {


    URL url = new URL(urladdress);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");


    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuilder content = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();

    con.disconnect();

    return content.toString();

  }
}
