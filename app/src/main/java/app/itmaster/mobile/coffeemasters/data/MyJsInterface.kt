package app.itmaster.mobile.coffeemasters.data

import android.content.Context
import android.os.Build
import android.webkit.JavascriptInterface
import android.widget.Toast
class MyJsInterface(private val mContext: Context) {

    @JavascriptInterface
    fun getAndroidVersion(): Int {
        return Build.VERSION.SDK_INT
    }

    @JavascriptInterface
    fun showToast() {
        Toast.makeText(mContext, "Feedback sent, thank you!", Toast.LENGTH_SHORT).show()
    }
}
