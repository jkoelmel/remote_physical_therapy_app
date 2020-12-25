package main.server.Activity;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

  Activity activity;

  @BeforeEach
  void resetActivity() {
    activity = new Activity(null);
  }

  @org.junit.jupiter.api.Test
  void failCreateActivity() {
    Exception ex =
        assertThrows(
            NullPointerException.class,
            () -> activity.createActivity("Test activity", 10, null, null));
  }

  @org.junit.jupiter.api.Test
  void getActivity() throws Exception {
    Activity activity = new Activity(null);
    try {
      activity.getActivity(100, 1);
    } catch (SQLException ex) {
      throw new Exception(ex.toString());
    }

    assertEquals(activity, activity.getActivity(100, 1));
  }

  @org.junit.jupiter.api.Test
  void setActivity_id() {
    activity.setActivity_id(1000);
    assertEquals(1000, activity.getActivity_id());
  }

  @org.junit.jupiter.api.Test
  void setType_activity() {
    activity.setType_activity("test activity");
    assertEquals("test activity", activity.getType_activity());
  }

  @org.junit.jupiter.api.Test
  void setDuration() {
    activity.setDuration(100);
    assertEquals(100, activity.getDuration());
  }

  @org.junit.jupiter.api.Test
  void setStart_time() {
    Timestamp time = new Timestamp(System.currentTimeMillis());
    activity.setStart_time(time);
    assertEquals(time, activity.getStart_time());
  }

  @org.junit.jupiter.api.Test
  void setEnd_time() {
    Timestamp time = new Timestamp(System.currentTimeMillis());
    activity.setEnd_time(time);
    assertEquals(time, activity.getEnd_time());
  }

  @org.junit.jupiter.api.Test
  void setPt() {
    activity.setPt(1000);
    assertEquals(1000, activity.getPt());
  }

  @org.junit.jupiter.api.Test
  void setPatient() {
    activity.setPatient(1000);
    assertEquals(1000, activity.getPatient());
  }
}
