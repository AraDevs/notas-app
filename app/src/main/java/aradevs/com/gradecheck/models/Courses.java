package aradevs.com.gradecheck.models;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class Courses {
    private String id;
    private String name;
    private Evaluations eva;

    public Courses() {}

    public Courses(String id, String name, Evaluations eva) {
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

    public Evaluations getEva() {
        return eva;
    }

    public void setEva(Evaluations eva) {
        this.eva = eva;
    }


    @Override
    public String toString() {
        return name;
    }
}
