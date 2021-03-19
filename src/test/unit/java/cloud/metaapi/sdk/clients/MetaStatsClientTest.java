package cloud.metaapi.sdk.clients;

import java.util.concurrent.CompletableFuture;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import cloud.metaapi.sdk.clients.HttpRequestOptions.Method;
import cloud.metaapi.sdk.clients.MetaStatsClient.Metrics;
import cloud.metaapi.sdk.clients.MetaStatsClient.MetricsResponse;

/**
 * Tests {@link MetaStatsClient}
 */
class MetaStatsClientTest {

  private Metrics expected;
  private String token = "token.payload.sign";
  private String accountId = "1234567";
  private HttpClient httpClient;
  private MetaStatsClient metaStatsClient;
  
  @BeforeEach
  void setUp() throws Exception {
    expected = new Metrics() {{ trades = 10; equity = 10102.5; balance = 10105;
      profit = 104; deposits = 10001; }}; 
    httpClient = new HttpClient();
    httpClient = Mockito.spy(httpClient);
    Mockito.doReturn(CompletableFuture.completedFuture(new MetricsResponse() {{
      metrics = expected; }})).when(httpClient).requestJson(Mockito.any(), Mockito.any());
    metaStatsClient = new MetaStatsClient(httpClient, token);
  }

  /**
   * Tests {@link MetaStatsClient#getMetrics(String)}
   */
  @Test
  void testRetrievesAccountMetricsFromApi() {
    Metrics metrics = metaStatsClient.getMetrics(accountId).join();
    Assertions.assertThat(metrics).usingRecursiveComparison().isEqualTo(expected);
    Mockito.verify(httpClient).requestJson(Mockito.argThat(opts -> {
      HttpRequestOptions expectedOpts = new HttpRequestOptions(
        "https://metastats-api-v1.agiliumtrade.agiliumtrade.ai/users/current/accounts/"
        + accountId + "/metrics", Method.GET);
      expectedOpts.getHeaders().put("auth-token", token);
      expectedOpts.getQueryParameters().put("includeOpenPositions", false);
      Assertions.assertThat(opts).usingRecursiveComparison().isEqualTo(expectedOpts);
      return true;
    }), Mockito.eq(MetricsResponse.class));
  }
  
  /**
   * Tests {@link MetaStatsClient#getMetrics(String, boolean)}
   */
  @Test
  void testRetrievesAccountMetricsWithIncludedOpenPositionsFromApi() {
    Metrics metrics = metaStatsClient.getMetrics(accountId, true).join();
    Assertions.assertThat(metrics).usingRecursiveComparison().isEqualTo(expected);
    Mockito.verify(httpClient).requestJson(Mockito.argThat(opts -> {
      HttpRequestOptions expectedOpts = new HttpRequestOptions(
        "https://metastats-api-v1.agiliumtrade.agiliumtrade.ai/users/current/accounts/"
        + accountId + "/metrics", Method.GET);
      expectedOpts.getHeaders().put("auth-token", token);
      expectedOpts.getQueryParameters().put("includeOpenPositions", true);
      Assertions.assertThat(opts).usingRecursiveComparison().isEqualTo(expectedOpts);
      return true;
    }), Mockito.eq(MetricsResponse.class));
  }
}
