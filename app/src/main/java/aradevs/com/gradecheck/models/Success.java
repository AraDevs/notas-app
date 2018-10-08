package aradevs.com.gradecheck.models;

/**
 * Created by Ar4 on 7/10/2018.
 */
public class Success {
    private String passed;
    private String failed;
    private String retired;

    public Success(String passed, String failed, String retired) {
        this.passed = passed;
        this.failed = failed;
        this.retired = retired;
    }

    public Success() {
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public String getFailed() {
        return failed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }

    public String getRetired() {
        return retired;
    }

    public void setRetired(String retired) {
        this.retired = retired;
    }
}
