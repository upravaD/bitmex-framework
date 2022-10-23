package org.example.framework.constants.URL;

public class URLBuilder {
  private URL url = new URL();

  public URLBuilder protocol(String protocol) {
    url.setProtocol(protocol);
    return this;
  }


  public URLBuilder baseUrl(String baseUrl) {
    url.setBaseUrl(baseUrl);
    return this;
  }

  public URLBuilder apiPath(String apiPath) {
    url.setApiPath(apiPath);
    return this;
  }

  public URLBuilder net(String net) {
    url.setNet(net);
    return this;
  }

  public URLBuilder recoursePath(String resourcePath) {
    url.setResourcePath(resourcePath);
    return this;
  }

  public URL build() {
    return url;
  }
}
