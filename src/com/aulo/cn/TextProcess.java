package com.aulo.cn;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

public class TextProcess extends JTextArea implements MouseListener {

	public TextProcess(String title,int rowNum,int columNum)
	{
		super(title,rowNum,columNum);
		
		init();
		
		this.addMouseListener(this);

	}

	public void action(ActionEvent event)
	{
		if(event.getActionCommand().equals(jMenuItem1.getText()))
		{
			copy();		//复制
		}
		
		else if (event.getActionCommand().equals(jMenuItem2.getText()))
		{
			cut();		//剪切
		}
		else if(event.getActionCommand().equals(jMenuItem3.getText()))
		{
			paste();  //粘贴
		}
	}
	
	public void init()
	{
		
		
		jMenuItem1  = new JMenuItem("复制");
		jMenuItem2 = new JMenuItem("剪切");
		jMenuItem3 = new JMenuItem("粘贴");
	
		jPopupMenu = new JPopupMenu();
		jPopupMenu.add(jMenuItem1);
		jPopupMenu.add(jMenuItem2);
		jPopupMenu.add(jMenuItem3);
		
	jMenuItem1.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent event)
		{
			action(event);
		}
		
	});
	jMenuItem2.addActionListener(new ActionListener()
	{
		
		public void actionPerformed(ActionEvent event)
		{
			action(event);
		}
	});
	jMenuItem3.addActionListener(new ActionListener()
	{
		
		public void actionPerformed(ActionEvent event)
		{
			action(event);
		}
	});
	
	this.add(jPopupMenu);
	}

	
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成方法存根

	}

	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成方法存根

	}

	public void mouseExited(MouseEvent e) {
		// TODO 自动生成方法存根

	}

	public void mousePressed(MouseEvent e) {

		// TODO 自动生成方法存根
		//System.out.println("press button");
		if(e.getButton() == MouseEvent.BUTTON3)   //判断是否右键点击
		{
			
			jMenuItem1.setEnabled(isCancopy());
			jMenuItem2.setEnabled(isCancopy());
			jMenuItem3.setEnabled(isClipBoardString());
			//init();
			//System.out.println("show");
			jPopupMenu.show(this, e.getX(), e.getY());  //在文本区域右键点击处弹出右键菜单
		}

	}

	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成方法存根

	}
	
	
	public boolean isClipBoardString()          //可否粘贴?
	{
		boolean b = false;

		   Clipboard clipboard = this.getToolkit().getSystemClipboard();
		   Transferable content = clipboard.getContents(this);
		   try {
		    if (content.getTransferData(DataFlavor.stringFlavor) instanceof String) {
		     b = true;
		    }
		   } catch (Exception e) {
		   }
		   return b;
		
	}
	
	public boolean isCancopy()   //能复制便能剪切
	{
		boolean b =false;
		
		int start = this.getSelectionStart();
		int end  = this.getSelectionEnd();
		if(start!=end)
		b = true;
		else
			 b =false;
		return b;
	}
	
	private JMenuItem jMenuItem1,jMenuItem2,jMenuItem3;
	private JPopupMenu jPopupMenu;

}

