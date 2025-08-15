package mx.nach.pruebaTecnica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.nach.pruebaTecnica.dto.ClienteRegularDto;
import mx.nach.pruebaTecnica.dto.ClienteVipDto;
import mx.nach.pruebaTecnica.dto.Persona;
import mx.nach.pruebaTecnica.dto.Response;
import mx.nach.pruebaTecnica.dto.TipoClienteDto;
import mx.nach.pruebaTecnica.entities.Clientes;
import mx.nach.pruebaTecnica.entities.TipoCliente;
import mx.nach.pruebaTecnica.repositories.CrudRepository;
import mx.nach.pruebaTecnica.utils.Constants;

/*
 * {@InheritDoc}
 */
@Service
public class CrudService implements CrudServiceInterface {

    /*
     * Repositorio CRUD para manejar las operaciones de base de datos.
     */
    @Autowired
    CrudRepository crudRepository;

    /*
     * MessageSource para manejar los mensajes de respuesta.
     */
    @Autowired
    private MessageSource messageSource;

    /*
    * {@InheritDoc}
    */
    @Override
    public void insertClient(Persona clienteDto) {
        Clientes cliente = clienteDto.toEntity();
        crudRepository.save(cliente);
    }

    /*
    * {@InheritDoc}
    */
    @Override
    public Response getClients(Pageable pageable) {
        Page<Clientes> clientes = crudRepository.findAll(pageable);

        Page<Persona> clientesDto = clientes.map(it -> {
            if(it.getTipoCliente().getId().equals(TipoClienteDto.VIP.getId() ) ) {
                Persona persona = new ClienteVipDto(null, null, null, TipoClienteDto.VIP);
                persona = persona.toDto(it, messageSource);
                return persona;
            }

            if(it.getTipoCliente().getId().equals(TipoClienteDto.REGULAR.getId() ) ) {
                Persona persona = new ClienteRegularDto(null, null, null, TipoClienteDto.REGULAR);
                persona = persona.toDto(it, messageSource);
                return persona;
            } else {
                String errorMessage = messageSource.getMessage(
                    Constants.ERROR_CAST_ENTITY_TO_DTO,
                    null,
                    null
                );
                throw new IllegalArgumentException(errorMessage);
            }
        });

        String message = messageSource.getMessage(
            Constants.SUCCESS,
            null,
            null
        );

        Response response = new Response(
            clientesDto,
            message,
            Boolean.TRUE
        );

        return response;
    }

    /*
    * {@InheritDoc}
    */
    @Override
    public Response getOneClient(Persona clienteDto) {
        Clientes cliente = new Clientes();
        if (clienteDto instanceof ClienteVipDto clienteVip) {
            cliente = crudRepository.findByEmail(clienteVip.email() );
        } else if(clienteDto instanceof ClienteRegularDto clienteRegular) {
            cliente = crudRepository.findByEmail(clienteRegular.email() );
        }

        String message = messageSource.getMessage(
            Constants.SUCCESS,
            null,
            null
        );

        Response response = new Response(
            cliente,
            message,
            Boolean.TRUE
        );

        return response;
    }

    /*
    * {@InheritDoc}
    */
    @Override
    public void updateClient(Persona clienteDto) {
        Clientes cliente = new Clientes();
        if (clienteDto instanceof ClienteVipDto clienteVip) {
            cliente = crudRepository.findByEmail(clienteVip.email() );
            cliente.setEdad(clienteVip.edad() );
            cliente.setNombre(clienteVip.nombre() );
            TipoCliente tipoCliente = new TipoCliente();
            tipoCliente.setTipoDeCliente(TipoClienteDto.VIP.name() );
            tipoCliente.setId(TipoClienteDto.VIP.getId() );
            cliente.setTipoCliente(
                tipoCliente
            );

        } else if(clienteDto instanceof ClienteRegularDto clienteRegular) {
            cliente = crudRepository.findByEmail(clienteRegular.email() );
            cliente.setEdad(clienteRegular.edad() );
            cliente.setNombre(clienteRegular.nombre() );
            TipoCliente tipoCliente = new TipoCliente();
            tipoCliente.setTipoDeCliente(TipoClienteDto.REGULAR.name() );
            tipoCliente.setId(TipoClienteDto.REGULAR.getId() );
            cliente.setTipoCliente(
                tipoCliente
            );
        }

        crudRepository.save(cliente);
    }

    /*
    * {@InheritDoc}
    */
    @Override
    public void deleteClient(Persona clienteDto) {
        Clientes cliente = new Clientes();
        if (clienteDto instanceof ClienteVipDto clienteVip) {
            cliente = crudRepository.findByEmail(clienteVip.email() );
        } else if(clienteDto instanceof ClienteRegularDto clienteRegular) {
            cliente = crudRepository.findByEmail(clienteRegular.email() );
        }

        crudRepository.delete(cliente);
    }

}
