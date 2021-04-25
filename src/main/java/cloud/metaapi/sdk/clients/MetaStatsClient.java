package cloud.metaapi.sdk.clients;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.annotation.JsonProperty;

import cloud.metaapi.sdk.clients.HttpRequestOptions.Method;

/**
 * Metaapi.cloud MetaStats MetaTrader API client
 */
public class MetaStatsClient {

  private HttpClient httpClient;
  private String host;
  private String token;
  
  /**
   * Constructs MetaStats API client instance with default domain agiliumtrade.agiliumtrade.ai
   * @param httpClient HTTP client
   * @param token authorization token
   */
  public MetaStatsClient(HttpClient httpClient, String token) {
    this(httpClient, token, "agiliumtrade.agiliumtrade.ai");
  }
  
  /**
   * Constructs MetaStats API client instance
   * @param httpClient HTTP client
   * @param token authorization token
   * @param domain domain to connect to
   */
  public MetaStatsClient(HttpClient httpClient, String token, String domain) {
    this.httpClient = httpClient;
    this.host = "https://metastats-api-v1." + domain;
    this.token = token;
  }

  /**
   * Profit from trading a currency pair in one trading day
   */
  public static class CurrencySummaryHistoryDayMetrics {
    /**
     * Date of trading day, in broker timezone, YYYY-MM-DD format
     */
    public String date;
    /**
     * Total profit of trading day
     */
    public double totalProfit;
    /**
     * Total pips of trading day, or {@code null}
     */
    public Double totalPips;
    /**
     * Total profit of short trades per day, or {@code null}
     */
    public Double shortProfit;
    /**
     * Total profit of long trades per day, or {@code null}
     */
    public Double longProfit;
    /**
     * Total pips of short trades per day, or {@code null}
     */
    public Double shortPips;
    /**
     * Total pips of long trades per day, or {@code null}
     */
    public Double longPips;
  }

  /**
   * Provides general data of this currency trading
   */
  public static class CurrencySummaryTotalMetrics {
    /**
     * Cumulative profit of this currency trading 
     */
    public double profit;
    /**
     * The number of all trades with this currency 
     */
    public int trades;
    /**
     * Cumulative pips of this currency trading, or {@code null}
     */
    public Double pips;
    /**
     * The number of winning trades with this currency, or {@code null}
     */
    public Integer wonTrades;
    /**
     * The number of losing trades with this currency, or {@code null}
     */
    public Integer lostTrades;
    /**
     * Percentage of winning trades with this currency, or {@code null}
     */
    public Double wonTradesPercent;
    /**
     * Percentage of losing trades with this currency, or {@code null}
     */
    public Double lostTradesPercent;
  }

  /**
   * Provides profit and number of trades in specific trade and currency
   */
  public static class CurrencySummaryTradeMetrics {
    /**
     * Cumulative profit of this currency trading
     */
    public double profit;
    /**
     * The number of all trades with this currency
     */
    public int trades;
    /**
     * Cumulative pips of this currency trading, or {@code null}
     */
    public Double pips;
  }

  /**
   * Provides statistics on winning and losing trades indicating the amount in the context
   * of long and short positions. Statistics is given for all currency pairs, for which
   * positions were opened
   */
  public static class CurrencySummaryMetrics {
    /**
     * Trading currency pair
     */
    public String currency;
    /**
     * History of trading a currency pair per trading days
     */
    public List<CurrencySummaryHistoryDayMetrics> history;
    /**
     * General data (such as profit, number of trades) about trading a specific currency pair
     */
    public CurrencySummaryTotalMetrics total;
    /**
     * Profit and number of trades of short trades in a specific currency, or {@code null}
     */
    @JsonProperty("short")
    public CurrencySummaryTradeMetrics short_;
    /**
     * Profit and number of trades of long trades in a specific currency, or {@code null}
     */
    @JsonProperty("long")
    public CurrencySummaryTradeMetrics long_;
  }

