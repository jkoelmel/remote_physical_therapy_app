package main.server.PatientInjury;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientInjuryTest {

  private PatientInjury patientInjury;

  @BeforeEach
  void resetPatientInjuryTest() {
    patientInjury = new PatientInjury(null);
  }

  @Test
  void createPatientInjuryTest() {
    Exception ex =
        assertThrows(Exception.class, () -> patientInjury.createPatientInjury(null, null));
  }

  @Test
  void getPatientInjuryTest() throws Exception {
    assertNotEquals(null, patientInjury.getPatientInjury());
  }

  @Test
  void setPatientInjuryTest() {
    patientInjury.setpatientInjury_id(1);
    assertEquals(1, patientInjury.getpatientInjury_id());
  }

  @Test
  void setPatient() {
    patientInjury.setPatient(1);
    assertEquals(1, patientInjury.getPatient());
  }

  @Test
  void setInjury() {
    patientInjury.setInjury(1);
    assertEquals(1, patientInjury.getInjury());
  }
}
