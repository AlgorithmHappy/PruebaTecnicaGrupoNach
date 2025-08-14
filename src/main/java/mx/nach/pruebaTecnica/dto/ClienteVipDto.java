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

public record ClienteVipDto (
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
     * Constructor para que solo se permita crear clientes de tipo VIP
     */
    public ClienteVipDto {
        if (tipoCliente != TipoClienteDto.VIP ) {
            throw new IllegalArgumentException("Solo se permite clientes de tipo VIP");
        }
    }

    /*
     * Convierte de entidad a dto
     * @param cliente Entidad de cliente
     * @return ClienteVipDto
     * @throws IllegalArgumentException si el tipo de cliente no es VIP
     */
    public static ClienteVipDto fromEntity(Clientes cliente) {
        if( cliente.getTipoCliente().getId() != TipoClienteDto.VIP.getId() ) {
            throw new IllegalArgumentException("Solo se permite clientes de tipo VIP");
        }
        return new ClienteVipDto(cliente.getId(), cliente.getNombre(), cliente.getEmail(), cliente.getEdad(), TipoClienteDto.VIP);
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
        tipoCliente.setTipoDeCliente(TipoClienteDto.VIP.name() );
        cliente.setEdad(this.edad() );
        return cliente;
    }
}

