package com.fh.android.timeup

import com.fh.android.timeup.beans.TimeMeasurement
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Test methods for TimeMeasurement functionalities.
 */
class TimeMeasurementTests {
    @Test
    fun getWorkingDurationHourFormatTest() {
        // arrange
        var beginDate = LocalDateTime.of(2020,1,1,12,0,0)
        var endDate = LocalDateTime.of(2020,1,1,13,33,0)
        var timeMeasurement = TimeMeasurement(beginDate, endDate);

        // act
        var formattedString = timeMeasurement.getWorkingDurationHourFormat()

        // assert
        assertEquals("1.6 h", formattedString)
    }

    @Test
    fun getWorkDateTest(){
        // arrange
        var beginDate = LocalDateTime.of(2020,1,1,12,30,0)
        var endDate = LocalDateTime.of(2020,1,2,13,30,0)
        var timeMeasurement = TimeMeasurement(beginDate, endDate);

        // act
        var workDate = timeMeasurement.getWorkDate()

        // assert
        assertEquals(LocalDate.of(2020,1,1), workDate)
    }
}
