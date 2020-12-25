package main.server.PatientAssignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientAssignmentTest {

  private PatientAssignment patientAssignment;

  @BeforeEach
  void resetPatientAssignmentTest() {
    patientAssignment = new PatientAssignment(null);
  }

  @Test
  void setPatientIdTest() {
    patientAssignment.setAssignment_id(1);
    assertEquals(1, patientAssignment.getAssignment_id());
  }

  @Test
  void setStartTimeTest() {
    Date date = new Date(System.currentTimeMillis());
    patientAssignment.setStart_time(date);
    assertEquals(date, patientAssignment.getStart_time());
  }

  @Test
  void setEndTimeTest() {
    Date date = new Date(System.currentTimeMillis());
    patientAssignment.setEnd_time(date);
    assertEquals(date, patientAssignment.getEnd_time());
  }

  @Test
  void setPtTest() {
    patientAssignment.setPt(1);
    assertEquals(1, patientAssignment.getPt());
  }

  @Test
  void setWorkoutTest() {
    patientAssignment.setWorkout(1);
    assertEquals(1, patientAssignment.getWorkout());
  }

  @Test
  void setPatientTest() {
    patientAssignment.setPatient(1);
    assertEquals(1, patientAssignment.getPatient());
  }

  @Test
  void setTitleTest() {
    patientAssignment.setTitle("test");
    assertEquals("test", patientAssignment.getTitle());
  }

  @Test
  void setExerciseIdTest() {
    patientAssignment.setExercise_id(1);
    assertEquals(1, patientAssignment.getExercise_id());
  }

  @Test
  void setExerciseUrlTest() {
    patientAssignment.setExercise_url("www.test.com");
    assertEquals("www.test.com", patientAssignment.getExercise_url());
  }

  @Test
  void setExerciseAltTextTest() {
    patientAssignment.setExercise_alt_text("test");
    assertEquals("test", patientAssignment.getExercise_alt_text());
  }

  @Test
  void setDescriptionTest() {
    patientAssignment.setDescription("test");
    assertEquals("test", patientAssignment.getDescription());
  }

  @Test
  void setLengthTest() {
    patientAssignment.setLength(1);
    assertEquals(1, patientAssignment.getLength());
  }
}
