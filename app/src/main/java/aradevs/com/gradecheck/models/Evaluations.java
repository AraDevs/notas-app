package aradevs.com.gradecheck.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ar4 on 25/08/2018.
 */
public class Evaluations {
    private String gradeId;
    private String descriptions;
    private String periods;
    private String evaluations;
    private String percentage;
    private String laboratory;

    public Evaluations() {}

    public Evaluations(String descriptions, String periods, String evaluations, String percentage, String gradeId, String laboratory) {
        this.descriptions = descriptions;
        this.periods = periods;
        this.evaluations = evaluations;
        this.percentage = percentage;
        this.gradeId = gradeId;
        this.laboratory = laboratory;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
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

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    // GET PROM BASED ON THE SELECTED PERIOD

    public Double getProm(ArrayList<Evaluations> evaluations, int periods) {
        Double total = 0.0;
        Double sum = 0.0;
        ArrayList<Double> grades = new ArrayList<>();
        Double percentage = 0.0;
        for (int e = 0; e < evaluations.size(); e++) {
            if (Integer.parseInt(evaluations.get(e).getPeriods()) == periods) {
                percentage += Double.parseDouble(evaluations.get(e).getPercentage());
            }
        }
        Log.e("Porcentage", String.valueOf(percentage));
        if (percentage == 100) {
            for (int i = 0; i < evaluations.size(); i++) {
                Double eva1 = 0.0;
                if (Integer.parseInt(evaluations.get(i).getPeriods()) == periods) {
                    eva1 = Double.parseDouble(evaluations.get(i).getEvaluations()) * Double.parseDouble(evaluations.get(i).getPercentage()) / 100;
                    Log.e("Nota", String.valueOf(eva1));
                    grades.add(eva1);
                }

            }
            for (Double grade : grades) {
                sum += grade;
            }

            total = sum;
        }

        return total;
    }

    public Double getPromLab(ArrayList<Evaluations> evaluations, int periods) {
        Double total = 0.0;
        Double sum = 0.0;
        ArrayList<Double> grades = new ArrayList<>();
        Double percentage = 0.0;
        for (int e = 0; e < evaluations.size(); e++) {
            if (Integer.parseInt(evaluations.get(e).getPeriods()) == periods) {
                percentage += Double.parseDouble(evaluations.get(e).getPercentage());
            }
        }
        Log.e("Porcentage", String.valueOf(percentage));
        if (percentage == 100) {
            for (int i = 0; i < evaluations.size(); i++) {
                Double eva1 = 0.0;
                if (Integer.parseInt(evaluations.get(i).getPeriods()) == periods) {
                    eva1 = Double.parseDouble(evaluations.get(i).getEvaluations()) * Double.parseDouble(evaluations.get(i).getPercentage()) / 100;
                    Log.e("Nota", String.valueOf(eva1));
                    grades.add(eva1);
                }

            }
            for (Double grade : grades) {
                sum += grade;
            }
            Log.e("Suma total", String.valueOf(sum));
            total = sum;
        }

        return total;
    }

    public ArrayList<Double> calculateRequired(Double tot, int current) {
        ArrayList<Double> required = new ArrayList<>();
        if (tot == 0) {
            required.add(6.7);
            required.add(5.7);
            required.add(5.7);
        } else {
            if (current == 2) {
                Double tempTotal;
                required.add(0.0);
                tempTotal = ((6 - tot) / 2) / 0.35;
                required.add(tempTotal);
                required.add(tempTotal);
            } else if (current == 3) {
                Double tempTotal;
                required.add(0.0);
                required.add(0.0);
                tempTotal = (6 - tot) / 0.35;
                required.add(tempTotal);
            }
        }
        return required;
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
