package aradevs.com.gradecheck.models;

import android.support.annotation.NonNull;

/**
 * Created by Ar4 on 9/11/2018.
 */
public class Corrections {
    private String id;
    private String description;
    private String owner;
    private String state;

    public Corrections() {
    }

    public Corrections(String id, String description, String owner, String state) {
        this.id = id;
        this.description = description;
        this.owner = owner;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @NonNull
    @Override
    public String toString() {
        return description;
    }
}
