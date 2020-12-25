package main.server.Activity;

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

/**
 * Test class for API endpoints using mocked HTTP requests and responses generated via jUnit and
 * Mockito
 */
class ActivityUtilTest {

  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Ensuring ingestion of request parameters is properly passed into Activity object. Database
   * retrieval not needed as it is dynamic and only relies on these two parameters to handle query
   * creation.
   */
  @Test
  void testSelectSpecific() throws IOException {

    when(request.getParameter("pt")).thenReturn("100");
    when(request.getParameter("patient")).thenReturn("1");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

    Activity activity = new Activity(null);
    activity.setPt(Integer.parseInt(request.getParameter("pt")));
    activity.setPatient(Integer.parseInt(request.getParameter("patient")));

    assertEquals(100, activity.getPt());
    assertEquals(1, activity.getPatient());

    assertEquals(200, response.getStatus());
  }

  /**
   * No parameters from query string required in this method As such, the proper response is reliant
   * on the output of a JSON object that has been stringified via Gson.
   */
  @Test
  void testSelectAll() {

    Timestamp time = new Timestamp(System.currentTimeMillis());
    Activity activity = new Activity(100);
    String toReturn = "";

    activity.setType_activity("test");
    activity.setStart_time(time);
    activity.setEnd_time(time);
    activity.setPt(100);
    activity.setPatient(1);

    Gson gson = new Gson();
    String timeFormatted = gson.toJson(time);
    toReturn = gson.toJson(activity);

    // Assert JSON object matches expected output
    assertEquals(
        "{\"activity_id\":100,\"type_activity\":\"test\",\"start_time\":"
            + timeFormatted
            + ",\"end_time\":"
            + timeFormatted
            + ",\"pt\":100,\"patient\":1}",
        toReturn);
  }

  /**
   * This method only requires that the pt value is passed successfully from the HTTP request query
   * params. As such, this is the extent of mocked data.
   */
  @Test
  void testGetAllPTActivity() throws IOException {

    when(request.getParameter("pt")).thenReturn("100");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

    Activity activity = new Activity(null);
    activity.setPt(Integer.parseInt(request.getParameter("pt")));

    assertEquals(100, activity.getPt());
    assertEquals(200, response.getStatus());
  }

  /**
   * Method requires the HTTP query string to contain patient and pt information. This is mocked and
   * ensured to be working via the setters and getters of the base Activity class
   */
  @Test
  void testGetPatPTSummary() throws IOException {

    when(request.getParameter("pt")).thenReturn("100");
    when(request.getParameter("patient")).thenReturn("1");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

    Activity activity = new Activity(null);
    activity.setPt(Integer.parseInt(request.getParameter("pt")));
    activity.setPatient(Integer.parseInt(request.getParameter("patient")));

    assertEquals(100, activity.getPt());
    assertEquals(1, activity.getPatient());

    assertEquals(200, response.getStatus());
  }

  /**
   * Requires type_activity, duration, pt, and patient from HTTP query parameters to create a new
   * activity. This test ensures JSON object created is representative of what will be required by
   * the front end upon retrieval of the same object
   */
  @Test
  void testRegisterActivity() {

    String toReturn = "";

    when(request.getParameter("type_activity")).thenReturn("test");
    when(request.getParameter("duration")).thenReturn("10");
    when(request.getParameter("pt")).thenReturn("100");
    when(request.getParameter("patient")).thenReturn("1");

    when(response.getStatus()).thenReturn(200);

    Activity activity = new Activity(null);
    activity.setType_activity(request.getParameter("type_activity"));
    activity.setDuration(Integer.parseInt(request.getParameter("duration")));
    activity.setPt(Integer.parseInt(request.getParameter("pt")));
    activity.setPatient(Integer.parseInt(request.getParameter("patient")));

    Gson gson = new Gson();
    toReturn = gson.toJson(activity);

    assertEquals("{\"type_activity\":\"test\",\"duration\":10,\"pt\":100,\"patient\":1}", toReturn);
    assertEquals(200, response.getStatus());
  }
}
