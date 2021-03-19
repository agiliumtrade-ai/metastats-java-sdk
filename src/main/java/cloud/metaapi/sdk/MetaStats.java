package cloud.metaapi.sdk;

import java.util.concurrent.CompletableFuture;

import cloud.metaapi.sdk.clients.HttpClient;
import cloud.metaapi.sdk.clients.MetaStatsClient;
import cloud.metaapi.sdk.clients.RetryOptions;
import cloud.metaapi.sdk.clients.MetaStatsClient.Metrics;

/**
 * MetaStats API SDK
 */
public class MetaStats {
  
  private MetaStatsClient metaStatsClient;
  
  /**
   * Connection options
   */
  public static class ConnectionOptions {
    /**
    * Http client request timeout in seconds, default 60
    */
    public int requestTimeout = 60;
    /**
     * Http client connect timeout in seconds, default 60
     */
    public int connectTimeout = 60;
    /**
     * request domain, default agiliumtrade.agiliumtrade.ai
     */
    public String domain = "agiliumtrade.agiliumtrade.ai"; 
    /**
     * Retry options
     */
    public RetryOptions retryOpts = new RetryOptions();
  }
   
  /**
   * Constructs MetaStats class instance
   * @param token authorization token
   */
  public MetaStats(String token) {
    this(token, new ConnectionOptions());
  }
  
  /**
   * Constructs MetaStats class instance
   * @param token authorization token
   * @param opts connection options
   */
  public MetaStats(String token, ConnectionOptions opts) {
    HttpClient httpClient = new HttpClient(opts.requestTimeout * 1000, opts.connectTimeout * 1000, opts.retryOpts);
    metaStatsClient = new MetaStatsClient(httpClient, token, opts.domain);
  }

  /**
   * Returns metrics of MetaApi account without including open positions
   * Https://metastats-api-v1.agiliumtrade.agiliumtrade.ai/swagger/#!/default/get_users_current_accounts_accountId_metrics
   * @param accountId MetaApi account id
   * @return account metrics
   */
  public CompletableFuture<Metrics> getMetrics(String accountId) {
    return metaStatsClient.getMetrics(accountId, false);
  }
  
  /**
   * Returns metrics of MetaApi account
   * Https://metastats-api-v1.agiliumtrade.agiliumtrade.ai/swagger/#!/default/get_users_current_accounts_accountId_metrics
   * @param accountId MetaApi account id
   * @param includeOpenPositions indicates whether open positions will be included
   * @return account metrics
   */
  public CompletableFuture<Metrics> getMetrics(String accountId, boolean includeOpenPositions) {
    return metaStatsClient.getMetrics(accountId, includeOpenPositions);
  }
}