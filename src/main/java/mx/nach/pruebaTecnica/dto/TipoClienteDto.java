package mx.nach.pruebaTecnica.dto;

/*
 * Enum para representar los tipos de cliente, con los valores de: "REGULAR" = 1 y "VIP" = 2.
 */
public enum TipoClienteDto {
    /*
     * Tipo de cliente "REGULAR", con código 1.
     */
    REGULAR(1),

    /*
     * Tipo de cliente "VIP", con código 2.
     */
    VIP(2);

    /*
     * id del tipo de cliente.
     */
    private final int id;

    /*
     * Constructor privado.
     * @param id El id del tipo de cliente.
     */
    TipoClienteDto(int id) {
        this.id = id;
    }

    /*
     * Obtiene el id del tipo de cliente.
     */
    public int getId() {
        return id;
    }

    /*
     * Convierte un código a su correspondiente TipoClienteDto.
     * @param id El código del tipo de cliente.
     * @return El TipoClienteDto correspondiente al código.
     * @throws IllegalArgumentException Si el código no es válido.
     */
    public static TipoClienteDto fromCodigo(int id) {
        for (TipoClienteDto t : TipoClienteDto.values() ) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + id);
    }
}
