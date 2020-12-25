package main.server.Specialization;

import com.google.gson.Gson;
import main.server.Server;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

public class SpecializationUtil {

  /**
   * Select a specific specialization given query parameters.
   *
   * @param request The required query parameters: spec_id
   * @param response The status code from the given request
   * @return The JSON object of the Specialization to be returned
   */
  public static String selectSpecifc(Request request, Response response) {
    String toReturn = "";

    try {
      Specialization spec =
          new Specialization(Integer.parseInt(request.queryMap().get("spec_id").value()));
      Gson gson = new Gson();
      toReturn = gson.toJson(spec.getSpecialization());

      System.out.println("Specialization has been selected");
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
   * Select all Specializations from the database.
   *
   * @param response The status code from the given request
   * @return The JSON object of the list of Specializations
   */
  public static String selectAll(Response response) {
    String toReturn = "";
    String query = "SELECT * FROM specialization";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      ResultSet rs = pst.executeQuery();

      ArrayList<Specialization> list = new ArrayList<>();
      while (rs.next()) {
        Specialization spec = new Specialization(rs.getInt("spec_id"));
        spec.setSpec_area(rs.getString("spec_area"));

        list.add(spec);
      }
      Gson gson = new Gson();
      toReturn = gson.toJson(list);

      System.out.println("All specializations have been selected");
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
   * Register a new Specialization into the database, given required query parameters.
   *
   * @param request The required query parameters: spec_area
   * @return The response status code -- whether the query was successful or not
   */
  public static Integer registerSpecialization(Request request) {
    try {
      Specialization spec = new Specialization(request.queryMap().get("spec_area").value());
      spec.createSpecialization();

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
