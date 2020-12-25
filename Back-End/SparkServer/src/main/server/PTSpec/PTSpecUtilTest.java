package main.server.PTSpec;

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

public class PTSpecUtilTest {
  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testSelectSpecific() throws IOException {
    when(request.getParameter("pt_spec_id")).thenReturn("1");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

    PTSpec ptSpec = new PTSpec(1);
    ptSpec.setSpec(2);
    ptSpec.setPT(1);

    assertEquals(1, ptSpec.getPTSpecId());
    assertEquals(1, ptSpec.getPT());
    assertEquals(2, ptSpec.getSpec());

    assertEquals(200, response.getStatus());
  }

  @Test
  void testSelectAll() {
    PTSpec ptSpec = new PTSpec(1);
    ptSpec.setSpec(2);
    ptSpec.setPT(1);

    Gson gson = new Gson();
    String toReturn = gson.toJson(ptSpec);

    assertEquals("{\"pt_spec_id\":1,\"pt\":1,\"spec\":2}", toReturn);
  }

  @Test
  void testRegisterPTSpec() {
    when(request.getParameter("pt")).thenReturn("1");
    when(request.getParameter("spec")).thenReturn("2");
    when(response.getStatus()).thenReturn(200);

    PTSpec ptSpec =
        new PTSpec(
            Integer.parseInt(request.getParameter("pt")),
            Integer.parseInt(request.getParameter("spec")));
    ptSpec.setPTSpecId(1);
    Gson gson = new Gson();
    String toReturn = gson.toJson(ptSpec);

    assertEquals("{\"pt_spec_id\":1,\"pt\":1,\"spec\":2}", toReturn);
    assertEquals(200, response.getStatus());
  }
}
