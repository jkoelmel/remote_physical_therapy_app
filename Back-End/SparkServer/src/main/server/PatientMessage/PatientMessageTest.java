package main.server.PatientMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class PatientMessageTest {

  private PatientMessage patientMessage;

  @BeforeEach
  void resetPatientMessage() {
    patientMessage = new PatientMessage(null);
  }

  @Test
  void createPatientMessageTest() {
    Exception ex =
        assertThrows(
            NullPointerException.class, () -> patientMessage.createMessage("test", null, null));
  }

  @Test
  void getMessageContentTest() throws Exception {
    assertNotEquals(null, patientMessage.getMessageContents());
  }

  @Test
  void setMessageIdTest() {
    patientMessage.setmessage_id(1);
    assertEquals(1, patientMessage.getmessage_id());
  }

  @Test
  void setMessage() {
    patientMessage.setMessage("test");
    assertEquals("test", patientMessage.getMessage());
  }

  @Test
  void setCreatedOnTest() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    patientMessage.setCreated_On(timestamp);
    assertEquals(timestamp, patientMessage.getCreated_On());
  }

  @Test
  void setPatientTest() {
    patientMessage.setPatient(1);
    assertEquals(1, patientMessage.getPatient());
  }

  @Test
  void setPtTest() {
    patientMessage.setPt(1);
    assertEquals(1, patientMessage.getPt());
  }
}
