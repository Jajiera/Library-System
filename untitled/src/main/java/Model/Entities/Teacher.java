package Model.Entities;

public class Teacher extends Users {
    private String academicGrade; // Ejemplo: "Magíster, Doctor"


    public Teacher(String fullName, String run, String genre, String academicGrade) {
        super(fullName, run, genre);
        this.academicGrade = academicGrade;
    }

    @Override
    public String getUserType() {
        return "Docente";
    }

    public String getAcademicGrade() {
        return academicGrade;
    }

    public void setAcademicGrade(String academicGrade) {
        this.academicGrade = academicGrade;
    }
}