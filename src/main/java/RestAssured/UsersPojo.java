package RestAssured;

/**
 * Hello world!
 *
 */
public class UsersPojo
{
  private  String email;
  private  String first_name;
  private  String last_name;
  private  String Id;

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }
}
