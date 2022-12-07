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
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	Manager m = new Manager("이윤희");
	JLabel[][] label;
	JButton com, open;
	JMenuBar mb;
	JMenu menu;
	JMenuItem[] items;
	JScrollPane ta;
	JTextField tf;

	Frame(String title) {
		super(title);
		this.setSize(750, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initmenu();
		initpanel1();
		initpanel2();
		this.setVisible(true);
	}

	private void initpanel1() {
		// TODO Auto-generated method stub
		frame.add(panel1, BorderLayout.CENTER);
	}

	private void initpanel2() {
		// TODO Auto-generated method stub
		ta = new JScrollPane(new JTextArea(5, 30));
		panel2.add(ta);
		frame.add(panel2, BorderLayout.SOUTH);
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
		label = new JLabel[m.N][m.N];
		panel1.setLayout(new GridLayout(m.N, m.N));
		Border b = LineBorder.createGrayLineBorder();
		for (int i = 0; i < m.userboard.length; i++) {
			for (int j = 0; j < m.userboard[i].length; j++) {
				String text = m.userboard[i][j].eng;
				label[i][j] = new JLabel(text);
				label[i][j].setHorizontalAlignment(JLabel.CENTER);
				//label[i][j].setHorizontalTextPosition(JLabel.CENTER);
				//label[i][j].setVerticalTextPosition(JLabel.CENTER);
				label[i][j].setBorder(b);
				label[i][j].setOpaque(true);
				label[i][j].setBackground(Color.WHITE);
				panel1.add(label[i][j]);
			}
		}
		panel1.revalidate();
		panel1.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		for (int i = 0; i < items.length; i++) {
			if (e.getSource() == items[i]) {
				m.N = Integer.parseInt(items[i].getText());
				initboard();
				break;
			}
		}

		for (int i = 0; i < label.length; i++) {
			for (int j = 0; j < label[i].length; j++) {
				if (e.getSource() == label[i][j]) {
					Color bg = new Color(255, 204, 255);
					if (label[i][j].getBackground() == Color.WHITE) {
						label[i][j].setText(label[i][j].getText() + "(O)");
						label[i][j].setBackground(bg);
						break;
					}
					for (Word[] w : m.userboard) {
						for (Word ww : w) {
							if (ww.eng.equals(label[i][j].getText()))
								ww.selected = true;
						}
					}
					for (Word[] w : m.comboard) {
						for (Word ww : w) {
							if (ww.eng.equals(label[i][j].getText()))
								ww.selected = true;
						}
					}
				}
			}
		}
	}
}
