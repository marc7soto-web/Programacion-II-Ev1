package cl.msoto.marcelosoto.ev1.restaurant.model

// Clase que representa un ítem del menú.
// Contiene el nombre del plato y su precio unitario.
data class ItemMenu(
    val nombre: String,       // Nombre del plato (ej: "Pastel de Choclo")
    val precioUnitario: Int   // Precio por unidad en pesos chilenos
)
