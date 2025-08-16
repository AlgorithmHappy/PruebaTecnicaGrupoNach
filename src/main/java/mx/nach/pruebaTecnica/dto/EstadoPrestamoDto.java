package mx.nach.pruebaTecnica.dto;

/*
 * Enum para representar el catalogo de los tipos de estado, con los valores de: "PENDIENTE = 1, PAGADO = 2".
 */
public enum EstadoPrestamoDto {
    /*
     * Tipo de prestamo "PENDIENTE", con código 1.
     */
    PENDIENTE(1),

    /*
     * Tipo de prestamo "PENDIENTE", con código 2.
     */
    PAGADO(2);

    /*
     * id del tipo de prestamo.
     */
    private final int id;

    /*
     * Constructor privado.
     * @param id El id del tipo de prestamo.
     */
    EstadoPrestamoDto(int id) {
        this.id = id;
    }

    /*
     * Obtiene el id del tipo de prestamo.
     */
    public int getId() {
        return id;
    }

    /*
     * Convierte un id a su correspondiente EstadoPrestamoDto.
     * @param id El id del tipo de cliente.
     * @return El EstadoPrestamoDto correspondiente al id.
     * @throws IllegalArgumentException Si el id no es válido.
     */
    public static TipoClienteDto fromCodigo(int id) {
        for (TipoClienteDto t : TipoClienteDto.values() ) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("Id inválido: " + id);
    }
}
