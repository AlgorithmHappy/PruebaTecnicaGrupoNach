package mx.nach.pruebaTecnica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<Response> findAllClients(Pageable pageable) {
        Response response = crudService.getClients(pageable);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("")
    public ResponseEntity<Void> updateOneClient(@RequestBody Request request) {
        
        
        return ResponseEntity.ok().build();
    }
}
