package matrix2x2;

public class Matrix {
	public double a11, a12, a21, a22;
	public String translation;
	public Matrix(String translation, double a11, double a12, double a21, double a22) {
		this.translation = translation;
		this.a11 = round(a11);
		this.a12 = round(a12);
		this.a21 = round(a21);
		this.a22 = round(a22);
	}
	public String getTranslation() {
		return translation;
	}
	public double geta11() {
		return a11;
	}
	public double geta12() {
		return a12;
	}
	public double geta21() {
		return a21;
	}
	public double geta22() {
		return a22;
	}
	private double round(double x) {
		double roundOff = (double) Math.round(x * 100) / 100;
		return roundOff;
	}
	public void setMatrix(String translation, double a11, double a12, double a21, double a22) {
		this.translation = translation;
		this.a11 = round(a11);
		this.a12 = round(a12);
		this.a21 = round(a21);
		this.a22 = round(a22);
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public void seta11(double a11) {
		this.a11  = a11;
	}
	public void seta12(double a12) {
		this.a12 = a12;
	}
	public void seta21(double a21) {
		this.a21 = a21;
	}
	public void seta22(double a22) {
		this.a22 = a22;
	}
	public String toString() {
		return a11+"  "+a12+"  "+"\n"+a21+"  "+a22+"\n";
	}
	
	
}
