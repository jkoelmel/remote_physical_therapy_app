package main.server.Workout;

import main.server.Server;

import java.sql.*;

public class Workout {

  private Integer workout_id;
  private String title;
  private Integer pt;

  /**
   * Constructor for a workout given its ID. This can be used when instantiating existing workouts.
   *
   * @param workout_id The integer ID of the workout
   */
  public Workout(Integer workout_id) {
    this.workout_id = workout_id;
  }

  /**
   * Constructor for a workout given its title and PT. This can be used when instantiating a new
   * workout.
   *
   * @param title The string title for the workout
   * @param pt The integer ID of the PT
   */
  public Workout(String title, Integer pt) {
    this.title = title;
    this.pt = pt;
  }

  /**
   * Create a new workout in the database given an instantiated workout object with title and PT.
   *
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public void createWorkout() throws Exception {
    String workoutQuery = "INSERT INTO workout(workout_id,title, pt) VALUES (NULL, ?, ?)";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(workoutQuery)) {

      pst.setString(1, this.title);
      pst.setInt(2, this.pt);
      pst.executeUpdate();

      System.out.println("Workout added to database");
    } catch (SQLException ex) {
      throw new Exception("Error inserting workout: " + ex.toString());
    }
  }

  /**
   * Get a workout from the database for the workout object. The object should be instantiated with
   * its ID first.
   *
   * @return The current workout object
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public Workout getWorkout() throws Exception {
    String workoutQuery = "SELECT * FROM workout WHERE workout_id = " + this.workout_id;
    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(workoutQuery)) {
      pst.executeQuery(workoutQuery);

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        setTitle(rs.getString("title"));
        setPT(rs.getInt("pt"));
      }
    } catch (SQLException ex) {
      throw new Exception(
          "Error getting workout with id " + this.workout_id + ": " + ex.toString());
    }
    return this;
  }

  /**
   * Update an existing workout in the database with a new title.
   *
   * @param title The string title for the workout
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public void updateWorkout(String title) throws Exception {
    String query = "UPDATE workout SET title = " + title + " WHERE workout_id = " + this.workout_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      pst.executeUpdate(query);

      System.out.println("Workout updated");
    } catch (SQLException ex) {
      throw new Exception(
          "Error updating workouts for patient with id " + this.workout_id + ": " + ex.toString());
    }
  }

  public void removeWorkout() throws Exception {

    String query = "DELETE FROM workout WHERE workout_id = " + this.workout_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      pst.executeUpdate(query);

      System.out.println("Workout removed");
    } catch (SQLException ex) {
      throw new Exception(
          "Error deleting workout with id " + this.workout_id + ": " + ex.toString());
    }
  }

  // Getters and setters
  public Integer getWorkoutId() {
    return workout_id;
  }

  public void setWorkoutId(Integer workoutId) {
    this.workout_id = workoutId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getPT() {
    return pt;
  }

  public void setPT(Integer PT) {
    this.pt = PT;
  }
}
