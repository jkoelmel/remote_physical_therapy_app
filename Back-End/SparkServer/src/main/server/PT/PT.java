package main.server.PT;

import main.server.AES.AES;
import main.server.Server;
import main.server.User.User;
import java.sql.*;

public class PT extends User {
  private Integer pt_id;
  private String description;
  private Integer user;

  /**
   * Default constructor, used for instantiating the PT object to return via JSON.
   *
   * @param email The email string for the PT
   * @param password The password string for the PT
   * @param f_name The first name string for the PT
   * @param l_name The last name string for the PT
   * @param company The company name string for the PT
   */
  public PT(String email, String password, String f_name, String l_name, String company) {
    super(email, password, f_name, l_name, company);
  }

  /**
   * Constructor for instantiating the PT given its ID.
   *
   * @param pt_id The PT's integer id
   */
  public PT(Integer pt_id) {
    this.pt_id = pt_id;
  }

  /**
   * Constructor used for instantiating the PT when logging in.
   *
   * @param email The email string for the PT
   */
  public PT(String email) {
    this.setEmail(email);
  }

  /**
   * Create a PT in the database, as well as it's relevant User. This requires that the PT object
   * has been instantiated via the default constructor, supplying email, password, first and last
   * names, and company.
   *
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public void createPT() throws Exception {
    String userQuery =
        "INSERT INTO user(user_id, email, password, f_name, l_name, company) VALUES(NULL, ?, ?, ?,"
            + " ?, ?);";
    String ptQuery = "INSERT INTO pt(pt_id, user) VALUES(NULL, LAST_INSERT_ID())";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(userQuery)) {

      // INSERT the PT into user
      pst.setString(1, getEmail());
      pst.setString(2, getPassword());
      pst.setString(3, getF_name());
      pst.setString(4, getL_name());
      pst.setString(5, getCompany());
      pst.executeUpdate();

      // INSERT the PT into pt
      pst.executeUpdate(ptQuery);
      System.out.println("PT added to database");
    } catch (SQLException ex) {
      throw new Exception("Error inserting user/pt: " + ex.toString());
    }
  }

  /**
   * Get a PT from the database given its email. This requires that a PT object has been
   * instantiated, and likely uses the "email" constructor.
   *
   * @return The current PT object
   * @throws Exception Throw a SQL exception so that frontend has context for the error.
   */
  public PT getPT() throws Exception {
    String ptQuery = "SELECT * FROM user u JOIN pt p " + "ON u.user_id = p.user WHERE u.email = ?";

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(ptQuery)) {
      pst.setString(1, this.getEmail());

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        // PT data
        setPt_id(rs.getInt("pt_id"));
        setUser(rs.getInt("user"));

        // User data
        setEmail(rs.getString("email"));
        setPassword(AES.decrypt(rs.getString("password"), User.secretUser));
        setF_name(rs.getString("f_name"));
        setL_name(rs.getString("l_name"));
        setCompany(rs.getString("company"));
        setDescription(rs.getString("description"));
      } else {
        throw new SQLException("A user with that email doesn't exist.");
      }
    } catch (SQLException ex) {
      throw new SQLException(
          "Error getting pt with email " + this.getEmail() + ": " + ex.toString());
    }

    return this;
  }

  public PT getInfo() throws Exception {
    String query = "SELECT * FROM pt WHERE pt_id = " + this.pt_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {

      ResultSet rs = pst.executeQuery();
      if (rs.next()) {
        // PT data
        setPt_id(rs.getInt("pt_id"));
        setDescription(rs.getString("description"));
        setUser(rs.getInt("user"));
      } else {
        throw new SQLException("Failed to get info from PT with that pt_id");
      }
    } catch (SQLException ex) {
      throw new SQLException("Failed to get info from PT" + ex.toString());
    }

    return this;
  }

  public PT updatePT(
      String description,
      String f_name,
      String l_name,
      String email,
      String password,
      String company)
      throws Exception {
    String query = "UPDATE pt SET description = '" + description + "' WHERE pt_id = " + this.pt_id;

    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(query)) {
      pst.executeUpdate(query);
      System.out.println("PT description updated");
    } catch (SQLException ex) {
      throw new Exception("Error updating pt with id " + this.pt_id + ": " + ex.toString());
    }

    String userQuery =
        "UPDATE user SET f_name = '"
            + f_name
            + "', l_name = '"
            + l_name
            + "', email = '"
            + email
            + "', password = '"
            + password
            + "', company = '"
            + company
            + "' WHERE user_id = "
            + this.user;
    try (Connection con =
            DriverManager.getConnection(
                Server.databasePath, Server.databaseUsername, Server.databasePassword);
        PreparedStatement pst = con.prepareStatement(userQuery)) {
      pst.executeUpdate(userQuery);
      System.out.println("PT profile updated");
    } catch (SQLException ex) {
      throw new Exception("Error updating pt with id " + this.pt_id + ": " + ex.toString());
    }

    return this;
  }

  // Getters and Setters
  public Integer getPt_id() {
    return pt_id;
  }

  public void setPt_id(Integer pt_id) {
    this.pt_id = pt_id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getUser() {
    return user;
  }

  public void setUser(Integer user) {
    this.user = user;
    setUser_id(user);
  }
}
