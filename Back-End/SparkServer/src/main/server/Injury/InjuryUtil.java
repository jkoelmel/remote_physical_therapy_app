package main.server.Injury;

import com.google.gson.Gson;
import main.server.Server;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

public class InjuryUtil {

  /**
   * Select specific injury by injury id
   *
   * @param request The required query parameters: injury_id
   * @param response The status code from the given request
   * @return The JSON object of the injury to be returned.
   */
  public static String selectSpecific(Request request, Response response) {
    String toReturn = "";
    try {
      Injury injury = new Injury(Integer.parseInt(request.queryMap().get("injury_id").value()));
      Gson gson = new Gson();
      toReturn = gson.toJson(injury.getInjury());

      System.out.println("Injury has been selected");
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
   * Select all injurys from database
   *
   * @param response The status code of selecting all injury
   * @return The JSON object of the list of workouts
   */
  public static String selectAll(Response response) {
    String toReturn = "";
    String query = "SELECT * FROM injury";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      ResultSet rs = pst.executeQuery();

      ArrayList<Injury> list = new ArrayList<>();
      while (rs.next()) {
        Injury injury = new Injury(rs.getInt("injury_id"));
        injury.setInjuryType(rs.getString("injury_type"));

        list.add(injury);
      }
      Gson gson = new Gson();
      toReturn = gson.toJson(list);

      System.out.println("All injuries have been selected");
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
   * Register a new injury
   *
   * @param request The required query parameters: injury_id
   * @return The response status code -- whether the query was successful or not
   */
  public static Integer registerInjury(Request request) {
    try {
      Injury injury = new Injury(Integer.parseInt(request.queryMap().get("injury_id").value()));
      injury.createInjury(request.queryMap().get("injury_type").value());
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
