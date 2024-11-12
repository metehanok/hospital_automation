package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import Helper.Helper;
import Model.Hasta;
import Model.User;
public class RegisterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_name;
	private JTextField fld_tcno;
	private JPasswordField fld_password;
	private Hasta hasta=new Hasta();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 384);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblAdSoyad = new JLabel("Ad Soyad");
		lblAdSoyad.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblAdSoyad.setBounds(10, 22, 98, 30);
		w_pane.add(lblAdSoyad);
		
		fld_name = new JTextField();
		fld_name.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_name.setColumns(10);
		fld_name.setBounds(10, 52, 266, 30);
		w_pane.add(fld_name);
		
		JLabel lblTcNo = new JLabel("TC No");
		lblTcNo.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblTcNo.setBounds(10, 93, 98, 30);
		w_pane.add(lblTcNo);
		
		fld_tcno = new JTextField();
		fld_tcno.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_tcno.setColumns(10);
		fld_tcno.setBounds(10, 121, 266, 30);
		w_pane.add(fld_tcno);
		
		JLabel lblifre = new JLabel("Şifre");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblifre.setBounds(10, 162, 98, 30);
		w_pane.add(lblifre);
		
		fld_password = new JPasswordField();
		fld_password.setBounds(10, 203, 266, 30);
		w_pane.add(fld_password);
		
		JButton btn_hastakayit = new JButton("Kayıt Ol");
		btn_hastakayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_tcno.getText().length()==0 || fld_password.getText().length()==0 || fld_name.getText().length()==0) {
					Helper.showMessage("fill");
				}
				else {
					try {
						boolean control=hasta.register(fld_tcno.getText(), fld_name.getText(), fld_password.getText());
						if(control) {
							Helper.showMessage("success");
							LoginGUI login=new LoginGUI();
							login.setVisible(true);
							dispose(); 
							
						}else {
							Helper.showMessage("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_hastakayit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_hastakayit.setBounds(10, 244, 266, 38);
		w_pane.add(btn_hastakayit);
		
		JButton btn_geridon = new JButton("Geri Dön");
		btn_geridon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login=new LoginGUI();
				login.setVisible(true);
				dispose(); 
			}
		});
		btn_geridon.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_geridon.setBounds(10, 298, 266, 38);
		w_pane.add(btn_geridon);
	}
}
