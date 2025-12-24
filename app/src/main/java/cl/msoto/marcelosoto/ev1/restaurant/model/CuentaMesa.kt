package cl.msoto.marcelosoto.ev1.restaurant.model

// Clase que representa la cuenta completa de una mesa.
// Permite agregar ítems, actualizar cantidades y calcular totales.
class CuentaMesa(
    val mesa: Int,                  // Número de la mesa
    var aceptaPropina: Boolean = true // Indica si la mesa acepta propina
) {
    // Lista interna de ítems pedidos en la mesa.
    private val _items: MutableList<ItemMesa> = mutableListOf()

    // Agrega un nuevo ítem a la cuenta.
    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        _items.add(ItemMesa(itemMenu, cantidad))
    }

    // Actualiza la cantidad de un plato ya existente en la cuenta.
    fun actualizarCantidad(nombrePlato: String, nuevaCantidad: Int) {
        _items.find { it.itemMenu.nombre == nombrePlato }?.cantidad = nuevaCantidad
    }

    // Calcula el total sin propina.
    fun calcularTotalSinPropina(): Int = _items.sumOf { it.calcularSubtotal() }

    // Calcula la propina (10% del total) si corresponde.
    fun calcularPropina(): Int = if (aceptaPropina) (calcularTotalSinPropina() * 0.10).toInt() else 0

    // Calcula el total final incluyendo propina.
    fun calcularTotalConPropina(): Int = calcularTotalSinPropina() + calcularPropina()

    // Devuelve la lista de ítems pedidos.
    fun obtenerItems(): List<ItemMesa> = _items.toList()
}
