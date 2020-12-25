package main.server.PatientInjury;

import com.google.gson.Gson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.when;

public class PatientInjuryUtilTest {
  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void selectSpecificTest() {

    when(request.getParameter("patient_injury_id")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    PatientInjury patientInjury =
        new PatientInjury(Integer.parseInt(request.getParameter("patient_injury_id")));

    Assertions.assertEquals(1, patientInjury.getpatientInjury_id());
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  void selectAllTest() {
    when(response.getStatus()).thenReturn(200);

    PatientInjury patientInjury = new PatientInjury(1);
    String toReturn = "";

    patientInjury.setpatientInjury_id(1);
    patientInjury.setPatient(2);
    patientInjury.setInjury(3);

    Gson gson = new Gson();
    toReturn = gson.toJson(patientInjury);

    Assertions.assertEquals("{\"patientInjury_id\":1,\"patient\":2,\"injury\":3}", toReturn);
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  void registerExerciseTest() {
    when(request.getParameter("patient_injury_id")).thenReturn("100");
    when(request.getParameter("patient")).thenReturn("1");
    when(request.getParameter("injury")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    PatientInjury patientInjury =
        new PatientInjury(Integer.parseInt(request.getParameter("patient_injury_id")));
    patientInjury.setPatient(1);
    patientInjury.setInjury(1);

    Gson gson = new Gson();
    String toReturn = gson.toJson(patientInjury);

    // Assert JSON object matches expected output

    Assertions.assertEquals("{\"patientInjury_id\":100,\"patient\":1,\"injury\":1}", toReturn);
    Assertions.assertEquals(200, response.getStatus());
  }
}
