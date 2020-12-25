package main.server.PT;

import com.google.gson.Gson;
import main.server.Patient.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PTUtilTest {
  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSelectSpecific() throws IOException {
    when(request.getParameter("email")).thenReturn("test@mail.com");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

    PT pt = new PT(request.getParameter("email"));
    assertEquals("test@mail.com", pt.getEmail());

    assertEquals(200, response.getStatus());
  }

  @Test
  void testSelectPatients() throws IOException {
    when(request.getParameter("pt_id")).thenReturn("1");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

    Patient patient = new Patient(1);
    patient.setPt(Integer.parseInt(request.getParameter("pt_id")));
    patient.setProspective_pt(Integer.parseInt(request.getParameter("pt_id")));
    patient.setPatient_id(1);
    patient.setCompany("test");
    patient.setF_name("John");
    patient.setL_name("Doe");
    patient.setEmail("test2@mail.com");
    patient.setUser(0);
    patient.setUser_id(0);
    patient.setInjury("knee");
    patient.setPassword("test");

    Gson gson = new Gson();
    String toReturn = gson.toJson(patient);

    assertEquals(
        "{\"patient_id\":1,\"user\":0,\"pt\":1,\"prospective_pt\":1,\"injury\":\"knee\",\"user_id\":0,\"email\":\"test2@mail.com\",\"password\":\"test\",\"f_name\":\"John\",\"l_name\":\"Doe\",\"company\":\"test\",\"secret\":\"passwordEncryption\"}",
        toReturn);
    assertEquals(200, response.getStatus());
  }

  @Test
  void testSelectAll() {
    PT pt = new PT(1);
    pt.setEmail("another@mail.com");
    pt.setF_name("Jane");
    pt.setL_name("Day");
    pt.setCompany("Another Test");

    Gson gson = new Gson();
    String toReturn = gson.toJson(pt);

    assertEquals(
        "{\"pt_id\":1,\"email\":\"another@mail.com\",\"f_name\":\"Jane\",\"l_name\":\"Day\",\"company\":\"Another"
            + " Test\",\"secret\":\"passwordEncryption\"}",
        toReturn);
  }

  @Test
  void testRegisterPT() {
    when(request.getParameter("email")).thenReturn("test@mail.com");
    when(request.getParameter("password")).thenReturn("test");
    when(request.getParameter("f_name")).thenReturn("John");
    when(request.getParameter("l_name")).thenReturn("Doe");
    when(request.getParameter("company")).thenReturn("Test Inc");
    when(response.getStatus()).thenReturn(200);

    PT pt =
        new PT(
            request.getParameter("email"),
            request.getParameter("password"),
            request.getParameter("f_name"),
            request.getParameter("l_name"),
            request.getParameter("company"));
    pt.setPt_id(1);
    Gson gson = new Gson();
    String toReturn = gson.toJson(pt);

    assertEquals(
        "{\"pt_id\":1,\"email\":\"test@mail.com\",\"password\":\"AmghBmX9T6vVzpvYoDf0dA\\u003d\\u003d\",\"f_name\":\"John\",\"l_name\":\"Doe\",\"company\":\"Test"
            + " Inc\",\"secret\":\"passwordEncryption\"}",
        toReturn);
    assertEquals(200, response.getStatus());
  }
}
