package org.example.framework;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import org.example.framework.constants.URL.ResourceURL;
import org.example.framework.constants.URL.URL;
import org.example.framework.constants.URL.URLBuilder;
import org.example.framework.constants.URL.UtilURL;
import org.example.framework.constants.Verb;
import org.example.framework.order.LimitOrder;
import org.json.JSONObject;

public class BitmexClient {

  private boolean isTestnet;
  private String apiKey;
  private String apiSecret;
  private final HttpClient client;

  public BitmexClient(String apiKey, String apiSecret, boolean isTestnet) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
    this.isTestnet = isTestnet;
    client = HttpClient.newHttpClient();
  }

  public HttpResponse<String> sendOrder(LimitOrder order) {
    URL url = new URLBuilder()
        .protocol(UtilURL.PROTOCOL)
        .net(getNet())
        .baseUrl(UtilURL.BASE_URL)
        .apiPath(UtilURL.API_PATH)
        .recoursePath(ResourceURL.ORDER)
        .build();

    String orderJsonStr = new JSONObject(order).toString();
    System.out.println(url.toString());
    String expires = createExpires();
    System.out.println(Verb.POST.toString());

    String signature = createSignature(url, Verb.POST.toString(), orderJsonStr, expires);

    while(signature.length() != 64) {
      expires = createExpires();
      signature = createSignature(url, Verb.POST.toString(), orderJsonStr, expires);
    }

    HttpRequest request = HttpRequest.newBuilder()
        .POST(BodyPublishers.ofString(orderJsonStr))
        .header("api-signature", signature)
        .header("api-expires", expires)
        .header("api-key", apiKey)
        .header("Content-Type", "application/json; charset=utf-8")
        .header("Accept", "application/json")
        .uri(URI.create(url.toString()))
        .build();

    HttpResponse<String> response;

    try {
      response = client.send(request, BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }

    return response;
  }

  private String createSignature(URL url, String verb, String data, String expires) {
    Signature signature = new Signature();
    String path = url.getApiPath()+url.getResourcePath();
    System.out.println(verb + " " + path + " " + data + " " + expires + apiSecret);
    return signature.signatureToString(signature.createSignature(
        verb, path, data, expires, apiSecret ));
  }

  private String getNet() {
    return isTestnet ? UtilURL.TESTNET : UtilURL.REALNET;
  }

  private String createExpires() {
    return (Instant.now().getEpochSecond() + 100) + "";
  }
}
