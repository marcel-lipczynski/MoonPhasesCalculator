import android.os.Build
import java.lang.Integer.parseInt

import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.floor

class MoonPhaseCalculator {

    fun Conway(year: Double, month: Double, day: Double): Double {
        var r: Double = year % 100;
        r %= 19;
        if (r > 9) {
            r -= 19;
        }
        r = ((r * 11) % 30) + month + day;
        if (month < 3) {
            r += 2;
        }
        if (year < 2000) {
            r -= 4
        } else {
            r -= 8.3
        }
        r = Math.floor(r + 0.5) % 30;

        if (r < 0) {
            return (r + 30)
        }
        return r

    }


}