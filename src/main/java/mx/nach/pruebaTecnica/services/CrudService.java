package mx.nach.pruebaTecnica.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import mx.nach.pruebaTecnica.dto.ClienteRegularDto;
import mx.nach.pruebaTecnica.dto.ClienteVipDto;
import mx.nach.pruebaTecnica.dto.EstadoPrestamoDto;
import mx.nach.pruebaTecnica.dto.Persona;
import mx.nach.pruebaTecnica.dto.PrestamoDto;
import mx.nach.pruebaTecnica.dto.Response;
import mx.nach.pruebaTecnica.dto.TipoClienteDto;
import mx.nach.pruebaTecnica.entities.Clientes;
import mx.nach.pruebaTecnica.entities.EstadoPrestamo;
import mx.nach.pruebaTecnica.entities.Prestamos;
import mx.nach.pruebaTecnica.entities.TipoCliente;
import mx.nach.pruebaTecnica.repositories.CrudEstadosPrestamosRepository;
import mx.nach.pruebaTecnica.repositories.CrudPrestamosRepository;
import mx.nach.pruebaTecnica.repositories.CrudRepository;
import mx.nach.pruebaTecnica.utils.Constants;

/*
 * {@InheritDoc}
 */
@Service
public class CrudService implements CrudServiceInterface {

    /*
     * Repositorio CRUD para manejar las operaciones de base de datos de clientes.
     */
    @Autowired
    CrudRepository crudRepository;

    /*
     * Repositorio CRUD para manejar las operaciones de base de datos de prestamos.
     */
    @Autowired
    CrudPrestamosRepository crudRepositoryPrestamos;

    /*
     * Repositorio CRUD para manejar las operaciones del catalogo de los estados de los prestamos.
     */
    @Autowired
    CrudEstadosPrestamosRepository crudRepositoryEstadosPrestamos;

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

            if(cliente == null) {
                String errorMessage = messageSource.getMessage(
                    Constants.ERROR_CLIENT_NOT_FOUND,
                    new Object[]{clienteVip.email()},
                    null
                );
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
            }

            cliente.setEdad(clienteVip.edad() );
            cliente.setNombre(clienteVip.nombre() );

            TipoCliente tipoCliente = new TipoCliente();
            tipoCliente.setTipoDeCliente(clienteVip.tipoCliente().name() );
            tipoCliente.setId(clienteVip.tipoCliente().getId() );
            cliente.setTipoCliente(
                tipoCliente
            );

        } else if(clienteDto instanceof ClienteRegularDto clienteRegular) {
            cliente = crudRepository.findByEmail(clienteRegular.email() );

            if(cliente == null) {
                String errorMessage = messageSource.getMessage(
                    Constants.ERROR_CLIENT_NOT_FOUND,
                    new Object[]{clienteRegular.email()},
                    null
                );
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
            }

            cliente.setEdad(clienteRegular.edad() );
            cliente.setNombre(clienteRegular.nombre() );
            TipoCliente tipoCliente = new TipoCliente();
            tipoCliente.setTipoDeCliente(clienteRegular.tipoCliente().name() );
            tipoCliente.setId(clienteRegular.tipoCliente().getId() );
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
    public void deleteClient(String email) {
        
        Clientes cliente = crudRepository.findByEmail(email);

        if(cliente == null) {
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_CLIENT_NOT_FOUND,
                new Object[]{email},
                null
            );
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        crudRepository.delete(cliente);
    }

    /*
    * {@InheritDoc}
    */
    @Override
    public void insertBorrowing(PrestamoDto prestamoDto) {
        Optional<Clientes> cliente = crudRepository.findById( UUID.fromString(prestamoDto.getClienteId() ) );

        if(cliente.isEmpty() ){
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_CLIENT_NOT_FOUND_BY_ID,
                new Object[]{prestamoDto.getClienteId()},
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }

        Optional<EstadoPrestamo> estado = crudRepositoryEstadosPrestamos.findById(Short.valueOf((short) prestamoDto.getEstado().getId() ) );
        if(estado.isEmpty() ){
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_ESTADO_PRESTAMO_NOT_FOUND_BY_ID,
                new Object[]{prestamoDto.getEstado().getId()},
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }

        Prestamos prestamo = prestamoDto.toEntity(cliente.get() , estado.get() );
        crudRepositoryPrestamos.save(prestamo);
    }

    /*
    * {@InheritDoc}
    */
    @Override
    public Response getBorrowings(Pageable pageable, String email) {
        Clientes cliente = crudRepository.findByEmail(email);

        if(cliente == null) {
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_CLIENT_NOT_FOUND,
                new Object[]{email},
                null
            );
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        Page<Prestamos> prestamos = crudRepositoryPrestamos.findByCliente(cliente, pageable);

        Page<PrestamoDto> prestamosDto = prestamos.map(it -> {
            PrestamoDto prestamoDto = new PrestamoDto(
                it
            );
            return prestamoDto;
        });

        Response response = new Response(
            prestamosDto,
            messageSource.getMessage(Constants.SUCCESS, null, null),
            Boolean.TRUE
        );

        return response;
    }

    /*
    * {@InheritDoc}
    */
    @Override
    public void updateStatusBorrowing(PrestamoDto prestamoDto) {
        Optional<Prestamos> prestamo = crudRepositoryPrestamos.findById(UUID.fromString(prestamoDto.getId() ) );
        if(prestamo.isEmpty() ){
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_PRESTAMO_NOT_FOUND_BY_ID,
                new Object[]{prestamoDto.getId()},
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }

        Optional<EstadoPrestamo> estadoPrestamo = crudRepositoryEstadosPrestamos.findById(Short.valueOf((short) prestamoDto.getEstado().getId() ) );
        if(estadoPrestamo.isEmpty() ){
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_ESTADO_PRESTAMO_NOT_FOUND_BY_ID,
                new Object[]{prestamoDto.getEstado().getId()},
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }

        Prestamos prestamoUpdate = prestamo.get();
        prestamoUpdate.setEstado(estadoPrestamo.get() );

        crudRepositoryPrestamos.save(prestamoUpdate);
    }

    /*
    * {@InheritDoc}
    */
    @Override
    public void deleteBorrowing(String idPrestamo) {
        Optional<Prestamos> prestamo = crudRepositoryPrestamos.findById(UUID.fromString(idPrestamo) );
        if(prestamo.isEmpty() ){
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_PRESTAMO_NOT_FOUND_BY_ID,
                new Object[]{idPrestamo},
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }

        crudRepositoryPrestamos.deleteById(UUID.fromString(idPrestamo) );
    }

    /*
    * {@InheritDoc}
    */
    @Override
    public Double montoTotal(String email) {
        Clientes cliente = crudRepository.findByEmail(email);

        if(cliente == null) {
            String errorMessage = messageSource.getMessage(
                Constants.ERROR_CLIENT_NOT_FOUND,
                new Object[]{email},
                null
            );
            throw new IllegalArgumentException(errorMessage);
        }

        List<Prestamos> prestamos = cliente.getPrestamos();
        Double sumPrestamos = prestamos.stream()
            .filter(
                it -> it.getEstado().getId().equals(EstadoPrestamoDto.PENDIENTE.getId() )
            )
            .mapToDouble(
                it -> it.getMonto()
            )
            .sum();

        return sumPrestamos + ( cliente.getTipoCliente().getTasaDeInteres().doubleValue() * sumPrestamos);
    }

}
