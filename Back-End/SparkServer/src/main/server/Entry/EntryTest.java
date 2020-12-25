package main.server.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

  private Entry entry;

  @BeforeEach
  void resetEntry() {
    entry = new Entry(null);
  }

  @Test
  void failCreateEntry() {
    Exception ex = assertThrows(NullPointerException.class, () -> entry.createEntry());
  }

  @Test
  void getDBEntry() throws Exception {
    assertNotEquals(null, entry.getDBEntry());
  }

  @Test
  void setEntry_id() {
    entry.setEntry_id(1000);
    assertEquals(1000, entry.getEntry_id());
  }

  @Test
  void setEntry() {
    entry.setEntry("Test");
    assertEquals("Test", entry.getEntry());
  }

  @Test
  void setCreated_on() {
    Timestamp time = new Timestamp(System.currentTimeMillis());
    entry.setCreated_on(time);
    assertEquals(time, entry.getCreated_on());
  }

  @Test
  void setPatient() {
    entry.setPatient(1000);
    assertEquals(1000, entry.getPatient());
  }

  @Test
  void setComment() {
    entry.setComment("test");
    assertEquals("test", entry.getComment());
  }
}
