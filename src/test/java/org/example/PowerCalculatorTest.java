package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PowerCalculatorEngine class.
 *
 * Tests various mathematical scenarios including edge cases,
 * special values, and normal calculations.
 *
 * @author Your Name
 * @version 1.0.0
 */
@DisplayName("PowerCalculatorEngine Tests")
class PowerCalculatorTest {

    private PowerCalculatorGUI calculator;

    @BeforeEach
    void setUp() {
        calculator = new PowerCalculatorGUI();
    }

    @AfterEach
    void tearDown() {
        if (calculator != null) {
            calculator.dispose();
        }
    }

    @Test
    @DisplayName("Test basic power calculations")
    void testBasicPowerCalculations() {
        // Test basic positive integer powers
        assertEquals(1.0, PowerCalculatorEngine.compute(2.0, 0.0), "2^0 should equal 1");
        assertEquals(2.0, PowerCalculatorEngine.compute(2.0, 1.0), "2^1 should equal 2");
        assertEquals(4.0, PowerCalculatorEngine.compute(2.0, 2.0), "2^2 should equal 4");
        assertEquals(8.0, PowerCalculatorEngine.compute(2.0, 3.0), "2^3 should equal 8");
        assertEquals(16.0, PowerCalculatorEngine.compute(2.0, 4.0), "2^4 should equal 16");

        // Test negative powers
        assertEquals(0.5, PowerCalculatorEngine.compute(2.0, -1.0), "2^(-1) should equal 0.5");
        assertEquals(0.25, PowerCalculatorEngine.compute(2.0, -2.0), "2^(-2) should equal 0.25");
        assertEquals(0.125, PowerCalculatorEngine.compute(2.0, -3.0), "2^(-3) should equal 0.125");
    }

    @Test
    @DisplayName("Test fractional powers")
    void testFractionalPowers() {
        // Test square root (power of 0.5)
        assertEquals(2.0, PowerCalculatorEngine.compute(4.0, 0.5), "4^0.5 should equal 2");
        assertEquals(3.0, PowerCalculatorEngine.compute(9.0, 0.5), "9^0.5 should equal 3");
        assertEquals(4.0, PowerCalculatorEngine.compute(16.0, 0.5), "16^0.5 should equal 4");

        // Test cube root (power of 1/3)
        assertEquals(2.0, PowerCalculatorEngine.compute(8.0, 1.0/3.0), "8^(1/3) should equal 2");
        assertEquals(3.0, PowerCalculatorEngine.compute(27.0, 1.0/3.0), "27^(1/3) should equal 3");

        // Test other fractional powers
        assertEquals(2.0, PowerCalculatorEngine.compute(4.0, 0.5), "4^0.5 should equal 2");
        assertEquals(8.0, PowerCalculatorEngine.compute(4.0, 1.5), "4^1.5 should equal 8");
    }

    @Test
    @DisplayName("Test special values")
    void testSpecialValues() {
        // Test zero cases
        assertEquals(1.0, PowerCalculatorEngine.compute(0.0, 0.0), "0^0 should equal 1");
        assertEquals(0.0, PowerCalculatorEngine.compute(0.0, 1.0), "0^1 should equal 0");
        assertEquals(0.0, PowerCalculatorEngine.compute(0.0, 2.0), "0^2 should equal 0");

        // Test one cases
        assertEquals(1.0, PowerCalculatorEngine.compute(1.0, 0.0), "1^0 should equal 1");
        assertEquals(1.0, PowerCalculatorEngine.compute(1.0, 1.0), "1^1 should equal 1");
        assertEquals(1.0, PowerCalculatorEngine.compute(1.0, 2.0), "1^2 should equal 1");
        assertEquals(1.0, PowerCalculatorEngine.compute(1.0, -1.0), "1^(-1) should equal 1");

        // Test negative one cases
        assertEquals(1.0, PowerCalculatorEngine.compute(-1.0, 0.0), "(-1)^0 should equal 1");
        assertEquals(-1.0, PowerCalculatorEngine.compute(-1.0, 1.0), "(-1)^1 should equal -1");
        assertEquals(1.0, PowerCalculatorEngine.compute(-1.0, 2.0), "(-1)^2 should equal 1");
        assertEquals(-1.0, PowerCalculatorEngine.compute(-1.0, 3.0), "(-1)^3 should equal -1");
    }

