package com.team687.frc2017.utilities;

import java.util.Arrays;

/**
 * Linear interpolation (and extrapolation if needed). Intended to use for
 * determining distance from the area of contour(s) from vision.
 * 
 * @author tedlin
 *
 */

public class LinearInterpolator {

    private double[] m_key;
    private double[] m_value;

    public LinearInterpolator(double[] key, double[] value) {
	Arrays.sort(key);
	Arrays.sort(value);
	m_key = key;
	m_value = value;
    }

    public double estimate(double input) {
	double output = 0;

	// put a hard limit on the minimum value
	if (input < m_key[0]) {
	    output = m_value[0];
	}
	// extrapolation (try to avoid this)
	if (input > m_key[m_key.length - 1]) {
	    double slope = (m_value[m_value.length - 1] - m_value[0]) / (m_key[m_key.length - 1] - m_key[0]);
	    double intercept = m_value[0] - slope * m_key[0];
	    output = slope * input + intercept;
	}
	// interpolation
	for (int i = 0; i < m_value.length - 2; i++) {
	    if (input > m_key[i] && input < m_key[i + 1]) {
		output = (m_value[i] * (m_key[i + 1] - input) + m_value[i + 1] * (input - m_key[i]))
			/ (m_key[i + 1] - m_key[i]);
	    }
	}
	return output;
    }

}
