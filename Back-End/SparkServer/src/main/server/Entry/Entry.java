package main.server.Entry;

import main.server.Server;

import java.sql.*;

/**
 * Entry class: Provides basic functionality for CRUD operations of entries into the portalDB
 * 'entry' table.
 */
public class Entry {

  private Integer entry_id;
  private String entry;
  private Timestamp created_on;
  private Integer patient;
  private String comment;

  /**
   * Entry constructor: Cannot be NULL because this constructor implies creating a matching object
   * to something in the database already
   *
   * @param entry
   * @param patient
   */
  public Entry(String entry, Integer patient) {
    this.entry = entry;
    this.patient = patient;
  }

  /**
   * Entry constructor: Can be NULL because value is auto-incremented for new entries
   *
   * @param entry_id
   */
  public Entry(Integer entry_id) {
    this.entry_id = entry_id;
  }

  /**
   * createEntry: Takes the values from the created Object that used the primary constructor to
   * insert a new entry into the database for a particular patient
   *
   * @throws Exception
   */
  public void createEntry() throws Exception {
    String entryQuery =
        "INSERT INTO entry(entry_id, entry, created_on, patient) VALUES(NULL, ?, NOW(), ?);";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(entryQuery)) {

      pst.setString(1, this.entry);
      pst.setInt(2, this.patient);
      pst.executeUpdate();

      System.out.println("Entry added to database");
    } catch (SQLException ex) {
      throw new Exception("Error inserting entry: " + ex.toString());
    }
  }

  /**
   * getDBEntry: Uses the calling Object's entry_id to retrieve all column data for that specific
   * row of the database
   *
   * @return Entry object will fields identical to desired entry in database
   * @throws Exception
   */
  public Entry getDBEntry() throws Exception {
    String entryQuery = "SELECT * FROM entry WHERE entry_id = " + this.entry_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(entryQuery)) {
      pst.executeQuery(entryQuery);

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        setEntry(rs.getString("entry"));
        setCreated_on(rs.getTimestamp("created_on"));
        setPatient(rs.getInt("patient"));
        setComment(rs.getString("comment"));
      }
    } catch (SQLException ex) {
      throw new Exception("Error getting entry with id " + this.entry_id + ": " + ex.toString());
    }

    return this;
  }

  /**
   * Update the existing entry with a new comment, given the existing entry object has been
   * initialized with ID.
   *
   * @param comment The string comment
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public void updateEntry(String comment) throws Exception {
    String query = "UPDATE entry SET comment = '" + comment + "' WHERE entry_id = " + this.entry_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      pst.executeUpdate(query);
      System.out.println("Entry updated");
    } catch (SQLException ex) {
      throw new Exception("Error updating Entry with id " + this.entry_id + ": " + ex.toString());
    }
  }

  /**
   * The following are all standard setters and getters for this class: getEntry_id setEntry_id
   * getEntry setEntry getCreated_on setCreated_on getPatient setPatient
   */
  public Integer getEntry_id() {
    return entry_id;
  }

  public void setEntry_id(Integer entry_id) {
    this.entry_id = entry_id;
  }

  public String getEntry() {
    return entry;
  }

  public void setEntry(String entry) {
    this.entry = entry;
  }

  public Timestamp getCreated_on() {
    return created_on;
  }

  public void setCreated_on(Timestamp created_on) {
    this.created_on = created_on;
  }

  public Integer getPatient() {
    return patient;
  }

  public void setPatient(Integer patient) {
    this.patient = patient;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
