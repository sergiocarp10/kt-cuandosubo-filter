package arrivals

import org.jsoup.nodes.Element
import java.util.Calendar

class ArrivalsRow(val line: String, val destination: String, val status: String) {
    private val arrivalTime: Calendar = Calendar.getInstance()

    companion object {

        fun from(e: Element) : ArrivalsRow? {
            val l = e.selectFirst(".arrivalsRouteEntry")?.child(0)?.text()
            val d = e.selectFirst(".arrivalsDestinationEntry")?.child(0)?.text()
            val s = e.selectFirst(".arrivalsStatusEntry")?.text()
            // val at = element.selectFirst(".arrivalsTimeEntry")?.text()

            if (l != null && d != null){
                val nl = l.replace(Regex("[A-Z]"), "")
                val nd = if (d.startsWith("a ")) d.substring(2) else d
                return ArrivalsRow(nl, nd, s ?: "")
            }

            return null
        }
    }

    init {
        if (status.isNotEmpty()){
            val minutes = status.toInt()
            arrivalTime.add(Calendar.MINUTE, minutes)
        }
    }

    fun getArrivalTimeStr() : String {
        val h = arrivalTime[Calendar.HOUR_OF_DAY]
        val m = arrivalTime[Calendar.MINUTE]
        val mStr = if (m < 10) "0$m" else "$m"
        return "$h:$mStr"
    }

    override fun toString(): String {
        return "line: $line, destination: $destination, status: $status', arrives at: ${getArrivalTimeStr()}"
    }
}