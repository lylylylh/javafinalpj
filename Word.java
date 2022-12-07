package bingo_game;

public class Word {

	String eng;
	String kor;
	boolean comselected;
	boolean userselected;

	public Word(String eng, String kor) {
		this.eng = eng;
		this.kor = kor;
		this.comselected = false;
		this.userselected = false;
	}

	@Override
	public String toString() {
		return this.eng;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Word tmp = (Word)obj;
		return this.eng.equals(tmp.eng) &&  this.kor.equals(tmp.kor);
	}
}