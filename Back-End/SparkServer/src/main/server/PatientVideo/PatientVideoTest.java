package main.server.PatientVideo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class PatientVideoTest {

  private PatientVideo patientVideo;

  @BeforeEach
  void resetPatientVideoTest() {
    patientVideo = new PatientVideo(null);
  }

  @Test
  void createPatientVideoTest() {
    Exception ex =
        assertThrows(
            NullPointerException.class,
            () -> patientVideo.createPatientVideo("www.test.com", null));
  }

  @Test
  void getPatientVideoTest() {
    patientVideo.setpatient_video_id(1);
    assertNotEquals(null, patientVideo.getpatient_video_id());
  }

  @Test
  void setPatientVideoIdTest() {
    patientVideo.setpatient_video_id(1);
    assertEquals(1, patientVideo.getpatient_video_id());
  }

  @Test
  void setVideoUrlTest() {
    patientVideo.setVideo_url("www.test.com");
    assertEquals("www.test.com", patientVideo.getVideo_url());
  }

  @Test
  void setFeedbackTest() {
    patientVideo.setComment("Test");
    assertEquals("Test", patientVideo.getComment());
  }

  @Test
  void setUploadedTest() {
    Timestamp time = new Timestamp(System.currentTimeMillis());

    patientVideo.setUploaded(time);
    assertEquals(time, patientVideo.getUploaded());
  }

  @Test
  void setPatientTest() {
    patientVideo.setPatient(1);
    assertEquals(1, patientVideo.getPatient());
  }
}
