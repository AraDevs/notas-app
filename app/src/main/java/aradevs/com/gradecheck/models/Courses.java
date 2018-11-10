package aradevs.com.gradecheck.models;

import java.util.ArrayList;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class Courses {
    private String id;
    private String registered_Course;
    private String name;
    private String uv;
    private ArrayList<Evaluations> eva;

    public Courses() {}

    public Courses(String id, String name, ArrayList<Evaluations> eva, String registered_Course, String uv) {
        this.id = id;
        this.name = name;
        this.eva = eva;
        this.registered_Course = registered_Course;
        this.uv = uv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Evaluations> getEva() {
        return eva;
    }

    public void setEva(ArrayList<Evaluations> eva) {
        this.eva = eva;
    }

    public String getRegistered_Course() {
        return registered_Course;
    }

    public void setRegistered_Course(String registered_Course) {
        this.registered_Course = registered_Course;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    @Override
    public String toString() {
        return name;
    }
}
