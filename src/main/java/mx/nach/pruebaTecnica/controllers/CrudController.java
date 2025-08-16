package mx.nach.pruebaTecnica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import mx.nach.pruebaTecnica.dto.ClienteRegularDto;
import mx.nach.pruebaTecnica.dto.ClienteVipDto;
import mx.nach.pruebaTecnica.dto.Persona;
import mx.nach.pruebaTecnica.dto.Request;
import mx.nach.pruebaTecnica.dto.Response;
import mx.nach.pruebaTecnica.dto.TipoClienteDto;
import mx.nach.pruebaTecnica.services.CrudServiceInterface;
import mx.nach.pruebaTecnica.utils.Constants;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




/*
 * Controlador CRUD para manejar las peticiones POST, GET, PUT y DELETE.
 */
@Controller
@RequestMapping("/api/v1/clientes")
public class CrudController {

    /*
     * MessageSource para manejar los mensajes de error.
     */
    @Autowired
    private MessageSource messageSource;

    /*
     * Servicio CRUD para los post, get, put y delete de clientes.
     */
    @Autowired
    private CrudServiceInterface crudService;

    /*
     * Post para crear un nuevo cliente.
     * @param request Request con la información del cliente a crear.
     * @return ResponseEntity que devuelve un 201 de creado.
     */
    @PostMapping("")   
    public ResponseEntity<Void> createClient(@Valid @RequestBody Request request){
        Persona persona = null;

        if(request.getTipoCliente().equals(TipoClienteDto.REGULAR.name() ) ){
            persona = new ClienteRegularDto(null, null, null, TipoClienteDto.REGULAR);
            persona = persona.requestToDto(request, messageSource);
        } else if(request.getTipoCliente().equals(TipoClienteDto.VIP.name() ) ){
            persona = new ClienteVipDto(null, null, null, TipoClienteDto.VIP);
            persona = persona.requestToDto(request, messageSource);
        } else {
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_TIPO_DE_CLIENTE_NO_SOPORTADO,
                new Object[]{request.getTipoCliente()}, 
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }

        crudService.insertClient(persona);
        return ResponseEntity.created(null).build();
    }

    /*
     * Get para devolver todos los clientes.
     * @param request Pageable para indicar cuantos elementos se quieren devolver.
     * @return ResponseEntity que devuelve un 200 de OK.
     */
    @GetMapping("")
    public ResponseEntity<Response> findAllClients(Pageable pageable) {
        Response response = crudService.getClients(pageable);
        return ResponseEntity.ok().body(response);
    }

    /*
     * Put para actualizar cliente.
     * @param request Request con el correo unico para actualizar al cliente.
     * @return ResponseEntity que devuelve un 200 de OK.
     */
    @PutMapping("")
    public ResponseEntity<Void> updateOneClient(@RequestBody Request request) {
        Persona persona = null;

        if(request.getTipoCliente().equals(TipoClienteDto.REGULAR.name() ) ){
            persona = new ClienteRegularDto(null, null, null, TipoClienteDto.REGULAR);
            persona = persona.requestToDto(request, messageSource);
        } else if(request.getTipoCliente().equals(TipoClienteDto.VIP.name() ) ){
            persona = new ClienteVipDto(null, null, null, TipoClienteDto.VIP);
            persona = persona.requestToDto(request, messageSource);
        } else {
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_TIPO_DE_CLIENTE_NO_SOPORTADO,
                new Object[]{request.getTipoCliente()}, 
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }

        crudService.updateClient(persona);
        return ResponseEntity.ok().build();
    }

    /*
     * Delete para borrar al cliente.
     * @param String email del cliente a borrar.
     * @return ResponseEntity que devuelve un 200 de OK.
     */
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteOneClient(@PathVariable("email") String email) {
        
        if(!Constants.EMAIL_PATTERN.matcher(email).matches() ){
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_EMAIL_NOT_VALID,
                new Object[]{email},
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }

        if(email.isBlank() ){
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_EMAIL_NOT_BLANK,
                new Object[]{email},
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }        

        crudService.deleteClient(email);

        return ResponseEntity.ok().build();
    }

}
