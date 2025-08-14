package mx.nach.pruebaTecnica.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mx.nach.pruebaTecnica.entities.Clientes;
import mx.nach.pruebaTecnica.entities.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

/*
 * Data Transfer Object (DTO) para representar un cliente.
 */
public record ClienteRegularDto (
    /*
     * ID del cliente, se ignora por seguridad
     */
    @JsonIgnore
    UUID id,
    
    /*
     * Nombre del cliente.
     */
    @NotBlank
    @Size(max = 100)
    String nombre,

    /*
     * Correo electrónico del cliente.
     */
    @NotBlank
    @Email
    @Size(max = 50)
    String email,

    /*
     * Edad del cliente.
     */
    @Min(value = 18)
    @Max(value = 125)
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
     * Convierte de entidad a dto
     * @param cliente Entidad de cliente
     * @return ClienteRegularDto
     * @throws IllegalArgumentException si el tipo de cliente no es REGULAR
     */
    public static ClienteRegularDto fromEntity(Clientes cliente) {
        if( cliente.getTipoCliente().getId() != TipoClienteDto.REGULAR.getId() ) {
            throw new IllegalArgumentException("Solo se permite clientes de tipo REGULAR");
        }
        return new ClienteRegularDto(cliente.getId(), cliente.getNombre(), cliente.getEmail(), cliente.getEdad(), TipoClienteDto.REGULAR);
    }

    /*
     * Convierte de dto a entidad
     * @return Entidad de cliente
     */
    public Clientes toEntity() {
        Clientes cliente = new Clientes();
        cliente.setId(this.id() );
        cliente.setNombre(this.nombre() );
        cliente.setEmail(this.email() );
        cliente.setEdad(this.edad() );

        TipoCliente tipoCliente = new TipoCliente();
        tipoCliente.setTipoDeCliente(TipoClienteDto.REGULAR.name() );
        cliente.setEdad(this.edad() );
        return cliente;
    }
}
