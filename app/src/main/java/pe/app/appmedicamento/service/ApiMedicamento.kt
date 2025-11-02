package pe.app.appmedicamento.service


import pe.app.appmedicamento.model.Medicamento
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiMedicamento {
    @GET("/medicamento/lista")
    fun fetchAllMedicamento(): Call<List<Medicamento>>
    @GET("/medicamento/buscar/{id}")
    fun fetchMedicamentoById(@Path("id") id: Int): Call<Medicamento>
    @POST("/medicamento/registrar")
    fun fetchSaveMedicamento(@Body Medicamento: Medicamento): Call<Medicamento>
    @PUT("/medicamento/actualizar")
    fun fetchUpdateMedicamento(@Body Medicamento: Medicamento): Call<Medicamento>
    @DELETE("/medicamento/eliminar/{id}")
    fun fetchDeleteMedicamento(@Path("id") id: Int): Call<Void>

}