package org.foolproofproject.picking.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;


public class Preference extends JDialog {

	private static final long serialVersionUID = -3910072899938489788L;
	private JComboBox unit;
	private NeturalField limit;
	private JCheckBox debug;
	private MainWindow parent;
	private JCheckBox hidden;
	
	public Preference( MainWindow window ) {
		super( window );
		
		parent = window;
		setTitle( "Preferences" );
		
		Container pane = getContentPane();
		pane.setLayout( new BoxLayout( pane, BoxLayout.Y_AXIS ) );
		
		JPanel limits = new JPanel();
		pane.add( limits );
		limits.setLayout( new GridLayout( 1, 2 ) );
		limits.setBorder( BorderFactory.createTitledBorder( "Limit" ) );
		
		limit = new NeturalField();
		limits.add( limit );
		
		unit = new JComboBox();
		limits.add( unit );
		unit.addItem( "B" );
		unit.addItem( "KB" );
		unit.addItem( "MB" );
		unit.addItem( "GB" );
		
		JPanel misc = new JPanel();
		pane.add( misc );
		misc.setLayout( new GridLayout( 2, 1 ) );
		misc.setBorder( BorderFactory.createTitledBorder( "Miscellaneous" ) );
		
		hidden = new JCheckBox( "View Hidden Files" );
		misc.add( hidden );
		hidden.setSelected( (Boolean) Configuration.get( "hidden" ) );
		debug = new JCheckBox( "Log debug information" );
		misc.add( debug );
		debug.setSelected( (Boolean) Configuration.get( "debug" ) );
		
		JPanel buttons = new JPanel();
		pane.add( buttons );
		buttons.setLayout( new GridLayout( 1, 3 ) );
		
		JButton ok = new JButton( "OK" );
		buttons.add( ok );
		ok.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toConf();
				parent.read();
				setVisible( false );
			}
		} );
		JButton apply = new JButton( "Apply" );
		buttons.add( apply );
		apply.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toConf();
				parent.read();
			}
		} );
		JButton cancel = new JButton( "Cancel" );
		buttons.add( cancel );
		cancel.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible( false );
			}
		} );
		
		pack();
		setLocationRelativeTo( parent );
	}
	
	public void exec( Long size, int index, boolean hidden ) {
		limit.setText( size.toString() );
		unit.setSelectedIndex( index );
		this.hidden.setSelected( hidden );
		setVisible( true );
	}
	
	private void toConf() {
		Configuration.set( "limit", limit.toLong() );
		Configuration.set( "unit", unit.getSelectedIndex() );
		Configuration.set( "debug", debug.isSelected() );
		Configuration.set( "hidden", false );
	}

}