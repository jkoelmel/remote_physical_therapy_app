package main.server.Workout;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorkoutTest {
  @Test
  void constructorsCreateWorkoutGivenParameters() {
    Workout workout = new Workout(1);
    assertEquals(1, workout.getWorkoutId());

    workout = new Workout("Knee workout", 2);
    assertEquals("Knee workout", workout.getTitle());
    assertEquals(2, workout.getPT());
  }

  @Test
  void gettersAndSettersShouldReturnCorrectData() {
    Workout workout = new Workout(1);
    assertEquals(1, workout.getWorkoutId());

    workout.setWorkoutId(2);
    assertEquals(2, workout.getWorkoutId());

    workout.setTitle("Leg Workout");
    assertEquals("Leg Workout", workout.getTitle());

    workout.setPT(3);
    assertEquals(3, workout.getPT());
  }
}
