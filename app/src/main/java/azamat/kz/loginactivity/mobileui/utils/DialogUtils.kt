package azamat.kz.loginactivity.mobileui.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import azamat.kz.loginactivity.R

fun Context.showAlert(message: String) {
    var builder = AlertDialog.Builder(this, R.style.ThemeOverlay_AppCompat_Dialog_Alert)
        .setMessage(message)
        .setCancelable(true)
        .setNegativeButton("Ok", { dialog, which ->
            dialog.cancel()
        }).create().show()
}