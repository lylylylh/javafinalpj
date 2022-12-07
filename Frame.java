package bingo_game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener {

	Container frame = this.getContentPane();
	JPanel panel1 = new JPanel(); // userboard
	JPanel panel2 = new JPanel(); // comboardtext, select, button
	JPanel panel3, panel3in; // text, select, button

	Manager m = new Manager("이윤희");
	JLabel[][] userlb, comlb; // userlabel, computerlabel
	JLabel select;
	JButton com, open;
	JMenuBar mb;
	JMenu menu;
	JMenuItem[] items;
	JTextArea ta;
	JTextField tf;
	int userbingo, combingo;

	Frame(String title) {
		super(title);
		this.setSize(750, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initmenu();
		initpanel1();
		initpanel3();
		this.setVisible(true);
	}

	private void initpanel2() {
		// TODO Auto-generated method stub
		frame.remove(panel1);
		frame.add(panel2, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();
	}

	private void comselect() {
		for (int i = 0; i < m.comboard.length; i++) {
			for (int j = 0; j < m.comboard[i].length; j++) {

			}
		}
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
			if (j == m.N)
				combingo++;
		}

		// 세로줄 빙고
		for (i = 0; i < m.N; i++) {
			for (j = 0; j < m.N; j++) {
				if (!m.comboard[j][i].comselected)
					break;
			}
			if (j == m.N)
				combingo++;
		}

		// 대각선 빙고
		for (i = 0; i < m.N; i++) {
			if (!m.comboard[i][i].comselected)
				break;
		}
		if (i == m.N)
			combingo++;

		// 대각선 빙고2
		i = 0;
		j = m.N - 1;
		while (i < m.N) {
			if (!m.comboard[i][j].comselected)
				break;
			i++;
			j--;
		}
		if (j < 0)
			combingo++;
	}

	private void compare() {
		if (userbingo != combingo) {
			System.out.println("게임종료");
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
		// TODO Auto-generated method stub
		frame.add(panel1, BorderLayout.CENTER);
	}

	private void initpanel3() {
		// TODO Auto-generated method stub
		panel3 = new JPanel();
		panel3in = new JPanel();
		com = new JButton("COM");
		com.addActionListener(e -> {
			initpanel2();
		});
		open = new JButton("OPEN");
		open.addActionListener(e -> {
			frame.remove(panel1);
			frame.remove(panel2);
			this.setSize(1000, 600);
			frame.add(panel1, BorderLayout.WEST);
			frame.add(panel2, BorderLayout.EAST);
			frame.revalidate();
			frame.repaint();
		});
		ta = new JTextArea(5, 30);
		tf = new JTextField(20);
		tf.addActionListener(this);
		select = new JLabel("SELECT");
		panel3in.add(select);
		panel3in.add(tf);
		panel3.setLayout(new BorderLayout());
		panel3.add(com, BorderLayout.WEST);
		panel3.add(open, BorderLayout.EAST);
		panel3.add(new JScrollPane(ta), BorderLayout.CENTER);
		panel3.add(panel3in, BorderLayout.NORTH);
		frame.add(panel3, BorderLayout.SOUTH);
	}

	private void initmenu() {
		// TODO Auto-generated method stub
		mb = new JMenuBar();
		menu = new JMenu("N x N");
		items = new JMenuItem[(int) Math.sqrt(m.list.size()) - 1];
		for (int i = 0; i < items.length; i++) {
			items[i] = new JMenuItem(Integer.toString(i + 2));
			menu.add(items[i]);
			items[i].addActionListener(this);
		}
		mb.add(menu);
		this.setJMenuBar(mb);
	}

	private void initboard() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub

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

		for (int i = 0; i < items.length; i++) {
			if (e.getSource() == items[i]) {
				m.N = Integer.parseInt(items[i].getText());
				initboard();
				break;
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Frame("202211341 이윤희");
	}
}