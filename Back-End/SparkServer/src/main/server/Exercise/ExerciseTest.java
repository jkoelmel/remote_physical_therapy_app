package main.server.Exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

  private Exercise exercise;

  @BeforeEach
  void resetExercise() {
    exercise = new Exercise(null);
  }

  @Test
  void failCreateExercise() {
    Exception ex =
        assertThrows(
            NullPointerException.class,
            () -> exercise.createExercise(null, "test", "test", "test"));
  }

  @Test
  void getExercise() throws Exception {
    assertNotEquals(null, exercise.getExercise());
  }

  @Test
  void setExerciseId() {
    exercise.setExerciseId(1000);
    assertEquals(1000, exercise.getExerciseId());
  }

  @Test
  void setTitle() {
    exercise.setTitle("test");
    assertEquals("test", exercise.getTitle());
  }

  @Test
  void setexercise_url() {
    exercise.setexercise_url("test_url");
    assertEquals("test_url", exercise.getexercise_url());
  }

  @Test
  void setThumbnail() {
    exercise.setThumbnail("test_thumbnail");
    assertEquals("test_thumbnail", exercise.getThumbnail());
  }

  @Test
  void setDescription() {
    exercise.setDescription("test");
    assertEquals("test", exercise.getDescription());
  }

  @Test
  void setTags() {
    exercise.setTags("test_tags");
    assertEquals("test_tags", exercise.getTags());
  }
}
