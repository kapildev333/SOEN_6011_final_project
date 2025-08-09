package org.example;

/**
 * PowerCalculatorEngine - Mathematical engine for power calculations.
 * 
 * This class provides the core mathematical functionality to calculate x^y (x raised to the power of y)
 * with proper handling of edge cases, special values, and mathematical precision.
 * 
 * @author Kapil Soni
 * @version 1.0.0
 */
public class PowerCalculatorEngine {
    
    // Constants for mathematical calculations
    private static final int SIGNIFICANT_BITS = 0x7fff_ffff;
    private static final double INFINITY_VALUE = Double.POSITIVE_INFINITY;
    
    /**
     * Calculates the low 32 bits of a double value.
     * 
     * @param x the double value
     * @return the low 32 bits as an int
     */
    private static int calculateLow(double x) {
        long transducer = Double.doubleToRawLongBits(x);
        return (int) transducer;
    }
    
    /**
     * Calculates a double value with modified low 32 bits.
     * 
     * @param x the original double value
     * @param low the new low 32 bits
     * @return the modified double value
     */
    private static double calculateLow(double x, int low) {
        long transX = Double.doubleToRawLongBits(x);
        return Double.longBitsToDouble((transX & 0xFFFF_FFFF_0000_0000L) |
                (low & 0x0000_0000_FFFF_FFFFL));
    }
    
    /**
     * Calculates the high 32 bits of a double value.
     * 
     * @param x the double value
     * @return the high 32 bits as an int
     */
    private static int calculateHigh(double x) {
        long transducer = Double.doubleToRawLongBits(x);
        return (int) (transducer >> 32);
    }
    
    /**
     * Calculates a double value with modified high 32 bits.
     * 
     * @param x the original double value
     * @param high the new high 32 bits
     * @return the modified double value
     */
    private static double calculateHigh(double x, int high) {
        long transX = Double.doubleToRawLongBits(x);
        return Double.longBitsToDouble((transX & 0x0000_0000_FFFF_FFFFL) |
                (((long) high)) << 32);
    }
    
