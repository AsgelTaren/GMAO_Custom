package gmao_custom.data;

public enum ArticleUnit {

	PIECE("Pi�ce"), METER("M�tre");

	private String name;

	private ArticleUnit(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
