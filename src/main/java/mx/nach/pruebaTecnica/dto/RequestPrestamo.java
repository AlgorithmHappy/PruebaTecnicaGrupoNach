package mx.nach.pruebaTecnica.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Clase request que representa la solicitud de un préstamo.
 */
@NoArgsConstructor
@Getter
@Setter
public class RequestPrestamo implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
     * Monto del préstamo.
     */
    @NotNull
    private Double monto;

    /*
     * Id del cliente asociado al préstamo.
     */
    @NotBlank
    private String clienteId;

    /*
     * Estado del préstamo (PENDIENTE, PAGADO) representado por un enum EstadoPrestamoDto.
     */
    @Size(max = 9)
    private String estado;
}
