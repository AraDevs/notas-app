package aradevs.com.gradecheck.helpers;

/**
 * Created by Ar4 on 26/08/2018.
 */
public class ServerHelper {
    //Declaring server endpoints
    public static final String URL = "http://192.168.1.3:8084/POOSistema_NotasAPI/";
    //public static final String URL = "http://104.248.67.79:8080/POOSistema_NotasAPI/";
    public static final String COURSES = "registeredCourses/byStudent/";
    public static final String USER = "students/login";
    public static final String TEACHERS = "employees/byStudent/";
    public static final String TEACHERS_ROOT = "employees/";
    public static final String COURSE_EVALUATIONS = "evaluations/byRegisteredCourse/";
    public static final String COURSE_TEACHER = "employees/byRegisteredCourse/";
    public static final String SUCCESS_RATIO = "courseTeachers/passCount/byRegisteredCourse/";
    public static final String PROFILE_IMAGE = "users/image/";

    public static final String CURRENT_GRADES = "/full/active";
    public static final String CURRENT_COURSES = "/courses/active";
    public static final String CURRENT_TEACHERS = "/users/people";
    public static final String ALL = "/full";
    public static final String GRADES = "/grades";
}
