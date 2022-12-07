package bingo_game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Manager {

	Scanner scan = new Scanner(System.in);
	Random r = new Random();

	String str;
	int N;

	ArrayList<Word> list = new ArrayList<>(); // 단어장 리스트
	Set<Word> words = new HashSet<>(); // 중복되지 않도록 N^2개의 word를 set에 저장
	Word[][] userboard, comboard;

	public Manager(String str) {
		this.str = str;
		makeWordlist("words.txt");
	}

	public void selectword() {
		
	}

	public void startgame() {
		userboard = new Word[N][N];
		comboard = new Word[N][N];
		makeBoard(words, userboard);
		makeBoard(words, comboard);
	}

	public void makeWordlist(String filename) {
		try {
			Scanner scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				String str = scan.nextLine();
				str = str.trim();
				String[] spilt = str.split("\t");
				list.add(new Word(spilt[0], spilt[1]));
			}
		} catch (FileNotFoundException e) {
			System.out.println("파일을 찾을 수 없습니다.");
		}
	}

	public void makeBoard(Set<Word> words, Word[][] board) {
		while (words.size() != N * N)
			words.add(list.get(r.nextInt(list.size())));
		Iterator<Word> it = words.iterator();
		while (it.hasNext()) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					board[i][j] = it.next();
				}
			}
		}
		words.clear();
	}
}
