package aradevs.com.gradecheck.models;

import java.util.ArrayList;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class Courses {
    private String id;
    private String name;
    private ArrayList<Evaluations> eva;

    public Courses() {}

    public Courses(String id, String name, ArrayList<Evaluations> eva) {
        this.id = id;
        this.name = name;
        this.eva = eva;
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


    @Override
    public String toString() {
        return name;
    }
}
