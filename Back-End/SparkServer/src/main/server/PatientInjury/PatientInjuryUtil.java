package main.server.PatientInjury;

import com.google.gson.Gson;
import main.server.Server;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

public class PatientInjuryUtil {

  /**
   * Select the specific patient injury given the patient injury id.
   *
   * @param request The required query parameter: patient_injury_id
   * @param response The status code from the given request
   * @return The JSON object of the patient injury to be returned.
   */
  public static String selectSpecific(Request request, Response response) {
    String toReturn = "";
    try {
      PatientInjury pi =
          new PatientInjury(Integer.parseInt(request.queryMap().get("patient_injury_id").value()));

      Gson gson = new Gson();
      toReturn = gson.toJson(pi.getPatientInjury());

      System.out.println("Patient injury has been selected");
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
   * Select all patient injury from the database.
   *
   * @param request The required query parameter: patient
   * @param response The status code from the given request
   * @return The JSON object of the patient injury to be returned.
   */
  public static String selectAll(Request request, Response response) {
    String toReturn = "";
    String query =
        "SELECT * FROM patient_injury WHERE patient = "
            + Integer.parseInt(request.queryMap().get("patient").value());

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      ResultSet rs = pst.executeQuery();

      ArrayList<PatientInjury> list = new ArrayList<>();
      while (rs.next()) {
        PatientInjury pi = new PatientInjury(rs.getInt("patient_injury_id"));
        pi.setPatient(rs.getInt("patient"));
        pi.setInjury(rs.getInt("injury"));
        list.add(pi);
      }
      Gson gson = new Gson();
      toReturn = gson.toJson(list);

      System.out.println("All injuries for patient have been selected");
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
   * Register a new exercise, given the required query parameters.
   *
   * @param request The required query parameters: patient_injury_id, patient, injury.
   * @return The status code -- whether the query was successful or not.
   */
  public static Integer registerExercise(Request request) {
    try {
      PatientInjury pi =
          new PatientInjury(Integer.parseInt(request.queryMap().get("patient_injury_id").value()));
      pi.createPatientInjury(
          Integer.parseInt(request.queryMap().get("patient").value()),
          Integer.parseInt(request.queryMap().get("injury").value()));
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
