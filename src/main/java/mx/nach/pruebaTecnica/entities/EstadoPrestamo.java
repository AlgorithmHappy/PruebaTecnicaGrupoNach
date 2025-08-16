package mx.nach.pruebaTecnica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Entidad de base de datos del catalogo del tipo de prestamo.
 */
@Entity
@Table(name = "estado_prestamo")
@Getter
@Setter
@NoArgsConstructor
public class EstadoPrestamo {

    /*
     * Id del estado del catalogo del prestamo.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    /*
     * Estado del prestamo (PENDIENTE = 1, PAGADO = 2).
     */
    @Column(nullable = false, length = 9)
    private String estado;
}
