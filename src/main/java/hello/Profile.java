package hello;

public class Profile {

  private final String fname;
  private final String lname;

  public Profile(String fname, String lname) {
    this.fname = fname;
    this.lname = lname;
  }

  public String getfName() {
    return fname;
  }

  public String getlName() {
    return lname;
  }
}