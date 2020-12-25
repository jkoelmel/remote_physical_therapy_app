package main.server.Patient;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.when;

public class PatientUtilTest {

  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void selectSpecificTest() {

    when(request.getParameter("patient_id")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Patient patient = new Patient(Integer.parseInt(request.getParameter("patient_id")));

    Assertions.assertEquals(1, patient.getPatient_id());
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  void selectAllTest() {
    when(response.getStatus()).thenReturn(200);

    Patient patient = new Patient(1);
    String toReturn = "";

    patient.setEmail("test@gmail.com");
    patient.setPassword("test");
    patient.setF_name("John");
    patient.setL_name("Doe");
    patient.setCompany("SFSU");
    patient.setPatient_id(1);
    patient.setUser_id(111);
    patient.setPt(100);
    patient.setProspective_pt(300);

    Gson gson = new Gson();
    toReturn = gson.toJson(patient);

    // Assert JSON object matches expected output

    Assertions.assertEquals(
        "{\"patient_id\":1,\"pt\":100,\"prospective_pt\":300,\"user_id\":111,\"email\""
            + ":\"test@gmail.com\",\"password\":\"test\",\"f_name\":\"John\",\"l_name\":\"Doe\",\"company\":\"SFSU\","
            + "\"secret\":\"passwordEncryption\"}",
        toReturn);
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  void registerPatientTest() {

    when(request.getParameter("email")).thenReturn("100");
    when(request.getParameter("password")).thenReturn("test");
    when(request.getParameter("f_name")).thenReturn("John");
    when(request.getParameter("l_name")).thenReturn("Doe");
    when(request.getParameter("company")).thenReturn("SFSU");
    when(response.getStatus()).thenReturn(200);

    Patient patient = new Patient(Integer.parseInt(request.getParameter("email")));
    patient.setPassword(request.getParameter("password"));
    patient.setF_name(request.getParameter("f_name"));
    patient.setL_name(request.getParameter("l_name"));
    patient.setCompany(request.getParameter("company"));

    Gson gson = new Gson();
    String toReturn = gson.toJson(patient);

    // Assert JSON object matches expected output

    Assertions.assertEquals(
        "{\"patient_id\":100,\"password\":\"test\",\"f_name\":\"John\",\"l_name\":\"Doe\",\"company\":\"SFSU\",\"secret\":\"passwordEncryption\"}",
        toReturn);
    Assertions.assertEquals(200, response.getStatus());
  }
}
