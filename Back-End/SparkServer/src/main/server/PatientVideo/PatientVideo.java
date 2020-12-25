package main.server.PatientVideo;

import main.server.Server;

import java.sql.*;

public class PatientVideo {

  private Integer patient_video_id;
  private String video_url;
  private String comment;
  private Timestamp uploaded;
  private Integer patient;

  /**
   * Default constructor for PatientVideo
   *
   * @param patient_video_id The integer value of patient_video_id
   */
  public PatientVideo(Integer patient_video_id) {
    this.patient_video_id = patient_video_id;
  }

  /**
   * Create a new patient video into the database, given its video_url and the patient.
   *
   * @param video_url The string value of video_url
   * @param patient The integer value of patient
   * @throws Exception Throws SQL exception
   */
  // TODO add uploaded field to all queries
  public void createPatientVideo(String video_url, Integer patient) throws Exception {
    String videoQuery = "INSERT INTO patient_video(video_url, patient) " + "VALUES( ?, ?)";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(videoQuery)) {

      // INSERT Activity into activity
      pst.setString(1, video_url);
      pst.setInt(2, patient);
      pst.executeUpdate();

      System.out.println("Video added to database");
    } catch (SQLException ex) {
      throw new Exception("Error inserting video: " + ex.toString());
    }
  }

  /**
   * Get a specific patient video from the database using the patient_video_id
   *
   * @return The current patient video object
   * @throws Exception Throws SQL exception
   */
  public PatientVideo getPatientVideo() throws Exception {
    String videoQuery =
        "SELECT * FROM patient_video WHERE patient_video_id = " + this.patient_video_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(videoQuery)) {
      pst.executeQuery(videoQuery);

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        setpatient_video_id(rs.getInt("patient_video_id"));
        setVideo_url(rs.getString("video_url"));
        setComment(rs.getString("comment"));
        setUploaded(rs.getTimestamp("uploaded"));
        setPatient(rs.getInt("patient"));
      }
    } catch (SQLException ex) {
      throw new Exception("Error getting exercise with id: " + ex.toString());
    }

    return this;
  }

  /**
   * Update an exisiting patient video from the database using the patient_video_id
   *
   * @param comment The string value of comment
   * @throws Exception Throws an exception
   */
  public void updatePatientVideo(String comment) throws Exception {

    String query =
        "UPDATE patient_video SET comment = '"
            + comment
            + "'"
            + " WHERE patient_video_id = "
            + this.patient_video_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      pst.executeUpdate(query);

      System.out.println("Patient Video updated");
    } catch (Exception ex) {
      throw new Exception(
          "Error updating video with id " + this.patient_video_id + ": " + ex.toString());
    }
  }

  /** Getters and Setters */
  public Integer getpatient_video_id() {
    return patient_video_id;
  }

  public void setpatient_video_id(Integer patient_video_id) {
    this.patient_video_id = patient_video_id;
  }

  public String getVideo_url() {
    return video_url;
  }

  public void setVideo_url(String video_url) {
    this.video_url = video_url;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Timestamp getUploaded() {
    return uploaded;
  }

  public void setUploaded(Timestamp uploaded) {
    this.uploaded = uploaded;
  }

  public Integer getPatient() {
    return patient;
  }

  public void setPatient(Integer patient) {
    this.patient = patient;
  }
}