  /**
   * Provides statistics for one trade period compared to the results for the previous period
   */
  public static class PeriodMetrics {
    /**
     * Cumulative profit of this period, or {@code null}
     */
    public Double profit;
    /**
     * Cumulative pips of this period, or {@code null}
     */
    public Double pips;
    /**
     * Cumulative lots of this period, or {@code null}
     */
    public Double lots;
    /**
     * Gain of this period, or {@code null}
     */
    public Double gain;
    /**
     * The number of trades of this period, or {@code null}
     */
    public Integer trades;
    /**
     * Percentage of winning trades of this period, or {@code null}
     */
    public Double wonTradesPercent;
    /**
     * Difference in profit with the previous period, or {@code null}
     */
    public Double profitDifference;
    /**
     * Difference in pips with the previous period, or {@code null}
     */
    public Double pipsDifference;
    /**
     * Difference in lots with the previous period, or {@code null}
     */
    public Double lotsDifference;
    /**
     * Difference in gain with the previous period, or {@code null}
     */
    public Double gainDifference;
    /**
     * Difference in the number of trades with the previous period, or {@code null}
     */
    public Double tradesDifference;
    /**
     * Difference in percentage of winning trades with the previous period, or {@code null}
     */
    public Double wonTradesPercentDifference;
  }

  /**
   * Provides statistics for today, this week, this month, this year
   */
  public static class Periods {
    /**
     * Trade information for today, or {@code null}
     */
    public PeriodMetrics today;
    /**
     * Trade information for this week, or {@code null}
     */
    public PeriodMetrics thisWeek;
    /**
     * Trade information for this month, or {@code null}
     */
    public PeriodMetrics thisMonth;
    /**
     * Trade information for this year, or {@code null}
     */
    public PeriodMetrics thisYear;
  }

  /**
   * Provides each profit received from the volume of trade and changes in balance, total
   * accumulated income and existing account drawdown by day
   */
  public static class DailyGrowthMetrics {
    /**
     * Date of trading day in broker timezone, YYYY-MM-DD format
     */
    public String date;
    /**
     * Cumulative profit per day, or {@code null}
     */
    public Double profit;
    /**
     * Cumulative pips per day, or {@code null}
     */
    public Double pips;
    /**
     * Cumulative lots per day, or {@code null}
     */
    public Double lots;
    /**
     * Cumulative gains per day, or {@code null}
     */
    public Double gains;
    /**
     * Total profit in this day end
     */
    public double totalProfit;
    /**
     * Total gains in this day end
     */
    public double totalGains;
    /**
     * Balance in this day end
     */
    public double balance;
    /**
     * Percentage of balance drawdown in this day end, or {@code null}
     */
    public Double drawdownPercentage;
    /**
     * Maximum registered balance drawdown in basic currency during this day, or {@code null}
     */
    public Double drawdownProfit;
  }

  /**
   * Currency pair trading information for monthly analysis
   */
  public static class MonthlyAnalyticCurrencyMetrics {
    /**
     * Currency pair
     */
    public String currency;
    /**
     * Average holding time of long trades, or {@code null}
     */
    public Double averageHoldingTimeLongsInMilliseconds;
    /**
     * Average holding time of short trades, or {@code null}
     */
    public Double averageHoldingTimeShortsInMilliseconds;
    /**
     * The difference between reward and risk, where the lesser is always one. So 0 means
     * reward:risk=1:1, 2 means 3:1, -0.5 means 1:1.5
     */
    public double rewardToRiskRatio;
    /**
     * The percentage of popularity of this currency this month
     */
    public double popularityPercent;
  }

  /**
   * Monthly analysis of trading on this account
   */
  public static class MonthlyAnalyticsMetrics {
    /**
     * Date of trading month in broker timezone, YYYY-MM format
     */
    public String date;
    /**
     * Cumulative profit per month, or {@code null}
     */
    public Double profit;
    /**
     * Cumulative pips per month, or {@code null}
     */
    public Double pips;
    /**
     * Cumulative lots per month, or {@code null}
     */
    public Double lots;
    /**
     * Cumulative gains per month, or {@code null}
     */
    public Double gains;
    /**
     * The number of trades of this month, or {@code null}
     */
    public Integer trades;
    /**
     * List of currency pair trading informations for monthly analysis, or {@code null}
     */
    public List<MonthlyAnalyticCurrencyMetrics> currencies;
  }

