package View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doktor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_hastatc;
	private JTextField fld_hastapass;
	private JTextField fld_perstc;
	private JPasswordField fld_perspassword;
	private DBConnection conn=new DBConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Otomasyonu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 546, 445);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(255, 255, 255));
		w_pane.setToolTipText("");
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("otomasyon.png")));
		lbl_logo.setBounds(177, 11, 136, 121);
		w_pane.add(lbl_logo);
		
		JLabel lblNewLabel = new JLabel("Hastane Yönetim Sistemine Hoşgeldiniz");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 22));
		lblNewLabel.setBounds(54, 128, 449, 30);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tabbedpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedpane.setBounds(10, 169, 493, 228);
		w_pane.add(w_tabbedpane);
		
		JPanel w_personellogin = new JPanel();
		w_personellogin.setBackground(new Color(255, 255, 255));
		w_tabbedpane.addTab("Doktor Giriş", null, w_personellogin, null);
		w_personellogin.setLayout(null);
		
		JLabel lblTcNumaras_1 = new JLabel("T.C Numarası:");
		lblTcNumaras_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 22));
		lblTcNumaras_1.setBounds(10, 11, 146, 30);
		w_personellogin.add(lblTcNumaras_1);
		
		fld_perstc = new JTextField();
		fld_perstc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_perstc.setColumns(10);
		fld_perstc.setBounds(154, 11, 301, 30);
		w_personellogin.add(fld_perstc);
		
		JLabel lblifre_1 = new JLabel("Şifre:");
		lblifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 22));
		lblifre_1.setBounds(10, 52, 146, 30);
		w_personellogin.add(lblifre_1);
		
		JButton btn_persgiris = new JButton("Giriş Yap");
		btn_persgiris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_perstc.getText().length()==0 || fld_perspassword.getText().length()==0) {
					Helper.showMessage("fill");
				}
				else {
					
					try {
						Connection con = conn.connDb();
						Statement st=con.createStatement();
						ResultSet rs=st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fld_perstc.getText().equals(rs.getString("tcno"))&& fld_perspassword.getText().equals(rs.getString("password")));
							//nesneyi veritabanında ki sütunlara göre javada oluşturduk
							if(rs.getString("type").equals("bashekim")) {
								//buradan itibaren bashekimguiye ait tasarıma geçtik
								Bashekim bhekim=new Bashekim();
								bhekim.setId(rs.getInt("id"));//
								bhekim.setPasword("password");
								bhekim.setName(rs.getString("name"));
								bhekim.setTcno(rs.getString("tcno"));
								bhekim.setType(rs.getString("type"));
								//BashekimGUI bGUI=new BashekimGUI(bhekim);
								bashekimGUI1 bGUI=new bashekimGUI1(bhekim);
								bGUI.setVisible(true);
								dispose();
							}
							if(rs.getString("type").equals("doktor")) {
								Doktor doktor=new Doktor();
								doktor.setId(rs.getInt("id"));//
								doktor.setPasword("password");
								doktor.setName(rs.getString("name"));
								doktor.setTcno(rs.getString("tcno"));
								doktor.setType(rs.getString("type"));
								DoctorGUI dGUI=new DoctorGUI(doktor);
								dGUI.setVisible(true);
								dispose();
							}
							//bu komut ile logingui kapatılıp bashekimguı açılır
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		btn_persgiris.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_persgiris.setBounds(176, 131, 116, 38);
		w_personellogin.add(btn_persgiris);
		
		fld_perspassword = new JPasswordField();
		fld_perspassword.setBounds(154, 52, 301, 30);
		w_personellogin.add(fld_perspassword);
		
		JPanel w_hastalogin = new JPanel();
		w_hastalogin.setBackground(new Color(255, 255, 255));
		w_tabbedpane.addTab("Hasta Girişi", null, w_hastalogin, null);
		w_hastalogin.setLayout(null);
		
		JLabel lblTcNumaras = new JLabel("T.C Numarası:");
		lblTcNumaras.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 22));
		lblTcNumaras.setBounds(10, 11, 146, 30);
		w_hastalogin.add(lblTcNumaras);
		
		JLabel lblifre = new JLabel("Şifre:");
		lblifre.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 22));
		lblifre.setBounds(10, 52, 146, 30);
		w_hastalogin.add(lblifre);
		
		fld_hastatc = new JTextField();
		fld_hastatc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_hastatc.setBounds(154, 11, 301, 30);
		w_hastalogin.add(fld_hastatc);
		fld_hastatc.setColumns(10);
		
		fld_hastapass = new JTextField();
		fld_hastapass.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_hastapass.setColumns(10);
		fld_hastapass.setBounds(154, 52, 301, 30);
		w_hastalogin.add(fld_hastapass);
		
		JButton btn_hastalogin = new JButton("Giriş Yap");
		btn_hastalogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_hastalogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_hastatc.getText().length()==0 || fld_hastapass.getText().length()==0) {
					Helper.showMessage("fill");
				}else {
					boolean key=true;
					try {
						Connection con = conn.connDb();
						Statement st=con.createStatement();
						ResultSet rs=st.executeQuery("SELECT * FROM user");
						
						while(rs.next()) {
							if(fld_hastatc.getText().equals(rs.getString("tcno")) && fld_hastapass.getText().equals(rs.getString("password")));
							//nesneyi veritabanında ki sütunlara göre javada oluşturduk
							if(rs.getString("type").equals("hasta")) {
								//buradan itibaren bashekimguiye ait tasarıma geçtik
								Hasta hasta=new Hasta();
								hasta.setId(rs.getInt("id"));//
								hasta.setPasword("password");
								hasta.setName(rs.getString("name"));
								hasta.setTcno(rs.getString("tcno"));
								hasta.setType(rs.getString("type"));
								//BashekimGUI bGUI=new BashekimGUI(bhekim);
								HastaGUI hGUI=new HastaGUI(hasta);
								hGUI.setVisible(true);
								dispose();
								key=false;
							}
							
							//bu komut ile logingui kapatılıp bashekimguı açılır
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(key) {
						Helper.showMessage("Böyle bir hasta bulunamadı lütfen kayıt olunuz ! ");
					}
				}
				
				
			}
		});
		btn_hastalogin.setBounds(281, 128, 116, 38);
		w_hastalogin.add(btn_hastalogin);
		
		JButton btn_hastakayit = new JButton("Kayıt Ol");
		btn_hastakayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI=new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btn_hastakayit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_hastakayit.setBounds(85, 128, 116, 38);
		w_hastalogin.add(btn_hastakayit);
	}
}
