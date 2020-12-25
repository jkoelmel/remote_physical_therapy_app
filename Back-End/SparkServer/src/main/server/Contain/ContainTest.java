package main.server.Contain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainTest {

  private Contain contain;

  @BeforeEach
  void resetContain() {
    contain = new Contain(null);
  }

  @Test
  void failCreateContain() {
    Exception ex = assertThrows(NullPointerException.class, () -> contain.createContain(null));
  }

  @Test
  void getExerciseList() throws Exception {
    contain.setcontain_id(1);
    assertNotEquals(null, contain.getExerciseList());
  }

  @Test
  void setcontain_id() {
    contain.setcontain_id(1000);
    assertEquals(1000, contain.getcontain_id());
  }

  @Test
  void setWorkout() {
    contain.setWorkout(1000);
    assertEquals(1000, contain.getWorkout());
  }

  @Test
  void setExercise() {
    contain.setExercise(1000);
    assertEquals(1000, contain.getExercise());
  }
}
