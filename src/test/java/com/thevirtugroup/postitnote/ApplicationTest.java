package com.thevirtugroup.postitnote;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Karate.class)
@CucumberOptions(features = "classpath:karate")
public class ApplicationTest {
    private static WireMockServer wireMockServer
            = new WireMockServer(WireMockConfiguration.options().port(8080));

    @BeforeClass
    public static void setUp() throws Exception {
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
        WireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo("/notes/1"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{ \"id\": 1234, name: \"John Smith\", text: \"Test1 description text\",\n" +
                                        "        createdAt: \"2021-09-13\",\n" +
                                        "        userId: 1 }")));

        WireMock.stubFor(
                WireMock.post(WireMock.urlEqualTo("/notes"))
                        .withHeader("content-type", WireMock.equalTo("application/json"))
                        .withRequestBody(WireMock.containing("name"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{ \"id\": 1234, name: \"John Smith\", text: \"Test1 description text\",\n" +
                                        "        createdAt: \"2021-09-13\",\n" +
                                        "        userId: 1 }")));

        WireMock.stubFor(
                WireMock.put(WireMock.urlEqualTo("/notes"))
                        .withHeader("content-type", WireMock.equalTo("application/json"))
                        .withRequestBody(WireMock.containing("name"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{ \"id\": 1234, name: \"John Smith\", text: \"Test1 description text\",\n" +
                                        "        createdAt: \"2021-09-13\",\n" +
                                        "        userId: 1 }")));

        WireMock.stubFor(
                WireMock.get(WireMock.urlEqualTo("/notes"))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("[{ \"id\": 1234, name: \"John Smith\", text: \"Test1 description text\",\n" +
                                        "        createdAt: \"2021-09-13\",\n" +
                                        "        userId: 1 }]")));

    }

    @AfterClass
    public static void tearDown() throws Exception {
        wireMockServer.stop();
    }
}