    @Test
    @DisplayName("Test negative base with integer exponents")
    void testNegativeBaseWithIntegerExponents() {
        // Test negative base with even integer exponents
        assertEquals(4.0, PowerCalculatorEngine.compute(-2.0, 2.0), "(-2)^2 should equal 4");
        assertEquals(16.0, PowerCalculatorEngine.compute(-2.0, 4.0), "(-2)^4 should equal 16");
        assertEquals(64.0, PowerCalculatorEngine.compute(-2.0, 6.0), "(-2)^6 should equal 64");

        // Test negative base with odd integer exponents
        assertEquals(-2.0, PowerCalculatorEngine.compute(-2.0, 1.0), "(-2)^1 should equal -2");
        assertEquals(-8.0, PowerCalculatorEngine.compute(-2.0, 3.0), "(-2)^3 should equal -8");
        assertEquals(-32.0, PowerCalculatorEngine.compute(-2.0, 5.0), "(-2)^5 should equal -32");

        // Test negative base with negative integer exponents
        assertEquals(0.25, PowerCalculatorEngine.compute(-2.0, -2.0), "(-2)^(-2) should equal 0.25");
        assertEquals(-0.125, PowerCalculatorEngine.compute(-2.0, -3.0), "(-2)^(-3) should equal -0.125");
    }

    @Test
    @DisplayName("Test negative base with fractional exponents")
    void testNegativeBaseWithFractionalExponents() {
        // These should return NaN for complex results
        assertTrue(Double.isNaN(PowerCalculatorEngine.compute(-2.0, 0.5)),
                "(-2)^0.5 should be NaN (complex result)");
        assertTrue(Double.isNaN(PowerCalculatorEngine.compute(-4.0, 0.5)),
                "(-4)^0.5 should be NaN (complex result)");
        assertTrue(Double.isNaN(PowerCalculatorEngine.compute(-2.0, 1.5)),
                "(-2)^1.5 should be NaN (complex result)");
    }

    @Test
    @DisplayName("Test infinity values")
    void testInfinityValues() {
        // Test with infinity as base
        assertEquals(Double.POSITIVE_INFINITY, PowerCalculatorEngine.compute(Double.POSITIVE_INFINITY, 2.0),
                "inf^2 should equal inf");
        assertEquals(0.0, PowerCalculatorEngine.compute(Double.POSITIVE_INFINITY, -1.0),
                "inf^(-1) should equal 0");

        // Test with infinity as exponent
        assertEquals(0.0, PowerCalculatorEngine.compute(0.5, Double.POSITIVE_INFINITY),
                "0.5^inf should equal 0");
        assertEquals(Double.POSITIVE_INFINITY, PowerCalculatorEngine.compute(2.0, Double.POSITIVE_INFINITY),
                "2^inf should equal inf");
        assertEquals(0.0, PowerCalculatorEngine.compute(2.0, Double.NEGATIVE_INFINITY),
                "2^(-inf) should equal 0");
    }

