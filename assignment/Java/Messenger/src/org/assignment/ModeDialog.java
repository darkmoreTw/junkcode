package org.assignment;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 模式選擇對話框
 * @author legnaleurc
 */
public class ModeDialog extends JDialog {

	private static final long serialVersionUID = -684903256850725954L;
	
	/**
	 * 顯示對話框並讓使用者選擇 server/client 模式
	 * @return host name 和 port number
	 * 
	 * 若 host 為 null 即為 server 模式，若是 null 物件代表使用者按下 cancel。
	 */
	public static URI getSelection() {
		ModeDialog md = new ModeDialog();
		md.setVisible(true);
		if( md.apply_ ) {
			return md.getURI();
		} else {
			return null;
		}
	}
	
	public ModeDialog() {
		super((java.awt.Frame)null, "Select connection mode", true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JRadioButton serverMode = new JRadioButton("Server listening port:");
		serverMode.setActionCommand("server");
		serverMode.setSelected(true);
		JRadioButton clientMode = new JRadioButton("Client connect to:");
		clientMode.setActionCommand("client");
		
		this.choise_ = new ButtonGroup();
		this.choise_.add(serverMode);
		this.choise_.add(clientMode);
		
		this.serverPort_ = new JTextField();
		this.clientHost_ = new JTextField( "localhost" );
		this.clientHost_.setColumns(32);
		this.clientPort_ = new JTextField();
		this.clientPort_.setColumns(8);
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apply_ = true;
				setVisible(false);
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				apply_ = false;
				setVisible(false);
			}
		});
		
		this.getContentPane().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.err.println("lasdjfoi");
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					apply_ = true;
					setVisible(false);
				}
			}
		});
		
		// layout
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel tmp = null;
		
		tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
		tmp.add(serverMode);
		tmp.add(this.serverPort_);
		this.add(tmp);
		
		tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
		tmp.add(clientMode);
		tmp.add(this.clientHost_);
		tmp.add(new JLabel(":"));
		tmp.add(this.clientPort_);
		this.add(tmp);
		
		tmp = new JPanel();
		tmp.setLayout(new BoxLayout(tmp, BoxLayout.X_AXIS));
		tmp.add(ok);
		tmp.add(cancel);
		this.add(tmp);
		
		this.pack();
	}
	
	private URI getURI() {
		String command = this.choise_.getSelection().getActionCommand();
		if( command == "server" ) {
			return new URI( null, Integer.parseInt(this.serverPort_.getText()) );
		} else if( command == "client" ) {
			return new URI( this.clientHost_.getText(), Integer.parseInt(this.clientPort_.getText()) );
		} else {
			return null;
		}
	}
	
	private ButtonGroup choise_;
	private JTextField serverPort_;
	private JTextField clientHost_;
	private JTextField clientPort_;
	private boolean apply_;

}
