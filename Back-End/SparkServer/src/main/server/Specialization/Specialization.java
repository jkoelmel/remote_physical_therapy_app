package main.server.Specialization;

import main.server.Server;
import java.sql.*;

public class Specialization {

  private Integer spec_id;
  private String spec_area;

  /**
   * Constructor given the existing Specialization ID.
   *
   * @param spec_id The integer ID for the given specialization
   */
  public Specialization(Integer spec_id) {
    this.spec_id = spec_id;
  }

  /**
   * Constructor given the area for a specialization. This is used primarily when registering a new
   * one, in which the ID is not already present.
   *
   * @param spec_area The specialization area string for the given specialization
   */
  public Specialization(String spec_area) {
    this.spec_area = spec_area;
  }

  /**
   * Insert a specialization into the database after initializing the object with its specialization
   * area.
   *
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public void createSpecialization() throws Exception {
    String specializationQuery = "INSERT INTO specialization(spec_id, spec_area) VALUES(null, ?)";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(specializationQuery)) {

      // INSERT Specialization into specialization
      pst.setString(1, this.spec_area);
      pst.executeUpdate();

      System.out.println("Specialization added to database");
    } catch (SQLException ex) {
      throw new Exception("Error inserting specialization: " + ex.toString());
    }
  }

  /**
   * Get a specialization from the database, and fill out the object with its info.
   *
   * @return The current Specialization object
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public Specialization getSpecialization() throws Exception {

    String specializationQuery = "SELECT * FROM specialization WHERE spec_id = " + this.spec_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(specializationQuery)) {
      pst.executeQuery(specializationQuery);

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        setSpec_id(rs.getInt("spec_id"));
        setSpec_area((rs.getString("spec_area")));
      }
    } catch (SQLException ex) {
      throw new Exception("Error getting specialization with id: " + ex.toString());
    }

    return this;
  }

  /**
   * Update an existing specialization with a new specialization area, given a Specialization object
   * which has already been instantiated with an ID.
   *
   * @param spec_area The new specialization area string
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public void updateSpecialization(String spec_area) throws Exception {
    String query =
        "UPDATE specialization SET spec_area = " + spec_area + " WHERE spec_id = " + spec_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      pst.executeUpdate(query);

      System.out.println("Specialization updated");
    } catch (Exception ex) {
      throw new Exception(
          "Error updating specialization with id " + this.spec_id + ": " + ex.toString());
    }
  }

  // Getters and Setters
  public Integer getSpec_id() {
    return spec_id;
  }

  public void setSpec_id(Integer spec_id) {
    this.spec_id = spec_id;
  }

  public String getSpec_area() {
    return spec_area;
  }

  public void setSpec_area(String spec_area) {
    this.spec_area = spec_area;
  }
}
