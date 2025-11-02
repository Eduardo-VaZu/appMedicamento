package pe.app.appmedicamento.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pe.app.appmedicamento.R


class MedicamentoView(item: View): RecyclerView.ViewHolder(item) {
    lateinit var imgFoto: ImageView
    lateinit var tvCodigo: TextView
    lateinit var tvNombre: TextView
    lateinit var tvstock: TextView
    lateinit var tvPrecio: TextView

    init {
        imgFoto = item.findViewById(R.id.imgFotoMedicamento)
        tvCodigo = item.findViewById(R.id.tvCodigoMedicamento)
        tvNombre = item.findViewById(R.id.tvNombreMedicamento)
        tvstock = item.findViewById(R.id.tvStockMedicamento)
        tvPrecio = item.findViewById(R.id.tvPrecioMedicamento)
    }
}