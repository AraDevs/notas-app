package aradevs.com.gradecheck.helpers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import aradevs.com.gradecheck.models.Courses;
import aradevs.com.gradecheck.models.Evaluations;
import aradevs.com.gradecheck.models.Success;
import aradevs.com.gradecheck.models.Teachers;
import aradevs.com.gradecheck.models.Users;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Ar4 on 26/08/2018.
 */
public class ParseJsonHelper {

    public ArrayList<Courses> parseJsonRegisteredCourses(JSONArray jsonObject) {
        //declaring useful variables
        ArrayList<Courses> courses = new ArrayList<>();
        //JSONArray registeredArray;

        // retrieving registered courses
        //registeredArray = jsonObject.getJSONArray("registeredCourse");

        //navigating through courses
        for (int i = 0; i < jsonObject.length(); i++) {
            try {
                //useful local variables
                ArrayList<Evaluations> tempEvaluation = new ArrayList<>();

                //fragmenting JSON in sections
                JSONObject registeredCourse = jsonObject.getJSONObject(i);
                JSONObject teacherObj = registeredCourse.getJSONObject("courseTeacher");
                JSONObject courseObj = teacherObj.getJSONObject("course");
                JSONArray evaArray = registeredCourse.getJSONArray("grades");


                for (int j = 0; j < evaArray.length(); j++) {
                    //obtaining current evaluations
                    JSONObject evaObject = evaArray.getJSONObject(j);
                    JSONObject currentEva = evaObject.getJSONObject("evaluation");
                    if (!currentEva.getBoolean("laboratory")) {
                        //filling evaluations model
                        Evaluations e = new Evaluations(
                                currentEva.getString("description"),
                                currentEva.getString("period"),
                                evaObject.getString("grade"),
                                currentEva.getString("percentage")
                        );
                        tempEvaluation.add(e);
                    }
                }


                //filling courses model
                Courses c = new Courses(
                        courseObj.getString("id"),
                        courseObj.getString("name"),
                        tempEvaluation,
                        registeredCourse.getString("id"),
                        courseObj.getString("uv"));

                //filling courses array list
                courses.add(c);

            } catch (JSONException e) {
                Log.e(TAG, "Json parsing Error: " + e.getMessage());
            }
        }

        return courses;
    }

    public ArrayList<Courses> parseJsonCourses(JSONArray jsonObject) {
        //declaring useful variables
        ArrayList<Courses> courses = new ArrayList<>();
        //JSONArray registeredArray;
        // retrieving registered courses
        //registeredArray = jsonObject.getJSONArray("registeredCourse");

        //navigating through courses
        for (int i = 0; i < jsonObject.length(); i++) {
            try {
                //fragmenting JSON in sections
                JSONObject registeredCourse = jsonObject.getJSONObject(i);
                JSONObject teacherObj = registeredCourse.getJSONObject("courseTeacher");
                JSONObject courseObj = teacherObj.getJSONObject("course");

                //filling evaluations model
                ArrayList<Evaluations> e = new ArrayList<>();

                //filling courses model
                Courses c = new Courses(
                        courseObj.getString("id"),
                        courseObj.getString("name"),
                        e,
                        registeredCourse.getString("id"),
                        courseObj.getString("uv"));

                //filling courses array list
                courses.add(c);

            } catch (JSONException e) {
                Log.e(TAG, "Json parsing Error: " + e.getMessage());
            }
        }

        return courses;

    }