    /**
     * Computes x raised to the power of y.
     * 
     * This method implements a sophisticated power calculation algorithm that handles
     * special cases, edge conditions, and provides high-precision results.
     * 
     * @param x the base
     * @param y the exponent
     * @return x^y
     */
    public static double compute(final double x, final double y) {
        double z;
        double r, s, t, u, v, w;
        int i, j, k, n;
        
        // y == zero: x**0 = 1, except for NaN^0 which should be NaN
        if (y == 0.0) {
            if (Double.isNaN(x)) {
                return Double.NaN;
            }
            return 1.0;
        }
        
        // +/-NaN return x + y to propagate NaN significands
        if (Double.isNaN(x) || Double.isNaN(y)) {
            return x + y;
        }
        
        final double yAbs = Math.abs(y);
        double xAbs = Math.abs(x);
        
        // Special values of y
        if (y == 2.0) {
            return x * x;
        } else if (y == 0.5) {
            if (x >= -Double.MAX_VALUE) {
                return Math.sqrt(x + 0.0);
            }
        } else if (yAbs == 1.0) {
            return (y == 1.0) ? x : 1.0 / x;
        } else if (yAbs == INFINITY_VALUE) {
            if (xAbs == 1.0) {
                return y - y;
            } else if (xAbs > 1.0) {
                return (y >= 0) ? y : 0.0;
            } else {
                return (y < 0) ? -y : 0.0;
            }
        }
        
        final int hx = calculateHigh(x);
        int ix = hx & SIGNIFICANT_BITS;
        
        // When x < 0, determine if y is an odd integer
        int yIsInt = 0;
        if (hx < 0) {
            if (yAbs >= 0x1.0p53) {
                yIsInt = 2;
            } else if (yAbs >= 1.0) {
                long yAbsAsLong = (long) yAbs;
                if (((double) yAbsAsLong) == yAbs) {
                    yIsInt = 2 - (int) (yAbsAsLong & 0x1L);
                }
            }
        }
        
        // Special value of x
        if (xAbs == 0.0 || xAbs == INFINITY_VALUE || xAbs == 1.0) {
            z = xAbs;
            if (y < 0.0) {
                // Handle 0^(-y) which should be NaN
                if (xAbs == 0.0) {
                    return Double.NaN;
                }
                z = 1.0 / z;
            }
            if (hx < 0) {
                if (((ix - 0x3ff00000) | yIsInt) == 0) {
                    z = (z - z) / (z - z);
                } else if (yIsInt == 1) {
                    z = -1.0 * z;
                }
            }
            return z;
        }
        
        n = (hx >> 31) + 1;
        
        // (x < 0)**(non-int) is NaN
        if ((n | yIsInt) == 0) {
            return (x - x) / (x - x);
        }
        
        s = 1.0;
        if ((n | (yIsInt - 1)) == 0) {
            s = -1.0;
        }
        
        double pH, pL, t1, t2;
        
        // |y| is huge
        if (yAbs > 0x1.00000_ffff_ffffp31) {
            final double INV_LN2 = 0x1.7154_7652_b82fep0;
            final double INV_LN2_H = 0x1.715476p0;
            final double INV_LN2_L = 0x1.4ae0_bf85_ddf44p-26;
            
            if (xAbs < 0x1.fffff_0000_0000p-1) {
                return (y < 0.0) ? s * INFINITY_VALUE : s * 0.0;
            }
            if (xAbs > 0x1.00000_ffff_ffffp0) {
                return (y > 0.0) ? s * INFINITY_VALUE : s * 0.0;
            }
            
            t = xAbs - 1.0;
            w = (t * t) * (0.5 - t * (0.3333333333333333333333 - t * 0.25));
            u = INV_LN2_H * t;
            v = t * INV_LN2_L - w * INV_LN2;
            t1 = u + v;
            t1 = calculateLow(t1, 0);
            t2 = v - (t1 - u);
        } else {
            final double CP = 0x1.ec70_9dc3_a03fdp-1;
            final double CP_H = 0x1.ec709ep-1;
            final double CP_L = -0x1.e2fe_0145_b01f5p-28;
            
            double zH, zL, ss, s2, sH, sL, tH, tL;
            n = 0;
            
            if (ix < 0x00100000) {
                xAbs *= 0x1.0p53;
                n -= 53;
                ix = calculateHigh(xAbs);
            }
            n += ((ix) >> 20) - 0x3ff;
            j = ix & 0x000fffff;
            ix = j | 0x3ff00000;
            if (j <= 0x3988E) {
                k = 0;
            } else if (j < 0xBB67A) {
                k = 1;
            } else {
                k = 0;
                n += 1;
                ix -= 0x00100000;
            }
            xAbs = calculateHigh(xAbs, ix);
            
            final double[] BP = {1.0, 1.5};
            final double[] DP_H = {0.0, 0x1.2b80_34p-1};
            final double[] DP_L = {0.0, 0x1.cfde_b43c_fd006p-27};
            
            final double L1 = 0x1.3333_3333_33303p-1;
            final double L2 = 0x1.b6db_6db6_fabffp-2;
            final double L3 = 0x1.5555_5518_f264dp-2;
            final double L4 = 0x1.1746_0a91_d4101p-2;
            final double L5 = 0x1.d864_a93c_9db65p-3;
            final double L6 = 0x1.a7e2_84a4_54eefp-3;
            
            u = xAbs - BP[k];
            v = 1.0 / (xAbs + BP[k]);
            ss = u * v;
            sH = ss;
            sH = calculateLow(sH, 0);
            tH = 0.0;
            tH = calculateHigh(tH, ((ix >> 1) | 0x20000000) + 0x00080000 + (k << 18));
            tL = xAbs - (tH - BP[k]);
            sL = v * ((u - sH * tH) - sH * tL);
            s2 = ss * ss;
            r = s2 * s2 * (L1 + s2 * (L2 + s2 * (L3 + s2 * (L4 + s2 * (L5 + s2 * L6)))));
            r += sL * (sH + ss);
            s2 = sH * sH;
            tH = 3.0 + s2 + r;
            tH = calculateLow(tH, 0);
            tL = r - ((tH - 3.0) - s2);
            u = sH * tH;
            v = sL * tH + tL * ss;
            pH = u + v;
            pH = calculateLow(pH, 0);
            pL = v - (pH - u);
            zH = CP_H * pH;
            zL = CP_L * pH + pL * CP + DP_L[k];
            t = (double) n;
            t1 = (((zH + zL) + DP_H[k]) + t);
            t1 = calculateLow(t1, 0);
            t2 = zL - (((t1 - t) - DP_H[k]) - zH);
        }
        
        // Split up y into (y1 + y2) and compute (y1 + y2) * (t1 + t2)
        double y1 = y;
        y1 = calculateLow(y1, 0);
        pL = (y - y1) * t1 + y * t2;
        pH = y1 * t1;
        z = pL + pH;
        j = calculateHigh(z);
        i = calculateLow(z);
        
        if (j >= 0x40900000) {
            if (((j - 0x40900000) | i) != 0) {
                return s * INFINITY_VALUE;
            } else {
                final double OVT = 8.0085662595372944372e-0017;
                if (pL + OVT > z - pH) {
                    return s * INFINITY_VALUE;
                }
            }
        } else if ((j & SIGNIFICANT_BITS) >= 0x4090cc00) {
            if (((j - 0xc090cc00) | i) != 0) {
                return s * 0.0;
            } else {
                if (pL <= z - pH) {
                    return s * 0.0;
                }
            }
        }
        
        final double P1 = 0x1.5555_5555_5553ep-3;
        final double P2 = -0x1.6c16_c16b_ebd93p-9;
        final double P3 = 0x1.1566_aaf2_5de2cp-14;
        final double P4 = -0x1.bbd4_1c5d_26bf1p-20;
        final double P5 = 0x1.6376_972b_ea4d0p-25;
        final double LG2 = 0x1.62e4_2fef_a39efp-1;
        final double LG2_H = 0x1.62e43p-1;
        final double LG2_L = -0x1.05c6_10ca_86c39p-29;
        
        i = j & SIGNIFICANT_BITS;
        k = (i >> 20) - 0x3ff;
        n = 0;
        if (i > 0x3fe00000) {
            n = j + (0x00100000 >> (k + 1));
            k = ((n & SIGNIFICANT_BITS) >> 20) - 0x3ff;
            t = 0.0;
            t = calculateHigh(t, (n & ~(0x000fffff >> k)));
            n = ((n & 0x000fffff) | 0x00100000) >> (20 - k);
            if (j < 0) {
                n = -n;
            }
            pH -= t;
        }
        t = pL + pH;
        t = calculateLow(t, 0);
        u = t * LG2_H;
        v = (pL - (t - pH)) * LG2 + t * LG2_L;
        z = u + v;
        w = v - (z - u);
        t = z * z;
        t1 = z - t * (P1 + t * (P2 + t * (P3 + t * (P4 + t * P5))));
        r = (z * t1) / (t1 - 2.0) - (w + z * w);
        z = 1.0 - (r - z);
        j = calculateHigh(z);
        j += (n << 20);
        if ((j >> 20) <= 0) {
            z = Math.scalb(z, n);
        } else {
            int zHi = calculateHigh(z);
            zHi += (n << 20);
            z = calculateHigh(z, zHi);
        }
        return s * z;
    }
} 