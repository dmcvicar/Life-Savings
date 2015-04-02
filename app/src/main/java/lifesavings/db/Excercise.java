package lifesavings.db;

/**
 * Created by dave on 4/2/15.
 */
public class Excercise {

    private int id;
    private int userid;
    private int time;
    private double duration;

    public Excercise() {

    }

    public Excercise(int rowid, int userid, int time, double duration, String excercise) {
        this.id = rowid;
        this.userid = userid;
        this.time = time;
        this.duration = duration;
        this.excercise = excercise;
    }

    String excercise;

    public String getExcercise() {
        return excercise;
    }

    public void setExcercise(String excercise) {
        this.excercise = excercise;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int rowid) {
        this.id = rowid;
    }
}