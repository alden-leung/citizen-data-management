package prog2.finalgroup;

public class Citizen implements Comparable<Citizen> {

    private String fullName, email, address;
    private int age, district;
    private boolean resident;
    private char gender;

    // Constructor
    public Citizen (String fullName,
                    String email,
                    String address,
                    int age,
                    boolean resident,
                    int district,
                    char gender) {

        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.age = age;
        this.resident = resident;
        this.district = district;
        this.gender = gender;

    } // end of Constructor

    // Getters
    public String getFullName() {
        return fullName;
    }

    public char getGender() {
        return gender;
    }

    public boolean isResident() {
        return resident;
    }

    public int getDistrict() {
        return district;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
    // end of Getters

    // Setters
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setResident(boolean resident) {
        this.resident = resident;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    // end of Setters

    public int compareTo(Citizen other) {
        return this.fullName.compareToIgnoreCase(other.fullName);
    } // end of Comparable implementation

    public String toString() {
        return "Citizen{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", district=" + district +
                ", resident=" + resident +
                ", gender=" + gender +
                '}';
    } // end of toString

} // end of Citizen
