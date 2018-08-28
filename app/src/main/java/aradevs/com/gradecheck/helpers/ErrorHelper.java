package aradevs.com.gradecheck.helpers;

/**
 * Created by Ar4 on 28/08/2018.
 */
public class ErrorHelper {
    public static String getError(String error) {
        String msg = "";
        switch (error) {
            case "Username or password not found":
                msg = "Clave o usuario incorrecto";
                break;
            case "This service is meant for students only":
                msg = "Este servicio es solo para estudiantes";
                break;
            case "Your user is deactivated":
                msg = "Vaya, parece que tu cuenta ha sido desactivada";
                break;
            default:
                msg = "No se pudo realizar la accion seleccionada";
                break;
        }
        return msg;
    }
}