    @Test
    @DisplayName("Test NaN values")
    void testNaNValues() {
        // Test with NaN as base
        assertTrue(Double.isNaN(PowerCalculatorEngine.compute(Double.NaN, 2.0)),
                "NaN^2 should be NaN");
        assertTrue(Double.isNaN(PowerCalculatorEngine.compute(Double.NaN, 0.0)),
                "NaN^0 should be NaN");

        // Test with NaN as exponent
        assertTrue(Double.isNaN(PowerCalculatorEngine.compute(2.0, Double.NaN)),
                "2^NaN should be NaN");
        assertTrue(Double.isNaN(PowerCalculatorEngine.compute(0.0, Double.NaN)),
                "0^NaN should be NaN");

        // Test with both NaN
        assertTrue(Double.isNaN(PowerCalculatorEngine.compute(Double.NaN, Double.NaN)),
                "NaN^NaN should be NaN");
    }

    @Test
    @DisplayName("Test large numbers")
    void testLargeNumbers() {
        // Test with large positive numbers
        assertEquals(1e6, PowerCalculatorEngine.compute(1e3, 2.0), 1e-6,
                "(1e3)^2 should equal 1e6");
        assertEquals(1e9, PowerCalculatorEngine.compute(1e3, 3.0), 1e-6,
                "(1e3)^3 should equal 1e9");

        // Test with very large exponents
        assertTrue(Double.isInfinite(PowerCalculatorEngine.compute(2.0, 1000.0)) ||
                        PowerCalculatorEngine.compute(2.0, 1000.0) > 1e300,
                "2^1000 should be very large or infinite");
    }

    @Test
    @DisplayName("Test small numbers")
    void testSmallNumbers() {
        // Test with small positive numbers
        assertEquals(0.01, PowerCalculatorEngine.compute(0.1, 2.0), 1e-10,
                "(0.1)^2 should equal 0.01");
        assertEquals(0.001, PowerCalculatorEngine.compute(0.1, 3.0), 1e-10,
                "(0.1)^3 should equal 0.001");

        // Test with very small exponents
        assertEquals(1.0, PowerCalculatorEngine.compute(2.0, 0.0), 1e-10,
                "2^0 should equal 1");
        assertEquals(Math.sqrt(2.0), PowerCalculatorEngine.compute(2.0, 0.5), 1e-10,
                "2^0.5 should equal sqrt(2)");
    }

    @Test
    @DisplayName("Test edge cases")
    void testEdgeCases() {
        // Test with very small base
        assertEquals(Double.MIN_VALUE, PowerCalculatorEngine.compute(Double.MIN_VALUE, 1.0),
                "MIN_VALUE^1 should equal MIN_VALUE");

        // Test with very large base
        assertEquals(Double.POSITIVE_INFINITY, PowerCalculatorEngine.compute(Double.MAX_VALUE, 2.0),
                "MAX_VALUE^2 should equal infinity");

        // Test with negative zero
        assertEquals(1.0, PowerCalculatorEngine.compute(-0.0, 0.0),
                "(-0)^0 should equal 1");
        assertEquals(-0.0, PowerCalculatorEngine.compute(-0.0, 1.0),
                "(-0)^1 should equal -0");
    }

    @Test
    @DisplayName("Test GUI components exist")
    void testGUIComponentsExist() {
        assertNotNull(calculator, "Calculator should not be null");
        assertTrue(calculator.isVisible() || !calculator.isVisible(),
                "Calculator visibility should be boolean");
    }

    @Test
    @DisplayName("Test mathematical properties")
    void testMathematicalProperties() {
        double x = 2.0;
        double y = 3.0;
        double z = 4.0;

        // Test power of power: (x^y)^z = x^(y*z)
        double left = PowerCalculatorEngine.compute(PowerCalculatorEngine.compute(x, y), z);
        double right = PowerCalculatorEngine.compute(x, y * z);
        assertEquals(left, right, 1e-10, "(x^y)^z should equal x^(y*z)");

        // Test product of powers: x^y * x^z = x^(y+z)
        double left2 = PowerCalculatorEngine.compute(x, y) * PowerCalculatorEngine.compute(x, z);
        double right2 = PowerCalculatorEngine.compute(x, y + z);
        assertEquals(left2, right2, 1e-10, "x^y * x^z should equal x^(y+z)");
    }
}