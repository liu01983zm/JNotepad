package com.aulo;
//java记事本 
import java.io.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
//import javax.swing.JPopupMenu;
//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.Transferable;
import javax.*;
import javax.swing.undo.*;
import javax.swing.text.*;
import com.aulo.*;
import com.aulo.cn.TextProcess;

public class NotePadFrame {
	public static void main(String args[]) {
		new TextPad();
	}
}

class TextPad extends JFrame implements ActionListener {
	String openname;
	JTextArea jta = new TextProcess("", 18, 52); // ====================//
	JCheckBoxMenuItem mto1 = new JCheckBoxMenuItem("自动换行", true);
	String ss1 = jta.getText(); // ======***** ss1 *****=========//
	UndoableEditListener ue = new UndoHander();
	UndoManager undo = new UndoManager();
	int StartFindPos = 0, a = 0, b = 0;
	GridBagConstraints gbc = new GridBagConstraints();

	public TextPad() {

		this.setTitle("记事本");
		this.setLocation(180, 100);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		JPanel jp = new JPanel(); // 面板
		JScrollPane jsp = new JScrollPane(jta);// 滚动条
		jp.add(jsp);
		JMenu mf = new JMenu("文件(F)");
		JMenuItem mtf1 = new JMenuItem("新建");
		mtf1.addActionListener(this);
		JMenuItem mtf2 = new JMenuItem("打开");
		mtf2.addActionListener(this);
		JMenuItem mtf3 = new JMenuItem("保存");
		mtf3.addActionListener(this);
		JMenuItem mtf4 = new JMenuItem("另存为");
		mtf4.addActionListener(this);
		JMenuItem mtf5 = new JMenuItem("退出");
		mtf5.addActionListener(this);
		JMenu me = new JMenu("编辑(E)");
		JMenuItem mte1 = new JMenuItem("撤消");
		mte1.addActionListener(this);
		jta.getDocument().addUndoableEditListener(ue);
		// if(undo.canUndo())
		// {
		// mte1.setEnabled(false);
		// }
		JMenuItem mte2 = new JMenuItem("剪切");
		mte2.addActionListener(this);
		JMenuItem mte3 = new JMenuItem("复制");
		mte3.addActionListener(this);
		JMenuItem mte4 = new JMenuItem("粘贴");
		mte4.addActionListener(this);
		JMenuItem mte6 = new JMenuItem("查找");
		mte6.addActionListener(this);
		JMenuItem mte8 = new JMenuItem("替换");
		mte8.addActionListener(this);
		JMenuItem mte10 = new JMenuItem("全选");
		mte10.addActionListener(this);
		JMenuItem mte11 = new JMenuItem("日期/时间");
		mte11.addActionListener(this);

		JMenu mo = new JMenu("格式(O)");
		mto1.addActionListener(this);
		JMenuItem mto2 = new JMenuItem("字体");
		mto2.addActionListener(this);

		JMenuItem mto3 = new JMenuItem("统计汉字个数"); // aulo add
		mto3.addActionListener(this);
		JMenuItem mto4 = new JMenuItem("转化为U码"); // aulo add
		mto4.addActionListener(this);
		JMenuItem mto5 = new JMenuItem("转化为U码2"); // aulo add
		mto5.addActionListener(this);
		JMenuItem mto6 = new JMenuItem("U码转化为字符串"); // aulo add
		mto6.addActionListener(this);
		JMenuItem mto7 = new JMenuItem("显示汉字拼音"); // aulo add
		mto7.addActionListener(this);

		JMenu mv = new JMenu("查看(V)");
		JMenuItem mtv1 = new JMenuItem("状态栏");
		mtv1.setEnabled(false);

		JMenu mh = new JMenu("帮助(H)");
		JMenuItem mth1 = new JMenuItem("关于记事本");
		mth1.addActionListener(this);
		JMenuBar mb = new JMenuBar();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		mb.add(mf);
		mb.add(me);
		mb.add(mo);
		mb.add(mv);
		mb.add(mh);
		this.setJMenuBar(mb);
		mf.add(mtf1);
		mf.add(mtf2);
		mf.add(mtf3);
		mf.add(mtf4);
		mf.addSeparator();
		mf.add(mtf5);
		me.add(mte1);
		me.addSeparator();
		me.add(mte2);
		me.add(mte3);
		me.add(mte4);
		me.addSeparator();
		me.add(mte6);
		me.add(mte8);
		me.addSeparator();
		me.add(mte10);
		me.add(mte11);
		mo.add(mto1);
		mo.add(mto2);
		mo.add(mto3);// aulo add
		mo.addSeparator();
		mo.add(mto4);// aulo add
		mo.add(mto5);// aulo add
		mo.add(mto6);// aulo add
		mo.add(mto7);// aulo add

		mv.add(mtv1);
		mh.add(mth1);
		this.getContentPane().add(jsp);
		this.setSize(600, 400);
		this.setResizable(true);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("打开")) {
			try {
				Frame f = new Frame();
				FileDialog fd = new FileDialog(f, "打开文件", FileDialog.LOAD);
				fd.setVisible(true);
				String fpath = fd.getDirectory();
				String fname = fd.getFile();
				openname = fpath + fname;
				BufferedReader br = new BufferedReader(new FileReader(openname));
				jta.setText("");
				String s = br.readLine();
				while (s != null) {
					jta.append(s + "\n");
					s = br.readLine();
				}
				br.close();
			} catch (Exception ex) {
			}
		}
		if (e.getActionCommand().equals("保存")) {
			String savename = openname;
			try {

				if (savename != null) {
					PrintWriter pw = new PrintWriter(new BufferedWriter(
							new FileWriter(savename)));
					pw.write(jta.getText(), 0, jta.getText().length());
					pw.flush();
				} else {
					Frame f = new Frame("保存");
					FileDialog fd = new FileDialog(f, "文件另存为", FileDialog.SAVE);
					fd.setVisible(true);
					try {
						String savepath = fd.getDirectory();
						savename = fd.getFile();
						if (savename != null) {
							PrintWriter pw = new PrintWriter(
									new BufferedWriter(new FileWriter(savepath
											+ savename)));
							pw.write(jta.getText(), 0, jta.getText().length());
							pw.flush();
						}
					} catch (Exception esave) {
					}
				}

			} catch (Exception esave) {
			}
		}
		if (e.getActionCommand().equals("新建")) {
			jta.setText("");
			openname = null;
		}
		if (e.getActionCommand().equals("另存为")) {
			Frame f = new Frame("保存");
			FileDialog fd = new FileDialog(f, "文件另存为", FileDialog.SAVE);
			fd.setVisible(true);
			try {
				String savepath = fd.getDirectory();
				String savename = fd.getFile();
				if (savename != null) {
					PrintWriter pw = new PrintWriter(new BufferedWriter(
							new FileWriter(savepath + savename)));
					pw.write(jta.getText(), 0, jta.getText().length());
					pw.flush();
				}
			} catch (Exception esave) {
			}
		}
		if (e.getActionCommand().equals("退出")) {
			String ss2 = jta.getText();
			if (!ss1.equals(ss2)) {
				System.out.println("File is changed.");
			}
			System.exit(0);
		}
		if (e.getActionCommand().equals("撤消")) {
			try {
				undo.undo();
			} catch (Exception eundo) {
			}
		}
		if (e.getActionCommand().equals("剪切")) {
			jta.cut();
		}
		if (e.getActionCommand().equals("复制")) {
			jta.copy();
		}
		if (e.getActionCommand().equals("粘贴")) {
			jta.paste();
		}
		if (e.getActionCommand().equals("删除")) {
		}
		if (e.getActionCommand().equals("全选")) {
			jta.selectAll();
		}
		if (e.getActionCommand().equals("查找")) {
			try {
				final JDialog jd = new JDialog(this, "查找", true);
				GridBagLayout gbl = new GridBagLayout();
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.weightx = 0.5;
				gbc.weighty = 0.5;
				gbc.gridwidth = 1;
				gbc.gridheight = 1;
				jd.getContentPane().setLayout(gbl);
				jd.setSize(380, 100);
				jd.setResizable(false);
				final JTextField jtf = new JTextField(15);
				JLabel jlFind = new JLabel("查找内容:");
				jd.getContentPane().add(jlFind);
				JButton jbFind = new JButton("查找");

				jbFind.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent efind) {
						String strA = jta.getText();
						String strB = jtf.getText();
						if (a >= 0) {
							a = strA.indexOf(strB, StartFindPos);
							b = strB.length();
							StartFindPos = a + b;
							if (a == -1) {
								JOptionPane.showMessageDialog(null,
										"没有您要查找的信息", "查找结果", 1);
								a = 0;
								StartFindPos = 0;
							}
							jta.select(a, StartFindPos);
						}
					}
				});
				JButton jbCancel = new JButton("取消");
				jbCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ejb) {
						jd.dispose();
					}
				});
				jd.getContentPane().add(jtf);
				jd.getContentPane().add(jbFind);
				jd.getContentPane().add(jbCancel);
				jd.setLocation(240, 200);
				jd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				jd.setVisible(true);
			} catch (Exception efind) {
			}
		}
		if (e.getActionCommand().equals("替换")) {
			final JDialog jd = new JDialog(this, "替换", true);
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			JLabel jlFind = new JLabel("查找:");
			JLabel jp = new JLabel("替换内容:");
			final JTextField jtf = new JTextField(15);
			final JTextField jtf1 = new JTextField(15);
			jd.getContentPane().setLayout(gbl);
			jd.setSize(330, 150);
			jd.setResizable(false);
			final JButton jbReplace = new JButton("替换");
			final JButton jbReplaceAll = new JButton("替换所有");
			final JButton jbCancel = new JButton("取消");
			final JButton jbFind = new JButton("查找");
			gbc.gridx = 0;
			gbc.gridy = 0;
			jd.getContentPane().add(jlFind, gbc);
			gbc.gridx = 1;
			gbc.gridy = 0;
			jd.getContentPane().add(jtf1, gbc);
			gbc.gridx = 2;
			gbc.gridy = 0;
			jd.getContentPane().add(jbFind, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			jd.getContentPane().add(jp, gbc);
			gbc.gridx = 1;
			gbc.gridy = 1;
			jd.getContentPane().add(jtf, gbc);
			gbc.gridx = 2;
			gbc.gridy = 1;
			jd.getContentPane().add(jbReplace, gbc);
			gbc.gridx = 2;
			gbc.gridy = 2;
			jd.getContentPane().add(jbReplaceAll, gbc);
			gbc.gridx = 2;
			gbc.gridy = 3;
			jd.getContentPane().add(jbCancel, gbc);
			jbFind.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent efind) {
					String strA = jta.getText();
					String strB = jtf1.getText();
					if (a >= 0) {
						a = strA.indexOf(strB, StartFindPos);
						b = strB.length();
						StartFindPos = a + b;
						if (a == -1) {
							JOptionPane.showMessageDialog(null, "没有您要查找的信息",
									"查找结果", 1);
							a = 0;
							StartFindPos = 0;
						}
						jta.select(a, StartFindPos);
					}
				}
			});
			jbReplace.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("替换")) {
						String strRepleace = jtf.getText();
						jta.replaceSelection(strRepleace);
					}
				}
			});
			jbReplaceAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					while (a > -1) {
						String strA = jta.getText();
						String strB = jtf1.getText();
						a = strA.indexOf(strB, StartFindPos);
						if (a == -1) {
							break;
						}
						b = strB.length();
						StartFindPos = a + b;
						jta.select(a, StartFindPos);
						String strRepleaceAll = jtf.getText();
						jta.replaceSelection(strRepleaceAll);
						StartFindPos = a + b;
					}
					JOptionPane.showMessageDialog(null, "全部替换完毕", "替换内容", 1);
					a = 0;
					StartFindPos = 0;
				}
			});
			jbCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ejb) {
					jd.dispose();
				}
			});
			jd.setLocation(240, 200);
			jd.setVisible(true);
			jd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		if (e.getActionCommand().equals("日期/时间")) {
			final JDialog jd = new JDialog(this, "插入日期");
			JPanel jp1 = new JPanel();
			jp1.setLayout(new FlowLayout(FlowLayout.LEFT));
			final JTextField jtf = new JTextField(10);
			JButton jbOK = new JButton("确定");
			JButton jbCancel = new JButton("取消");
			jp1.add(jtf);
			jp1.add(jbOK);
			jp1.add(jbCancel);
			jd.getContentPane().add(jp1, "North");
			JPanel jp2 = new JPanel();
			jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
			final JCheckBox jcb1 = new JCheckBox("格式一");
			final JCheckBox jcb2 = new JCheckBox("格式二");
			final JCheckBox jcb3 = new JCheckBox("格式三");
			jp2.add(jcb1);
			jp2.add(jcb2);
			jp2.add(jcb3);
			jd.getContentPane().add(jp2, "Center");
			jd.setSize(220, 120);
			jd.setResizable(false);
			jd.setLocation(240, 200);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cl = Calendar.getInstance();
			DateFormat df = DateFormat.getInstance();
			final String sdate = df.format(cl.getTime());
			jcb1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("格式一")) {
						if (jcb1.isSelected()) {
							try {
								SimpleDateFormat sdf1 = new SimpleDateFormat(
										"yy年MM月dd日");
								Date d = sdf.parse(sdate);
								jtf.setText(sdf1.format(d));
								jcb2.setEnabled(false);
								jcb3.setEnabled(false);
							} catch (Exception estyle1) {
								estyle1.printStackTrace();
							}
						} else {
							jcb2.setEnabled(true);
							jcb3.setEnabled(true);
						}
						try {
							System.out.println(jta.getLineStartOffset(3));
						} catch (Exception eee) {
						}
					}
				}
			});
			jcb2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("格式二")) {
						if (jcb2.isSelected()) {
							try {
								SimpleDateFormat sdf1 = new SimpleDateFormat(
										"yy/MM/dd");
								Date d = sdf.parse(sdate);
								jtf.setText(sdf1.format(d));
								jcb1.setEnabled(false);
								jcb3.setEnabled(false);
							} catch (Exception estyle2) {
								estyle2.printStackTrace();
							}
						} else {
							jcb1.setEnabled(true);
							jcb3.setEnabled(true);
						}
					}
				}
			});
			jcb3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("格式三")) {
						if (jcb3.isSelected()) {
							jtf.setText(sdate);
							jcb1.setEnabled(false);
							jcb2.setEnabled(false);
						} else {
							jcb1.setEnabled(true);
							jcb2.setEnabled(true);
						}
					}
				}
			});
			jbOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand().equals("确定")) {
						int pos = jta.getCaretPosition();
						jta.insert(jtf.getText(), pos);
						// jd.dispose();
					}
					jd.dispose();
				}
			});
			jbCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ejb) {
					jd.dispose();
				}
			});
			jd.setVisible(true);
			jd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		if (e.getActionCommand().equals("自动换行")) {
			if (mto1.getState()) {
				jta.setLineWrap(true);
				jta.setWrapStyleWord(true);
			} else {
				jta.setLineWrap(false);
				jta.setWrapStyleWord(false);
			}
		}
		if (e.getActionCommand().equals("字体")) {
			final JDialog jd = new JDialog(this, "字体设置");
			jd.setLocation(240, 200);
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			JButton jbOK = new JButton("确定");
			JButton jbCancel = new JButton("取消");
			JTextField jtf1 = new JTextField(6);
			final JTextArea jtaview = new JTextArea(4, 8);
			final JTextField jtf2 = new JTextField(6);
			final JTextField jtf3 = new JTextField(3);
			JComboBox jcb1 = new JComboBox();
			final JComboBox jcb2 = new JComboBox();
			jcb2.addItem("BOLD");
			jcb2.addItem("ITALIC");
			jcb2.addItem("PLAIN");
			jcb2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jtf2.setText((String) jcb2.getSelectedItem());
				}
			});
			final JComboBox jcb3 = new JComboBox();
			jcb3.addItem("14");
			jcb3.addItem("18");
			jcb3.addItem("22");
			jcb3.addItem("26");
			jcb3.addItem("30");
			jcb3.addItem("34");
			jcb3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jtf3.setText((String) jcb3.getSelectedItem());
				}
			});
			gbc.gridheight = 1;
			gbc.gridwidth = 1;
			gbc.weightx = 0.5;
			gbc.weighty = 0.5;
			jd.getContentPane().setLayout(gbl);
			JLabel jl2 = new JLabel("字型:");
			JLabel jl3 = new JLabel("大小:");
			gbc.gridx = 0;
			gbc.gridy = 0;
			jd.getContentPane().add(jl2, gbc);
			gbc.gridx = 2;
			gbc.gridy = 0;
			jd.getContentPane().add(jl3, gbc);
			gbc.gridx = 0;
			gbc.gridy = 1;
			jd.getContentPane().add(jtf2, gbc);
			gbc.gridx = 2;
			gbc.gridy = 1;
			jd.getContentPane().add(jtf3, gbc);
			gbc.gridx = 0;
			gbc.gridy = 2;
			jd.getContentPane().add(jcb2, gbc);
			gbc.gridx = 2;
			gbc.gridy = 2;
			jd.getContentPane().add(jcb3, gbc);
			gbc.gridx = 4;
			gbc.gridy = 1;
			jd.getContentPane().add(jbOK, gbc);
			gbc.gridx = 4;
			gbc.gridy = 2;
			jd.getContentPane().add(jbCancel, gbc);
			jbOK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (jtf2.getText().equals("PLAIN")) {
						int fontsize = Integer.parseInt(jtf3.getText());
						int fontstyle = 0;
						Font f = new Font("字体设置", fontstyle, fontsize);
						jta.setFont(f);
					}
					if (jtf2.getText().equals("BOLD")) {
						int fontsize = Integer.parseInt(jtf3.getText());
						int fontstyle = 1;
						Font f = new Font("字体设置", fontstyle, fontsize);
						jta.setFont(f);
					}
					if (jtf2.getText().equals("ITALIC")) {
						int fontsize = Integer.parseInt(jtf3.getText());
						int fontstyle = 2;
						Font f = new Font("字体设置", fontstyle, fontsize);
						jta.setFont(f);
					}
					jd.dispose();
				}
			});
			jbCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jd.dispose();
				}
			});
			jd.setSize(200, 120);
			jd.setResizable(false);
			jd.setVisible(true);
			jd.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		if (e.getActionCommand().equals("转化为U码"))// aulo add//==mto3
		{
			try {
				ss1 = jta.getText();
				String unicodeTmp = Encoding2.toFormatUnicode(ss1); // toFormatUnicode(ss1);
				jta.setText(unicodeTmp);
			} catch (Exception ex) {
				// System.out.println("Exception :"+ex);
				jta.setText("Exception :" + ex);
			}
		}
		if (e.getActionCommand().equals("统计汉字个数"))// aulo add//==mto4
		{
			try {
				ss1 = jta.getText();
				String chineseCharNum = Encoding2.getChineseChar(ss1);
				jta.setText(chineseCharNum);
			} catch (Exception ex) {
				// System.out.println("Exception :"+ex);
				jta.setText("Exception :" + ex);
			}
		}
		if (e.getActionCommand().equals("转化为U码2"))// aulo add //==mto5
		{
			try {
				ss1 = jta.getText();
				String unicodeTmp = Encoding2.toUnicode(ss1, true);// toUnicode(ss1,true);
				jta.setText(unicodeTmp);
			} catch (Exception ex) {
				// System.out.println("Exception :"+ex);
				jta.setText("Exception :" + ex);
			}
		}
		if (e.getActionCommand().equals("U码转化为字符串"))// aulo add //==mto6
		{
			try {
				ss1 = jta.getText();
				String unicodeTmp = Encoding2.unicodeToStr(ss1);// unicodeToStr(ss1);
				jta.setText(unicodeTmp);
			} catch (Exception ex) {
				// System.out.println("Exception :"+ex);
				jta.setText("Exception :" + ex);
			}
		}
		if (e.getActionCommand().equals("显示汉字拼音"))// aulo add //==mto7
		{
			try {
				ss1 = jta.getText();
				String pingyinTmp = CnToSpell.getFullSpell(ss1);
				jta.setText(pingyinTmp);
			} catch (Exception ex) {
				// System.out.println("Exception :"+ex);
				jta.setText("Exception :" + ex);
			}
		}

		if (e.getActionCommand().equals("关于记事本")) {
			JOptionPane jop = new JOptionPane(null,
					JOptionPane.INFORMATION_MESSAGE);
			jop.showMessageDialog(null,
					"JAVA记事本 \nAuthor: Jimmy Liu \nQQ:279212591", "关于记事本",
					JOptionPane.OK_OPTION);

			// showMessageDialog(Component parentComponent, Object message,
			// String title, int messageType)
		}

	}

	class UndoHander implements UndoableEditListener {
		public void undoableEditHappened(UndoableEditEvent eundo) {
			undo.addEdit(eundo.getEdit());
		}
	}

	private static char toHex(int nibble) {
		return hexDigit[(nibble & 0xF)];
	}

	private static final char[] hexDigit = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

}
