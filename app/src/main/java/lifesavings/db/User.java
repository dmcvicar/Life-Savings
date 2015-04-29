package lifesavings.db;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dave on 4/2/15.
 */
public class User implements Serializable{

    private int userid;
    private String name;
    private String gender;
    private int weight;
    private int height;
    private double bmi;
    private int category;
    private int age;
    double bfp;
    String iconPath;

    public User() {

    }

    public User(int userid, String name, String gender, int weight, int height, double bmi, int category, double bfp, int age, String iconPath) {
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.category = category;
        this.bfp = bfp;
        this.age = age;
        this.iconPath = iconPath;
    }
    public User(int userid, String name, String gender, int weight, int height, double bmi, int category,int age, String iconPath) {
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.category = category;
        this.bfp = -1;
        this.age = age;
        this.iconPath = iconPath;
    }
    public User(int userid, String name, String gender, int weight, int height, double bmi, int age) {
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.category = calcCategory(this.bmi);
        this.bfp = -1;
        this.age = age;
        this.iconPath = iconPath;
    }

    public User (int userid, String name, String gender, int weight, int height, int age, String iconPath){
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.bmi = calcBMI(weight,height);
        this.category = calcCategory(this.bmi);
        this.bfp = -1;
        this.age = age;
        this.iconPath = iconPath;
    }
    public static double calcBMI(int weight, int height){
        // dw = double weight, dh = double height for accuracy reasons.
        // bmi = (mass(lbs)/height(inches)) * 703
        double dw = (double)weight;
        double dh = (double)height;
        System.out.println(((dw/(dh*dh))*703.0));
        return (dw/(dh*dh))*703.0;
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

    public void setBmi(double bmi) {
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

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public String toString() {
        return name;
    }


    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public ArrayList<String> toArrayList() {
        ArrayList<String> data = new ArrayList<String>();
        data.add(Integer.toString(userid));
        data.add(name);
        data.add(gender);
        data.add(Integer.toString(weight));
        data.add(Integer.toString(height));
        data.add(Double.toString(bmi));
        data.add(Integer.toString(category));
        data.add(Double.toString(bfp));
        data.add(Integer.toString(age));
        data.add(iconPath);
        return data;
    }

    public static User fromArrayList( ArrayList<String> data) {
        User out = new User(
                Integer.parseInt(data.get(0)),
                data.get(1),
                data.get(2),
                Integer.parseInt(data.get(3)),
                Integer.parseInt(data.get(4)),
                Double.parseDouble(data.get(5)),
                Integer.parseInt(data.get(6)),
                Double.parseDouble(data.get(7)),
                Integer.parseInt(data.get(8)),
                data.get(9));
        return out;
    }

}
