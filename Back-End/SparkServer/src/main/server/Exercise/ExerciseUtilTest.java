package main.server.Exercise;

import com.google.gson.Gson;
import main.server.Workout.Workout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Test class for API endpoints using mocked HTTP requests and responses generated via jUnit and
 * Mockito
 */
class ExerciseUtilTest {

  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  /**
   * Requires the exercise_id from the HTTP request params to retrieve selected exercise from the
   * database. As long as constructor properly assigns this value, the rest of the function shall
   * work as desired
   */
  @Test
  void testSelectSpecific() {

    when(request.getParameter("exercise_id")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Exercise exercise = new Exercise(Integer.parseInt(request.getParameter("exercise_id")));

    assertEquals(1, exercise.getExerciseId());
    assertEquals(200, response.getStatus());
  }

  /**
   * Requires no information from the HTTP request params. Test checks formatting of JSON objects
   * returned to the frontend for parsing
   */
  @Test
  void testSelectAll() {

    when(response.getStatus()).thenReturn(200);

    Exercise exercise = new Exercise(1);
    String toReturn = "";

    exercise.setexercise_url("video.com/test");
    exercise.setTitle("title");
    exercise.setDescription("description");
    exercise.setTags("tags");
    exercise.setThumbnail("thumbnail");

    Gson gson = new Gson();
    toReturn = gson.toJson(exercise);

    // Assert JSON object matches expected output
    assertEquals(
        "{\"exercise_id\":1,\"exercise_url\":\"video.com/test\","
            + "\"thumbnail\":\"thumbnail\",\"title\":\"title\",\"description\":\"description\","
            + "\"tags\":\"tags\"}",
        toReturn);
    assertEquals(200, response.getStatus());
  }

  /**
   * Requires the workout value from the HTTP request params. Ingestion of the right value is the
   * key component to testing
   */
  @Test
  void testGetWorkoutExercises() {

    when(request.getParameter("workout")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Workout workout = new Workout(Integer.parseInt(request.getParameter("workout")));

    assertEquals(1, workout.getWorkoutId());
    assertEquals(200, response.getStatus());
  }

  /**
   * Requires all fields of exercise to create a new Object and add it to the database. Testing
   * involves adding all request params to the object successfully
   */
  @Test
  void testRegisterExercise() {

    when(request.getParameter("exercise_id")).thenReturn("1");
    when(request.getParameter("exercise_url")).thenReturn("video.com/test");
    when(request.getParameter("title")).thenReturn("title");
    when(request.getParameter("description")).thenReturn("description");
    when(request.getParameter("tags")).thenReturn("tags");
    when(response.getStatus()).thenReturn(200);

    Exercise exercise = new Exercise(Integer.parseInt(request.getParameter("exercise_id")));
    exercise.setexercise_url(request.getParameter("exercise_url"));
    exercise.setTitle(request.getParameter("title"));
    exercise.setDescription(request.getParameter("description"));
    exercise.setTags(request.getParameter("tags"));

    Gson gson = new Gson();
    String toReturn = gson.toJson(exercise);

    // Assert JSON object created from request params is working properly
    assertEquals(
        "{\"exercise_id\":1,\"exercise_url\":\"video.com/test\","
            + "\"title\":\"title\",\"description\":\"description\",\"tags\":\"tags\"}",
        toReturn);
    assertEquals(200, response.getStatus());
  }
}
