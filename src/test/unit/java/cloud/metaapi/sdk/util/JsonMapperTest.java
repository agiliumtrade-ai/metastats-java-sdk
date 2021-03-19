package cloud.metaapi.sdk.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import cloud.metaapi.sdk.util.EnumModelExample.ExampleEnum;

/**
 * Class for testing json enum parsing
 */
class EnumModelExample {
  
  /**
   * Some example enum
   */
  public enum ExampleEnum { SOME_VALUE }
  
  /**
   * Some example field using the enum
   */
  public ExampleEnum field;
}

/**
 * Class for testing json list parsing
 */
class ListModelExample {

  /**
   * Some example list field
   */
  public List<Integer> list;
}

/**
 * MT version
 */
enum Version {
  VERSION_4(4),
  VERSION_5(5);
  
  private final int version;

  @JsonCreator
  public static Version ofNumber(final int version) {
    for (Version item : Version.values())
      if (item.version == version) return item;
    return null;
  }
  
  private Version(int version) {
    this.version = version;
  }
  
  @JsonValue
  public int getNumber() {
    return version;
  }
}

/**
 * Tests {@link JsonMapper}
 */
public class JsonMapperTest {
  
  /**
   * Tests {@link JsonMapper#getInstance()}
   */
  @Test
  void testTransformsStringFieldIntoEnumValueByName() throws Exception {
    String json = "{\"field\": \"SOME_VALUE\"}";
    EnumModelExample model = JsonMapper.getInstance().readValue(json, EnumModelExample.class);
    assertEquals(ExampleEnum.SOME_VALUE, model.field);
  }
  
  /**
   * Tests {@link JsonMapper#getInstance()}
   */
  @Test
  void testTransformsModelEnumValueNameIntoJsonString() throws Exception {
    EnumModelExample model = new EnumModelExample();
    model.field = ExampleEnum.SOME_VALUE;
    JsonNode actualJson = JsonMapper.getInstance().valueToTree(model);
    assertTrue(actualJson.get("field").isTextual());
    assertEquals(actualJson.get("field").asText(), "SOME_VALUE");
  }
  
  /**
   * Tests {@link JsonMapper#getInstance()}
   */
  @Test
  void testTransformsListIntoJsonArray() throws Exception {
    String jsonString = "{\"list\": [28, 42]}";
    ListModelExample expected = new ListModelExample();
    expected.list = new ArrayList<>();
    expected.list.add(28);
    expected.list.add(42);
    ListModelExample actual = JsonMapper.getInstance().readValue(jsonString, ListModelExample.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
  
  /**
   * Tests {@link JsonMapper#getInstance()}
   */
  @Test
  void testTransformsJsonArrayIntoLists() throws Exception {
    ListModelExample model = new ListModelExample();
    model.list = new LinkedList<>();
    model.list.add(28);
    model.list.add(42);
    String expected = "{\"list\":[28,42]}";
    String actual = JsonMapper.getInstance().writeValueAsString(model);
    assertEquals(expected, actual);
  }
  
  /**
   * Tests {@link JsonMapper#getInstance()}
   */
  @Test
  void testTransformsValuedEnumIntoJson() throws JsonProcessingException {
    Version version = Version.VERSION_4;
    assertEquals("4", JsonMapper.getInstance().writeValueAsString(version));
  }
  
  /**
   * Tests {@link JsonMapper#getInstance()}
   */
  @Test
  void testParsesValuedEnumFromJson() throws JsonProcessingException {
    assertEquals(Version.VERSION_4, JsonMapper.getInstance().readValue("4", Version.class));
  }
  
  public static class MapModel {
    public Map<String, Object> data;
  }
  
  /**
   * Tests {@link JsonMapper#getInstance()}
   */
  @Test
  void testInfersMapValueTypesAutomatically() throws JsonProcessingException {
    MapModel object = new MapModel();
    object.data = new HashMap<>();
    object.data.put("a", 42);
    object.data.put("b", "Hello");
    Map<String, Object> child = new HashMap<>();
    child.put("c", 3000);
    object.data.put("children", Arrays.asList(child));
    String json = JsonMapper.getInstance().writeValueAsString(object); 
    assertEquals("{\"data\":{\"a\":42,\"b\":\"Hello\",\"children\":[{\"c\":3000}]}}", json);
    MapModel actual = JsonMapper.getInstance().readValue(json, MapModel.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(object);
  }
  
  static class UnderscoreModel {
    @JsonProperty("short")
    public short short_;
    @JsonProperty("long")
    public long long_;
  }
  
  /**
   * Tests {@link JsonMapper#getInstance()}
   */
  @Test
  void testAddsUnderscoreToJavaKeywordKeys() throws JsonProcessingException {
    String json = "{\"short\":42,\"long\":12}"; 
    UnderscoreModel actual = JsonMapper.getInstance().readValue(json, UnderscoreModel.class);
    assertThat(actual).usingRecursiveComparison().isEqualTo(new UnderscoreModel() {{
      short_ = 42;
      long_ = 12;
    }});
  }
}