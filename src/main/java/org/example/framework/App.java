package org.example.framework;

import java.net.http.HttpResponse;
import org.example.framework.constants.OrderSide;
import org.example.framework.constants.Symbol;
import org.example.framework.order.LimitOrder;

public class App {

  public static void main(String[] args) {
    String apiKey = "LDqZ9I7HO41M-U3t4ke583x5";
    String apiSecret = "IXdZW9wP3za2XXwmYIs1ah3E-5JwD4RQdWtOvtgHDALBYp5J";
    BitmexClient bitmexClient = new BitmexClient(apiKey, apiSecret, true);

    LimitOrder order = new LimitOrder(Symbol.XBTUSD, OrderSide.SELL, 30000.0, 100.0);

    HttpResponse<String> response = bitmexClient.sendOrder(order);
    System.out.println(response.body());
  }
}
