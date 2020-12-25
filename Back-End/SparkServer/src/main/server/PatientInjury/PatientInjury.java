package main.server.PatientInjury;

import main.server.Server;

import java.sql.*;

public class PatientInjury {

  private Integer patientInjury_id;
  private Integer patient;
  private Integer injury;

  /**
   * Constructor for PatientInjury
   *
   * @param patientInjury_id The integer value of patientInjury_id
   */
  public PatientInjury(Integer patientInjury_id) {
    this.patientInjury_id = patientInjury_id;
  }

  /**
   * Create a new patient injury into the database
   *
   * @param patient The integer value of patient
   * @param injury The integer value of injury
   * @throws Exception throws a SQL exception
   */
  public void createPatientInjury(Integer patient, Integer injury) throws Exception {
    String patientInjuryQuery =
        "INSERT INTO patient_injury(patient_injury_id, patient, injury" + " VALUES(null, ? ,?)";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(patientInjuryQuery)) {

      // INSERT Activity into activity
      pst.setInt(1, patient);
      pst.setInt(2, injury);
      pst.executeUpdate();

      System.out.println("Patient Injury added to database");
    } catch (SQLException ex) {
      throw new Exception("Error inserting patient injury: " + ex.toString());
    }
  }

  /**
   * get the patient injury from the database.
   *
   * @return The current patient injury object.
   * @throws Exception Throws a SQL exception
   */
  public PatientInjury getPatientInjury() throws Exception {
    String patientInjuryQuery =
        "SELECT * FROM patient_injury WHERE patient_injury_id = " + this.patientInjury_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(patientInjuryQuery)) {
      pst.executeQuery(patientInjuryQuery);

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        setpatientInjury_id(rs.getInt("patient_injury_id"));
        setPatient(rs.getInt("patient"));
        setInjury(rs.getInt("injury"));
      }
    } catch (SQLException ex) {
      throw new Exception("Error getting patient injury with id: " + ex.toString());
    }

    return this;
  }

  /** Getters and Setters */
  public Integer getpatientInjury_id() {
    return patientInjury_id;
  }

  public void setpatientInjury_id(Integer patientInjury_id) {
    this.patientInjury_id = patientInjury_id;
  }

  public Integer getPatient() {
    return patient;
  }

  public void setPatient(Integer patient) {
    this.patient = patient;
  }

  public Integer getInjury() {
    return injury;
  }

  public void setInjury(Integer injury) {
    this.injury = injury;
  }
}
