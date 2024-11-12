package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.Item;
import Model.Clinic;
import Model.Hasta;
import Model.Whour;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTable;
import Helper.Helper;

//HERKES GİRİŞ YAPABİLİYOR VERİ TABANINDA OLMAYAN BİLE NEDEN OLUYOR BAK??

public class HastaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private static Hasta hasta=new Hasta();
	private Clinic clinic=new Clinic();
	private JTable table_doktor;
	private DefaultTableModel doctormodel;
	private Object[]doctordata=null;
	private JTable table_whour;
	private Whour whour=new Whour();
	private DefaultTableModel whourmodel;
	private Object[]whourdata=null;
	private int selectdoctorId=0;
	private String selectdoctorName=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HastaGUI frame = new HastaGUI(hasta);
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
	public HastaGUI(Hasta hasta) throws SQLException {
		
		doctormodel=new DefaultTableModel();
		Object[]coldoctor=new Object[2];//veritabanında ki kaç başlığın gösterileceğini belirten komut
		coldoctor[0]="ID";
		coldoctor[1]="Ad Soyad";
		doctormodel.setColumnIdentifiers(coldoctor);
		doctordata=new Object[2];
		
		whourmodel=new DefaultTableModel();
		Object[]colwhour=new Object[2];//veritabanında ki kaç başlığın gösterileceğini belirten komut
		colwhour[0]="ID";
		colwhour[1]="Tarih";
		whourmodel.setColumnIdentifiers(colwhour);
		whourdata=new Object[2];
		
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz , Sayın"+hasta.getName());
		lblNewLabel.setBounds(23, 11, 236, 19);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		w_pane.add(lblNewLabel);
		
		JButton btn_cikisyap = new JButton("Çıkış Yap");
		btn_cikisyap.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_cikisyap.setBounds(546, 11, 123, 31);
		w_pane.add(btn_cikisyap);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 105, 726, 358);
		w_pane.add(w_tab);
		
		JPanel w_randevu = new JPanel();
		w_randevu.setBackground(new Color(255, 255, 255));
		w_tab.addTab("Randevu Sistemi", null, w_randevu, null);
		w_randevu.setLayout(null);
		
		JScrollPane w_scrollDoktor = new JScrollPane();
		w_scrollDoktor.setBounds(10, 32, 266, 287);
		w_randevu.add(w_scrollDoktor);
		
		table_doktor = new JTable(doctormodel);
		w_scrollDoktor.setRowHeaderView(table_doktor);
		
		JLabel lblPoliklinikAd = new JLabel("Poliklinik Adı");
		lblPoliklinikAd.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblPoliklinikAd.setBounds(288, 11, 98, 30);
		w_randevu.add(lblPoliklinikAd);
		
		JComboBox select_clinic = new JComboBox();
		select_clinic.setBounds(286, 41, 147, 30);
		select_clinic.addItem("Poliklinik Seç");
		for(int i=0;i<clinic.getList().size();i++) {
			select_clinic.addItem(new Item(clinic.getList().get(i).getId(),clinic.getList().get(i).getName()));
			
		}
		select_clinic.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				if(select_clinic.getSelectedIndex()!=0) {
					JComboBox c=(JComboBox) e.getSource();
					Item item=(Item) c.getSelectedItem();
					DefaultTableModel clearModel=(DefaultTableModel) table_doktor.getModel();
					clearModel.setRowCount(0); 
					try {
						for(int i =0;i<clinic.getClinicDoctorList(item.getKey()).size();i++) {
							doctordata[0]= clinic.getClinicDoctorList(item.getKey()).get(i).getId();
							doctordata[1]= clinic.getClinicDoctorList(item.getKey()).get(i).getName();
							doctormodel.addRow(doctordata);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//seçili polikliniği getiren komut
				}else {
					DefaultTableModel clearModel=(DefaultTableModel) table_doktor.getModel();
					clearModel.setRowCount(0); 
				}
		}
				
		} );
		w_randevu.add(select_clinic);
		
		JLabel lblNewLabel_1 = new JLabel("Doktor Listesi");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(10, 7, 136, 19);
		w_randevu.add(lblNewLabel_1);
		
		JLabel lblDoktorSe = new JLabel("Doktor Seç");
		lblDoktorSe.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblDoktorSe.setBounds(288, 98, 98, 30);
		w_randevu.add(lblDoktorSe);
		
		JButton btn_selectdoktor = new JButton("Seç");
		btn_selectdoktor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row=table_doktor.getSelectedRow();
				if(row>=0) {
					String value=table_doktor.getModel().getValueAt(row,0).toString();
					int id=Integer.parseInt(value);
					DefaultTableModel clearModel=(DefaultTableModel) table_whour.getModel();
					clearModel.setRowCount(0); 
					
					try {
						for(int i=0;i<whour.getwhourList(id).size();i++) {
							whourdata[0]=whour.getwhourList(id).get(i).getId();
							whourdata[1]=whour.getwhourList(id).get(i).getWdate();
							whourmodel.addRow(whourdata);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table_whour.setModel(whourmodel); 
					selectdoctorId=id;
					selectdoctorName=table_whour.getModel().getValueAt(row, 2).toString();
				//	System.out.println(selectdoctorId+" - "+selectdoctorName);
				}else {
					Helper.showMessage("Lütfen Doktor Seçiniz !"); 
				}
				
			}
		});
		btn_selectdoktor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_selectdoktor.setBounds(286, 139, 147, 30);
		w_randevu.add(btn_selectdoktor);
		
		JLabel lblNewLabel_1_1 = new JLabel("Uygun Saatler");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(445, 7, 136, 19);
		w_randevu.add(lblNewLabel_1_1);
		
		JScrollPane w_scrollwhour = new JScrollPane();
		w_scrollwhour.setBounds(445, 32, 266, 287);
		w_randevu.add(w_scrollwhour);
		
		table_whour = new JTable();
		w_scrollwhour.setViewportView(table_whour);
		
		JLabel lblRandevuAl = new JLabel("Randevu ");
		lblRandevuAl.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblRandevuAl.setBounds(288, 196, 98, 30);
		w_randevu.add(lblRandevuAl);
		
		JButton btn_addappointment = new JButton("Randevu Al");
		btn_addappointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow=table_whour.getSelectedRow();
				if(selRow>=0) {
					 String date=table_whour.getModel().getValueAt(selRow,0).toString();
					 try {
						boolean control=hasta.addAppointment(selectdoctorId,selectdoctorName, hasta.getId(),hasta.getName(),date);
						if(control) {
							Helper.showMessage("success");
							hasta.updatewhourStatus(selectdoctorId, date); 
							updatewhourModel(selectdoctorId); 
						}else {
							Helper.showMessage("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
				}else {
					Helper.showMessage("Lütfen geçerli bir tarih giriniz");
				}
				
			}
		});
		btn_addappointment.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_addappointment.setBounds(286, 237, 147, 30);
		w_randevu.add(btn_addappointment);
	
	}
	public void updatewhourModel(int doctor_id) throws SQLException {
		DefaultTableModel clearModel=(DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for(int i=0;i<whour.getwhourList(doctor_id).size();i++) {
			whourdata[0]=whour.getwhourList(doctor_id).get(i).getId();
			whourdata[1]=whour.getwhourList(doctor_id).get(i).getWdate();
			whourmodel.addRow(whourdata);
		}
}
	}
