package main.server.Workout;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class WorkoutUtilTest {
  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testSelectSpecific() throws IOException {
    when(request.getParameter("workout_id")).thenReturn("1");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

    Workout workout = new Workout(1);
    workout.setPT(1);
    workout.setTitle("test");

    assertEquals(1, workout.getPT());
    assertEquals("test", workout.getTitle());

    assertEquals(200, response.getStatus());
  }

  @Test
  void testSelectAll() {
    Workout workout = new Workout(1);
    workout.setPT(1);
    workout.setTitle("test");

    Gson gson = new Gson();
    String toReturn = gson.toJson(workout);

    assertEquals("{\"workout_id\":1,\"title\":\"test\",\"pt\":1}", toReturn);
  }

  @Test
  void testRegisterWorkout() {
    when(request.getParameter("title")).thenReturn("test");
    when(request.getParameter("pt")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Workout workout =
        new Workout(request.getParameter("title"), Integer.parseInt(request.getParameter("pt")));
    workout.setWorkoutId(1);
    Gson gson = new Gson();
    String toReturn = gson.toJson(workout);

    assertEquals("{\"workout_id\":1,\"title\":\"test\",\"pt\":1}", toReturn);
    assertEquals(200, response.getStatus());
  }
}
