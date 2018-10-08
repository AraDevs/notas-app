package aradevs.com.gradecheck.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class Evaluations {
    private String descriptions;
    private String periods;
    private String evaluations;
    private String percentage;

    public Evaluations() {}

    public Evaluations(String descriptions, String periods, String evaluations, String percentage) {
        this.descriptions = descriptions;
        this.periods = periods;
        this.evaluations = evaluations;
        this.percentage = percentage;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(String evaluations) {
        this.evaluations = evaluations;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
    // GET PROM BASED ON THE SELECTED PERIOD

    public Double getProm(ArrayList<Evaluations> evaluations, int periods) {
        Double total = 0.0;
        Double sum = 0.0;
        ArrayList<Double> grades = new ArrayList<>();

        if (evaluations.size() >= 3) {
            for (int i = 0; i < evaluations.size(); i++) {
                Double eva1 = 0.0;
                if (Integer.parseInt(evaluations.get(i).getPeriods()) == periods) {
                    eva1 = Double.parseDouble(evaluations.get(i).getEvaluations()) * Double.parseDouble(evaluations.get(i).getPercentage()) / 100;
                }
                Log.e("Nota", String.valueOf(eva1));
                grades.add(eva1);
            }
            for (Double grade : grades) {
                sum += grade;
            }
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
