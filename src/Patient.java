public class Patient {
    int id;
    String name;
    int age;
    String gender;

    public Patient(int id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String toString() {
        return id + " - " + name + " (" + gender + ", " + age + " years)";
    }
}