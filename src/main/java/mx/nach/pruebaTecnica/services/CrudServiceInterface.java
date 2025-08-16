package mx.nach.pruebaTecnica.services;

import mx.nach.pruebaTecnica.dto.Persona;
import mx.nach.pruebaTecnica.dto.PrestamoDto;
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
     * @param Correo electronico que en la base de datos es unico para eliminar al cliente.
     */
    public void deleteClient(String email);

    /*
     * Método para insertar un prestamo.
     * @param Prestamo DTO del cliente VIP o REGULAR a insertar.
     */
    public void insertBorrowing(PrestamoDto prestamoDto);

    /*
     * Método para obtener todos los prestamos de determinado cliente.
     * @param pageable Información de paginación.
     * @param email Email para identificar al cliente.
     * @return Respuesta del servicio.
     */
    public Response getBorrowings(Pageable pageable, String email);

    /*
     * Método para cambiar de estado un prestamo.
     * @param Prestamo DTO del cliente VIP o REGULAR a actualizar.
     */
    public void updateStatusBorrowing(PrestamoDto prestamoDto);

    /*
     * Método para eliminar un prestamo.
     * @param Id del prestamo.
     */
    public void deleteBorrowing(String idPrestamo);

    /*
     * Método para pagar el monto total de un cliente.
     * @param email Correo del cliente.
     * @return Monto total a pagar.
     */
    public Double montoTotal(String email);
}
