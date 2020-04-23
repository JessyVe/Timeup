package com.fh.android.timeup

import com.fh.android.timeup.beans.Project
import com.fh.android.timeup.beans.TimeMeasurement
import com.fh.android.timeup.enums.UpdateStrings
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime


/**
 * Test methods for Project functionalities.
 */
class ProjectTests {

    var project :  Project? = null
    val measurementList = mutableListOf<TimeMeasurement>()
    
    @Before
    fun setUp() {
        measurementList.addAll(mutableListOf<TimeMeasurement>(
            TimeMeasurement(LocalDateTime.of(2020,1,1,13,30,0), LocalDateTime.of(2020,1,1,15,0,0)),
            TimeMeasurement(LocalDateTime.of(2020,1,2,8,0,0), LocalDateTime.of(2020,1,2,9,0,0))
        ))
        project = Project("My Test Project", ArrayList(measurementList), 500, false)
    }

    @Test
    fun addTimeMeasurement(){
        // arrange
        project?.addTimeMeasurement(TimeMeasurement(LocalDateTime.of(2020,1,3,8,0,0),
            LocalDateTime.of(2020,1,3,9,0,0)))

        // act
        var timeSpend = project?.getTotalTimeSpendHourFormat()

        // assert
        assertEquals("3.5 h", timeSpend)
    }

    @Test
    fun removeTimeMeasurement(){
        // arrange
        project?.removeTimeMeasurement(measurementList[0])

        // act
        var timeSpend = project?.getTotalTimeSpendHourFormat()

        // assert
        assertEquals("1 h", timeSpend)
    }

    @Test
    fun getTotalTimeSpendHourFormatTest(){
        // act
        var totalTimeSpendHourFormatString = project?.getTotalTimeSpendHourFormat()

        // assert
        assertEquals("2.5 h", totalTimeSpendHourFormatString)
    }

    @Test
    fun getLastUpdateDateTest(){
        // act
        var lastUpdateDate = project?.getLastUpdateDate()

        // assert
        assertEquals(LocalDate.of(2020,1,2), lastUpdateDate)
    }

    @Test
    fun getLastUpdateJustNowString(){
        // arrange
        project?.addTimeMeasurement(TimeMeasurement(LocalDateTime.now().minusHours(1), LocalDateTime.now()))

        // act
        var lastUpdateString = project?.getLastUpdateString()

        // assert
        assertEquals(lastUpdateString, UpdateStrings.JUST_NOW.description);
    }
}
