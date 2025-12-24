package cl.msoto.marcelosoto.ev1.restaurant.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import cl.msoto.marcelosoto.ev1.R
import cl.msoto.marcelosoto.ev1.restaurant.model.CuentaMesa
import cl.msoto.marcelosoto.ev1.restaurant.model.ItemMenu
import java.text.NumberFormat
import java.util.*

// Activity principal de la aplicación.
// Controla la interfaz gráfica y conecta con la lógica de la cuenta de mesa.
class MainActivity : AppCompatActivity() {

    // Formato de moneda en pesos chilenos.
    private val clpFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CL"))

    // Variables de la cuenta y los ítems del menú.
    private lateinit var cuenta: CuentaMesa
    private lateinit var pastelMenu: ItemMenu
    private lateinit var cazuelaMenu: ItemMenu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Carga el layout XML

        // Inicialización de los ítems del menú.
        pastelMenu = ItemMenu("Pastel de Choclo", 12000)
        cazuelaMenu = ItemMenu("Cazuela", 10000)

        // Inicialización de la cuenta de la mesa.
        cuenta = CuentaMesa(1, true)
        cuenta.agregarItem(pastelMenu, 0)
        cuenta.agregarItem(cazuelaMenu, 0)

        // Referencias a los elementos de la interfaz.
        val edtPastel = findViewById<EditText>(R.id.edtPastelCantidad)
        val edtCazuela = findViewById<EditText>(R.id.edtCazuelaCantidad)
        val switchPropina = findViewById<SwitchCompat>(R.id.switchPropina)

        // Listener para cambios en la cantidad de Pastel.
        edtPastel.addTextChangedListener(simpleWatcher {
            cuenta.actualizarCantidad("Pastel de Choclo", it.toIntOrNull() ?: 0)
            actualizarPantalla()
        })

        // Listener para cambios en la cantidad de Cazuela.
        edtCazuela.addTextChangedListener(simpleWatcher {
            cuenta.actualizarCantidad("Cazuela", it.toIntOrNull() ?: 0)
            actualizarPantalla()
        })

        // Listener para activar/desactivar propina.
        switchPropina.setOnCheckedChangeListener { _, isChecked ->
            cuenta.aceptaPropina = isChecked
            actualizarPantalla()
        }

        // Actualiza la pantalla al iniciar.
        actualizarPantalla()
    }

    // Función que actualiza los TextView con los valores actuales de la cuenta.
    private fun actualizarPantalla() {
        val pastelSubtotal = findViewById<TextView>(R.id.txtPastelSubtotal)
        val cazuelaSubtotal = findViewById<TextView>(R.id.txtCazuelaSubtotal)
        val totalComida = findViewById<TextView>(R.id.txtTotalComida)
        val propinaMonto = findViewById<TextView>(R.id.txtPropinaMonto)
        val totalFinal = findViewById<TextView>(R.id.txtTotalFinal)

        // Obtiene los ítems de la cuenta.
        val pastel = cuenta.obtenerItems().first { it.itemMenu.nombre == "Pastel de Choclo" }
        val cazuela = cuenta.obtenerItems().first { it.itemMenu.nombre == "Cazuela" }

        // Actualiza los textos con formato CLP.
        pastelSubtotal.text = "Subtotal: ${clpFormat.format(pastel.calcularSubtotal())}"
        cazuelaSubtotal.text = "Subtotal: ${clpFormat.format(cazuela.calcularSubtotal())}"
        totalComida.text = "Comida: ${clpFormat.format(cuenta.calcularTotalSinPropina())}"
        propinaMonto.text = "Propina: ${clpFormat.format(cuenta.calcularPropina())}"
        totalFinal.text = "TOTAL: ${clpFormat.format(cuenta.calcularTotalConPropina())}"
    }

    // Función auxiliar para simplificar el uso de TextWatcher.
    private fun simpleWatcher(onChange: (String) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onChange(s?.toString() ?: "")
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        }
    }
}
