package com.company;

import Jama.Matrix;

public class BCF {
	private double[] x;		// empty array
	private double[] t;		// stockPrices
	private final double alpha = 5e-3;
	private final double beta = 11.1;
	private final int M;	// order of curve fitting
	private final int N;	// size of data
	private Matrix S;
	private Matrix I;
	
	public BCF(double[] x, double[] t, int m) {
		this.x = x;
		this.t= t;
		M = m;
		N = x.length;
		I = Matrix.identity(M + 1, M + 1); //unit matrix
		computeS();
	}
	
	// compute matrix S
	private void computeS() {
		Matrix sum = new Matrix(M + 1, M + 1);
		for(int i = 0; i < N; i++) {
			Matrix phi = getPhiX(x[i]);
			sum = sum.plus(phi.times(phi.transpose()));
		}
		sum = sum.times(beta);
		S = I.times(alpha).plus(sum);
		S = S.inverse();
	}
	
	// compute matrix φ(x)
	private Matrix getPhiX(double x) {
		double[] phiVal = new double[M + 1];
		for(int i = 0; i <= M; i++) {
			phiVal[i] = Math.pow(x, i);
		}
		Matrix phi = new Matrix(phiVal, M + 1);
		return phi;
	}
	
	// compute s2(x)
	public double getS2X(double x) {
		Matrix s = getPhiX(x).transpose().times(S).times(getPhiX(x));
		double sVal = s.get(0, 0);
		double s2x = 1 / beta + sVal;
		return s2x;
	}
	
	// compute m(x)
	public double getMx(double x) {
		Matrix sum = new Matrix(M + 1, 1);
		for(int i = 0; i < N; i++) {
			Matrix phi = getPhiX(this.x[i]);
			sum = sum.plus(phi.times(t[i]));
		}
		Matrix mx = getPhiX(x).transpose().times(beta);
		mx = mx.times(S);
		mx = mx.times(sum);
		return mx.get(0, 0);
	}
	
	
}
