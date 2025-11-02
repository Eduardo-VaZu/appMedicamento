package pe.app.appmedicamento.utils

import pe.app.appmedicamento.service.ApiMedicamento

class ApiUtils {
    companion object {
        val BASE_URL="https://apimedicamento.onrender.com"

        fun getAPIServiceMedicamento(): ApiMedicamento {
            return RetrofitClient.getClient(BASE_URL).create(ApiMedicamento::class.java)
        }
    }
}