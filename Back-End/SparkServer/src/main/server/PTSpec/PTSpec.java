package main.server.PTSpec;

import main.server.Server;

import java.sql.*;

public class PTSpec {

  private Integer pt_spec_id;
  private Integer pt;
  private Integer spec;

  /**
   * Default constructor given the PT and their spec.
   *
   * @param pt The integer ID of the PT
   * @param spec The specialization number
   */
  public PTSpec(Integer pt, Integer spec) {
    this.pt = pt;
    this.spec = spec;
  }

  /**
   * Constructor given an existing PT spec.
   *
   * @param pt_spec_id The integer ID of the PT spec
   */
  public PTSpec(Integer pt_spec_id) {
    this.pt_spec_id = pt_spec_id;
  }

  /**
   * Create a PT spec in the database, given its PT and their specialization. This should be called
   * after the default constructor has initialized the PT spec object with PT and Spec.
   *
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public void createPTSpec() throws Exception {
    String PTSpecQuery = "INSERT INTO pt_spec(pt_spec_id,pt,spec) VALUES (NULL,?,?)";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(PTSpecQuery)) {

      pst.setInt(1, getPT());
      pst.setInt(2, getSpec());

      pst.executeUpdate();

      pst.executeUpdate(PTSpecQuery);
      System.out.println("PTSpec added to database");

    } catch (SQLException ex) {
      throw new Exception("Error creating PTSpec: " + ex.toString());
    }
  }

  /**
   * Get the current PT spec from the database, for the current pt_spec_id.
   *
   * @return The current PT spec object
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public PTSpec getPTSpec() throws Exception {

    String PTSpecQuery = "SELECT * FROM pt_spec WHERE pt_spec_id = " + pt_spec_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(PTSpecQuery)) {
      pst.executeQuery(PTSpecQuery);

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        setPTSpecId(rs.getInt("pt_spec_id"));
        setPT(rs.getInt("pt"));
        setSpec(rs.getInt("spec"));
      }
    } catch (SQLException ex) {
      throw new Exception("Error getting exercise with id: " + ex.toString());
    }

    return this;
  }

  /**
   * Update the existing PT spec given it's ID, and its new PT ID and spec number.
   *
   * @param pt The integer ID of the PT
   * @param spec The integer corresponding to their specialization
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public void updatePTSpec(Integer pt, Integer spec) throws Exception {
    String query =
        "UPDATE pt_spec SET pt =" + pt + ", spec =" + spec + " WHERE pt_spec_id = " + pt_spec_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      pst.executeUpdate(query);

      System.out.println("PTSpec updated");
    } catch (SQLException ex) {
      throw new Exception("Error updating PTSpec: " + ex.toString());
    }
  }

  // Getters and setters
  public Integer getPTSpecId() {
    return pt_spec_id;
  }

  public void setPTSpecId(Integer pt_spec_id) {
    this.pt_spec_id = pt_spec_id;
  }

  public Integer getPT() {
    return pt;
  }

  public void setPT(Integer pt) {
    this.pt = pt;
  }

  public Integer getSpec() {
    return spec;
  }

  public void setSpec(Integer spec) {
    this.spec = spec;
  }
}
