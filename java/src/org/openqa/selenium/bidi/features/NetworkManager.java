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

package org.openqa.selenium.bidi.features;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.module.Network;
import org.openqa.selenium.bidi.network.*;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;

public class NetworkManager {
  private static final Logger LOG = Logger.getLogger(NetworkManager.class.getName());

  private final Network network;

  public NetworkManager(WebDriver driver) {
    this.network = new Network(driver);
  }

  public void basicAuth(UsernameAndPassword usernameAndPassword) {
    network.addIntercept(new AddInterceptParameters(InterceptPhase.AUTH_REQUIRED));
    addAuthEventListener(usernameAndPassword);
  }

  public void basicAuth(URI uri, UsernameAndPassword usernameAndPassword) {
    // Can probably return the intercept id to remove intercepting at a later point
    network.addIntercept(
        new AddInterceptParameters(InterceptPhase.AUTH_REQUIRED).urlStringPattern(uri.toString()));
    addAuthEventListener(usernameAndPassword);
  }

  public void modifyRequest(URI uri, HttpRequest request) {
    network.addIntercept(
        new AddInterceptParameters(InterceptPhase.BEFORE_REQUEST_SENT)
            .urlStringPattern(uri.toString()));

    network.onBeforeRequestSent(
        beforeRequestSent -> {
          String id = beforeRequestSent.getRequest().getRequestId();
          ContinueRequestParameters parameters =
              new ContinueRequestParameters(id).url(request.getUri()).method(request.getMethod());

          List<Header> headers = new ArrayList<>();
          request.forEachHeader(
              (name, value) ->
                  headers.add(new Header(name, new BytesValue(BytesValue.Type.STRING, value))));

          if (headers.isEmpty()) {
            parameters.headers(headers);
          }

          if (request.getMethod().equals(HttpMethod.POST)) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try (InputStream is = request.getContent().get()) {
              is.transferTo(bos);
            } catch (IOException e) {
              LOG.warning(e.getMessage());
              return;
            }

            BytesValue bytesValue =
                new BytesValue(
                    BytesValue.Type.BASE64, Base64.getEncoder().encodeToString(bos.toByteArray()));

            parameters.body(bytesValue);
          }
          network.continueRequest(parameters);
        });
  }

  public void modifyResponse(URI uri, HttpResponse response) {
    network.addIntercept(
        new AddInterceptParameters(InterceptPhase.RESPONSE_STARTED)
            .urlStringPattern(uri.toString()));

    network.onResponseStarted(
        responseDetails -> {
          String id = responseDetails.getRequest().getRequestId();
          ProvideResponseParameters parameters = new ProvideResponseParameters(id);

          List<Header> headers = new ArrayList<>();
          response.forEachHeader(
              (name, value) ->
                  headers.add(new Header(name, new BytesValue(BytesValue.Type.STRING, value))));

          if (headers.isEmpty()) {
            parameters.headers(headers);
          }

          parameters.statusCode(response.getStatus());

          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          try (InputStream is = response.getContent().get()) {
            is.transferTo(bos);
          } catch (IOException e) {
            LOG.warning(e.getMessage());
            return;
          }

          BytesValue bytesValue =
              new BytesValue(
                  BytesValue.Type.BASE64, Base64.getEncoder().encodeToString(bos.toByteArray()));

          parameters.body(bytesValue);

          network.provideResponse(parameters);
        });
  }

  private void addAuthEventListener(UsernameAndPassword usernameAndPassword) {
    network.onAuthRequired(
        responseDetails -> {
          String requestId = responseDetails.getRequest().getRequestId();
          try {
            network.continueWithAuth(
                responseDetails.getRequest().getRequestId(), usernameAndPassword);
            return;
          } catch (Exception e) {
            LOG.log(Level.WARNING, e.getMessage());
            // Fall through
          }
          network.cancelAuth(requestId);
        });
  }
}
