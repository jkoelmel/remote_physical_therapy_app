package main.server.PTMessage;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PTMessageUtilTest {
  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testSelectSpecific() throws IOException {
    when(request.getParameter("patient_message_id")).thenReturn("1");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

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

    assertEquals(200, response.getStatus());
  }

  @Test
  void testGetPatPtMessages() throws IOException {
    when(request.getParameter("patient")).thenReturn("1");
    when(request.getParameter("pt")).thenReturn("2");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

    PTMessage ptMessage = new PTMessage(1);
    ptMessage.setCreated_On(Timestamp.valueOf("2020-11-13 19:14:01"));
    ptMessage.setMessage("test");
    ptMessage.setPatient(1);
    ptMessage.setPt(2);
    ptMessage.setSender("test@mail.com");

    Gson gson = new Gson();
    String toReturn = gson.toJson(ptMessage);

    assertEquals(200, response.getStatus());
    assertEquals(
        "{\"message_id\":1,\"message\":\"test\",\"created_On\":\"Nov 13, 2020, 7:14:01"
            + " PM\",\"patient\":1,\"pt\":2,\"sender\":\"test@mail.com\",\"secret\":\"messageEncryption\"}",
        toReturn);
  }

  @Test
  void testRegisterMessage() {
    when(request.getParameter("message")).thenReturn("test");
    when(request.getParameter("patient")).thenReturn("1");
    when(request.getParameter("pt")).thenReturn("2");
    when(response.getStatus()).thenReturn(200);

    PTMessage ptMessage = new PTMessage(1);
    ptMessage.setCreated_On(Timestamp.valueOf("2020-11-13 19:14:01"));
    ptMessage.setMessage("test");
    ptMessage.setPatient(1);
    ptMessage.setPt(2);
    ptMessage.setSender("test@mail.com");
    Gson gson = new Gson();
    String toReturn = gson.toJson(ptMessage);

    assertEquals(
        "{\"message_id\":1,\"message\":\"test\",\"created_On\":\"Nov 13, 2020, 7:14:01"
            + " PM\",\"patient\":1,\"pt\":2,\"sender\":\"test@mail.com\",\"secret\":\"messageEncryption\"}",
        toReturn);
    assertEquals(200, response.getStatus());
  }
}
