package main.server.Specialization;

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

public class SpecializationUtilTest {
  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testSelectSpecific() throws IOException {
    when(request.getParameter("spec_id")).thenReturn("1");

    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    when(response.getWriter()).thenReturn(pw);
    when(response.getStatus()).thenReturn(200);

    Specialization specialization = new Specialization(1);
    specialization.setSpec_area("knee");

    assertEquals("knee", specialization.getSpec_area());
    assertEquals(1, specialization.getSpec_id());

    assertEquals(200, response.getStatus());
  }

  @Test
  void testSelectAll() {
    Specialization specialization = new Specialization(1);
    specialization.setSpec_area("knee");

    Gson gson = new Gson();
    String toReturn = gson.toJson(specialization);

    assertEquals("{\"spec_id\":1,\"spec_area\":\"knee\"}", toReturn);
  }

  @Test
  void testRegisterSpecialization() {
    when(request.getParameter("spec_area")).thenReturn("back");
    when(response.getStatus()).thenReturn(200);

    Specialization specialization = new Specialization(request.getParameter("spec_area"));
    specialization.setSpec_id(1);
    Gson gson = new Gson();
    String toReturn = gson.toJson(specialization);

    assertEquals("{\"spec_id\":1,\"spec_area\":\"back\"}", toReturn);
    assertEquals(200, response.getStatus());
  }
}