  /**
   * Opening/closing deals by days of the week or by by hours of the day
   */
  public static class TradeByTimeMetrics {
    /**
     * Date of trading month in broker timezone, YYYY-MM format
     */
    public String date;
    /**
     * The total profit of the trades at this time
     */
    public double profit;
    /**
     * The total profit of short trades at this time, or {@code null}
     */
    public Double shortProfit;
    /**
     * The total profit of long trades at this time, or {@code null}
     */
    public Double longProfit;
    /**
     * The total profit of winning trades at this time, or {@code null}
     */
    public Double wonProfit;
    /**
     * The total profit of losing trades at this time, or {@code null}
     */
    public Double lostProfit;
    /**
     * The total pips of the trades at this time, or {@code null}
     */
    public Double pips;
    /**
     * The total pips of short trades at this time, or {@code null}
     */
    public Double shortPips;
    /**
     * The total pips of long trades at this time, or {@code null}
     */
    public Double longPips;
    /**
     * The total pips of winning trades at this time, or {@code null}
     */
    public Double wonPips;
    /**
     * The total pips of losing trades at this time, or {@code null}
     */
    public Double lostPips;
    /**
     * Cumulative lots of trades at this time
     */
    public double lots;
    /**
     * Cumulative gains of trades at this time
     */
    public double gains;
    /**
     * Cumulative gains of short trades at this time, or {@code null}
     */
    public Double shortGains;
    /**
     * Cumulative gains of long trades at this time, or {@code null}
     */
    public Double longGains;
    /**
     * Cumulative gains of winning trades at this time, or {@code null}
     */
    public Double wonGains;
    /**
     * Cumulative gains of losing trades at this time, or {@code null}
     */
    public Double lostGains;
    /**
     * The number of all trades at this time
     */
    public int trades;
    /**
     * The number of short trades at this time, or {@code null}
     */
    public Integer shortTrades;
    /**
     * The number of long trades at this time, or {@code null}
     */
    public Integer longTrades;
    /**
     * The number of winning trades at this time, or {@code null}
     */
    public Integer wonTrades;
    /**
     * The number of losing trades at this time, or {@code null}
     */
    public Integer lostTrades;
    /**
     * Percentage of short trades at this time, or {@code null}
     */
    public Double shortTradesPercent;
    /**
     * Percentage of long trades at this time, or {@code null}
     */
    public Double longTradesPercent;
    /**
     * Percentage of winning trades at this time, or {@code null}
     */
    public Double wonTradesPercent;
    /**
     * Percentage of losing trades at this time, or {@code null}
     */
    public Double lostTradesPercent;
    /**
     * Day hour (only for by hour case), within 0-23, or {@code null}
     */
    public Integer hour;
    /**
     * Weekday number (only for by day case), within 0-6, or {@code null}
     */
    public Integer day;
  }

  /**
   * Risk of ruin of balance metrics
   */
  public static class RiskOfRuinMetrics {
    /**
     * Loss size of balance
     */
    public double lossSize;
    /**
     * Probability of loss shows the risk of losing a particular part of the balance
     */
    public double probabilityOfLoss;
    /**
     * The number of losing trades that must be entered sequentially in order for this
     * part of the balance to be lost
     */
    public int consecutiveLosingTrades;
  }

  /**
   * Metrics of one trade duration
   */
  public static class OneTradeDurationMetrics {
    /**
     * List of gains for this duration
     */
    public List<Double> gains;
    /**
     * List of profits for this duration
     */
    public List<Double> profits;
    /**
     * List of lots for this duration
     */
    public List<Double> lots;
    /**
     * List of pips for this duration, or {@code null}
     */
    public List<Double> pips;
    /**
     * Duration of trades in minutes
     */
    public double durationInMinutes;
  }

  /**
   * Metrics for each duration of trades
   */
  public static class TradeDurationMetrics {
    /**
     * Metrics of winning trades, or {@code null}
     */
    public List<OneTradeDurationMetrics> won;
    /**
     * Metrics of losing trades, or {@code null}
     */
    public List<OneTradeDurationMetrics> lost;
  }

  /**
   * Collection of metrics of trades in the current column for the diagram
   */
  public static class TradeDurationDiagramColumnCollectionMetrics {
    /**
     * List of gains
     */
    public List<Double> gains;
    /**
     * List of profits
     */
    public List<Double> profits;
    /**
     * List of lots
     */
    public List<Double> lots;
    /**
     * List of pips, or {@code null}
     */
    public List<Double> pips;
  }

  /**
   * Information column about the duration of trades for the diagram
   */
  public static class TradeDurationDiagramColumnMetrics {
    /**
     * The number of durations in this column
     */
    public int durations;
    /**
     * The number of trades in this column
     */
    public int trades;
    /**
     * Name of this column, one of "seconds", "minutes", "hours", "days", "weeks", "months"
     */
    public String name;
    /**
     * Minimum trade duration in this column in seconds
     */
    public int minDurationInSeconds;
    /**
     * Maximum trade duration in this column in seconds, or {@code null}
     */
    public Integer maxDurationInSeconds;
    /**
     * Collection of metrics of winning trades in this column, or {@code null}
     */
    public TradeDurationDiagramColumnCollectionMetrics won;
    /**
     * Collection of metrics of losing trades in this column, or {@code null}
     */
    public TradeDurationDiagramColumnCollectionMetrics lost;
  }

