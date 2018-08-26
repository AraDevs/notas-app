package aradevs.com.gradecheck.helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import aradevs.com.gradecheck.models.Courses;
import aradevs.com.gradecheck.models.Evaluations;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Ar4 on 26/08/2018.
 */
public class ParseJson {

    public ArrayList<Courses> parseJson(JSONObject jsonObject) {
        //declaring useful variables
        ArrayList<Courses> courses = new ArrayList<>();
        JSONArray registeredArray;

        try {
            // retrieving registered courses
            registeredArray = jsonObject.getJSONArray("registeredCourse");

            //navigating through courses
            for (int i = 0; i < registeredArray.length(); i++) {
                try {
                    //useful local variables
                    ArrayList<String> tempEval = new ArrayList<>();
                    ArrayList<String> tempPercentages = new ArrayList<>();

                    //fragmenting JSON in sections
                    JSONObject registeredCourse = registeredArray.getJSONObject(i);
                    JSONObject teacherObj = registeredCourse.getJSONObject("courseTeacher");
                    JSONObject courseObj = teacherObj.getJSONObject("course");
                    JSONArray evaArray = registeredCourse.getJSONArray("gradeList");

                    for (int j = 0; j < evaArray.length(); j++) {
                        //obtaining current evaluations
                        JSONObject evaObject = evaArray.getJSONObject(j);
                        JSONObject currentEva = evaObject.getJSONObject("evaluation");

                        //adding data to array list
                        tempEval.add(evaObject.getString("grade"));
                        tempPercentages.add(currentEva.getString("percentage"));
                    }

                    //filling evaluations model
                    Evaluations e = new Evaluations(
                            "1",
                            tempEval,
                            tempPercentages
                    );

                    //filling courses model
                    Courses c = new Courses(
                            courseObj.getString("id"),
                            courseObj.getString("name"),
                            e);

                    //filling courses array list
                    courses.add(c);

                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing Error: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courses;
    }
}

