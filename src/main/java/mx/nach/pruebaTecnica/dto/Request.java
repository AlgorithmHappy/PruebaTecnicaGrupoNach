package mx.nach.pruebaTecnica.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Clase que debera enviarse como cuerpo de la peticion.
 */
@NoArgsConstructor
@Getter
@Setter
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
     * Nombre del cliente.
     */
    @NotBlank
    @Size(max = 100)
    String nombre;

    /*
     * Correo electrónico del cliente.
     */
    @NotBlank
    @Email
    @Size(max = 50)
    String email;

    /*
     * Edad del cliente.
     */
    @Min(value = 18)
    @Max(value = 125)
    Short edad;

    /*
     * Tipo de cliente.
     */
    @NotBlank
    @Size(max = 7)
    String tipoCliente;
}
