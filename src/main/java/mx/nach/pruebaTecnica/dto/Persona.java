package mx.nach.pruebaTecnica.dto;

import org.springframework.context.MessageSource;

import mx.nach.pruebaTecnica.entities.Clientes;

/*
 * Interfaz de la que extenderán los DTOs de cliente VIP y Regular
 */
public interface Persona {

    /*
     * Método para convertir el DTO a una entidad Clientes
     * @return Entidad Clientes
     */
    public Clientes toEntity();

    /*
     * Método para convertir la entidad a un DTO de tipo REGULAR o VIP Clientes.
     * @param Clientes Entidad de cliente.
     * @param messageSource Mensaje de error en caso de error.
     * @return  Clientes.
     */
    public Persona toDto(Clientes cliente, MessageSource messageSource);

    /*
     * Método para convertir un Request a un DTO de tipo REGULAR o VIP.
     * @param request Request con los datos del cliente.
     * @param messageSource Mensaje de error en caso de error.
     */
    public Persona requestToDto(Request request, MessageSource messageSource);
}
