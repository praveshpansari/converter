// Object of type currency having name, symbol and factor
public class Currency {
	String name, symbol;
	Double factor;

	public Currency(String name, String symbol, Double factor) {
		this.name = name;
		this.symbol = symbol;
		this.factor = factor;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the factor
	 */
	public Double getFactor() {
		return factor;
	}

	/**
	 * @param factor the factor to set
	 */
	public void setFactor(Double factor) {
		this.factor = factor;
	}

}
