package main.server.Injury;

import main.server.Server;

import java.sql.*;

public class Injury {

  private Integer injury_id;
  private String injuryType;

  /**
   * Default constructor, used for instantiating injury with injury_ID
   *
   * @param injury_id: The identification number for the injury
   */
  public Injury(Integer injury_id) {
    this.injury_id = injury_id;
  }

  /**
   * createInjury creates an Injury in the database with an injury id and type of injury.
   *
   * @param injury
   * @throws Exception throws a SQL exception
   */
  public void createInjury(String injury) throws Exception {
    String injuryQuery = "INSERT INTO injury(injury_id, injury_type) VALUES(null, ?)";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(injuryQuery)) {

      // INSERT Injury into injury
      pst.setString(1, injury);
      pst.executeUpdate();

      System.out.println("Injury added to database");
    } catch (SQLException ex) {
      throw new Exception("Error inserting injury: " + ex.toString());
    }
  }

  /**
   * @return the current injury
   * @throws Exception throws a SQL exception
   */
  public Injury getInjury() throws Exception {
    String injuryQuery = "SELECT * FROM injury WHERE injury_id = " + this.injury_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(injuryQuery)) {
      pst.executeQuery(injuryQuery);

      ResultSet rs = pst.executeQuery();

      if (rs.next()) {
        setinjury_id(rs.getInt("injury_id"));
        setInjuryType(rs.getString("injury_type"));
      }

    } catch (SQLException ex) {
      throw new Exception("Error getting injury with id: " + ex.toString());
    }

    return this;
  }

  /** Getters and Setters */
  public Integer getinjury_id() {
    return injury_id;
  }

  public void setinjury_id(Integer injury_id) {
    this.injury_id = injury_id;
  }

  public String getInjuryType() {
    return injuryType;
  }

  public void setInjuryType(String injuryType) {
    this.injuryType = injuryType;
  }
}
