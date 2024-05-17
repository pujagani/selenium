// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.openqa.selenium.bidi.network;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.openqa.selenium.remote.http.Contents.utf8String;
import static org.openqa.selenium.testing.Safely.safelyCall;
import static org.openqa.selenium.testing.drivers.Browser.*;

import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.bidi.HasBiDi;
import org.openqa.selenium.environment.webserver.AppServer;
import org.openqa.selenium.environment.webserver.NettyAppServer;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.testing.JupiterTestBase;
import org.openqa.selenium.testing.NotYetImplemented;

class NetworkManagerCommandsTest extends JupiterTestBase {
  private String page;
  private AppServer server;

  @BeforeEach
  public void setUp() {
    server = new NettyAppServer();
    server.start();
  }

  @Test
  @NotYetImplemented(SAFARI)
  @NotYetImplemented(IE)
  @NotYetImplemented(EDGE)
  @NotYetImplemented(CHROME)
  void canPassAuthCredentials() {
    UsernameAndPassword credentials = new UsernameAndPassword("test", "test");

    ((HasBiDi) driver).network().basicAuth(credentials);

    page = server.whereIs("basicAuth");
    driver.get(page);
    assertThat(driver.findElement(By.tagName("h1")).getText()).isEqualTo("authorized");
  }

  @Test
  @NotYetImplemented(SAFARI)
  @NotYetImplemented(IE)
  @NotYetImplemented(EDGE)
  @NotYetImplemented(CHROME)
  @NotYetImplemented(FIREFOX)
  // Browsers are to yet to implement adding filtering by URI
  void canPassAuthCredentialsForURI() throws URISyntaxException {
    URI uri = new URI(server.whereIs("basicAuth"));
    UsernameAndPassword credentials = new UsernameAndPassword("test", "test");

    ((HasBiDi) driver).network().basicAuth(uri, credentials);

    page = server.whereIs("basicAuth");
    driver.get(page);
    assertThat(driver.findElement(By.tagName("h1")).getText()).isEqualTo("authorized");
  }

  @Test
  @NotYetImplemented(SAFARI)
  @NotYetImplemented(IE)
  @NotYetImplemented(EDGE)
  @NotYetImplemented(FIREFOX)
  @NotYetImplemented(CHROME)
  // Browsers are to yet to implement modifying network request body
  void canModifyNetworkRequest() throws URISyntaxException {
    HttpRequest request =
        new HttpRequest(HttpMethod.GET, server.whereIs("/bidi/logEntryAdded.html"));

    ((HasBiDi) driver).network().modifyRequest(new URI(server.whereIs("basicAuth")), request);

    page = server.whereIs("basicAuth");
    driver.get(page);

    assertThat(driver.getPageSource()).contains("events");
  }

  @Test
  @NotYetImplemented(SAFARI)
  @NotYetImplemented(IE)
  @NotYetImplemented(EDGE)
  @NotYetImplemented(CHROME)
  @NotYetImplemented(FIREFOX)
  // Browsers are to yet to implement modifying network response body
  void canModifyNetworkResponse() throws URISyntaxException {
    ((HasBiDi) driver)
        .network()
        .modifyResponse(
            new URI(server.whereIs("/bidi/logEntryAdded.html")),
            new HttpResponse()
                .setStatus(200)
                .setContent(
                    utf8String(
                        "<html><head><title>Hello," + " World!</title></head><body/></html>")));

    page = server.whereIs("/bidi/logEntryAdded.html");
    driver.get(page);

    assertThat(driver.getPageSource()).contains("Hello");
  }

  @AfterEach
  public void quitDriver() {
    if (driver != null) {
      driver.quit();
    }
    safelyCall(server::stop);
  }
}
