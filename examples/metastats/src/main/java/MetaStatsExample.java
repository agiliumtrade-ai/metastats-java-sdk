import com.fasterxml.jackson.core.JsonProcessingException;

import cloud.metaapi.sdk.MetaStats;
import cloud.metaapi.sdk.clients.MetaStatsClient.Metrics;
import cloud.metaapi.sdk.util.JsonMapper;

public class MetaStatsExample {

  // your MetaApi API token
  private static String token = getEnvOrDefault("TOKEN", "<put in your token here>");
  // your MetaApi account id
  private static String accountId = getEnvOrDefault("ACCOUNT_ID", "<put in your MetaApi account id here>");
   
  public static void main(String[] args) {
    MetaStats metaStats = new MetaStats(token);
    // you can configure http client via second parameter,
    // see javadoc in-code documentation for full definition of possible configuration options
    
    try {
      Metrics metrics = metaStats.getMetrics(accountId).join();
      System.out.println(asJson(metrics).substring(0, 200) + "..."); //-> {trades: ..., balance: ..., ...}
    } catch (Exception err) {
      System.err.println(err);
    }
    System.exit(0);
  }
   
  private static String getEnvOrDefault(String name, String defaultValue) {
    String result = System.getenv(name);
    return (result != null ? result : defaultValue);
  }

  private static String asJson(Object object) throws JsonProcessingException {
    return JsonMapper.getInstance().writeValueAsString(object);
  }
}