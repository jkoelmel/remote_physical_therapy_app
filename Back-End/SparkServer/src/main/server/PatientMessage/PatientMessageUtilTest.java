package main.server.PatientMessage;

import com.google.gson.Gson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Timestamp;

import static org.mockito.Mockito.when;

public class PatientMessageUtilTest {
  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void selectSpecificTest() {
    when(request.getParameter("message_id")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    PatientMessage patientMessage =
        new PatientMessage(Integer.parseInt(request.getParameter("message_id")));

    Assertions.assertEquals(1, patientMessage.getmessage_id());
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  void selectAllTest() {
    when(response.getStatus()).thenReturn(200);

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    PatientMessage patientMessage = new PatientMessage(1);
    String toReturn = "";

    patientMessage.setmessage_id(1);
    patientMessage.setMessage("test");
    patientMessage.setCreated_On(timestamp);
    patientMessage.setPatient(1);
    patientMessage.setPt(2);

    Gson gson = new Gson();
    String timeFormatted = gson.toJson(timestamp);
    toReturn = gson.toJson(patientMessage);

    Assertions.assertEquals(
        "{\"message_id\":1,\"message\":\"test\",\"created_On\":"
            + timeFormatted
            + ",\"patient\":1,\"pt\":2,\"secret\":\"messageEncryption\"}",
        toReturn);
    Assertions.assertEquals(200, response.getStatus());
  }
}
