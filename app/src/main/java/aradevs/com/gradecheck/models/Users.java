package aradevs.com.gradecheck.models;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class Users {
    private String id;
    private String personId;
    private String name;
    private String surname;
    private String username;
    private String phone;
    private String email;

    public Users() {}

    public Users(String id, String personId, String name, String surname, String username, String phone, String email) {
        this.id = id;
        this.personId = personId;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name+' '+surname;
    }
}
