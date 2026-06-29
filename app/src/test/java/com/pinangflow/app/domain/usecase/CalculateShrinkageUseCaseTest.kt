package com.pinangflow.app.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateShrinkageUseCaseTest {

    private val calculateShrinkageUseCase = CalculateShrinkageUseCase()

    @Test
    fun `berat akhir setelah 5 hari penjemuran dengan penyusutan 2 persen (compound)`() {
        val initialWeight = 1000.0
        val dailyShrinkage = 2.0
        val days = 5

        // Formula: 1000 * (1 - 0.02)^5 = 1000 * (0.98)^5 = 1000 * 0.90392 = 903.92
        val expected = 903.92079
        
        val actual = calculateShrinkageUseCase(initialWeight, dailyShrinkage, days, isCompound = true)
        
        assertEquals(expected, actual, 0.01)
    }
    
    @Test
    fun `berat akhir setelah 5 hari penjemuran dengan penyusutan 2 persen (linear)`() {
        val initialWeight = 1000.0
        val dailyShrinkage = 2.0
        val days = 5

        // Formula: 1000 - (1000 * 0.02 * 5) = 1000 - 100 = 900
        val expected = 900.0
        
        val actual = calculateShrinkageUseCase(initialWeight, dailyShrinkage, days, isCompound = false)
        
        assertEquals(expected, actual, 0.01)
    }

    @Test
    fun `berat tidak boleh negatif pada penyusutan linear panjang`() {
        val initialWeight = 100.0
        val dailyShrinkage = 10.0
        val days = 20

        val expected = 0.0
        
        val actual = calculateShrinkageUseCase(initialWeight, dailyShrinkage, days, isCompound = false)
        
        assertEquals(expected, actual, 0.01)
    }
}
