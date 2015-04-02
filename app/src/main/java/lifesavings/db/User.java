package lifesavings.db;

/**
 * Created by dave on 4/2/15.
 */
public class User {

    private int userid;
    private String name;
    private String gender;
    private int weight;
    private int height;
    private int bmi;
    private int category;

    public User() {

    }

    public User(int userid, String name, String gender, int weight, int height, int bmi, int category, double bfp) {
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.category = category;
        this.bfp = bfp;
    }

    public double getBfp() {
        return bfp;
    }

    public void setBfp(double bfp) {
        this.bfp = bfp;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    double bfp;

}
