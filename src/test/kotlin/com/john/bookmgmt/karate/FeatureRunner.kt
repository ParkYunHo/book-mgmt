package com.john.bookmgmt.karate

import com.intuit.karate.junit5.Karate

class FeatureRunner {

    @Karate.Test
    fun karateTestRun(): Karate {
        return Karate.run("karateBooks").relativeTo(javaClass)
    }
}