package mx.nach.pruebaTecnica.dto;

import org.springframework.context.MessageSource;

import mx.nach.pruebaTecnica.entities.Clientes;
import mx.nach.pruebaTecnica.entities.TipoCliente;
import mx.nach.pruebaTecnica.utils.Constants;

/*
 * Data Transfer Object (DTO) para representar un cliente VIP.
 */
public record ClienteVipDto (
    
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
     * Constructor para que solo se permita crear clientes de tipo VIP
     */
    public ClienteVipDto {
        if (tipoCliente != TipoClienteDto.VIP ) {
            throw new IllegalArgumentException("Solo se permite clientes de tipo VIP");
        }
    }

    /*
     * {@InheritDoc}
     * @throws IllegalArgumentException si el tipo de cliente no es VIP
     */
    public Persona toDto(Clientes cliente, MessageSource messageSource) {
        if( cliente.getTipoCliente().getId() != TipoClienteDto.VIP.getId() ) {
            String errorMessage = messageSource.getMessage(Constants.ERROR_DTO, new Object[]{TipoClienteDto.VIP.name()}, null);
            throw new IllegalArgumentException(errorMessage);
        }
        return new ClienteVipDto(cliente.getNombre(), cliente.getEmail(), cliente.getEdad(), TipoClienteDto.VIP);
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
        tipoCliente.setTipoDeCliente(TipoClienteDto.VIP.name() );
        tipoCliente.setId(TipoClienteDto.VIP.getId() );
        cliente.setTipoCliente(tipoCliente);

        return cliente;
    }

    /*
     * {@InheritDoc}
     */
    public Persona requestToDto(Request request, MessageSource messageSource) {
        if(!request.getTipoCliente().equals(TipoClienteDto.VIP.name() ) ) {
            String errorMessage = messageSource.getMessage(Constants.ERROR_CAST_REQUEST_TO_DTO, new Object[]{TipoClienteDto.VIP.name()}, null);
            throw new IllegalArgumentException(errorMessage);
        }

        return new ClienteVipDto(request.getNombre(), request.getEmail(), request.getEdad(), TipoClienteDto.VIP);
    }
}

