package main.server.PTMessage;

import com.google.gson.Gson;
import main.server.AES.AES;
import main.server.Server;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

public class PTMessageUtil {

  private static final String secret = "messageEncryption";

  /**
   * Select the specific PT message given the message ID from the patient.
   *
   * @param request The required query parameters: patient_message_id
   * @param response The status code from the given request
   * @return The JSON object of the PT's message to be returned
   */
  public static String selectSpecific(Request request, Response response) {
    String toReturn = "";

    try {
      PTMessage message =
          new PTMessage(Integer.parseInt(request.queryMap().get("patient_message_id").value()));
      Gson gson = new Gson();
      String mySQLtoSHA = AES.decrypt(message.getMessage(), secret);
      message.setMessage(AES.decrypt(mySQLtoSHA, secret));
      toReturn = gson.toJson(message.getMessage());

      System.out.println("Message has been selected");
      response.type("application/json");
      response.status(200);
    } catch (Exception ex) {
      System.err.println(ex.toString());
      response.status(400);
    }
    return toReturn;
  }

  /**
   * Select all messages between the given PT and their given patient.
   *
   * @param request The required query parameters: pt, patient
   * @param response The status code from the given request
   * @return The JSON object of the PT's and patients messages to be returned
   */
  public static String getPatPtMessages(Request request, Response response) {
    String toReturn = "";
    String query =
        "WITH pt_messages AS ( SELECT u.email AS sender, pt_m.message, pt_m.created_on FROM"
            + " pt_message pt_m INNER JOIN pt p ON pt_m.pt = p.pt_id INNER JOIN user u ON p.user ="
            + " u.user_id WHERE pt_m.pt = ? AND pt_m.patient = ?), patient_messages AS ( SELECT"
            + " u2.email AS sender, p_m.message, p_m.created_on FROM patient_message p_m INNER"
            + " JOIN patient p2 ON p_m.patient = p2.patient_id INNER JOIN user u2 ON p2.user ="
            + " u2.user_id WHERE p_m.pt = ? AND p_m.patient = ?) SELECT * FROM pt_messages UNION"
            + " ALL SELECT * FROM patient_messages ORDER BY created_on";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      int patient = Integer.parseInt(request.queryMap().get("patient").value());
      int pt = Integer.parseInt(request.queryMap().get("pt").value());

      pst.setInt(1, pt);
      pst.setInt(2, patient);
      pst.setInt(3, pt);
      pst.setInt(4, patient);

      ResultSet rs = pst.executeQuery();

      ArrayList<PTMessage> list = new ArrayList<>();
      while (rs.next()) {
        PTMessage message = new PTMessage(rs.getString("sender"), rs.getTimestamp("created_on"));
        String contents = AES.decrypt(rs.getString("message"), secret).split("-")[0];
        message.setMessage(contents);
        list.add(message);
      }
      Gson gson = new Gson();
      toReturn = gson.toJson(list);

      System.out.println("All messages between patient and pt have been selected");
      response.type("application/json");
      response.status(200);
    } catch (SQLException sqlEx) {
      System.err.println(sqlEx.toString());
      response.status(500);
    } catch (Exception ex) {
      System.err.println(ex.toString());
      response.status(400);
    }

    return toReturn;
  }

  /**
   * Register a new message in the database, given the required query parameters.
   *
   * @param request The required query parameters: pt, patient, message
   * @return The response status code -- whether the query was successful or not
   */
  public static Integer registerMessage(Request request) {
    try {
      PTMessage message = new PTMessage(null);

      message.createMessage(
          request.queryMap().get("message").value(),
          Integer.parseInt(request.queryMap().get("patient").value()),
          Integer.parseInt(request.queryMap().get("pt").value()));
      return 200;
    } catch (SQLException sqlEx) {
      System.err.println(sqlEx.toString());
      return 500;
    } catch (Exception ex) {
      System.err.println(ex.toString());
      return 400;
    }
  }
}
