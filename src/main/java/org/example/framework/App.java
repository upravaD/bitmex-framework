package org.example.framework;

import org.example.framework.constants.OrderSide;
import org.example.framework.constants.Symbol;
import org.example.framework.order.LimitOrder;

import java.net.http.HttpResponse;

public class App {

  public static void main(String[] args) {
    String apiKey = "B0JgUAGtaNYrAxttArJT-v5H";
    String apiSecret = "ztEAx9hQf84BtF3Mc0VtaSWThqfqpyXxetHniIGAP_H0HAyv";
    BitmexClient bitmexClient = new BitmexClient(apiKey, apiSecret, true);

    LimitOrder order = new LimitOrder(Symbol.XBTUSD, OrderSide.SELL, 3000.0, 10.0);

    HttpResponse<String> response = bitmexClient.sendOrder(order);
    System.out.println(response.body());
  }
}
