package mx.nach.pruebaTecnica.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.nach.pruebaTecnica.entities.Clientes;
import mx.nach.pruebaTecnica.entities.EstadoPrestamo;
import mx.nach.pruebaTecnica.entities.Prestamos;

/*
 * Clase DTO para representar un préstamo.
 */
@NoArgsConstructor
@Getter
@Setter
public class PrestamoDto {
    
    /*
     * Constructor que inicializa el DTO a partir de un RequestPrestamo.
     */
    public PrestamoDto(RequestPrestamo requestPrestamo) {
        if(requestPrestamo == null) {
            throw new IllegalArgumentException("El requestPrestamo no puede ser nulo");
        }    

        this.monto = requestPrestamo.getMonto();
        this.clienteId = requestPrestamo.getClienteId();
        this.estado = EstadoPrestamoDto.valueOf(requestPrestamo.getEstado() );
        this.fecha = LocalDate.now();
    }

    /*
     * Constructor que inicializa el DTO a partir de un RequestPrestamo.
     */
    public PrestamoDto(Prestamos prestamo) {
        this.monto = prestamo.getMonto();
        this.clienteId = prestamo.getCliente()
            .getId()
            .toString();
        this.estado = EstadoPrestamoDto.valueOf(prestamo.getEstado().getEstado() );
        this.fecha = prestamo.getFecha();
        this.id = prestamo.getId().toString();
    }

    /*
     * Id del préstamo UUID.
     */
    private String id;

    /*
     * Monto del préstamo.
     */
    private double monto;

    /*
     * Id del cliente asociado al préstamo.
     */
    private String clienteId;

    /* 
     * Fecha del préstamo.
     */
    private LocalDate fecha;

    /*
     * Estado del préstamo (PENDIENTE, PAGADO) representado por un enum EstadoPrestamoDto.
     */
    private EstadoPrestamoDto estado;

    /*
     * Convierte este DTO a una entidad Prestamos.
     * @param cliente El cliente asociado al préstamo.
     * @param estado El estado del préstamo.
     * @return La entidad Prestamos correspondiente a este DTO.
     */
    public Prestamos toEntity(Clientes cliente, EstadoPrestamo estado) {
        if(cliente == null || estado == null) {
            throw new IllegalArgumentException("Cliente y Estado no pueden ser nulos");
        }
        if(!cliente.getId().toString().equals(this.clienteId) ){
            throw new IllegalArgumentException("El ID del cliente no coincide con el ID proporcionado en el DTO");
        }
        if(!estado.getEstado().equals( this.estado.name() ) ){
            throw new IllegalArgumentException("El estado no coincide con el estado proporcionado en el DTO");
        }

        Prestamos prestamo = new Prestamos();
        prestamo.setMonto(monto);
        prestamo.setFecha(fecha);
        prestamo.setEstado(estado);
        prestamo.setCliente(cliente);
        
        return prestamo;
    }
}
