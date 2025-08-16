package mx.nach.pruebaTecnica.utils;

import java.util.regex.Pattern;

/*
 * Clase de constantes para el microservicio.
 */
public class Constants {
    /*
     * Claves del message.properties
     */
    public static String SUCCESS = "request.success";
    public static String ERROR_DTO = "error.dto";
    public static String ERROR_CAST_ENTITY_TO_DTO = "error.castEntityToDto";
    public static String ERROR_CAST_REQUEST_TO_DTO = "error.castRequestToDto";
    public static String ERROR_TIPO_DE_CLIENTE_NO_SOPORTADO = "error.TipoDeClienteNoSoportado";
    public static String ERROR_CLIENT_NOT_FOUND = "error.clientNotFound";
    public static String ERROR_CLIENT_NOT_FOUND_BY_ID = "error.clientNotFoundById";
    public static String ERROR_EMAIL_NOT_BLANK = "error.emailBlank";
    public static String ERROR_EMAIL_NOT_VALID = "error.emailInvalid";
    public static String ERROR_ESTADO_PRESTAMO_NOT_FOUND_BY_ID = "error.estadoPrestamoNotFoundById";
    public static String ERROR_PRESTAMO_NOT_FOUND_BY_ID = "error.prestamoNotFoundById";
    public static String ERROR_PRESTAMO_ESTADO_NO_SOPORTADO = "error.prestamoEstadoNoSoportado";

    /*
     * Validacion de correo electronico.
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
}
