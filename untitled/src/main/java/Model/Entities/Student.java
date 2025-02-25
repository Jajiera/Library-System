package Model.Entities;

public class Student extends Users {
    private String career;

    public Student(String fullName, String run, String genre, String career) {
        super(fullName, run, genre);
        this.career = career;
    }

    @Override
    public String getUserType() {
        return "Estudiante";
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }
}
