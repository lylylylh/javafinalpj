package bingo_game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener, ItemListener {

	Container frame = this.getContentPane();
	JPanel panel1 = new JPanel(); // userboard
	JPanel panel2 = new JPanel(); // comboard
	JPanel panel3, panel3tf, panel3btn; // text, select, button
	JPanel panel4; // level, title
	JPanel bothboard; // open button -> open the both board

	Manager m = new Manager("이윤희");
	JLabel[][] userlb, comlb;
	JLabel select, level, title;
	JButton com, user, open, restart;
	JComboBox<Integer> levelCB;
	Integer[] levels = { 2, 3, 4, 5, 6, 7 };
	JTextArea ta;
	JTextField tf;
	int userbingo, combingo;
	int[] row, col, diagonal;
	int win = 0, lose = 0, draw = 0;

	Frame(String title) {
		super(title);
		this.setSize(750, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initpanel4();
		initpanel1();
		initpanel3();
		this.setVisible(true);
	}

	private void selectInitial() {
		for (Word[] w : m.comboard) {
			for (Word ww : w) {
				ww.comselected = false;
			}
		}
		for (Word[] w : m.userboard) {
			for (Word ww : w) {
				ww.userselected = false;
			}
		}
	}

	private void comselect() {
		String str; // computer가 선택한 단어의 eng
		row = new int[m.N]; // 같은 row index의 선택된 횟수 저장 배열 , i고정 j변화
		col = new int[m.N]; // 같은 column index의 선택된 횟수 저장 배열, j고정 i변화
		diagonal = new int[2]; // 대각선
		int max = 0;
		int maxindex = (m.N - 1) / 2;

		for (int i = 0; i < m.N; i++) {
			for (int j = 0; j < m.N; j++) {
				if (m.comboard[i][j].comselected) {
					row[i]++;
					col[j]++;
				}
			}
		}
		for (int i = 0; i < m.N; i++) {
			if (m.comboard[i][i].comselected) {
				diagonal[0]++;
			}
		}
		int a = 0;
		int b = m.N - 1;
		while (a < m.N) {
			if (m.comboard[a][b].comselected)
				diagonal[1]++;
			a++;
			b--;
		}

		// 가장 많이 선택된 행이나 열 찾기
		for (int j = 0; j < row.length; j++) {
			if (row[j] > max) {
				if (row[j] != m.N) {
					max = row[j];
					maxindex = j;
				}
			}
		}
		for (int i = 0; i < col.length; i++) {
			if (col[i] > max) {
				if (col[i] != m.N) {
					max = col[i];
					maxindex = i + m.N;
				}
			}
		}
		for (int i = 0; i < diagonal.length; i++) {
			if (diagonal[i] > max) {
				if (diagonal[i] != m.N) {
					max = diagonal[i];
					maxindex = i + m.N * 2;
				}
			}
		}

		if (maxindex < m.N) { // 가로줄이 max
			for (int j = 0; j < m.N; j++) {
				if (!m.comboard[maxindex][j].comselected) {
					str = m.comboard[maxindex][j].eng;
					userselect(str);
					break;
				}
			}
		} else if (maxindex < m.N * 2) { // 세로줄이 max
			maxindex -= m.N;
			for (int i = 0; i < m.N; i++) {
				if (!m.comboard[i][maxindex].comselected) {
					str = m.comboard[i][maxindex].eng;
					userselect(str);
					break;
				}
			}
		} else { // 대각선이 max
			if ((maxindex - m.N * 2) == 0) {
				for (int i = 0; i < m.N; i++) {
					if (!m.comboard[i][i].comselected) {
						str = m.comboard[i][i].eng;
						userselect(str);
						break;
					}
				}
			} else {
				int i = 0;
				int j = m.N - 1;
				while (i < m.N) {
					if (!m.comboard[i][j].comselected) {
						str = m.comboard[i][j].eng;
						userselect(str);
						break;
					}
					a++;
					b--;
				}
			}
		}
	}

	private void initpanel4() {
		// level combobox
		panel4 = new JPanel();
		level = new JLabel("LEVEL : ");
		levelCB = new JComboBox<>(this.levels);
		levelCB.setSelectedItem(null);
		this.levelCB.addItemListener(this);
		title = new JLabel("         B.I.N.G.O  ");
		Color c = new Color(204, 153, 255);
		title.setForeground(c);
		Font font = new Font("Britannic Bold", Font.BOLD, 50);
		title.setFont(font);
		panel4.add(title);
		panel4.add(level);
		panel4.add(levelCB);
		frame.add(panel4, BorderLayout.NORTH);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED) {
			panel1.removeAll();
			panel2.removeAll();
			frame.remove(panel2);
			frame.add(panel1, BorderLayout.CENTER);
			frame.revalidate();
			frame.repaint();
			this.setSize(750, 600);
			this.setLocationRelativeTo(null);
			int level = (Integer) this.levelCB.getSelectedItem();
			m.N = level;
			initboard();
		}
		else if (e.getStateChange() == ItemEvent.DESELECTED) {
			selectInitial();
			ta.setText("");
		}
	}

	private void initpanel2() {
		// paint computer board
		frame.remove(panel1);
		frame.add(panel2, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}

	private void userbingo() {
		int i, j;
		userbingo = 0; // 초기화
		// 가로줄 빙고
		for (i = 0; i < m.N; i++) {
			for (j = 0; j < m.N; j++) {
				if (!m.userboard[i][j].userselected)
					break;
			}
			if (j == m.N)
				userbingo++;
		}

		// 세로줄 빙고
		for (i = 0; i < m.N; i++) {
			for (j = 0; j < m.N; j++) {
				if (!m.userboard[j][i].userselected)
					break;
			}
			if (j == m.N)
				userbingo++;
		}

		// 대각선 빙고
		for (i = 0; i < m.N; i++) {
			if (!m.userboard[i][i].userselected)
				break;
		}
		if (i == m.N)
			userbingo++;

		// 대각선 빙고2
		i = 0;
		j = m.N - 1;
		while (i < m.N) {
			if (!m.userboard[i][j].userselected)
				break;
			i++;
			j--;
		}
		if (j < 0)
			userbingo++;
	}

	private void combingo() {
		int i, j;
		combingo = 0;// 초기화
		// 가로줄 빙고
		for (i = 0; i < m.N; i++) {
			for (j = 0; j < m.N; j++) {
				if (!m.comboard[i][j].comselected)
					break;
			}
			if (j == m.N) {
				combingo++;
			}
		}

		// 세로줄 빙고
		for (i = 0; i < m.N; i++) {
			for (j = 0; j < m.N; j++) {
				if (!m.comboard[j][i].comselected)
					break;
			}
			if (j == m.N) {
				combingo++;
			}
		}

		// 대각선 빙고
		for (i = 0; i < m.N; i++) {
			if (!m.comboard[i][i].comselected)
				break;
		}
		if (i == m.N) {
			combingo++;
		}

		// 대각선 빙고2
		i = 0;
		j = m.N - 1;
		while (i < m.N) {
			if (!m.comboard[i][j].comselected)
				break;
			i++;
			j--;
		}
		if (j < 0) {
			combingo++;
		}
	}

	private void compare() {
		String str = "";
		double winrate = 0.0;
		if (userbingo == combingo) {
			if (userbingo == m.N*2+2) {
				str += "게임종료. 무승부입니다.\n";
				draw++;
			}
		} else {
			if (userbingo > combingo) {
				str += "게임종료. 승리하였습니다.\n";
				win++;

			} else if (userbingo < combingo) {
				str += "게임종료. 패배하였습니다.\n";
				lose++;
			}
		}
		if ((userbingo == m.N*2+2 && combingo == m.N*2+2) || (userbingo != combingo)) {
			str += win + "승 " + draw + "무 " + lose + "패 \n";
			winrate = (win + draw * 0.5) / (win + draw + lose) * 100;
			str += "승률 : " + winrate + "%";
			JOptionPane.showMessageDialog(null, str, "GAMEOVER", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void userselect(String str) {
		Color bg = new Color(255, 204, 255);
		// userlabel
		for (int i = 0; i < userlb.length; i++) {
			for (int j = 0; j < userlb[i].length; j++) {
				if (str.equals(userlb[i][j].getText())) {
					if (userlb[i][j].getBackground() == Color.WHITE) {
						for (Word[] w : m.userboard) {
							for (Word ww : w) {
								if (ww.eng.equals(str)) {
									ww.userselected = true;
									break;
								}
							}
						}
						userlb[i][j].setText(userlb[i][j].getText() + "(O)");
						userlb[i][j].setBackground(bg);
						break;
					}
				}
			}
		}

		// computer label
		for (int i = 0; i < comlb.length; i++) {
			for (int j = 0; j < comlb[i].length; j++) {
				if (str.equals(comlb[i][j].getText())) {
					if (comlb[i][j].getBackground() == Color.WHITE) {
						for (Word[] w : m.comboard) {
							for (Word ww : w) {
								if (ww.eng.equals(str)) {
									ww.comselected = true;
									break;
								}
							}
						}
						comlb[i][j].setText(comlb[i][j].getText() + "(O)");
						comlb[i][j].setBackground(bg);
						break;
					}
				}
			}
		}
	}

	private void initpanel1() {
		// paint userboard
		frame.add(panel1, BorderLayout.CENTER);
	}

	private void initpanel3() {
		// computer board button, com/user both open button, textarea, select textfield
		panel3 = new JPanel();
		panel3tf = new JPanel();
		panel3btn = new JPanel();
		bothboard = new JPanel();
		user = new JButton("USER");
		user.addActionListener(this);
		com = new JButton("COM");
		com.addActionListener(e -> {
			initpanel2();
		});
		open = new JButton("OPEN");
		open.addActionListener(this);
		restart = new JButton("RESTART");
		restart.addActionListener(this);

		ta = new JTextArea(5, 30);
		tf = new JTextField(20);
		tf.addActionListener(this);
		select = new JLabel("SELECT");
		panel3tf.add(select);
		panel3tf.add(tf);
		panel3.setLayout(new BorderLayout());
		panel3btn.setLayout(new GridLayout(2, 2, 3, 3));
		panel3btn.add(user);
		panel3btn.add(com);
		panel3btn.add(open);
		panel3btn.add(restart);
		panel3.add(panel3btn, BorderLayout.EAST);
		panel3.add(new JScrollPane(ta), BorderLayout.CENTER);
		panel3.add(panel3tf, BorderLayout.NORTH);
		frame.add(panel3, BorderLayout.SOUTH);
	}

	private void initboard() {
		// paint the board
		m.startgame();

		// user bingo board
		userlb = new JLabel[m.N][m.N];
		panel1.setLayout(new GridLayout(m.N, m.N));
		Border b = LineBorder.createGrayLineBorder();
		for (int i = 0; i < m.userboard.length; i++) {
			for (int j = 0; j < m.userboard[i].length; j++) {
				String text = m.userboard[i][j].eng;
				userlb[i][j] = new JLabel(text);
				userlb[i][j].setHorizontalAlignment(JLabel.CENTER);
				userlb[i][j].setBorder(b);
				userlb[i][j].setOpaque(true);
				userlb[i][j].setBackground(Color.WHITE);
				panel1.add(userlb[i][j]);
			}
		}
		panel1.revalidate();
		panel1.repaint();

		// computer bingo board
		comlb = new JLabel[m.N][m.N];
		panel2.setLayout(new GridLayout(m.N, m.N));
		for (int i = 0; i < m.comboard.length; i++) {
			for (int j = 0; j < m.comboard[i].length; j++) {
				String text = m.comboard[i][j].eng;
				comlb[i][j] = new JLabel(text);
				comlb[i][j].setHorizontalAlignment(JLabel.CENTER);
				comlb[i][j].setBorder(b);
				comlb[i][j].setOpaque(true);
				comlb[i][j].setBackground(Color.WHITE);
				panel2.add(comlb[i][j]);
			}
		}
		panel2.revalidate();
		panel2.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == user) {
			frame.remove(panel2);
			frame.add(panel1, BorderLayout.CENTER);
			frame.revalidate();
			frame.repaint();
		}

		if (e.getSource() == open) {
			frame.remove(panel1);
			frame.remove(panel2);
			bothboard.setLayout(new GridLayout(1, 2, 5, 5));
			bothboard.add(panel1);
			bothboard.add(panel2);
			frame.add(bothboard, BorderLayout.CENTER);
			this.setSize(new Dimension(800 + m.N * 50, 600));
			this.setLocationRelativeTo(null);
			frame.revalidate();
			frame.repaint();
			if (combingo == userbingo) {
				draw++;
				String str = "";
				double winrate = 0.0;
				str += "게임종료. 무승부입니다.\n";
				str += win + "승 " + draw + "무 " + lose + "패 \n";
				winrate = (win + draw * 0.5) / (win + draw + lose) * 100;
				str += "승률 : " + winrate + "%";
				JOptionPane.showMessageDialog(null, str, "GAMEOVER", JOptionPane.INFORMATION_MESSAGE);
			}
			selectInitial();
			panel1.removeAll();
			panel2.removeAll();
			frame.remove(bothboard);
			frame.add(panel1);
			frame.revalidate();
			frame.repaint();
			levelCB.setSelectedItem(null);
		}

		if (e.getSource() == restart) {
			ta.setText("");
			selectInitial();
			panel1.removeAll();
			panel2.removeAll();
			frame.revalidate();
			frame.repaint();
			levelCB.setSelectedItem(null);
			JOptionPane.showMessageDialog(null, "LEVEL을 선택하세요.");
			this.setSize(750, 600);
			this.setLocationRelativeTo(null);
		}

		if (e.getSource() == tf) {
			JTextField text = (JTextField) e.getSource();
			String str = text.getText();
			if (userlb != null && m.userboard != null) {
				userselect(str);
				for (Word[] w : m.userboard) {
					for (Word ww : w) {
						if (ww.eng.equals(str)) {
							ta.append(str + " (" + ww.kor + ") 를 선택하였습니다.\n");
						}
					}
				}
			}
			text.setText("");
			comselect();
			userbingo();
			combingo();
			compare();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Frame("202211341 이윤희");
	}
}