    public Users parseJsonUsers(JSONObject jsonObject) {
        //declaring useful variables
        Users users = new Users();

        try {
            JSONObject userJson = jsonObject.getJSONObject("user");
            users.setId(jsonObject.getString("id"));
            users.setEmail(userJson.getJSONObject("person").getString("email"));
            users.setName(userJson.getJSONObject("person").getString("name"));
            users.setPhone(userJson.getJSONObject("person").getString("phone"));
            users.setSurname(userJson.getJSONObject("person").getString("surname"));
            users.setUsername(userJson.getString("username"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<Teachers> parseJsonTeachers(JSONArray jsonObject) {

        ArrayList<Teachers> teachers = new ArrayList<>();

        //JSONArray registeredArray;
        // retrieving registered courses
        //registeredArray = jsonObject.getJSONArray("employee");

        //navigating through courses
        for (int i = 0; i < jsonObject.length(); i++) {
            try {
                //fragmenting JSON in sections
                JSONObject teacherObj = jsonObject.getJSONObject(i);
                JSONObject userObj = teacherObj.getJSONObject("user");
                JSONObject personObj = userObj.getJSONObject("person");

                Users u = new Users(userObj.getString("id"),
                        personObj.getString("name"),
                        personObj.getString("surname"),
                        userObj.getString("username"),
                        personObj.getString("phone"),
                        personObj.getString("email"));

                Teachers t = new Teachers(teacherObj.getString("id"), u);
                //filling teachers array list
                teachers.add(t);

            } catch (JSONException e) {
                Log.e(TAG, "Json parsing Error: " + e.getMessage());
            }
        }

        return teachers;
    }

    public Teachers parseJsonSingleTeacher(JSONObject jsonObject) {

        Teachers teachers = new Teachers();

        //JSONArray registeredArray;
        // retrieving registered courses
        //registeredArray = jsonObject.getJSONArray("employee");
        try {
            //fragmenting JSON in sections
            JSONObject userObj = jsonObject.getJSONObject("user");
            JSONObject personObj = userObj.getJSONObject("person");

            Users u = new Users(userObj.getString("id"),
                    personObj.getString("name"),
                    personObj.getString("surname"),
                    userObj.getString("username"),
                    personObj.getString("phone"),
                    personObj.getString("email"));

            teachers = new Teachers(jsonObject.getString("id"), u);

        } catch (JSONException e) {
            Log.e(TAG, "Json parsing Error: " + e.getMessage());
        }

        return teachers;
    }

    public ArrayList<Courses> parseJsonTeacherCourses(JSONObject jsonObject) {
        //declaring useful variables
        ArrayList<Courses> courses = new ArrayList<>();
        JSONArray registeredArray;

        //try {
        // retrieving registered courses
        Log.e("Tamano: ", String.valueOf(jsonObject.length()));
        try {
            registeredArray = jsonObject.getJSONArray("courseTeachers");
            //navigating through courses
            for (int i = 0; i < registeredArray.length(); i++) {
                try {
                    //fragmenting JSON in sections
                    JSONObject registeredCourse = registeredArray.getJSONObject(i);
                    JSONObject courseObj = registeredCourse.getJSONObject("course");

                    //filling evaluations model
                    //filling evaluations model
                    ArrayList<Evaluations> e = new ArrayList<>();

                    //filling courses model
                    Courses c = new Courses(
                            courseObj.getString("id"),
                            courseObj.getString("name"),
                            e,
                            registeredCourse.getString("id"),
                            courseObj.getString("uv"));

                    //filling courses array list
                    courses.add(c);

                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing Error: " + e.getMessage());
                }
            }/*
            }catch (Exception e){
                JSONObject courseObj = jsonObject.getJSONObject("courseTeachers").getJSONObject("course");

                //filling evaluations model
                Evaluations evaluations = new Evaluations(
                        null,
                        null,
                        null
                );

                //filling courses model
                Courses c = new Courses(
                        courseObj.getString("id"),
                        courseObj.getString("name"),
                        evaluations);

                //filling courses array list
                courses.add(c);
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public ArrayList<Evaluations> parseJsonCourseEvaluations(JSONArray jsonObject) {
        //filling evaluations model
        ArrayList<Evaluations> evaluations = new ArrayList<>();
        //JSONArray registeredArray;
        // retrieving registered courses
        //registeredArray = jsonObject.getJSONArray("evaluation");

        //navigating through courses
        for (int i = 0; i < jsonObject.length(); i++) {
            try {
                //obtaining current evaluations
                JSONObject evaObject = jsonObject.getJSONObject(i);
                JSONArray grade = evaObject.getJSONArray("grades");
                if (!evaObject.getBoolean("laboratory")) {
                    Evaluations tempEva = new Evaluations();
                    tempEva.setDescriptions(evaObject.getString("description"));
                    tempEva.setEvaluations(grade.getJSONObject(0).getString("grade"));
                    tempEva.setPercentage(evaObject.getString("percentage"));
                    tempEva.setPeriods(evaObject.getString("period"));
                    evaluations.add(tempEva);
                }

            } catch (JSONException e) {
                Log.e(TAG, "Json parsing Error: " + e.getMessage());
            }
        }

        return evaluations;
    }

    public Success parseJsonSuccess(JSONObject jsonObject) {
        Success s = new Success();
        try {
            s = new Success(jsonObject.getString("passed"), jsonObject.getString("failed"), jsonObject.getString("retired"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }
}

