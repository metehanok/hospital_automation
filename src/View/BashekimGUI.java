package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.Bashekim;

import java.awt.Color;
import java.awt.Window.Type;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Helper.*;
import javax.swing.JTabbedPane;
public class BashekimGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	static Bashekim bashekim=new Bashekim();
	private JTextField fld_doktorad;
	private JTextField fld_doktortc;
	private JTextField txtDoktorYnetimi;
	private JTextField fld_kullanıcıid;
	private JPasswordField fld_doktorsifre;
	private JTable table_doktor;
	//tablelara modeller sayesinde veri ekleme komutudur
	private DefaultTableModel doctormodel=null;//veritabanında ki doktor columnlarını modelleyen yapı
	private Object[] doctordata=null;//doktoru modelleyen yapıyı tutan yapı
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					//frame içine de aynı nesneyi vermek zorundayız
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public BashekimGUI(Bashekim bashekim) throws SQLException {//giriş yapıldığını anlayabilmek için yapıcı const. içine nesne verilmeli
		doctormodel=new DefaultTableModel();
		Object[]coldoctorname=new Object[4];//veritabanında ki kaç başlığın gösterileceğini belirten komut
		coldoctorname[0]="ID";
		coldoctorname[1]="Ad Soyad";
		coldoctorname[2]="Tc No";
		coldoctorname[3]="Şifre";
		doctormodel.setColumnIdentifiers(coldoctorname);
		doctordata=new Object[4];
		for(int i=0; i<bashekim.getDoctorList().size();i++) {
			doctordata[0]=bashekim.getDoctorList().get(i).getId(); 
			doctordata[1]=bashekim.getDoctorList().get(i).getName();
			doctordata[2]=bashekim.getDoctorList().get(i).getTcno();
			doctordata[3]=bashekim.getDoctorList().get(i).getPasword();
			doctormodel.addRow(doctordata);//her oluşturulduktan sonra doctordata içine atıldı
			
		}
		setFont(new Font("Dialog", Font.PLAIN, 9));
		setBackground(Color.WHITE);
		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 803, 535);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(255, 255, 255));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz , Sayın"+bashekim.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 305, 26);
		w_pane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(586, 8, 123, 31);
		w_pane.add(btnNewButton);
		
		JPanel w_hastalogin = new JPanel();
		w_hastalogin.setLayout(null);
		w_hastalogin.setBackground(Color.WHITE);
		w_hastalogin.setBounds(10, 106, 769, 381);
		w_pane.add(w_hastalogin);
		
		fld_doktorad = new JTextField();
		fld_doktorad.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_doktorad.setColumns(10);
		fld_doktorad.setBounds(534, 41, 225, 30);
		w_hastalogin.add(fld_doktorad);
		
		fld_doktortc = new JTextField();
		fld_doktortc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_doktortc.setColumns(10);
		fld_doktortc.setBounds(534, 101, 225, 30);
		w_hastalogin.add(fld_doktortc);
		
		JButton btn_doktorkayit = new JButton("Ekle");
		btn_doktorkayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_doktorad.getText().length()==0 || fld_doktorsifre.getText().length()==0 || fld_doktortc.getText().length()==0) {
					Helper.showMessage("fill");
				}else {
					try {
						boolean control=bashekim.adddoktor(fld_doktortc.getText(),fld_doktorad.getText(),fld_doktorsifre.getText());
						if(control) { 
							Helper.showMessage("success");
							fld_doktorad.setText(null);
							fld_doktortc.setText(null);
							fld_doktorsifre.setText(null);
							updatedoctorModel();
							//kaydettikten sonra text alanında aynı veriler kalmaması için
							
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_doktorkayit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_doktorkayit.setBounds(534, 213, 225, 38);
		w_hastalogin.add(btn_doktorkayit);
		
		JLabel lblAdSoyad = new JLabel("Ad Soyad");
		lblAdSoyad.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblAdSoyad.setBounds(532, 11, 98, 30);
		w_hastalogin.add(lblAdSoyad);
		
		JLabel lblTcNumaras_1 = new JLabel("T.C Numarası:");
		lblTcNumaras_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblTcNumaras_1.setBounds(534, 75, 146, 30);
		w_hastalogin.add(lblTcNumaras_1);
		
		JLabel lblifre_1 = new JLabel("Şifre:");
		lblifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblifre_1.setBounds(534, 142, 53, 30);
		w_hastalogin.add(lblifre_1);
		
		JLabel lblifre_1_1 = new JLabel("Kullanıcı ID:");
		lblifre_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblifre_1_1.setBounds(534, 262, 146, 30);
		w_hastalogin.add(lblifre_1_1);
		
		fld_kullanıcıid = new JTextField();
		fld_kullanıcıid.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_kullanıcıid.setColumns(10);
		fld_kullanıcıid.setBounds(534, 303, 225, 30);
		w_hastalogin.add(fld_kullanıcıid);
		
		fld_doktorsifre = new JPasswordField();
		fld_doktorsifre.setBounds(534, 172, 225, 30);
		w_hastalogin.add(fld_doktorsifre);
		
		JButton btn_doktorsil = new JButton("Sil");
		btn_doktorsil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_kullanıcıid.getText().length()==0) {
					Helper.showMessage("Lütfen geçerli bir doktor seçiniz");
				}else {
					if(Helper.confirm("sure")) {
						int selectid=Integer.parseInt(fld_kullanıcıid.getText());
						//gelen veri stringtir bu metod sayesinde integera çevridik
						try {
							boolean control=bashekim.deletedoktor(selectid);
							//kullanıcı silme metodunu çağırarak boolean değer içinde tuttuk
							//yani true ya da false bir değer döndürecek
							if(control) {
								Helper.showMessage("success");
								fld_kullanıcıid.setText(null);
								updatedoctorModel();
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_doktorsil.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_doktorsil.setBounds(534, 344, 225, 38);
		w_hastalogin.add(btn_doktorsil);
		
		JScrollPane w_scrolldoktor = new JScrollPane();
		w_scrolldoktor.setBounds(10, 11, 514, 371);
		w_hastalogin.add(w_scrolldoktor);
		
		table_doktor = new JTable(doctormodel);
		w_scrolldoktor.setViewportView(table_doktor);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(-5, 11, 376, 304);
		w_hastalogin.add(tabbedPane);
		table_doktor.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
		
			

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				try {
					fld_kullanıcıid.setText(table_doktor.getValueAt(table_doktor.getSelectedRow(), 0).toString());
				} catch (Exception e2) {
					// TODO: handle exception
				}
				//tıkladığın satırı kullanıcı sil text kutusuna getirme işlemi
				table_doktor.getModel().addTableModelListener(new TableModelListener() {
					
					@Override
					public void tableChanged(TableModelEvent e) {
						// TODO Auto-generated method stub
						if(e.getType()==TableModelEvent.UPDATE){
							int selectid=Integer.parseInt(table_doktor.getValueAt(table_doktor.getSelectedRow(),0).toString());
							String selectname=table_doktor.getValueAt(table_doktor.getSelectedRow(),1).toString();
							String selecttcno=table_doktor.getValueAt(table_doktor.getSelectedRow(),2).toString();
							String selectpassword=table_doktor.getValueAt(table_doktor.getSelectedRow(),3).toString();
							
							try {
								boolean control =bashekim.updatedoktor(selectid,selecttcno,selectpassword,selectname);
							
							} catch (Exception e2) {
								// TODO: handle exception
							}
							//girilmiş veriyi güncelleme işlemi,üstüne tıklayarak güncelleme yapılmış oluyor
							
				}
					}
				});
			}
		});	
			
		txtDoktorYnetimi = new JTextField();
		txtDoktorYnetimi.setText("Doktor Yönetimi");
		txtDoktorYnetimi.setFont(new Font("Tahoma", Font.BOLD, 18));
		txtDoktorYnetimi.setColumns(10);
		txtDoktorYnetimi.setBounds(10, 75, 225, 30);
		w_pane.add(txtDoktorYnetimi);
		
		
		
		
		
		
	}
	public void updatedoctorModel() throws SQLException {//eklendikten sonra görünen listenin güncellenmesi
		DefaultTableModel clearmodel=(DefaultTableModel) table_doktor.getModel();
		clearmodel.setRowCount(0);
		for(int i=0; i<bashekim.getDoctorList().size();i++) {	
			doctordata[0]=bashekim.getDoctorList().get(i).getId(); 
			doctordata[1]=bashekim.getDoctorList().get(i).getName();
			doctordata[2]=bashekim.getDoctorList().get(i).getTcno();
			doctordata[3]=bashekim.getDoctorList().get(i).getPasword();
			doctormodel.addRow(doctordata);//her oluşturulduktan sonra doctordata içine atıldı
			
		}
		}
}
