package main.server.PatientVideo;

import com.google.gson.Gson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Timestamp;

import static org.mockito.Mockito.when;

public class PatientVideoUtilTest {
  @Mock HttpServletRequest request;

  @Mock HttpServletResponse response;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void selectSpecificTest() {
    when(request.getParameter("patient_video_id")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    PatientVideo patientVideo =
        new PatientVideo(Integer.parseInt(request.getParameter("patient_video_id")));

    Assertions.assertEquals(1, patientVideo.getpatient_video_id());
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  void selectAllTest() {

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    when(response.getStatus()).thenReturn(200);

    PatientVideo patientVideo = new PatientVideo(1);
    String toReturn = "";

    patientVideo.setpatient_video_id(1);
    patientVideo.setVideo_url("www.test.com");
    patientVideo.setComment("test");
    patientVideo.setUploaded(timestamp);
    patientVideo.setPatient(1);

    Gson gson = new Gson();
    String timeFormatted = gson.toJson(timestamp);

    toReturn = gson.toJson(patientVideo);

    Assertions.assertEquals(
        "{\"patient_video_id\":1,\"video_url\":\"www.test.com\",\"feedback\":\"test\",\"uploaded\":"
            + timeFormatted
            + ",\"patient\":1}",
        toReturn);
    Assertions.assertEquals(200, response.getStatus());
  }

  @Test
  void registerPatientVideoTest() {
    when(request.getParameter("video_url")).thenReturn("1");
    when(request.getParameter("patient")).thenReturn("1");
    when(response.getStatus()).thenReturn(200);

    PatientVideo patientVideo =
        new PatientVideo(Integer.parseInt(request.getParameter("video_url")));
    patientVideo.setPatient(1);

    Gson gson = new Gson();
    String toReturn = gson.toJson(patientVideo);

    Assertions.assertEquals("{\"patient_video_id\":1,\"patient\":1}", toReturn);
    Assertions.assertEquals(200, response.getStatus());
  }
}