  /**
   * Trading statistics metrics
   */
  public static class Metrics {
    /**
     * Indicates whether open positions are included in the metrics, "false" means that
     * there are no open positions Only for a request with includeOpenPositions=true.
     * Can be {@code null}
     */
    public Boolean inclusive;
    /**
     * Money on the account, not accounting for the results of currently open positions
     */
    public double balance;
    /**
     * Date of maximum balance that have ever been on the account, in broker timezone,
     * YYYY-MM-DD HH:mm:ss.SSS format, or {@code null}
     */
    public String highestBalanceDate;
    /**
     * Maximum balance that have ever been on the account, or {@code null}
     */
    public Double highestBalance;
    /**
     * The result (current amount) of all positions, including opened
     */
    public double equity;
    /**
     * Total number of closed positions on the account
     */
    public int trades;
    /**
     * Total amount withdrawn from the deposit, or {@code null}
     */
    public Double withdrawals;
    /**
     * Average trade length (time from open to close) in milliseconds, or {@code null}
     */
    public Double averageTradeLengthInMilliseconds;
    /**
     * The best profit from one trade that has ever been on the account, or {@code null}
     */
    public Double bestTrade;
    /**
     * The worst profit from one trade that has ever been on the account, or {@code null}
     */
    public Double worstTrade;
    /**
     * The best pips from one trade that has ever been on the account, or {@code null}
     */
    public Double bestTradePips;
    /**
     * The worst pips from one trade that has ever been on the account, or {@code null}
     */
    public Double worstTradePips;
    /**
     * Date of the best profit from one trade that have ever been on the account, in broker
     * timezone, YYYY-MM-DD HH:mm:ss.SSS format, or {@code null}
     */
    public String bestTradeDate;
    /**
     * Date of the best pips from one trade that have ever been on the account, in broker
     * timezone, YYYY-MM-DD HH:mm:ss.SSS format, or {@code null}
     */
    public String bestTradePipsDate;
    /**
     * Date of the worst profit from one trade that have ever been on the account, in broker
     * timezone, YYYY-MM-DD HH:mm:ss.SSS format, or {@code null}
     */
    public String worstTradeDate;
    /**
     * Date of the worst pips from one trade that have ever been on the account, in broker
     * timezone, YYYY-MM-DD HH:mm:ss.SSS format, or {@code null}
     */
    public String worstTradePipsDate;
    /**
     * Commissions charged by the broker for the entire period, or {@code null}
     */
    public Double commissions;
    /**
     * Compound daily rate of return, or {@code null}
     */
    public Double dailyGain;
    /**
     * Compound monthly rate of return, or {@code null}
     */
    public Double monthlyGain;
    /**
     * Percentage of current equity to balance, or {@code null}
     */
    public Double equityPercent;
    /**
     * The average expected profitability of one trade in basic currency, or {@code null}
     */
    public Double expectancy;
    /**
     * The average expected profitability of one trade in pips, or {@code null}
     */
    public Double expectancyPips;
    /**
     * Time-weighted rate of return, or {@code null}
     */
    public Double gain;
    /**
     * Geometric holding period return, or {@code null}
     */
    public Double geometricHoldingPeriodReturn;
    /**
     * Cumulative interest and swap for the entire period, or {@code null}
     */
    public Double interest;
    /**
     * The number of long trades, or {@code null}
     */
    public Integer longTrades;
    /**
     * The number of short trades, or {@code null}
     */
    public Integer shortTrades;
    /**
     * The number of long winning trades, or {@code null}
     */
    public Integer longWonTrades;
    /**
     * The number of short winning trades, or {@code null}
     */
    public Integer shortWonTrades;
    /**
     * Percentage of long winning trades, or {@code null}
     */
    public Double longWonTradesPercent;
    /**
     * Percentage of short winning trades, or {@code null}
     */
    public Double shortWonTradesPercent;
    /**
     * Percentage of maximum drawdown of balance during the entire trading history,
     * or {@code null}
     */
    public Double maxDrawdown;
    /**
     * Total volume of trades, or {@code null}
     */
    public Double lots;
    /**
     * Cumulative price units, or {@code null}
     */
    public Double pips;
    /**
     * The total yield of closed positions for the entire period (total result)
     */
    public double profit;
    /**
     * Cumulative deposit for the entire period
     */
    public double deposits;
    /**
     * Simple deposit increase without regard to reinvestment, or {@code null}
     */
    public Double absoluteGain;
    /**
     * The amount yielded by winning trades divided by the amount of losses yielded by
     * losing trades, or {@code null}
     */
    public Double profitFactor;
    /**
     * Average return earned in excess of the risk-free rate per unit of volatility,
     * or {@code null}
     */
    public Double sharpeRatio;
    /**
     * Differentiates harmful volatility from total overall volatility, or {@code null}
     */
    public Double sortinoRatio;
    /**
     * Statistical measure of volatility shows how much variation or dispersion, or {@code null}
     */
    public Double standardDeviationProfit;
    /**
     * A statistical measure that is used to describe profit distribution, or {@code null}
     */
    public Double kurtosisProfit;
    /**
     * Average holding period return, or {@code null}
     */
    public Double averageHoldingPeriodReturn;
    /**
     * Average win in basic currency, or {@code null}
     */
    public Double averageWin;
    /**
     * Average win in pips, or {@code null}
     */
    public Double averageWinPips;
    /**
     * Average loss in basic currency, or {@code null}
     */
    public Double averageLoss;
    /**
     * Average loss in pips, or {@code null}
     */
    public Double averageLossPips;
    /**
     * Percentage of winning trades, or {@code null}
     */
    public Double wonTradesPercent;
    /**
     * Percentage of losing trades, or {@code null}
     */
    public Double lostTradesPercent;
    /**
     * Ability of a trading system to generate wins and losses in streaks, or {@code null}
     */
    public Double zScore;
    /**
     * Probability that a profit will be followed by a profit and a loss by a loss,
     * or {@code null}
     */
    public Double probability;
    /**
     * Currency trading summary, or {@code null}
     */
    public List<CurrencySummaryMetrics> currencySummary;
    /**
     * Daily gain shows the change in account profitability on trading days,
     * or {@code null}
     */
    public List<DailyGrowthMetrics> dailyGrowth;
    /**
     * Monthly analysis of trading on this account, or {@code null}
     */
    public List<MonthlyAnalyticsMetrics> monthlyAnalytics;
    /**
     * Closing deals by days of the week, or {@code null}
     */
    public List<TradeByTimeMetrics> closeTradesByWeekDay;
    /**
     * Opening deals by hour of the day, or {@code null}
     */
    public List<TradeByTimeMetrics> openTradesByHour;
    /**
     * Trading stats for a few periods compared to the results for the previous period,
     * or {@code null}
     */
    public Periods periods;
    /**
     * Risk of ruin of balance, or {@code null}
     */
    public List<RiskOfRuinMetrics> riskOfRuin;
    /**
     * Metrics for each duration of trades, or {@code null}
     */
    public TradeDurationMetrics tradeDuration;
    /**
     * List of information columns about the duration of trades for the diagram,
     * or {@code null}
     */
    public List<TradeDurationDiagramColumnMetrics> tradeDurationDiagram;
  }

