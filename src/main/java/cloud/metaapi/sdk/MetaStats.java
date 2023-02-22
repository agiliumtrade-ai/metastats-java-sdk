package cloud.metaapi.sdk;

import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cloud.metaapi.sdk.clients.HttpClient;
import cloud.metaapi.sdk.clients.MetaStatsClient;
import cloud.metaapi.sdk.clients.RetryOptions;
import cloud.metaapi.sdk.clients.error_handler.ValidationException;
import cloud.metaapi.sdk.clients.MetaStatsClient.Metrics;

/**
 * MetaStats API SDK
 */
public class MetaStats {
  
  private static Logger logger = LogManager.getLogger(MetaStats.class);
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
    try {
      initialize(token, new ConnectionOptions());
    } catch (ValidationException e) {
      logger.error("Specified options are invalid", e);
    }
  }
  
  /**
   * Constructs MetaStats class instance
   * @param token authorization token
   * @param opts connection options
   * @throws ValidationException if specified options are invalid
   */
  public MetaStats(String token, ConnectionOptions opts) throws ValidationException {
    initialize(token, opts);
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
  
  private void initialize(String token, ConnectionOptions opts) throws ValidationException {
    HttpClient httpClient = new HttpClient(opts.requestTimeout * 1000, opts.connectTimeout * 1000, opts.retryOpts);
    metaStatsClient = new MetaStatsClient(httpClient, token, opts.domain);
  }
}