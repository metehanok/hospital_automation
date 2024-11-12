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

import Helper.Helper;
import Helper.Item;
import Model.Bashekim;
import Model.Clinic;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class bashekimGUI1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fld_doktorad;
	private JTextField fld_doktortc;
	private JPasswordField fld_doktorsifre;
	private JTextField fld_kullanıcıid;
	private JTable table_doktor;
	//tablelara modeller sayesinde veri ekleme komutudur
	private DefaultTableModel doctormodel=null;//veritabanında ki doktor columnlarını modelleyen yapı
	private Object[] doctordata=null;//doktoru modelleyen yapıyı tutan yapı
	static Bashekim bashekim=new Bashekim();
	private JTable table_clinic;
	private JTextField fld_clinicname;
	private DefaultTableModel clinicmodel=null;
	private Object[] clinicdata=null;
	private JPopupMenu clinicMenu;
	Clinic clinic=new Clinic();
	private JTable table_worker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bashekimGUI1 frame = new bashekimGUI1(bashekim);
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
	public bashekimGUI1(Bashekim bashekim) throws SQLException {
		//Doktor modeli
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
		clinicmodel=new DefaultTableModel();
		Object[]colclinic=new Object[2];
		colclinic[0]="ID";
		colclinic[1]="Poliklinik Adı";
		clinicmodel.setColumnIdentifiers(colclinic);
		clinicdata=new Object[2];
		for(int i=0;i<clinic.getList().size();i++) {
			clinicdata[0]=clinic.getList().get(i).getId();
			clinicdata[1]=clinic.getList().get(i).getName();
			clinicmodel.addRow(clinicdata);
		}
		//WorkerModel
		DefaultTableModel workerModel=new DefaultTableModel();
		Object[] colworker=new Object[2];
		colworker[0]="ID";
		colworker[1]="Ad Soyad";
		workerModel.setColumnIdentifiers(colworker);
		Object[] workerdata=new Object[2];
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 814, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 59, 780, 347);
		contentPane.add(tabbedPane);
		
		JPanel w_klinikyönetim = new JPanel();
		tabbedPane.addTab("Poliklinikler", null, w_klinikyönetim, null);
		w_klinikyönetim.setLayout(null);
		
		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 11, 238, 297);
		w_klinikyönetim.add(w_scrollClinic);
		
		//bu menü kliniklerin güncellenip silinmesini uygulama üzerinden yapılabilir kılar
		clinicMenu=new JPopupMenu();
		JMenuItem updateMenu=new JMenuItem("Güncelle");
		JMenuItem deleteMenu=new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);
		
		updateMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selID=Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(),0).toString());

					Clinic selectedClinic = clinic.getFetch(selID);
					UpdateClinicGUI updateGUI=new UpdateClinicGUI(selectedClinic);
					updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					updateGUI.setVisible(true);
			}
			
		});
		
		table_clinic = new JTable(clinicmodel);
		table_clinic.setComponentPopupMenu(clinicMenu);
		//klinik tablosu üzerinde popup menüyü aktif kılan komut
		table_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point =e.getPoint();
				int selectedrow=table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedrow, selectedrow);
				//sağ tıklandığı an bulunduğu koordinata göre işlemi devam ettirebilmesini sağlayan komut,
				//çünkü sayfa boyu klinik bulunacak ve her yerde sağ tıklayıp işlem yapabilmek için koordinata göre hesaplama yaptırdık
				
			}
		});
		w_scrollClinic.setViewportView(table_clinic);
		
		JLabel lblPoliklinikAd = new JLabel("Poliklinik Adı");
		lblPoliklinikAd.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblPoliklinikAd.setBounds(255, 11, 98, 30);
		w_klinikyönetim.add(lblPoliklinikAd);
		
		fld_clinicname = new JTextField();
		fld_clinicname.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_clinicname.setColumns(10);
		fld_clinicname.setBounds(255, 41, 225, 30);
		w_klinikyönetim.add(fld_clinicname);
		
		JButton btn_clinickayit = new JButton("Ekle");
		btn_clinickayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_clinicname.getText().length()==0) {
					Helper.showMessage("fill");
				}else {
					try {
						if(clinic.addclinic(fld_clinicname.getText()));
						Helper.showMessage("success");
						fld_clinicname.setText(null);
						updateclinicModel(); 
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btn_clinickayit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_clinickayit.setBounds(255, 82, 225, 38);
		w_klinikyönetim.add(btn_clinickayit);
		
		JScrollPane w_scrollworker = new JScrollPane();
		w_scrollworker.setBounds(513, 11, 252, 297);
		w_klinikyönetim.add(w_scrollworker);
		
		table_worker = new JTable();
		w_scrollworker.setViewportView(table_worker);
		
		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(258, 215, 222, 38);
		for(int i=0;i<bashekim.getDoctorList().size();i++) {
			select_doctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(),bashekim.getDoctorList().get(i).getName()));
			//seçili doktoru yaızlmış kliniklere atama işlemini yapmak için yazılan proses
		}
		select_doctor.addActionListener(e ->{
			JComboBox c=(JComboBox) e.getSource();
			Item item=(Item) c.getSelectedItem();
			System.out.println(item.getKey()+ " : "+item.getValue());
		});
		w_klinikyönetim.add(select_doctor);
		
		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow=table_clinic.getSelectedRow();
				if(selRow >=0) {
					//eğer seçili bir klinik satırı varsa
					String selClinic=table_clinic.getModel().getValueAt(selRow, 0).toString();
					int selClinicid=Integer.parseInt(selClinic);
					Item doctoritem=(Item) select_doctor.getSelectedItem();
					try {
						boolean control=bashekim.addWorker(doctoritem.getKey(), selClinicid);
						if(control) {
							Helper.showMessage("success");
							DefaultTableModel clearModel=(DefaultTableModel) table_worker.getModel();
							clearModel.setRowCount(0);
							for(int i=0;i<bashekim.getClinicDoctorList(selClinicid).size();i++) {
								workerdata[0]=bashekim.getClinicDoctorList(selClinicid).get(i).getId();
								workerdata[1]=bashekim.getClinicDoctorList(selClinicid).get(i).getName();
								workerModel.addRow(workerdata);
							}
							table_worker.setModel(workerModel); 
						}else {
							Helper.showMessage("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else {
					Helper.showMessage("Lütfen bir poliklinik seçiniz!");
				}
			}
		});
		btn_addWorker.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_addWorker.setBounds(258, 264, 225, 38);
		w_klinikyönetim.add(btn_addWorker);
		
		JLabel lblPoliklinikAd_1 = new JLabel("Poliklinik Adı");
		lblPoliklinikAd_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblPoliklinikAd_1.setBounds(255, 131, 98, 30);
		w_klinikyönetim.add(lblPoliklinikAd_1);
		
		JButton btn_workerSelect = new JButton("Seç");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow=table_clinic.getSelectedRow();
				 if(selRow >=0) {
					 String selClinic=table_clinic.getModel().getValueAt(selRow, 0).toString();
						int selClinicid=Integer.parseInt(selClinic);
						//seçilen kliniğin ıd'sini bulma
						DefaultTableModel clearModel=(DefaultTableModel) table_worker.getModel();
						clearModel.setRowCount(0);
						
						try {
							for(int i=0;i<bashekim.getClinicDoctorList(selClinicid).size();i++) {
								workerdata[0]=bashekim.getClinicDoctorList(selClinicid).get(i).getId();
								workerdata[1]=bashekim.getClinicDoctorList(selClinicid).get(i).getName();
								workerModel.addRow(workerdata);
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						table_worker.setModel(workerModel);
				 }else {
					 Helper.showMessage("Lütfen bir poliklinik seçiniz!");
				 }
			}
		});
		btn_workerSelect.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_workerSelect.setBounds(255, 158, 225, 38);
		w_klinikyönetim.add(btn_workerSelect);
		
		JPanel w_doktoryönetim =new JPanel();
		tabbedPane.addTab("Doktor Yönetimi", null, w_doktoryönetim, null);
		w_doktoryönetim.setLayout(null);
		
		JLabel lblAdSoyad = new JLabel("Ad Soyad");
		lblAdSoyad.setBounds(468, 5, 98, 30);
		lblAdSoyad.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		w_doktoryönetim.add(lblAdSoyad);
		
		fld_doktorad = new JTextField();
		fld_doktorad.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_doktorad.setColumns(10);
		fld_doktorad.setBounds(468, 35, 225, 30);
		w_doktoryönetim.add(fld_doktorad);
		
		JLabel lblTcNumaras_1 = new JLabel("T.C Numarası:");
		lblTcNumaras_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblTcNumaras_1.setBounds(468, 57, 146, 30);
		w_doktoryönetim.add(lblTcNumaras_1);
		
		fld_doktortc = new JTextField();
		fld_doktortc.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_doktortc.setColumns(10);
		fld_doktortc.setBounds(468, 88, 225, 30);
		w_doktoryönetim.add(fld_doktortc);
		
		JLabel lblifre_1 = new JLabel("Şifre:");
		lblifre_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblifre_1.setBounds(468, 121, 44, 21);
		w_doktoryönetim.add(lblifre_1);
		
		fld_doktorsifre = new JPasswordField();
		fld_doktorsifre.setBounds(468, 146, 225, 30);
		w_doktoryönetim.add(fld_doktorsifre);
		
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
		btn_doktorkayit.setBounds(468, 187, 225, 38);
		w_doktoryönetim.add(btn_doktorkayit);
		
		JLabel lblifre_1_1 = new JLabel("Kullanıcı ID:");
		lblifre_1_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblifre_1_1.setBounds(468, 224, 121, 21);
		w_doktoryönetim.add(lblifre_1_1);
		
		fld_kullanıcıid = new JTextField();
		fld_kullanıcıid.setFont(new Font("Yu Gothic Light", Font.PLAIN, 18));
		fld_kullanıcıid.setColumns(10);
		fld_kullanıcıid.setBounds(468, 250, 225, 30);
		w_doktoryönetim.add(fld_kullanıcıid);
		
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
		btn_doktorsil.setBounds(468, 287, 225, 21);
		w_doktoryönetim.add(btn_doktorsil);
		
		JScrollPane w_scrolldoktor = new JScrollPane();
		w_scrolldoktor.setBounds(10, 11, 445, 297);
		w_doktoryönetim.add(w_scrolldoktor);
		
		table_doktor = new JTable(doctormodel);
		w_scrolldoktor.setViewportView(table_doktor);
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz , Sayın"+bashekim.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 305, 26);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(535, 6, 123, 31);
		contentPane.add(btnNewButton);
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
	public void updateclinicModel() throws SQLException {
		DefaultTableModel clearmodel=(DefaultTableModel) table_clinic.getModel();
		clearmodel.setRowCount(0);
		for(int i=0;i<clinic.getList().size();i++) {
			clinicdata[0]=clinic.getList().get(i).getId();
			clinicdata[1]=clinic.getList().get(i).getName();
			clinicmodel.addRow(clinicdata);
		}
	}
}
