package main.server.PTSpec;

import com.google.gson.Gson;
import main.server.Server;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

public class PTSpecUtil {
  /**
   * Select a specific PT spec given it's required query parameter.
   *
   * @param request The required query parameters: pt_spec_id
   * @param response The status code from the given request
   * @return The JSON object of the PT spec to be returned
   */
  public static String selectSpecific(Request request, Response response) {
    String toReturn = "";
    try {
      PTSpec ptSpec = new PTSpec(Integer.parseInt(request.queryMap().get("pt_spec_id").value()));
      Gson gson = new Gson();
      toReturn = gson.toJson(ptSpec.getPTSpec());

      System.out.println("PT spec has been selected");
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
   * Select all PT specs.
   *
   * @param response The status code from the given request
   * @return The JSON object of the PT spec to be returned
   */
  public static String selectAll(Response response) {
    String toReturn = "";
    String query = "SELECT * FROM pt_spec";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      ResultSet rs = pst.executeQuery();

      ArrayList<PTSpec> list = new ArrayList<>();
      while (rs.next()) {
        PTSpec ptSpec = new PTSpec(rs.getInt("pt_spec_id"));
        ptSpec.setPT(rs.getInt("pt"));
        ptSpec.setSpec(rs.getInt("spec"));

        list.add(ptSpec);
      }
      Gson gson = new Gson();
      toReturn = gson.toJson(list);

      System.out.println("All PT Specs have been selected");
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
   * Register a new PT Spec with the database.
   *
   * @param request The required query parameters: pt, spec
   * @return The response status code -- whether the query was successful or not
   */
  public static Integer registerPTSpec(Request request) {
    try {
      PTSpec ptSpec =
          new PTSpec(
              Integer.parseInt(request.queryMap().get("pt").value()),
              Integer.parseInt(request.queryMap().get("spec").value()));

      ptSpec.createPTSpec();

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
