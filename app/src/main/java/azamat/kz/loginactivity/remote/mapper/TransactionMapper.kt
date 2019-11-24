package azamat.kz.loginactivity.remote.mapper

import azamat.kz.loginactivity.remote.model.TransactionModel
import com.google.gson.Gson

class TransactionMapper: ModelMapper<String, TransactionModel> {

    override fun mapFromModel(model: String): TransactionModel {
        return Gson().fromJson(model, TransactionModel::class.java)
    }
}