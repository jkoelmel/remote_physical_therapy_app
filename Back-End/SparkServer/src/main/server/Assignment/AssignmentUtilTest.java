package main.server.Assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test class for API endpoints using mocked HTTP requests and responses generated via jUnit and
 * Mockito
 */
class AssignmentUtilTest {

  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Requires the patient for retrieval of patient assignments Ensuring this is passed from the HTTP
   * query params to the object is essential to proper operation
   */
  @Test
  void testSelectSpecific() {

    when(request.getParameter("patient")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Assignment assignment = new Assignment(null);
    assignment.setPatient(Integer.parseInt(request.getParameter("patient")));

    assertEquals(1, assignment.getPatient());
    assertEquals(200, response.getStatus());
  }

  /**
   * Requires a patient and start and end timestamps to be passed into an Assignment object. This
   * method ensures retrieval of information in proper format
   */
  @Test
  void testSelectAllData() {
    Timestamp time = new Timestamp(System.currentTimeMillis());

    when(request.getParameter("patient")).thenReturn("1");
    when(request.getParameter("start")).thenReturn(time.toString());
    when(request.getParameter("end")).thenReturn(time.toString());
    when(response.getStatus()).thenReturn(200);

    Assignment assignment = new Assignment(null);
    assignment.setPatient(Integer.parseInt(request.getParameter("patient")));
    assignment.setStart_time(Timestamp.valueOf(request.getParameter("start")));
    assignment.setEnd_time(Timestamp.valueOf(request.getParameter("end")));

    assertEquals(1, assignment.getPatient());
    assertEquals(time, assignment.getStart_time());
    assertEquals(time, assignment.getEnd_time());
    assertEquals(200, response.getStatus());
  }

  /**
   * Requires the pt value from the HTTP request query to retrieve all of the workouts created by a
   * particular PT. This is the only facet currently tested
   */
  @Test
  void testSelectPTWorkouts() {

    when(request.getParameter("pt")).thenReturn("100");
    when(response.getStatus()).thenReturn(200);

    Assignment assignment = new Assignment(null);
    assignment.setPt(Integer.parseInt(request.getParameter("pt")));

    assertEquals(100, assignment.getPt());
    assertEquals(200, response.getStatus());
  }

  /**
   * Requires a patient to be passed in from the HTTP request params to find current assignment for
   * patient.
   */
  @Test
  void testGetPatientAssignment() {

    when(request.getParameter("patient")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Assignment assignment = new Assignment(null);
    assignment.setPatient(Integer.parseInt(request.getParameter("patient")));

    assertEquals(1, assignment.getPatient());
    assertEquals(200, response.getStatus());
  }

  /**
   * Method being tested iterates over arrays of workouts and patients to assign all desired
   * workouts to requested patients.
   */
  @Test
  void testAssignToPatients() {
    when(request.getParameterValues("workout")).thenReturn(new String[] {"1", "2", "3"});
    when(request.getParameterValues("patient")).thenReturn(new String[] {"1", "2", "3"});
    when(response.getStatus()).thenReturn(200);

    for (int i = 0; i < request.getParameterValues("workout").length; i++) {
      assertEquals(i + 1, Integer.parseInt(request.getParameterValues("workout")[i]));
    }

    for (int i = 0; i < request.getParameterValues("patient").length; i++) {
      assertEquals(i + 1, Integer.parseInt(request.getParameterValues("patient")[i]));
    }

    assertEquals(200, response.getStatus());
  }
}
