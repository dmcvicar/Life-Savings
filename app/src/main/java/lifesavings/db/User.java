package lifesavings.db;

import java.io.Serializable;

/**
 * Created by dave on 4/2/15.
 */
public class User implements Serializable {

    private int userid;
    private String name;
    private String gender;
    private int weight;
    private int height;
    private double bmi;
    private int category;
    double bfp;

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
    public User(int userid, String name, String gender, int weight, int height, int bmi, int category) {
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.category = category;
        this.bfp = -1;
    }
    public User(int userid, String name, String gender, int weight, int height, int bmi) {
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.category = calcCategory(this.bmi);
        this.bfp = -1;
    }

    public User (int userid, String name, String gender, int weight, int height){
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bmi = calcBMI(weight,height);
        this.category = calcCategory(this.bmi);
        this.bfp = -1;
    }
    public static double calcBMI(int weight, int height){
        // dw = double weight, dh = double height for accuracy reasons.
        // bmi = (mass(lbs)/height(inches)) * 703
        double dw = (double)weight;
        double dh = (double)height;
        return (dw/dh)*703.0;
    }
    public static int calcCategory(double bmi){
        int rank;
        rank  = -1;
        if(bmi < 16.0)
        {
            //severe thinness
            rank = 1;
        }
        else if (bmi >= 16.0 && bmi < 17.0)
        {
            //moderate thinness
            rank = 2;
        }
        else if (bmi >= 17.0 && bmi < 18.5)
        {
            //mild thinness
            rank = 3;
        }
        else if (bmi >= 18.5 && bmi < 25)
        {
            //normal
            rank = 4;
        }
        else if (bmi >= 25 && bmi < 30)
        {
            //overweight
            rank = 5;
        }
        else if (bmi >= 30 && bmi < 35)
        {
            //obese class I
            rank = 6;
        }
        else if (bmi >= 35 && bmi < 40)
        {
            // obese class II
            rank = 7;
        }
        else
        {
            // obese class II
            rank = 8;
        }
        return rank;
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

    public double getBmi() {
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

}
