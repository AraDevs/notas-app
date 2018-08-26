package aradevs.com.gradecheck.models;

import java.util.ArrayList;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class Evaluations {
    private String period;
    private ArrayList<String> evaluations;
    private ArrayList<String> percentaje;

    public Evaluations() {}

    public Evaluations(String period, ArrayList<String> evaluations, ArrayList<String> percentaje) {
        this.period = period;
        this.evaluations = evaluations;
        this.percentaje = percentaje;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public ArrayList<String> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(ArrayList<String> evaluations) {
        this.evaluations = evaluations;
    }

    public ArrayList<String> getPercentaje() {
        return percentaje;
    }

    public void setPercentaje(ArrayList<String> percentaje) {
        this.percentaje = percentaje;
    }

    public Double getProm(int position) {
        Double total = 0.0;
        Double sum;
        ArrayList<Double> grades = new ArrayList<>();

        if (getEvaluations().size() >= 3) {
            for (int i = (3 * position) - 3; i < (3 * position); i++) {
                Double eva1;
                eva1 = Double.parseDouble(getEvaluations().get(i)) * Double.parseDouble(getPercentaje().get(i)) / 100;
                grades.add(eva1);
            }
            sum = grades.get(0) + grades.get(1) + grades.get(2);
            switch (position) {
                case 1:
                    total = sum * 0.30;
                    break;
                case 2:
                    total = sum * 0.35;
                    break;
                case 3:
                    total = sum * 0.35;
            }
        }

        return total;
    }
}
