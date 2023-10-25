package org.openqa.selenium.bidi.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UrlPattern {
  private Optional<String> protocol;
  private Optional<String> hostname;
  private Optional<String> port;
  private Optional<String> pathname;
  private Optional<String> search;

  private String pattern;

  public UrlPattern(
      Optional<String> protocol,
      Optional<String> hostname,
      Optional<String> port,
      Optional<String> pathname,
      Optional<String> search) {
    this.protocol = protocol;
    this.hostname = hostname;
    this.port = port;
    this.pathname = pathname;
    this.search = search;
  }

  public UrlPattern(String pattern) {
    this.pattern = pattern;
  }

  public Map<String, Object> toMap() {
    final Map<String, Object> map = new HashMap<>();
    if (pattern != null) {
      map.put("type", "string");
      map.put("pattern", this.pattern);
    } else {
      map.put("type", "pattern");
      protocol.ifPresent(value -> map.put("protocol", value));
      hostname.ifPresent(value -> map.put("hostname", value));
      port.ifPresent(value -> map.put("port", value));
      pathname.ifPresent(value -> map.put("pathname", value));
      search.ifPresent(value -> map.put("search", value));
    }

    return map;
  }
}
