package mx.nach.pruebaTecnica.dto;

/*
 * Response que recivirán los clientes de la API.
 */
public record Response(
    Object data,
    String message,
    boolean success
) {

}
