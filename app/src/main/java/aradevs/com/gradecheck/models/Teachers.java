package aradevs.com.gradecheck.models;

/**
 * Created by Ar4 on 7/10/2018.
 */
public class Teachers {
    private String id;
    private Users users;

    public Teachers() {
    }

    public Teachers(String id, Users users) {
        this.id = id;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
