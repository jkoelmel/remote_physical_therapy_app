package main.server.User;

import main.server.AES.AES;

/**
 * The User class is abstract, so cannot be instantiated on its own. This class can be tested via PT
 * and Patient tests, which both extend it.
 */
public abstract class User {
  private Integer user_id;
  private String email;
  private String password;
  private String f_name;
  private String l_name;
  private String company;
  public static final String secretUser = "passwordEncryption";

  /** Default constructor. */
  public User() {}

  /**
   * The required constructor for any given User. PT and Patient constructors which need to
   * implement this will call super(params).
   *
   * @param email The email string for the User
   * @param password The password string for the User
   * @param f_name The first name string for the User
   * @param l_name The last name string for the User
   * @param company The company name string for the User
   */
  public User(String email, String password, String f_name, String l_name, String company) {
    this.email = email;
    this.password = AES.encrypt(password, secretUser);
    this.f_name = f_name;
    this.l_name = l_name;
    this.company = company;
  }

  // Getters and setters
  public Integer getUser_id() {
    return user_id;
  }

  public void setUser_id(Integer user_id) {
    this.user_id = user_id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getF_name() {
    return f_name;
  }

  public void setF_name(String f_name) {
    this.f_name = f_name;
  }

  public String getL_name() {
    return l_name;
  }

  public void setL_name(String l_name) {
    this.l_name = l_name;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }
}
