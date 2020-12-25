package main.server.Workout;

import com.google.gson.Gson;
import main.server.Server;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

public class WorkoutUtil {
  /**
   * Select a specific existing workout from the database, given its ID.
   *
   * @param request The required query parameters: workout_id
   * @param response The status code from the given request
   * @return The JSON object of the Workout to be returned
   */
  public static String selectSpecific(Request request, Response response) {
    String toReturn = "";
    try {
      Workout workout = new Workout(Integer.parseInt(request.queryMap().get("workout_id").value()));
      Gson gson = new Gson();
      toReturn = gson.toJson(workout.getWorkout());

      System.out.println("Workout has been selected");
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
   * Select all Workouts from the database.
   *
   * @param response The status code from the given request
   * @return The JSON object of the list of workouts
   */
  public static String selectAll(Response response) {
    String toReturn = "";

    String query = "SELECT * FROM workout";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      ResultSet rs = pst.executeQuery();

      ArrayList<Workout> list = new ArrayList<>();
      while (rs.next()) {
        Workout workout = new Workout(rs.getInt("workout_id"));

        workout.setPT(rs.getInt("pt"));
        workout.setTitle(rs.getString("title"));
        list.add(workout);
      }
      Gson gson = new Gson();
      toReturn = gson.toJson(list);

      System.out.println("All Workouts have been selected");
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
   * Register a new Workout into the database, given required query parameters.
   *
   * @param request The required query parameters: title, pt
   * @return The response status code -- whether the query was successful or not
   */
  public static Integer registerWorkout(Request request) {
    try {
      Workout workout =
          new Workout(
              request.queryMap().get("title").value(),
              Integer.parseInt(request.queryMap().get("pt").value()));
      workout.createWorkout();
      return 200;
    } catch (SQLException sqlEx) {
      System.err.println(sqlEx.toString());
      return 500;
    } catch (Exception ex) {
      System.err.println(ex.toString());
      return 400;
    }
  }

  public static Integer deleteWorkout(Request request) {
    try {
      Workout workout = new Workout(Integer.parseInt(request.queryMap().get("workout_id").value()));

      workout.removeWorkout();
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
