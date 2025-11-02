package pe.app.appmedicamento.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.app.appmedicamento.R
import pe.app.appmedicamento.RegistroMedicamentoActivity
import pe.app.appmedicamento.model.Medicamento
import pe.app.appmedicamento.view.MedicamentoView


class MedicamentosAdapter(
    private var listado: List<Medicamento>?
): RecyclerView.Adapter<MedicamentoView>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicamentoView {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicamento_main, parent, false)
        return MedicamentoView(view)
    }

    override fun onBindViewHolder(
        holder: MedicamentoView,
        position: Int
    ) {
        listado?.get(position)?.let { medicamento ->
            holder.tvCodigo.text = medicamento.codigo.toString()
            holder.tvNombre.text = medicamento.nombre
            holder.tvstock.text = medicamento.stock.toString()
            holder.tvPrecio.text = String.format("%.2f", medicamento.precio)

            Glide.with(holder.itemView.context)
                .load(medicamento.foto)
                .into(holder.imgFoto)

            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, RegistroMedicamentoActivity::class.java)
                intent.putExtra("MEDICAMENTO_ID", medicamento.codigo)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listado?.size ?: 0
    }
}