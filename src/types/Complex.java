package types;

import org.la4j.matrix.dense.Basic2DMatrix;

public class Complex {
	public double re;
	public double im;
	
	public Complex(double real, double imaginary) {
		re = real;
		im = imaginary;
	}
	
	public double abs() {
		// Source of this algorithm can be found in the newsgroup excerpt at
		// http://steve.hollasch.net/cgindex/math/pythag-root.txt
		if (re == 0) return im;
		if (im == 0) return re;
		double a = Math.abs(re);
		double b = Math.abs(im);
		double r; //ratio of q/p
		if (a < b) { r = a; a = b; b = r; }
		r = b/a;
		return a*Math.sqrt(1+r*r);
	}
	
	public double angle() { return Math.atan2(im, re); }
	
	public Complex add(Complex z) {
		double a = this.re + z.re;
		double b = this.im + z.im;
		return new Complex(a, b);
	}
	
	public Complex add(double b) { return new Complex(this.re+b,this.im); }
	
	public Complex sub(Complex z) {
		double a = this.re - z.re;
		double b = this.im - z.im;
		return new Complex(a, b);
	}
	
	public Complex times(Complex z) {
		double a = this.re * z.re - this.im * z.im;
		double b = this.re * z.im + this.im * z.re;
		return new Complex(a, b);
	}
	
	public Complex times(double d) { return new Complex(re*d,im*d); }
	public Complex conj() { return new Complex(re, -im); }
	
	public Complex reciprocal() {
		double a = re*re + im*im;
		return new Complex(re / a, -im / a);
	}
	
	public Complex mobius(double a, double b, double c, double d) { return this.times(a).add(b).divide(this.times(c).add(d)); }
	public Complex mobius(Basic2DMatrix f) { return mobius(f.get(0, 0), f.get(0, 1), f.get(1, 0), f.get(1, 1)); }
	
	public Complex divide(Complex z) { return this.times(z.reciprocal()); }
	public Complex exp() { return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im)); }
	public Complex sin() { return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im)); }
	public Complex cos() { return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im)); }
	public Complex tan() { return sin().divide(cos()); }
}