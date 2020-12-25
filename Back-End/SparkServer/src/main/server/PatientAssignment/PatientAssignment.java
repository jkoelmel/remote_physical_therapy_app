package main.server.PatientAssignment;

import java.sql.Date;

public class PatientAssignment {

  private Integer assignment_id;
  private Date start_time;
  private Date end_time;
  private Integer pt;
  private Integer workout;
  private Integer patient;
  private String title;
  private Integer exercise_id;
  private String exercise_url;
  private String exercise_alt_text;
  private String description;
  private Integer length;

  /**
   * Constructor for PatientAssignment
   *
   * @param assignment_id the integer value of assignment_id
   */
  public PatientAssignment(Integer assignment_id) {
    this.assignment_id = assignment_id;
  }

  /** Getters and Setters */
  public Integer getAssignment_id() {
    return assignment_id;
  }

  public void setAssignment_id(Integer assignment_id) {
    this.assignment_id = assignment_id;
  }

  public Date getStart_time() {
    return start_time;
  }

  public void setStart_time(Date start_time) {
    this.start_time = start_time;
  }

  public Date getEnd_time() {
    return end_time;
  }

  public void setEnd_time(Date end_time) {
    this.end_time = end_time;
  }

  public Integer getPt() {
    return pt;
  }

  public void setPt(Integer pt) {
    this.pt = pt;
  }

  public Integer getWorkout() {
    return workout;
  }

  public void setWorkout(Integer workout) {
    this.workout = workout;
  }

  public Integer getPatient() {
    return patient;
  }

  public void setPatient(Integer patient) {
    this.patient = patient;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getExercise_id() {
    return exercise_id;
  }

  public void setExercise_id(Integer exercise_id) {
    this.exercise_id = exercise_id;
  }

  public String getExercise_url() {
    return exercise_url;
  }

  public void setExercise_url(String exercise_url) {
    this.exercise_url = exercise_url;
  }

  public String getExercise_alt_text() {
    return exercise_alt_text;
  }

  public void setExercise_alt_text(String exercise_alt_text) {
    this.exercise_alt_text = exercise_alt_text;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }
}
