package pe.app.appmedicamento

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import pe.app.appmedicamento.adapter.MedicamentosAdapter
import pe.app.appmedicamento.model.Medicamento
import pe.app.appmedicamento.service.ApiMedicamento
import pe.app.appmedicamento.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoMedicamentoActivity: AppCompatActivity() {

    private lateinit var rvMedicamento: RecyclerView
    private lateinit var apiMedicamento: ApiMedicamento
    private lateinit var btnNuevo: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listado_medicamento_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        apiMedicamento = ApiUtils.getAPIServiceMedicamento()
        rvMedicamento = findViewById(R.id.rvMedicamento)
        btnNuevo = findViewById(R.id.btnNuevo)
        progressBar = findViewById(R.id.progressBarLista)

        btnNuevo.setOnClickListener {
            val intent = Intent(this, RegistroMedicamentoActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        cargarMedicamentos()
    }

    fun cargarMedicamentos(){

        progressBar.visibility = View.VISIBLE
        rvMedicamento.visibility = View.GONE

        apiMedicamento.fetchAllMedicamento().enqueue(object: Callback<List<Medicamento>>{
            override fun onResponse(call: Call<List<Medicamento>?>, response: Response<List<Medicamento>?>) {
                progressBar.visibility = View.GONE
                rvMedicamento.visibility = View.VISIBLE

                if (response.isSuccessful){
                    val datos: List<Medicamento>? = response.body()
                    val adaptador = MedicamentosAdapter(datos)
                    rvMedicamento.layoutManager= LinearLayoutManager(this@ListadoMedicamentoActivity)
                    rvMedicamento.adapter = adaptador
                }
            }

            override fun onFailure(call: Call<List<Medicamento>?>, t: Throwable) {
                progressBar.visibility = View.GONE

                Toast.makeText(
                    this@ListadoMedicamentoActivity,
                    t.message,
                    Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

}