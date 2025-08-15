package mx.nach.pruebaTecnica.repositories;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import mx.nach.pruebaTecnica.entities.Clientes;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repositorio CRUD para manejar las operaciones de base de datos.
 */
@Repository
public interface CrudRepository extends JpaRepository<Clientes, UUID> {
    /*
     * Método para buscar un cliente por su email.
     * @param email Email del cliente a buscar.
     * @return Cliente encontrado.
     */
    public Clientes findByEmail(String email);
}
