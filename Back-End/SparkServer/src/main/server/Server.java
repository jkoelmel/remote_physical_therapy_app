package main.server;

import com.google.gson.Gson;
import main.server.Exercise.ExerciseUtil;
import main.server.PT.*;
import main.server.Activity.*;
import main.server.PTMessage.PTMessageUtil;
import main.server.Patient.*;
import main.server.Entry.*;
import main.server.Assignment.*;
import main.server.Contain.*;
import main.server.PatientMessage.PatientMessageUtil;
import main.server.PatientVideo.PatientVideoUtil;
import main.server.Workout.WorkoutUtil;

import java.sql.*;

import static spark.Spark.*;

/**
 * Server driver program to provide routing for all major endpoints accessed by the frontend. Calls
 * respective functions in Util classes throughout the pacakge.
 */
public class Server {
  public static final String databasePath =
      "jdbc:mysql://portaldb.cciebyoevg9q.us-west-1.rds.amazonaws.com:3306/portalDB";

  public static final String databaseUsername = "admin";
  public static final String databasePassword = "Csc648Team2";

  public static void main(String[] args) {

    port(8080);
    /** The following code provides the required headers to solve CORS-issues */
    options(
        "/*",
        (request, response) -> {
          String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
          if (accessControlRequestHeaders != null) {
            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
          }

          String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
          if (accessControlRequestMethod != null) {
            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
          }

          return "OK";
        });

    before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    System.out.println("Starting server on port 8080");

    path(
        // base route prepended before all API requests
        "/api",
        () -> {
          // Console logging
          before("/*", (q, a) -> System.out.println("Received api call"));

          path(
              // PT routing provides functionality for all major PT queries
              "/pt",
              () -> {
                // Requires email
                get("/email", PTUtil::selectSpecific);
                // No parameters
                get("/all", (request, response) -> PTUtil.selectAll(response));
                // Requires pt_id
                get("/patients", PTUtil::selectPatients);
                // Requires pt
                get("/summary", ActivityUtil::getAllPTActivity);
                // Requires pt and patient
                get("/patient-activity", ActivityUtil::getPatPTSummary);
                // Requires pt
                get("/workouts", AssignmentUtil::selectPTWorkouts);
                // Requires workout
                get("/exercises", ExerciseUtil::getWorkoutExercises);
                // Requires title, pt, exercise_id array, and description array
                post(
                    "/create",
                    (request, response) -> {
                      response.status(ContainUtil.createWorkout(request));
                      return response.status();
                    });
                // Requires workout array, patient array, and pt
                post(
                    "/assign",
                    (request, response) -> {
                      response.status(AssignmentUtil.assignToPatients(request));
                      return response.status();
                    });
                // Requires email, password, f_name, l_name, and company
                post(
                    "/register",
                    (request, response) -> {
                      response.status(PTUtil.registerPT(request));
                      return response.status();
                    });
                // Requires email and password
                post(
                    "/login",
                    (request, response) -> {
                      response.status(PTUtil.loginPT(request));
                      return response.status();
                    });
                path(
                    // Path for message creation and retrieval
                    "/message",
                    () -> {
                      // Requires pt and patient
                      get("/id", PTMessageUtil::getPatPtMessages);
                      // Requires message, patient, and pt
                      post(
                          "/register",
                          (request, response) -> {
                            response.status(PTMessageUtil.registerMessage(request));
                            return response.status();
                          });
                    });
                put(
                    "/update",
                    (request, response) -> {
                      response.status(PTUtil.updatePT(request));
                      return response.status();
                    });
              });

          path(
              // Used for patient creation for testing
              "/patient",
              () -> {
                // Requires patient_id
                get("/id", PatientUtil::selectSpecific);
                // No parameters
                get("/all", (request, response) -> PatientUtil.selectAll(response));
                // Requires email, password, f_name, l_name, company
                post(
                    "/register",
                    (request, response) -> {
                      response.status(PatientUtil.registerPatient(request));
                      return response.status();
                    });
                // Requires patient_id, pt, prospective_pt
                put(
                    "/update-pt",
                    (request, response) -> {
                      response.status(PatientUtil.attachTherapist(request));
                      return response.status();
                    });

                path(
                    // Used for progress log creation and retrieval
                    "/entry",
                    () -> {
                      // Requires entry_id
                      get("/id", EntryUtil::selectSpecific);
                      // Requires patient_id
                      get("/all", EntryUtil::selectAll);
                      // Requires entry and patient_id
                      post(
                          "/register",
                          (request, response) -> {
                            response.status(EntryUtil.registerEntry(request));
                            return response.status();
                          });
                      // Requires entry_id and comment
                      put(
                          "/comment",
                          (request, response) -> {
                            response.status(EntryUtil.updateComment(request));
                            return response.status();
                          });
                    });

                path(
                    // Used for patient video creation and retrieval
                    "/video",
                    () -> {
                      // Requires patient
                      get("/id", PatientVideoUtil::selectAll);
                      // Requires video_url and patient
                      post(
                          "/register",
                          (request, response) -> {
                            response.status(EntryUtil.registerEntry(request));
                            return response.status();
                          });
                      put(
                          "/comment",
                          (request, response) -> {
                            response.status(PatientVideoUtil.updatePatientVideo(request));
                            return response.status();
                          });
                    });

                path(
                    // Used to get workouts assigned to a patient
                    "/workout",
                    () -> {
                      // Requires patient
                      get("/id", AssignmentUtil::getPatientAssignment);
                      put(
                          "/remove",
                          (request, response) -> {
                            response.status(WorkoutUtil.deleteWorkout(request));
                            return response.status();
                          });
                    });
              });

          path(
              // Used for creating and retrieving logs of PT activity
              "/activity",
              () -> {
                // Requires pt and patient query fields to get all activity between the two
                get("/id", ActivityUtil::selectSpecific);
                // Returns all activity data
                get("/all", (request, response) -> ActivityUtil.selectAll(response));
                // Requires type_activity, duration, pt, and patient
                post(
                    "/register",
                    (request, response) -> {
                      response.status(ActivityUtil.registerActivity(request));
                      return response.status();
                    });
              });

          path(
              // Used for checking assignments to patient
              "/assign",
              () -> {
                // Requires patient in query to find workout indices
                get("/id", AssignmentUtil::selectSpecific);
                // Requires patient in query to find all details, assignment, workout, and exercises
                get("/all", AssignmentUtil::selectAllData);
              });

          path(
              // Used for message creation and retrieval
              "/message",
              () -> {
                // Requires pt and patient
                get("/id", PatientMessageUtil::selectAll);
                // Requires message, patient, and pt
                post(
                    "/register",
                    (request, response) -> {
                      response.status(PatientMessageUtil.registerMessage(request));
                      return response.status();
                    });
              });

          path(
              // Used to get exercises assigned to a specific workout
              "/workout",
              () -> {
                // Requires workout_id in query to find exercises
                get("/id", ContainUtil::selectExercises);
              });

          path(
              // Used to get all exercise info from the exercise table
              "/exercise",
              () -> {
                // No requirements, used for exercise library page
                get("/all", ExerciseUtil::selectAll);
                post(
                    "/register",
                    (request, response) -> {
                      response.status(ExerciseUtil.registerExercise(request));
                      return response.status();
                    });
              });
          // Test path for local server testing
          path("/database", () -> get("/version", (request, response) -> databaseVersion()));

          // Example get request and response
          path(
              "/example",
              () ->
                  get(
                      "/user",
                      (request, response) -> {
                        response.status(200);
                        response.type("application/json");
                        PT pt =
                            new PT(
                                "testmail@mail.com",
                                "testPassword",
                                "john",
                                "doe",
                                "some company inc.");
                        Gson gson = new Gson();
                        return gson.toJson(pt);
                      }));
          path(
              // Used for AWS health checks and automated error handling
              "/heartbeat",
              () ->
                  get(
                      "/check",
                      (request, response) -> {
                        response.status(heartbeatCheck());
                        return response.status();
                      }));
          // Console logging
          after("/*", (q, a) -> System.out.println("API call completed"));
        });
  }

  private static String databaseVersion() {
    String query = "SELECT VERSION()";
    String toReturn = "not initialized";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query)) {
      if (rs.next()) {
        toReturn = "MySQL Version " + rs.getString(1);
      }
    } catch (SQLException ex) {
      toReturn = ex.toString();
    }

    return toReturn;
  }

  private static Integer heartbeatCheck() {
    return 200;
  }
}
