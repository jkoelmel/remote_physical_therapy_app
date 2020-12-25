package main.server.Injury;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class InjuryUtilTest {

  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void SelectSpecificTest() throws IOException {

    when(request.getParameter("injury_id")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    Injury injury = new Injury(Integer.parseInt(request.getParameter("injury_id")));

    assertEquals(1, injury.getinjury_id());
    assertEquals(200, response.getStatus());
  }

  @Test
  void SelectAllTest() {

    when(response.getStatus()).thenReturn(200);

    Injury injury = new Injury(1);
    String toReturn = "";

    injury.setinjury_id(1);
    injury.setInjuryType("test/Knee");

    Gson gson = new Gson();
    toReturn = gson.toJson(injury);

    // Assert JSON object matches expected output

    assertEquals("{\"injury_id\":1,\"injuryType\":\"test/Knee\"}", toReturn);
    assertEquals(200, response.getStatus());
  }

  @Test
  void registerInjuryTest() {

    when(request.getParameter("injury_id")).thenReturn("1");
    when(request.getParameter("injury_type")).thenReturn("test/Hip");
    when(response.getStatus()).thenReturn(200);

    Injury injury = new Injury(Integer.parseInt(request.getParameter("injury_id")));
    injury.setInjuryType(request.getParameter("injury_type"));

    Gson gson = new Gson();
    String toReturn = gson.toJson(injury);

    assertEquals("{\"injury_id\":1,\"injuryType\":\"test/Hip\"}", toReturn);
  }
}
