package pe.app.appmedicamento.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Medicamento(
    var codigo: Int,
    var nombre: String,
    var stock: Int,
    var precio: Double,
    var foto: String,
): Serializable {
}