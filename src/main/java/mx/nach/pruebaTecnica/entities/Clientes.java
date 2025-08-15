package mx.nach.pruebaTecnica.entities;

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
 * Entidad de base de datos de un cliente.
 */
@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
public class Clientes {
    /*
     * id Que identifica de manera unica a un cliente, se utiliza UUID por seguridad.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /*
     * Nombre del cliente.
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /*
     * Correo electronico del cliente.
     */
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    /*
     * Edad del cliente.
     */
    @Column(nullable = true)
    private Short edad;

    /*
     * Tipo de cliente, se relaciona con la entidad TipoCliente de muchos a uno.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_catalogo_cliente", nullable = false)
    private TipoCliente tipoCliente;
}
