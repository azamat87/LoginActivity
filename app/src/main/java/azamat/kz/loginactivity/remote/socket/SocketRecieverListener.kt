package azamat.kz.loginactivity.remote.socket

import azamat.kz.loginactivity.remote.model.TransactionModel

interface SocketRecieverListener {

    fun setMessage(transaction: TransactionModel)

}