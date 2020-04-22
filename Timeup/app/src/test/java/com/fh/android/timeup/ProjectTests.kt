package com.fh.android.timeup

import com.fh.android.timeup.beans.Project
import com.fh.android.timeup.beans.TimeMeasurement
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime


/**
 * Test methods for Project functionalities.
 */
class ProjectTests {

    var project :  Project? = null

    @Before
    fun setUp() {
        val measurementList = mutableListOf<TimeMeasurement>(
            TimeMeasurement(LocalDateTime.of(2020,1,1,13,30,0), LocalDateTime.of(2020,1,1,15,0,0)),
            TimeMeasurement(LocalDateTime.of(2020,1,2,8,0,0), LocalDateTime.of(2020,1,2,9,0,0))
        )
        project = Project("My Test Project", ArrayList(measurementList), 500, false)
    }

    @Test
    fun addTimeMeasurement(){
        // arrange

        // act

        // assert

    }

    @Test
    fun removeTimeMeasurement(){
        // arrange

        // act

        // assert

    }

    @Test
    fun getTotalTimeSpendHourFormatTest(){
        // act
        var totalTimeSpendHourFormatString = project?.getTotalTimeSpendHourFormat()

        // assert
        assertEquals("2.5 h", totalTimeSpendHourFormatString)
    }

    @Test
    fun getTotalTimeSpendTest(){
        // arrange

        // act

        // assert

    }

    @Test
    fun getLastUpdateDateTest(){
        // arrange

        // act

        // assert

    }

    @Test
    fun getLastUpdateString(){
        // arrange

        // act

        // assert

    }
}
