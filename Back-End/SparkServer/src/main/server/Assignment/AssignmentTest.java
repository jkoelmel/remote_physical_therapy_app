package main.server.Assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {

  private Assignment assignment;

  @BeforeEach
  void resetAssignment() {
    assignment = new Assignment(null);
  }

  @Test
  void failCreateAssignment() {
    Exception ex =
        assertThrows(
            NullPointerException.class, () -> assignment.createAssignment(null, null, null));
  }

  @Test
  void getAssignment() throws Exception {
    assertNotEquals(null, assignment.getAssignment(1000));
  }

  @Test
  void setassignment_id() {
    assignment.setassignment_id(1000);
    assertEquals(1000, assignment.getassignment_id());
  }

  @Test
  void setStart_time() {
    Timestamp time = new Timestamp(currentTimeMillis());
    assignment.setStart_time(time);
    assertEquals(time, assignment.getStart_time());
  }

  @Test
  void setEnd_time() {
    Timestamp time = new Timestamp(currentTimeMillis());
    assignment.setEnd_time(time);
    assertEquals(time, assignment.getEnd_time());
  }

  @Test
  void setPt() {
    assignment.setPt(1000);
    assertEquals(1000, assignment.getPt());
  }

  @Test
  void setWorkout() {
    assignment.setWorkout(1000);
    assertEquals(1000, assignment.getWorkoutID());
  }

  @Test
  void setTitle() {
    assignment.setTitle("test title");
    assertEquals("test title", assignment.getTitle());
  }

  @Test
  void setPatient() {
    assignment.setPatient(1000);
    assertEquals(1000, assignment.getPatient());
  }
}
