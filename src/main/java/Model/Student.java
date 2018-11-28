package Model;

public class Student {
    protected String studentID;
    protected String firstName;
    protected String lastName;
    protected String year;
    protected String credit;
    protected String registersubject;
    protected String path;

    public Student(){}

    public Student(String studentID, String firstName, String lastName, String year) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRegistersubject() {
        return registersubject;
    }

    public void setRegistersubject(String registersubject) {
        this.registersubject = registersubject;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID='" + studentID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", year='" + year + '\'' +
                ", credit='" + credit + '\'' +
                ", registersubject='" + registersubject + '\'' +
                '}';
    }
}
