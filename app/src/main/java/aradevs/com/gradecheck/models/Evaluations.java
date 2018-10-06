package aradevs.com.gradecheck.models;

import java.util.ArrayList;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class Evaluations {
    private ArrayList<String> period;
    private ArrayList<String> evaluations;
    private ArrayList<String> percentage;

    public Evaluations() {}

    public Evaluations(ArrayList<String> period, ArrayList<String> evaluations, ArrayList<String> percentage) {
        this.period = period;
        this.evaluations = evaluations;
        this.percentage = percentage;
    }

    public ArrayList<String> getPeriod() {
        return period;
    }

    public void setPeriod(ArrayList<String> period) {
        this.period = period;
    }

    public ArrayList<String> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(ArrayList<String> evaluations) {
        this.evaluations = evaluations;
    }

    public ArrayList<String> getPercentage() {
        return percentage;
    }

    public void setPercentage(ArrayList<String> percentage) {
        this.percentage = percentage;
    }

    /* GET PROM BASED ON THE SELECTED PERIOD
    public Double getProm(int period) {
        Double total = 0.0;
        Double sum;
        ArrayList<Double> grades = new ArrayList<>();

        if (getEvaluations().size() >= 3) {
            for (int i = (3 * period) - 3; i < (3 * period); i++) {
                Double eva1;
                eva1 = Double.parseDouble(getEvaluations().get(i)) * Double.parseDouble(getPercentage().get(i)) / 100;
                grades.add(eva1);
            }
            sum = grades.get(0) + grades.get(1) + grades.get(2);
            switch (period) {
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
    */

    /*GET PROM OF ALL PERIODS AUTOMATICALLY*/
    public Double getProm() {
        Double total = 0.0;
        Double sum;
        ArrayList<Double> grades = new ArrayList<>();

        //If available evaluations are more than 3
        if (getEvaluations().size() >= 3) {
            //saving the last period evaluated on the following loop
            String lastPeriod = "";
            //getting grades per period
            for (String period : getPeriod()) {

                //if the period hasn't been evaluated yet
                if (!period.equals(lastPeriod)) {

                    lastPeriod = period;

                    //get the evaluations
                    for (int i = (3 * Integer.parseInt(period)) - 3; i < (3 * Integer.parseInt(period)); i++) {
                        Double eva1;
                        eva1 = Double.parseDouble(getEvaluations().get(i)) * Double.parseDouble(getPercentage().get(i)) / 100;
                        grades.add(eva1);
                    }
                    //sum of all the evaluations
                    sum = grades.get(0) + grades.get(1) + grades.get(2);
                    //multiplying by the corresponding percentage
                    switch (Integer.parseInt(period)) {
                        case 1:
                            total += sum * 0.30;
                            break;
                        case 2:
                            total += sum * 0.35;
                            break;
                        case 3:
                            total += sum * 0.35;
                    }

                }
            }

        }

        //returning data
        return total;
    }
}
