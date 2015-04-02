package lifesavings.db;

/**
 * Created by dave on 4/2/15.
 */
public class Money {


    private int id;
    private String excercise;
    private double money;

    public String getExcercise() {
        return excercise;
    }

    public void setExcercise(String excercise) {
        this.excercise = excercise;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double moneySaved) {
        this.money = moneySaved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
