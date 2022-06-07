import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class A {
    fun a() {
        GlobalScope.launch(Dispatchers.IO) {

        }


    }
}