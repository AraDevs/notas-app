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
        // Variables locales
        ArrayList<Courses> courses = new ArrayList<>();
        JSONArray jsonArray;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("registeredCourse");

            for (int i = 0; i < jsonArray.length(); i++) {
                try {

                    ArrayList<String> tempEvals = new ArrayList<>();
                    ArrayList<String> tempPercentajes = new ArrayList<>();

                    JSONObject registeredCourse = jsonArray.getJSONObject(i);
                    JSONObject teacher = registeredCourse.getJSONObject("courseTeacher");
                    JSONObject course = teacher.getJSONObject("course");
                    JSONArray evaArray = registeredCourse.getJSONArray("gradeList");

                    for (int j = 0; j < evaArray.length(); j++) {

                        JSONObject evaObject = evaArray.getJSONObject(j);
                        JSONObject currentEva = evaObject.getJSONObject("evaluation");

                        tempEvals.add(evaObject.getString("grade"));
                        tempPercentajes.add(currentEva.getString("percentage"));

                    }

                    Evaluations e = new Evaluations(
                            "1",
                            tempEvals,
                            tempPercentajes
                    );

                    Courses c = new Courses(
                            course.getString("id"),
                            course.getString("name"),
                            e);

                    courses.add(c);

                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing json: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courses;
    }
}

