package gmao_custom.data;

public enum ArticleUnit {

	PIECE("Pièce"), METER("Mètre");

	private String name;

	private ArticleUnit(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
