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
 * Entidad de base de datos del catalogo de los tipos de cliente.
 */
@Entity
@Table(name = "catalogo_clientes")
@Getter
@Setter
@NoArgsConstructor
public class TipoCliente {

    /*
     * Id del tipo de cliente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
     * Tipo de cliente (REGULAR = 1, VIP = 2).
     */
    @Column(nullable = false, length = 7)
    private String tipoDeCliente;
}
