package cl.msoto.marcelosoto.ev1.restaurant.model

// Clase que representa un ítem pedido en una mesa.
// Relaciona un ItemMenu con la cantidad solicitada.
data class ItemMesa(
    val itemMenu: ItemMenu,   // Plato elegido
    var cantidad: Int         // Cantidad pedida
) {
    // Calcula el subtotal multiplicando cantidad por precio unitario.
    fun calcularSubtotal(): Int = cantidad * itemMenu.precioUnitario
}
