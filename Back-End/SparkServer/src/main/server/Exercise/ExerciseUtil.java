package main.server.Exercise;

import com.google.gson.Gson;
import main.server.Server;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

/**
 * ExerciseUtil class: Provides functions for usage in endpoints so that the actual functionality of
 * the CRUD operations in Exercise class are encapsulated properly
 */
public class ExerciseUtil {

  /**
   * selectSpecific: Uses the exercise_id provided by the queryMap from the browser request to
   * search the database for exact entry
   *
   * @param request
   * @param response
   * @return matching entry from the database, will return empty JSON object if nothing is found
   */
  public static String selectSpecific(Request request, Response response) {
    String toReturn = "";
    try {
      Exercise exercise =
          new Exercise(Integer.parseInt(request.queryMap().get("exercise_id").value()));
      Gson gson = new Gson();
      toReturn = gson.toJson(exercise.getExercise());

      System.out.println("Exercise has been selected");
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
   * selectAll: Simple table query that returns all current exercises in the table
   *
   * @param request
   * @param response
   * @return JSON array of Exercise objects
   */
  public static String selectAll(Request request, Response response) {
    String toReturn = "";
    String query = "SELECT * FROM exercise GROUP BY exercise_url";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      ResultSet rs = pst.executeQuery();

      ArrayList<Exercise> list = new ArrayList<>();
      while (rs.next()) {
        Exercise exercise = new Exercise(rs.getInt("exercise_id"));
        exercise.setexercise_url(rs.getString("exercise_url"));
        exercise.setThumbnail(rs.getString("thumbnail"));
        exercise.setTitle(rs.getString("title"));
        exercise.setDescription(rs.getString("description"));
        exercise.setTags(rs.getString("tags"));

        list.add(exercise);
      }
      Gson gson = new Gson();
      toReturn = gson.toJson(list);

      System.out.println("All exercises have been selected");
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
   * getWorkoutExercises: Uses the workout value provided by the queryMap by the browser request to
   * locate all associated exercises in the database for that workout
   *
   * @param request
   * @param response
   * @return JSON array of Exercise object related to that workout
   */
  public static String getWorkoutExercises(Request request, Response response) {
    String toReturn = "";
    String query =
        "SELECT workout.workout_id, workout.title, exercise.title AS \"exercise\","
            + " exercise.exercise_url, exercise.description  FROM contain JOIN workout ON"
            + " contain.workout = workout.workout_id JOIN exercise ON contain.exercise ="
            + " exercise.exercise_id WHERE workout_id = "
            + Integer.parseInt(request.queryMap().get("workout").value())
            + " ORDER BY exercise ASC";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      ResultSet rs = pst.executeQuery();

      ArrayList<Exercise> list = new ArrayList<>();
      while (rs.next()) {
        Exercise exercise = new Exercise(null);
        exercise.setexercise_url(rs.getString("exercise_url"));
        exercise.setTitle(rs.getString("exercise"));
        exercise.setDescription(rs.getString("description"));

        list.add(exercise);
      }
      Gson gson = new Gson();
      toReturn = gson.toJson(list);

      System.out.println("All exercises for specific workout have been selected");
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
   * registerExercise: Takes all needed info from the queryMap in the browser request to generate a
   * new Exercise object and insert it into the database
   *
   * @param request
   * @return
   */
  public static Integer registerExercise(Request request) throws Exception {
    try {
      Exercise exercise =
          new Exercise(Integer.parseInt(request.queryMap().get("exercise_id").value()));
      exercise.createExercise(
          request.queryMap().get("exercise_url").value(),
          request.queryMap().get("title").value(),
          request.queryMap().get("description").value(),
          request.queryMap().get("tags").value());
      return 200;
    } catch (SQLException sqlEx) {
      System.err.println(sqlEx.toString());
      return 500;
    } catch (Exception ex) {
      // TODO quick implementation, will fix later
      if (ex instanceof java.lang.NumberFormatException) {
        Exercise exercise = new Exercise(null);
        exercise.createExercise(
            request.queryMap().get("exercise_url").value(),
            request.queryMap().get("title").value(),
            request.queryMap().get("description").value(),
            request.queryMap().get("tags").value());
        return 200;
      } else {
        System.err.println(ex.toString());
        return 400;
      }
    }
  }
}
