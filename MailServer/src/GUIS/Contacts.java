package GUIS;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class Contacts extends JFrame {

	private JPanel contentPane;
	private JButton btnAddContact;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Contacts frame = new Contacts();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Contacts() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 604, 369);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRemoveContact = new JButton("Remove Selected Contact");
		btnRemoveContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRemoveContact.setBounds(319, 204, 194, 44);
		contentPane.add(btnRemoveContact);
		
		btnAddContact = new JButton("Add Contact");
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContactInfo.main(null);
			}
		});
		btnAddContact.setBounds(351, 92, 137, 44);
		contentPane.add(btnAddContact);
		
		JLabel lblNewLabel_1 = new JLabel("My Contacts");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(49, 8, 147, 43);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPaneContactList = new JScrollPane();
		scrollPaneContactList.setBounds(22, 62, 174, 265);
		contentPane.add(scrollPaneContactList);
		
		JList contactList = new JList();
		contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPaneContactList.setViewportView(contactList);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Contacts.class.getResource("/Images/86c289b40b6ed48055a16ce9f168822c.jpg")));
		lblNewLabel.setBounds(0, 0, 600, 340);
		contentPane.add(lblNewLabel);
	}
}