  /**
   * Metrics response model
   */
  protected static class MetricsResponse {
    /**
     * Metrics
     */
    public Metrics metrics;
  }
  
  /**
   * Returns metrics of MetaApi account without including open positions
   * Https://metastats-api-v1.agiliumtrade.agiliumtrade.ai/swagger/#!/default/get_users_current_accounts_accountId_metrics
   * @param accountId MetaApi account id
   * @return account metrics
   */
  public CompletableFuture<Metrics> getMetrics(String accountId) {
    return getMetrics(accountId, false);
  }
  
  /**
   * Returns metrics of MetaApi account
   * Https://metastats-api-v1.agiliumtrade.agiliumtrade.ai/swagger/#!/default/get_users_current_accounts_accountId_metrics
   * @param accountId MetaApi account id
   * @param includeOpenPositions indicates whether open positions will be included
   * In the metrics, default false
   * @return account metrics
   */
  public CompletableFuture<Metrics> getMetrics(String accountId, boolean includeOpenPositions) {
    HttpRequestOptions opts = new HttpRequestOptions(host + "/users/current/accounts/" + accountId + "/metrics", Method.GET);
    opts.getQueryParameters().put("includeOpenPositions", includeOpenPositions);
    opts.getHeaders().put("auth-token", token);
    return httpClient.requestJson(opts, MetricsResponse.class).thenApply(metrics -> metrics.metrics);
  }
}