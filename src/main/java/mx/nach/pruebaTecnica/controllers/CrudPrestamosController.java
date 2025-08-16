package mx.nach.pruebaTecnica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import mx.nach.pruebaTecnica.dto.EstadoPrestamoDto;
import mx.nach.pruebaTecnica.dto.PrestamoDto;
import mx.nach.pruebaTecnica.dto.RequestPrestamo;
import mx.nach.pruebaTecnica.dto.Response;
import mx.nach.pruebaTecnica.services.CrudServiceInterface;
import mx.nach.pruebaTecnica.utils.Constants;

/*
 * Controlador CRUD para manejar las peticiones POST, PATCH y DELETE de prestamos.
 */
@Controller
@RequestMapping("/api/v1/prestamos")
public class CrudPrestamosController {

    /*
     * Servicio CRUD para los post, get, patch y delete de clientes.
     */
    @Autowired
    private CrudServiceInterface crudService;

    /*
     * MessageSource para manejar los mensajes de error.
     */
    @Autowired
    private MessageSource messageSource;

    /*
     * Post para crear un nuevo prestamo.
     * @param request Request con la información del prestamo a crear.
     * @return ResponseEntity que devuelve un 201 de creado.
     */
    @PostMapping("")   
    public ResponseEntity<Void> createPrestamo(@Valid @RequestBody RequestPrestamo request){
        PrestamoDto prestamo = new PrestamoDto(request);

        crudService.insertBorrowing(prestamo);
        return ResponseEntity.created(null).build();
    }

    /*
     * Get para devolver todos los prestamos.
     * @param request Pageable para indicar cuantos elementos se quieren devolver.
     * @param email Email del cliente para filtrar los prestamos.
     * @return ResponseEntity que devuelve un 200 de OK.
     */
    @GetMapping("")
    public ResponseEntity<Response> findAllBorrowings(Pageable pageable, @RequestParam(name = "email", required = true) String email) {
        Response response = crudService.getBorrowings(pageable, email);
        return ResponseEntity.ok().body(response);
    }

    /*
     * Put para actualizar el estado de un prestamo.
     * @param request Request con el correo unico para actualizar al cliente.
     * @return ResponseEntity que devuelve un 200 de OK.
     */
    @PutMapping("")
    public ResponseEntity<Void> updateOneClient(
        @RequestParam(name = "id", required = true) String id,
        @RequestParam(name = "estado", required = true) String estado
    ) {
        PrestamoDto prestamo = new PrestamoDto();
        prestamo.setId(id);

        if(estado.equals(EstadoPrestamoDto.PAGADO.name() ) ){
            prestamo.setEstado(EstadoPrestamoDto.PAGADO);
        } else if(estado.equals(EstadoPrestamoDto.PENDIENTE.name() ) ){
            prestamo.setEstado(EstadoPrestamoDto.PENDIENTE);
        } else {
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_PRESTAMO_ESTADO_NO_SOPORTADO,
                new Object[]{estado}, 
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }       

        crudService.updateStatusBorrowing(prestamo);

        return ResponseEntity.ok().build();
    }

    /*
     * Delete para eliminar un prestamo.
     * @param id ID del prestamo a eliminar.
     * @return ResponseEntity que devuelve un 204 de no contenido.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowing(@PathVariable("id") String id) {
        crudService.deleteBorrowing(id);
        return ResponseEntity.noContent().build();
    }

    /*
     * Get para devolver el monto total de los prestamos.
     * @param request Pageable para indicar cuantos elementos se quieren devolver.
     * @param email Email del cliente para filtrar los prestamos.
     * @return ResponseEntity que devuelve un 200 de OK.
     */
    @GetMapping("/total")
    public ResponseEntity<Response> montoTotal(@RequestParam(name = "email", required = true) String email) {
        Double montoTotal = crudService.montoTotal(email);
        Response response = new Response(
            montoTotal,
            messageSource.getMessage(Constants.SUCCESS, null, null),
            Boolean.TRUE
        );        
        return ResponseEntity.ok().body(response);
    }
}
