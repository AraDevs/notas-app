package aradevs.com.gradecheck.models;

public class Evaluations {
    private String period;
    private String eva1;
    private String eva2;
    private String eva3;

    public Evaluations() {}

    public Evaluations(String period, String eva1, String eva2, String eva3) {
        this.period = period;
        this.eva1 = eva1;
        this.eva2 = eva2;
        this.eva3 = eva3;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getEva1() {
        return eva1;
    }

    public void setEva1(String eva1) {
        this.eva1 = eva1;
    }

    public String getEva2() {
        return eva2;
    }

    public void setEva2(String eva2) {
        this.eva2 = eva2;
    }

    public String getEva3() {
        return eva3;
    }

    public void setEva3(String eva3) {
        this.eva3 = eva3;
    }
}
