package dominio;

import exepciones.DivisionPorCeroExcepcion;

public class Calculadora {

	// CONSTANTS
	private static final int LIMITE_NUMEROS_DISPLAY = 21;

	// Attributes
	private double num1;
	private double num2;
	private String operacion;
	private String numActual;
	private String numMemoria;
	private boolean coma = false;
	private boolean primeraPulsacion = false;

	// Constructor
	public Calculadora() {

		reset();
	}

	// Getters and Setters

	public double getNum1() {
		return num1;
	}

	public void setNum1(double num1) {
		this.num1 = num1;
	}

	public double getNum2() {
		return num2;
	}

	public void setNum2(double num2) {
		this.num2 = num2;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) throws DivisionPorCeroExcepcion {

		if (this.operacion.isEmpty()) {
			this.num1 = Replace(this.numActual);
			this.coma = false;
			this.primeraPulsacion = false;

		} else {
			if (primeraPulsacion) {
				this.num2 = Replace(this.numActual);
				this.num1 = calcular();
				this.primeraPulsacion = false;
			}
		}

		this.operacion = operacion;

	}

	public String getNumActual() {
		return numActual;
	}

	public void setNumActual(String numActual) {
		this.numActual = numActual;
	}

	public String getNumMemoria() {
		return numMemoria;
	}

	public void setNumMemoria(String numMemoria) {
		this.numMemoria = numMemoria;
	}

	// Arithmetic operations
	public double sumar() {
		return this.num1 + this.num2;
	}

	public double restar() {
		return this.num1 - this.num2;
	}

	public double multiplicar() {
		return this.num1 * this.num2;

	}

	public double dividir() throws DivisionPorCeroExcepcion {
		if (this.num2 == 0) {
			throw new DivisionPorCeroExcepcion();
		} else {
			return this.num1 / this.num2;
		}
	}

	public double porcentaje() {
		return 0;
	}

	public double raiz() {

		double tmp = Math.sqrt(Double.parseDouble(this.numActual));

		Replace(this.numActual = Double.toString(tmp));

		comprobarDecimales(tmp);

		return tmp;

	}

	public double inversa() throws DivisionPorCeroExcepcion {
		return 0;
	}

	// Change sign to current value
	public void cambiarSigno() {

		double tmp = Double.valueOf(this.numActual);
		tmp *= -1;
		this.numActual = Double.toString(tmp);

		comprobarDecimales(tmp);

	}

	// Reset all variables
	public void reset() {

		this.operacion = "";
		this.numActual = "0";
		this.numMemoria = "";
		this.coma = false;
		this.primeraPulsacion = false;
	}

	public void clearError() {

		this.primeraPulsacion = false;
		this.numActual = "0";

	}

	// Concatenate number to current number
	public String concatenar(String numero) {

		// ---------------------W A R N I N G---------------------
		if (LIMITE_NUMEROS_DISPLAY <= this.numActual.length())
			return this.numActual;
		// ---------------------W A R N I N G---------------------

		if (numero == ",") {
			if (!this.coma)
				this.coma = true;
			else
				return this.numActual;
		}

		if (!primeraPulsacion) {
			primeraPulsacion = true;

			if (numero.equals(","))
				this.numActual = "0";
			else
				return this.numActual = numero;
		}

		// Start with comma automatically puts us 0
		if (this.numActual.equals("0") && !numero.equals(","))
			return this.numActual = numero;

		return this.numActual += numero;
	}

	public void retroceder() {

		// The first IF prohibits deleting digits from the result

		if (this.primeraPulsacion) {
			if (this.numActual.charAt(this.numActual.length() - 1) == ',')
				this.coma = false;

			if (this.numActual.length() == 1) {
				this.numActual = "0";
			} else {
				this.numActual = this.numActual.substring(0, this.numActual.length() - 1);
			}
		}
	}

	public void sumarMemoria() {
	}

	public void restarMemoria() {
	}

	public double calcular() throws DivisionPorCeroExcepcion {
		double res = 0;
		switch (operacion) {
		case "+":
			res = sumar();
			break;
		case "-":
			res = restar();
			break;
		case "*":
			res = multiplicar();
			break;
		case "/":
			res = dividir();
			break;
		case "+%":
			porcentaje();
			porcentaje();
			res = sumar();
			sumar();
			break;
		case "-%":
			porcentaje();
			res = restar();
			break;
		case "*%":
			porcentaje();
			res = num2;
			break;
		case "/%":
			porcentaje();
			res = dividir();
			break;
		}

		this.num1 = 0;
		this.num2 = 0;
		this.operacion = "";
		this.primeraPulsacion = false;

		this.numActual = Double.toString(res);
		this.numActual = this.numActual.replace(".", ",");

		comprobarDecimales(res);
		return res;
	}

	public void Igual() throws DivisionPorCeroExcepcion {

		if (this.operacion.equals(""))
			return;
		this.num2 = Replace(this.numActual);

		calcular();

	}

	private Double Replace(String aux) {

		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");

		double res = Double.parseDouble(aux);

		return res;

	}

	private void comprobarDecimales(double res) {

		if (res - (int) res == 0) {
			this.numActual = this.numActual.substring(0, this.numActual.length() - 2);
			this.coma = false;

		} else {
			this.coma = true;
		}

	}

}