import android.os.Build
import java.lang.Integer.parseInt

import java.sql.Date
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sin

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

    fun Trig1(year: Int, month: Int, day: Int): Int {
        val thisJD = julday(year, month, day);
        val degToRad = 3.14159265 / 180;
        var oldJ: Double = 0.0
        val K0 = floor((year - 1900) * 12.3685);
        val T = (year - 1899.5) / 100;
        val T2 = T * T;
        val T3 = T * T * T;
        val J0 = 2415020 + 29 * K0;
        val F0 =
            0.0001178 * T2 - 0.000000155 * T3 + (0.75933 + 0.53058868 * K0) - (0.000837 * T + 0.000335 * T2);
        val M0 = 360 * (GetFrac(K0 * 0.08084821133)) + 359.2242 - 0.0000333 * T2 - 0.00000347 * T3;
        val M1 = 360 * (GetFrac(K0 * 0.07171366128)) + 306.0253 + 0.0107306 * T2 + 0.00001236 * T3;
        val B1 =
            360 * (GetFrac(K0 * 0.08519585128)) + 21.2964 - (0.0016528 * T2) - (0.00000239 * T3);
        var phase = 0;
        var jday = 0.0;
        while (jday < thisJD) {
            var F = F0 + 1.530588 * phase;
            var M5 = (M0 + phase * 29.10535608) * degToRad;
            var M6 = (M1 + phase * 385.81691806) * degToRad;
            var B6 = (B1 + phase * 390.67050646) * degToRad;
            F -= 0.4068 * sin(M6) + (0.1734 - 0.000393 * T) * sin(M5);
            F += 0.0161 * sin(2 * M6) + 0.0104 * sin(2 * B6);
            F -= 0.0074 * sin(M5 - M6) - 0.0051 * sin(M5 + M6);
            F += 0.0021 * sin(2 * M5) + 0.0010 * sin(2 * B6 - M6);
            F += 0.5 / 1440;
            oldJ = jday;
            jday = J0 + 28 * phase + floor(F);
            phase++;
        }
        val finalValue = (thisJD - oldJ) % 30
        return finalValue.toInt()

    }

    fun Trig2(year: Int, month: Int, day: Int): Int {
        val n = floor(12.37 * (year - 1900 + ((1.0 * month - 0.5) / 12.0)));
        val RAD = 3.14159265 / 180.0;
        val t = n / 1236.85;
        val t2 = t * t;
        val as1 = 359.2242 + 29.105356 * n;
        val am = 306.0253 + 385.816918 * n + 0.010730 * t2;
        var xtra = 0.75933 + 1.53058868 * n + ((1.178e-4) - (1.55e-7) * t) * t2;
        xtra += (0.1734 - 3.93e-4 * t) * Math.sin(RAD * as1) - 0.4068 * Math.sin(RAD * am);
        var i: Double
        if (xtra > 0.0) {
            i = floor(xtra)
        } else {
            i = ceil(xtra - 1)
        }

        val j1 = julday(year, month, day)
        val jd = (2415020 + 28 * n) + i
        return ((j1 - jd + 30) % 30).toInt();
    }


    private fun GetFrac(fr: Double): Double {
        return (fr - floor(fr));
    }

    fun julday(year2: Int, month: Int, day: Int): Double {
        var year = year2
        if (year < 0) {
            year++; }
        var jy = year
        var jm = month + 1;
        if (month <= 2) {
            jy--; jm += 12; }
        var jul = floor(365.25 * jy) + floor(30.6001 * jm) + day + 1720995;
        if (day + 31 * (month + 12 * year) >= (15 + 31 * (10 + 12 * 1582))) {
            val ja = floor(0.01 * jy);
            jul = jul + 2 - ja + floor(0.25 * ja);
        }
        return jul;
    }


}