package main.server.PatientVideo;

import com.google.gson.Gson;
import main.server.Server;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

public class PatientVideoUtil {

  /**
   * Select a specific patient video, given its required parameters.
   *
   * @param request The required query parameters: patient_video_id
   * @param response The status code from the given request
   * @return The JSON object of the poatient video to be returned
   */
  public static String selectSpecific(Request request, Response response) {
    String toReturn = "";
    try {
      PatientVideo pv =
          new PatientVideo(Integer.parseInt(request.queryMap().get("patient_video_id").value()));
      Gson gson = new Gson();
      toReturn = gson.toJson(pv.getPatientVideo());

      System.out.println("Patient Video has been selected");
      response.type("application/json");
      response.status(200);
    } catch (SQLException sqlEx) {
      System.err.println(sqlEx.toString());
      response.status(500);
    } catch (Exception ex) {
      System.err.println(ex.toString());
      response.status(400);
    }
    return toReturn;
  }

  /**
   * Select all patient video from the database, given it's required paramters.
   *
   * @param request The required parameters: patient
   * @param response The status code from the given request
   * @return The JSON object of the patient video to be returned.
   */
  public static String selectAll(Request request, Response response) {
    String toReturn = "";
    String query =
        "SELECT * FROM patient_video WHERE patient = "
            + Integer.parseInt(request.queryMap().get("patient").value())
            + " ORDER BY uploaded DESC";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      ResultSet rs = pst.executeQuery();

      ArrayList<PatientVideo> list = new ArrayList<>();
      while (rs.next()) {
        PatientVideo pv = new PatientVideo(rs.getInt("patient_video_id"));
        pv.setVideo_url(rs.getString("video_url"));
        pv.setComment(rs.getString("comment"));
        pv.setUploaded(rs.getTimestamp("uploaded"));
        pv.setPatient(rs.getInt("patient"));

        list.add(pv);
      }
      Gson gson = new Gson();
      toReturn = gson.toJson(list);

      System.out.println("All videos have been selected");
      response.type("application/json");
      response.status(200);
    } catch (SQLException sqlEx) {
      System.err.println(sqlEx.toString());
      response.status(500);
    } catch (Exception ex) {
      System.err.println(ex.toString());
      response.status(400);
    }
    return toReturn;
  }

  /**
   * Register a new patient video into the database, given the required query parameters.
   *
   * @param request The required query parameters: video_url, patient
   * @return The status code -- whether they were successful or not
   */
  public static Integer registerPatientVideo(Request request) {
    try {
      PatientVideo pv = new PatientVideo(null);
      pv.createPatientVideo(
          request.queryMap().get("video_url").value(),
          Integer.parseInt(request.queryMap().get("patient").value()));
      return 200;
    } catch (SQLException sqlEx) {
      System.err.println(sqlEx.toString());
      return 500;
    } catch (Exception ex) {
      System.err.println(ex.toString());
      return 400;
    }
  }

  public static Integer updatePatientVideo(Request request) {
    try {
      PatientVideo pv =
          new PatientVideo(Integer.parseInt(request.queryMap().get("patient_video_id").value()));
      pv.updatePatientVideo(request.queryMap().get("comment").value());
      return 200;
    } catch (SQLException sqlEx) {
      System.err.println(sqlEx.toString());
      return 500;
    } catch (Exception ex) {
      System.err.println(ex.toString());
      return 400;
    }
  }
}
