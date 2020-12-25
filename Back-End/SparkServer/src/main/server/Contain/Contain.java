package main.server.Contain;

import main.server.Server;
import java.sql.*;

/**
 * Contain Class: Provides basic functionality for CRUD operations of entries in the portalDB
 * 'contain' table. This table is a relationship turned table for the M:M required between workouts
 * and exercises
 */
public class Contain {

  private Integer contain_id;
  private Integer workout;
  private Integer exercise;

  /**
   * Contain constructor: Can be NULL becuase value is auto-incremented for new entries in the
   * database
   *
   * @param contain_id
   */
  public Contain(Integer contain_id) {
    this.contain_id = contain_id;
  }

  /**
   * createContain: Takes the param Integer for workout and associates the entry to the most
   * recently added exercise because the workouts are created at the same time as the custom
   * exercises in order desired by the pt creating it
   *
   * @param workout
   * @throws Exception
   */
  public void createContain(Integer workout) throws Exception {
    String containQuery = "INSERT INTO contain(workout, exercise) VALUES (?, ?)";

    Integer exercise_id = -1;
    String query = "SELECT MAX(exercise_id) FROM exercise";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(containQuery);
        PreparedStatement qst = con.prepareStatement(query)) {

      ResultSet rs = qst.executeQuery();

      while (rs.next()) {
        exercise_id = rs.getInt("MAX(exercise_id)");
      }

      // INSERT Contain into contain
      pst.setInt(1, workout);
      pst.setInt(2, exercise_id);
      pst.executeUpdate();
      System.out.println("Contain added to database");
    } catch (SQLException ex) {
      throw new Exception("Error inserting contains: " + ex.toString());
    }
  }

  /**
   * getExerciseList: Calling object uses its workout_id to query the database and provide all
   * exercises associated with it as logged by the 'contain' table
   *
   * @return
   * @throws Exception
   */
  public Contain getExerciseList() throws Exception {
    String containQuery =
        "SELECT * FROM exercise e INNER JOIN contain  c ON e.exercise_id = c.exercise"
            + " WHERE c.workout = "
            + this.workout;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(containQuery)) {
      pst.executeQuery(containQuery);

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        // Contain data
        setcontain_id(rs.getInt("contain_id"));
        setWorkout(rs.getInt("workout"));
        setExercise(rs.getInt("exercise"));
        ;
      }
    } catch (SQLException ex) {
      throw new Exception("Error getting exercise data: " + ex.toString());
    }
    return this;
  }

  /**
   * updateContain: Basic operation to update a contain entry in the database. The params are used
   * to fulfill escaped values in the SQL statement
   *
   * @param workout
   * @param exercise
   * @throws Exception
   */
  public void updateContain(Integer workout, Integer exercise) throws Exception {
    String query =
        "UPDATE contain SET workout = "
            + workout
            + ", exercise = "
            + exercise
            + " WHERE contain_id + "
            + this.contain_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      pst.executeUpdate(query);

      System.out.println("Contain updated");
    } catch (Exception ex) {
      throw new Exception("Error updating contain: " + ex.toString());
    }
  }

  /**
   * The following are all standard setters and getters for this class: getcontain_id setcontain_id
   * getWorkout setWorkout getExercise setExercise
   */
  public Integer getcontain_id() {
    return contain_id;
  }

  public void setcontain_id(Integer contain_id) {
    this.contain_id = contain_id;
  }

  public Integer getWorkout() {
    return workout;
  }

  public void setWorkout(Integer workout) {
    this.workout = workout;
  }

  public Integer getExercise() {
    return exercise;
  }

  public void setExercise(Integer exercise) {
    this.exercise = exercise;
  }
}
