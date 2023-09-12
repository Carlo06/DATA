public class Student {
    private String name;
    private int id;
    private double gwa;

    public Student(String name, int id, double gwa) {
        this.name = name;
        this.id = id;
        this.gwa = gwa;
    }

    // Getters and setters (you can generate these methods in your IDE)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGwa() {
        return gwa;
    }

    public void setGwa(double gwa) {
        this.gwa = gwa;
    }

    @Override
    public String toString() {
        return "Student [Name = " + name + ", Student Number = " + id + ", GWA = " + gwa + " ]";
    }
}
