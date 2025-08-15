package mx.nach.pruebaTecnica.services;

import mx.nach.pruebaTecnica.dto.Persona;
import mx.nach.pruebaTecnica.dto.Response;

import org.springframework.data.domain.Pageable;

/*
 * Interfaz para el servicio CRUD para el POST, GET, PUT y DELETE.
 */
public interface CrudServiceInterface {

    /*
     * Método para insertar un cliente.
     * @param Persona DTO del cliente VIP o REGULAR a insertar.
     */
    public void insertClient(Persona clienteDto);

    /*
     * Método para obtener todos los clientes.
     * @param pageable Información de paginación.
     * @return Respuesta del servicio.
     */
    public Response getClients(Pageable pageable);

    /*
     * Método para obtener todos los clientes.
     * @param Person DTO del cliente VIP o REGULAR a buscar.
     * @return Respuesta del servicio.
     */
    public Response getOneClient(Persona clienteDto);

    /*
     * Método para actualizar un cliente.
     * @param Persona DTO del cliente VIP o REGULAR a actualizar.
     */
    public void updateClient(Persona clienteDto);

    /*
     * Método para eliminar un cliente.
     * @param Persona DTO del cliente VIP o REGULAR a eliminar.
     */
    public void deleteClient(Persona clienteDto);
}
