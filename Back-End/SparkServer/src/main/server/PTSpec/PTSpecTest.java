package main.server.PTSpec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PTSpecTest {
  @Test
  void constructorsCreatePTSpecsGivenParameters() {
    Integer pt_spec_id = 1;
    PTSpec ptSpec = new PTSpec(pt_spec_id);

    assertEquals(pt_spec_id, ptSpec.getPTSpecId());

    Integer pt = 2;
    Integer spec = 3;
    ptSpec = new PTSpec(pt, spec);

    assertEquals(pt, ptSpec.getPT());
    assertEquals(spec, ptSpec.getSpec());
  }

  @Test
  void gettersAndSettersShouldReturnCorrectData() {
    PTSpec ptSpec = new PTSpec(1);
    assertEquals(1, ptSpec.getPTSpecId());
    ptSpec.setPTSpecId(2);
    assertEquals(2, ptSpec.getPTSpecId());

    ptSpec = new PTSpec(3, 4);
    assertEquals(3, ptSpec.getPT());
    assertEquals(4, ptSpec.getSpec());
    ptSpec.setPT(5);
    assertEquals(5, ptSpec.getPT());
    ptSpec.setSpec(6);
    assertEquals(6, ptSpec.getSpec());
  }
}
