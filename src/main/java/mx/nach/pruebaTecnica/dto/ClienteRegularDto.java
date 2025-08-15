package mx.nach.pruebaTecnica.dto;

import org.springframework.context.MessageSource;

import mx.nach.pruebaTecnica.entities.Clientes;
import mx.nach.pruebaTecnica.entities.TipoCliente;
import mx.nach.pruebaTecnica.utils.Constants;

/*
 * Data Transfer Object (DTO) para representar un cliente regular.
 */
public record ClienteRegularDto (
    
    /*
     * Nombre del cliente.
     */
    String nombre,

    /*
     * Correo electrónico del cliente.
     */
    String email,

    /*
     * Edad del cliente.
     */
    Short edad,

    /*
     * Tipo de cliente.
     */
    TipoClienteDto tipoCliente
) implements Persona { 

    /*
     * Constructor para que solo se permita crear clientes de tipo REGULAR
     */
    public ClienteRegularDto {
        if (tipoCliente != TipoClienteDto.REGULAR ) {
            throw new IllegalArgumentException("Solo se permite clientes de tipo REGULAR");
        }
    }

    /*
     * {@InheritDoc}
     * @throws IllegalArgumentException si el tipo de cliente no es REGULAR
     */
    public Persona toDto(Clientes cliente, MessageSource messageSource) {
        if( cliente.getTipoCliente().getId() != TipoClienteDto.REGULAR.getId() ) {
            String errorMessage = messageSource.getMessage(Constants.ERROR_DTO, new Object[]{TipoClienteDto.REGULAR.name()}, null);
            throw new IllegalArgumentException(errorMessage);
        }
        return new ClienteRegularDto(cliente.getNombre(), cliente.getEmail(), cliente.getEdad(), TipoClienteDto.REGULAR);
    }

    /*
     * {@InheritDoc}
     */
    public Clientes toEntity() {
        Clientes cliente = new Clientes();
        cliente.setNombre(this.nombre() );
        cliente.setEmail(this.email() );
        cliente.setEdad(this.edad() );

        TipoCliente tipoCliente = new TipoCliente();
        tipoCliente.setTipoDeCliente(TipoClienteDto.REGULAR.name() );
        tipoCliente.setId(TipoClienteDto.REGULAR.getId() );
        cliente.setTipoCliente(tipoCliente);

        return cliente;
    }

    /*
     * {@InheritDoc}
     */
    public Persona requestToDto(Request request, MessageSource messageSource) {
        if(!request.getTipoCliente().equals(TipoClienteDto.REGULAR.name() ) ) {
            String errorMessage = messageSource.getMessage(Constants.ERROR_CAST_REQUEST_TO_DTO, new Object[]{TipoClienteDto.REGULAR.name()}, null);
            throw new IllegalArgumentException(errorMessage);
        }

        return new ClienteRegularDto(request.getNombre(), request.getEmail(), request.getEdad(), TipoClienteDto.REGULAR);
    }
}
