package main.server.PTMessage;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PTMessageTest {
  @Test
  void constructorsCreatePTMessagesGivenParameters() {
    Integer message_id = 1;
    PTMessage ptMessage = new PTMessage(message_id);

    assertEquals(message_id, ptMessage.getmessage_id());

    Timestamp timestamp = Timestamp.valueOf("2020-11-13 19:14:01");
    String sender = "test@mail.com";
    ptMessage = new PTMessage(sender, timestamp);

    assertEquals(sender, ptMessage.getSender());
    assertEquals(timestamp, ptMessage.getCreated_On());
  }

  @Test
  void gettersAndSettersShouldReturnCorrectData() {
    PTMessage ptMessage = new PTMessage(1);
    ptMessage.setCreated_On(Timestamp.valueOf("2020-11-13 19:14:01"));
    ptMessage.setMessage("test");
    ptMessage.setPatient(1);
    ptMessage.setPt(2);
    ptMessage.setSender("test@mail.com");

    assertEquals(1, ptMessage.getmessage_id());
    assertEquals(Timestamp.valueOf("2020-11-13 19:14:01"), ptMessage.getCreated_On());
    assertEquals("test", ptMessage.getMessage());
    assertEquals(1, ptMessage.getPatient());
    assertEquals(2, ptMessage.getPt());
    assertEquals("test@mail.com", ptMessage.getSender());
  }
}
