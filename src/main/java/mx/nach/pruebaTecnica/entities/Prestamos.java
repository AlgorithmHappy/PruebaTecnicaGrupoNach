package mx.nach.pruebaTecnica.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Entidad Prestamos que representa un prestamo en la base de datos.
 */
@Entity
@Table(name = "prestamos")
@Getter
@Setter
@NoArgsConstructor
public class Prestamos {

    /*
     * id Que identifica de manera unica a un prestamo, se utiliza UUID por seguridad.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /*
     * Monto del prestamo.
     */
    @Column(nullable = false)
    private Double monto;

    /*
     * Fecha del prestamo.
     */
    @Column(nullable = false)
    private LocalDate fecha;

    /*
     * Estado del prestamo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoPrestamo estado;

    /*
     * Relación muchos a uno con la entidad Clientes.
     */
    @ManyToOne(fetch = FetchType.LAZY) // relación dueña de la FK
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;
}
