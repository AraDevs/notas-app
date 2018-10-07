package aradevs.com.gradecheck.models;

import java.util.ArrayList;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class Evaluations {
    private ArrayList<String> descriptions;
    private ArrayList<String> periods;
    private ArrayList<String> evaluations;
    private ArrayList<String> percentage;

    public Evaluations() {}

    public Evaluations(ArrayList<String> descriptions, ArrayList<String> periods, ArrayList<String> evaluations, ArrayList<String> percentage) {
        this.descriptions = descriptions;
        this.periods = periods;
        this.evaluations = evaluations;
        this.percentage = percentage;
    }

    public ArrayList<String> getPeriods() {
        return periods;
    }

    public void setPeriods(ArrayList<String> periods) {
        this.periods = periods;
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

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }

    // GET PROM BASED ON THE SELECTED PERIOD
    public Double getProm(int periods) {
        Double total = 0.0;
        Double sum;
        ArrayList<Double> grades = new ArrayList<>();

        if (getEvaluations().size() >= 3) {
            for (int i = (3 * periods) - 3; i < (3 * periods); i++) {
                Double eva1 = 0.0;
                if (Integer.parseInt(getPeriods().get(i)) == periods) {
                    eva1 = Double.parseDouble(getEvaluations().get(i)) * Double.parseDouble(getPercentage().get(i)) / 100;
                }
                grades.add(eva1);
            }
            sum = grades.get(0) + grades.get(1) + grades.get(2);
            switch (periods) {
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
    /*
    /*GET PROM OF ALL PERIODS AUTOMATICALLY*//*
    public Double getProm() {
        Double total = 0.0;
        Double sum;
        ArrayList<Double> grades = new ArrayList<>();
        //If available evaluations are more than 3
        if (getEvaluations().size() >= 3) {
            //saving the last periods evaluated on the following loop
            String lastPeriod = "";
            //getting grades per periods
            for (String period : getPeriods()) {

                //if the periods hasn't been evaluated yet
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
    }*/
}
