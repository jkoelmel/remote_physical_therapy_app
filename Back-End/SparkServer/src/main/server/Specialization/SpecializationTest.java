package main.server.Specialization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpecializationTest {
  @Test
  void constructorsCreateSpecializationGivenParameters() {
    Specialization spec = new Specialization(1);
    assertEquals(1, spec.getSpec_id());

    spec = new Specialization("shoulders");
    assertEquals("shoulders", spec.getSpec_area());
  }

  @Test
  void gettersAndSettersShouldReturnCorrectData() {
    Specialization spec = new Specialization(1);
    assertEquals(1, spec.getSpec_id());
    spec.setSpec_id(2);
    assertEquals(2, spec.getSpec_id());

    spec = new Specialization("shoulders");
    assertEquals("shoulders", spec.getSpec_area());
    spec.setSpec_area("back");
    assertEquals("back", spec.getSpec_area());
  }
}
