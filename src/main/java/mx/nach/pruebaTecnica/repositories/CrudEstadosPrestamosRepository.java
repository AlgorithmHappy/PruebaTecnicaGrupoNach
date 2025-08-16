package mx.nach.pruebaTecnica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.nach.pruebaTecnica.entities.EstadoPrestamo;

/*
 * Repositorio CRUD para manejar las operaciones de base de datos del catalogo de los estados de los prestamos.
 */
public interface CrudEstadosPrestamosRepository extends JpaRepository<EstadoPrestamo, Short> {

}
