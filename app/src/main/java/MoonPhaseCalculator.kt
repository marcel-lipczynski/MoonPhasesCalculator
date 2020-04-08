import android.os.Build
import java.lang.Integer.parseInt

import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.floor

class MoonPhaseCalculator {

    fun Conway(year: Int, month: Int, day: Int): Int {
        var r: Double = year.toDouble() % 100.0;
        r %= 19.0;
        if (r > 9.0) {
            r -= 19.0;
        }
        r = ((r * 11.0) % 30.0) + month.toDouble() + day.toDouble()
        if (month < 3) {
            r += 2
        }
        if (year < 2000) {
            r -= 4
        } else {
            r -= 8.3
        }
        r = floor(r + 0.5) % 30.0;

        if (r < 0.0) {
            return (r.toInt() + 30)
        }
        return r.toInt()

    }


}