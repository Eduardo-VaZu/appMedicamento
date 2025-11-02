package pe.app.appmedicamento

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import pe.app.appmedicamento.model.Medicamento
import pe.app.appmedicamento.service.ApiMedicamento
import pe.app.appmedicamento.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroMedicamentoActivity : AppCompatActivity() {
    private lateinit var tvTituloFormulario: TextView
    private lateinit var edtNombre: TextInputEditText
    private lateinit var edtStock: TextInputEditText
    private lateinit var edtPrecio: TextInputEditText
    private lateinit var edtImagenUrl: TextInputEditText
    private lateinit var btnGuardar: Button
    private lateinit var btnEliminar: Button
    private lateinit var btnVolver: Button
    private lateinit var apiMedicamento: ApiMedicamento
    private lateinit var progressBar: ProgressBar
    private var medicamentoAEditar: Medicamento? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_medicamento_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvTituloFormulario = findViewById(R.id.tvTituloFormulario)
        edtNombre = findViewById(R.id.edtNombre)
        edtStock = findViewById(R.id.edtStock)
        edtPrecio = findViewById(R.id.edtPrecio)
        edtImagenUrl = findViewById(R.id.edtImagenUrl)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnEliminar = findViewById(R.id.btnEliminar)
        btnVolver = findViewById(R.id.btnVolver)
        apiMedicamento = ApiUtils.getAPIServiceMedicamento()
        progressBar = findViewById(R.id.progressBarRegistro)

        val medicamentoId = intent.getIntExtra("MEDICAMENTO_ID", -1)

        if (medicamentoId != -1) {
            cargarDatosDelMedicamento(medicamentoId)
        } else {
            tvTituloFormulario.text = "Nuevo Medicamento"
            btnGuardar.text = "Guardar"
            btnEliminar.visibility = View.GONE
        }

        btnGuardar.setOnClickListener {
            if (medicamentoAEditar == null) {
                registrar()
            } else {
                actualizar()
            }
        }
        btnVolver.setOnClickListener {
            finish()
        }
        btnEliminar.setOnClickListener {
            medicamentoAEditar?.let {
                mostrarDialogoConfirmacion()
            }
        }
    }

    private fun cargarDatosDelMedicamento(id: Int) {
        tvTituloFormulario.text = "Cargando datos..."
        mostrarCargando(true)

        apiMedicamento.fetchMedicamentoById(id).enqueue(object : Callback<Medicamento> {
            override fun onResponse(call: Call<Medicamento>, response: Response<Medicamento>) {
                mostrarCargando(false)
                if (response.isSuccessful) {
                    medicamentoAEditar = response.body()
                    preCargarDatos()
                    btnGuardar.isEnabled = true
                } else {
                    mostrarToast("Error al cargar datos: ${response.message()}")
                    finish()
                }
            }

            override fun onFailure(call: Call<Medicamento>, t: Throwable) {
                mostrarCargando(false)
                mostrarToast("Error de red: ${t.message}")
                finish()
            }
        })
    }

    private fun registrar() {
        mostrarCargando(true)

        val medicamento = obtenerDatosDelFormulario() ?: return

        apiMedicamento.fetchSaveMedicamento(medicamento).enqueue(object : Callback<Medicamento> {
            override fun onResponse(call: Call<Medicamento>, response: Response<Medicamento>) {
                mostrarCargando(false)

                if (response.isSuccessful) {
                    mostrarToast("Medicamento registrado con éxito")
                    finish()
                } else {
                    mostrarToast("Error al registrar: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Medicamento>, t: Throwable) {
                mostrarCargando(false)
                mostrarToast("Error de conexión: ${t.message}")
            }
        })
    }

    private fun actualizar() {

        val medicamentoValido = obtenerDatosDelFormulario() ?: return
        val medicamentoConId = medicamentoValido.copy(codigo = medicamentoAEditar!!.codigo)
        mostrarCargando(true)

        apiMedicamento.fetchUpdateMedicamento(medicamentoConId).enqueue(object : Callback<Medicamento> {
            override fun onResponse(call: Call<Medicamento>, response: Response<Medicamento>) {
                mostrarCargando(false)
                if (response.isSuccessful) {
                    mostrarToast("Medicamento actualizado con éxito")
                    finish()
                } else {
                    mostrarToast("Error al actualizar: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Medicamento>, t: Throwable) {
                mostrarCargando(false)
                mostrarToast("Error de conexión: ${t.message}")
            }
        })
    }

    private fun eliminarMedicamento() {
        val id = medicamentoAEditar!!.codigo
        mostrarCargando(true)
        apiMedicamento.fetchDeleteMedicamento(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                mostrarCargando(false)
                if (response.isSuccessful) {
                    mostrarToast("Medicamento eliminado con éxito")
                    finish()
                } else {
                    mostrarToast("Error al eliminar: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>,t: Throwable) {
                mostrarCargando(false)
                mostrarToast("Error de conexión: ${t.message}")
            }
        })
    }

    private fun obtenerDatosDelFormulario(): Medicamento? {
        val nombre = edtNombre.text.toString().trim()
        val stockStr = edtStock.text.toString().trim()
        val precioStr = edtPrecio.text.toString().trim().replace(',', '.')
        val imagen = edtImagenUrl.text.toString().trim()

        if (nombre.isBlank() || stockStr.isBlank() || precioStr.isBlank() || imagen.isBlank()) {
            mostrarToast("Por favor, complete todos los campos")
            return null
        }

        val stock: Int
        try {
            stock = stockStr.toInt()
        } catch (e: NumberFormatException) {
            mostrarToast("El stock debe ser un número entero válido")
            return null
        }

        val precio: Double
        try {
            precio = precioStr.toDouble()
        } catch (e: NumberFormatException) {
            mostrarToast("El precio debe ser un número decimal válido (ej: 10.50)")
            return null
        }

        if (stock < 0) {
            mostrarToast("El stock no puede ser negativo")
            return null
        }

        if (precio <= 0) {
            mostrarToast("El precio debe ser mayor a cero")
            return null
        }

        if (!imagen.startsWith("http://") && !imagen.startsWith("https://")) {
            mostrarToast("La URL de la imagen debe ser válida (empezar con http:// o https://)")
            return null
        }

        return Medicamento(
            codigo = 0,
            nombre = nombre,
            stock = stock,
            precio = precio,
            foto = imagen
        )
    }

    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this@RegistroMedicamentoActivity, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun mostrarDialogoConfirmacion() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Eliminación")
            .setMessage("¿Estás seguro de que deseas eliminar '${medicamentoAEditar?.nombre}'?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                eliminarMedicamento()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun preCargarDatos() {
        medicamentoAEditar?.let { med ->
            tvTituloFormulario.text = "Actualizar Medicamento"
            edtNombre.setText(med.nombre)
            edtStock.setText(med.stock.toString())
            edtPrecio.setText(String.format("%.2f", med.precio))
            edtImagenUrl.setText(med.foto)
            btnGuardar.text = "Actualizar"
            btnEliminar.visibility = View.VISIBLE
        }
    }

    private fun mostrarCargando(estaCargando: Boolean) {
        if (estaCargando) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }

        val habilitado = !estaCargando
        edtNombre.isEnabled = habilitado
        edtStock.isEnabled = habilitado
        edtPrecio.isEnabled = habilitado
        edtImagenUrl.isEnabled = habilitado
        btnGuardar.isEnabled = habilitado
        btnEliminar.isEnabled = habilitado
        btnVolver.isEnabled = habilitado
    }
}