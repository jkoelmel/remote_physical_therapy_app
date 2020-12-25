package main.server.Assignment;

import main.server.Server;

import java.sql.*;

/**
 * Assignment Class: Provides basic functionality for CRUD operations of entries into the portalDB
 * 'assignment' table.
 */
public class Assignment {

  private Integer assignment_id;
  private Timestamp start_time;
  private Timestamp end_time;
  private Integer pt;
  private Integer workout;
  private String title;
  private Integer patient;

  /**
   * Assignment constructor: Can be NULL because value is auto-incremented for new entries by the
   * database
   *
   * @param assignment_id
   */
  public Assignment(Integer assignment_id) {
    this.assignment_id = assignment_id;
  }

  /**
   * createAssignment: Takes the provided params to create an SQL INSERT statement that will place a
   * new entry into the 'assignment' table in the database
   *
   * @param pt
   * @param workout
   * @param patient
   * @throws Exception
   */
  public void createAssignment(Integer pt, Integer workout, Integer patient) throws Exception {
    String assignmentQuery =
        "INSERT INTO assignment(assignment_id, start_time, end_time, pt, workout, patient)"
            + " VALUES(NULL, now(), NULL, ?, ?, ?)";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(assignmentQuery)) {

      // INSERT the Assignment into assignment
      pst.setInt(1, pt);
      pst.setInt(2, workout);
      pst.setInt(3, patient);
      pst.executeUpdate();

      System.out.println("Assignment added to database");
    } catch (SQLException sqlEx) {
      throw new Exception("Error inserting assignment: " + sqlEx.toString());
    }
  }

  /**
   * getAssignment: Called by an existing Assignment object to retrieve all data pertinent to that
   * entry in the database
   *
   * @param patient
   * @return
   * @throws Exception
   */
  public Assignment getAssignment(Integer patient) throws Exception {
    String assignmentQuery =
        "SELECT * FROM workout w JOIN assignment a "
            + "ON workout_id = a.workout WHERE a.patient = "
            + patient;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(assignmentQuery)) {
      pst.executeQuery(assignmentQuery);

      ResultSet rs = pst.executeQuery();
      while (rs.next()) {
        // Assignment data
        setWorkout(rs.getInt("workout"));
        setPatient(rs.getInt("patient"));
        setPt(rs.getInt("pt"));
      }
    } catch (SQLException ex) {
      throw new Exception("Error getting assignment: " + ex.toString());
    }

    return this;
  }

  /**
   * updateAssignment: Updates the end_time of the assignment in the database Useful trigger to
   * update 'assignment' table when a patient is assigned something new.
   *
   * @throws Exception
   */
  public void updateAssignment() throws Exception {
    String query = "UPDATE assignment SET end_time = now() WHERE assignment_id = " + assignment_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      pst.executeUpdate(query);

      System.out.println("Assignment updated");
    } catch (Exception ex) {
      throw new Exception("Error updating assignment: " + ex.toString());
    }
  }

  /**
   * The following are all standard setters and getters for this class: getassignment_id
   * setassignment_id getStart_time setStart_time getEnd_time setEnd_time getPt setPt setWorkout
   * getWorkoutID setWorkout getTitle setTitle getPatient setPatient
   */
  public Integer getassignment_id() {
    return assignment_id;
  }

  public void setassignment_id(Integer assignment_id) {
    this.assignment_id = assignment_id;
  }

  public Timestamp getStart_time() {
    return start_time;
  }

  public void setStart_time(Timestamp start_time) {
    this.start_time = start_time;
  }

  public Timestamp getEnd_time() {
    return end_time;
  }

  public void setEnd_time(Timestamp end_time) {
    this.end_time = end_time;
  }

  public Integer getPt() {
    return pt;
  }

  public void setPt(Integer pt) {
    this.pt = pt;
  }

  public Integer getWorkoutID() {
    return workout;
  }

  public void setWorkout(Integer workout) {
    this.workout = workout;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getPatient() {
    return patient;
  }

  public void setPatient(Integer patient) {
    this.patient = patient;
  }
}
