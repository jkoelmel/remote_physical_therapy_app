package main.server.Injury;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InjuryTest {

  private Injury injury;

  @BeforeEach
  void resetInjury() {
    injury = new Injury(null);
  }

  @Test
  void failCreateInjury() {
    Exception ex = assertThrows(Exception.class, () -> injury.createInjury(null));
  }

  @Test
  void getInjuryTest() throws Exception {
    assertNotEquals(null, injury.getInjury());
  }

  @Test
  void setInjuryIdTest() {
    injury.setinjury_id(1000);
    assertEquals(1000, injury.getinjury_id());
  }

  @Test
  void setInjuryTypeTest() {
    injury.setInjuryType("Hip Replacement");
    assertEquals("Hip Replacement", injury.getInjuryType());
  }
}
