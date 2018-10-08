package aradevs.com.gradecheck.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ar4 on 7/10/2018.
 */
public class Teachers implements Parcelable {
    private String id;
    private Users users;

    public Teachers() {
    }

    public Teachers(String id, Users users) {
        this.id = id;
        this.users = users;
    }

    protected Teachers(Parcel in) {
        id = in.readString();
    }

    public static final Creator<Teachers> CREATOR = new Creator<Teachers>() {
        @Override
        public Teachers createFromParcel(Parcel in) {
            return new Teachers(in);
        }

        @Override
        public Teachers[] newArray(int size) {
            return new Teachers[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }
}
