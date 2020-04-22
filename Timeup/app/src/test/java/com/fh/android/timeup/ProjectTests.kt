package com.fh.android.timeup

import com.fh.android.timeup.pojo.Project
import com.fh.android.timeup.pojo.TimeMeasurement
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
            TimeMeasurement(LocalDateTime.of(2020,1,1,13,30,0), LocalDateTime.of(2020,1,1,13,30,0)),
            TimeMeasurement(LocalDateTime.of(2020,1,1,13,30,0), LocalDateTime.of(2020,1,1,13,30,0))
        )
        project = Project("My Test Project", ArrayList(measurementList), false)
    }

    @Test
    fun setProjectTitleTest() {
        // arrange

        // act

        // assert

    }

    @Test
    fun setProjectStatusTest(){
        // arrange

        // act

        // assert

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
        // arrange

        // act

        // assert

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
}
