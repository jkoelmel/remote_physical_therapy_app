package main.server.Patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {

  private Patient patient;

  @BeforeEach
  void resetPatient() {
    patient = new Patient(null);
  }

  @Test
  void failCreatePatient() {
    Exception ex = assertThrows(Exception.class, () -> patient.createPatient());
  }

  @Test
  void getPatientTest() throws Exception {
    assertNotEquals(null, patient.getPatient());
  }

  @Test
  void setPatientIdTest() {
    patient.setPatient_id(123);
    assertEquals(123, patient.getPatient_id());
  }

  @Test
  void setUserTest() {
    patient.setUser(111);
    assertEquals(111, patient.getUser());
  }

  @Test
  void setPtTest() {
    patient.setPt(1);
    assertEquals(1, patient.getPt());
  }

  @Test
  void setProspectivePtTest() {
    patient.setProspective_pt(1);
    assertEquals(1, patient.getProspective_pt());
  }

  @Test
  void setInjuryTest() {
    patient.setInjury("Knee");
    assertEquals("Knee", patient.getInjury());
  }
}
