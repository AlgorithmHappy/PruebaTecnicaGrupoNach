package mx.nach.pruebaTecnica.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.nach.pruebaTecnica.entities.Clientes;
import mx.nach.pruebaTecnica.entities.Prestamos;

public interface CrudPrestamosRepository extends JpaRepository<Prestamos, UUID> {
    public Page<Prestamos> findByCliente(Clientes cliente, Pageable pageable);
}
