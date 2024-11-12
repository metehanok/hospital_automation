package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doktor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DoctorGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private static Doktor doctor=new Doktor();
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData=null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
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
	public DoctorGUI(Doktor doctor) throws SQLException {
		
		whourModel=new DefaultTableModel();
		Object[]colwhour=new Object[2];
		colwhour[0]="ID";
		colwhour[1]="Tarih";
		whourModel.setColumnIdentifiers(colwhour);
		whourData=new Object[2];
		for(int i=0;i<doctor.getwhourList(doctor.getId()).size();i++) {
			whourData[0]=doctor.getwhourList(doctor.getId()).get(i).getId();
			whourData[1]=doctor.getwhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
		
		
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 814, 454);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hoşgeldiniz , Sayın"+doctor.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 22, 305, 26);
		w_pane.add(lblNewLabel);
		
		JButton btn_cikisyap = new JButton("Çıkış Yap");
		btn_cikisyap.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_cikisyap.setBounds(595, 26, 123, 31);
		w_pane.add(btn_cikisyap);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(325, 219, 5, 5);
		w_pane.add(tabbedPane);
		
		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBounds(10, 70, 780, 347);
		w_pane.add(w_tab);
		
		JPanel w_whour = new JPanel();
		w_tab.addTab("Çalışma Saatleri", null, w_whour, null);
		w_whour.setLayout(null);
		
		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 11, 118, 31);
		w_whour.add(select_date);
		
		JComboBox select_time = new JComboBox();
		select_time.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		select_time.setModel(new DefaultComboBoxModel(new String[] {"09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"}));
		select_time.setBounds(152, 11, 83, 31);
		w_whour.add(select_time);
		
		JButton btn_addwhour = new JButton("Ekle");
		btn_addwhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String date=sdf.format(select_date.getDate());
				if(date.length()==0) {
					Helper.showMessage("Lütfen geçerli bir tarih giriniz");
				}
				else {
					String time= " "+select_time.getSelectedItem().toString() + ":00";
					String selectdate=date+time;
					try {
						boolean control=doctor.addwhour(doctor.getId(),doctor.getName(), selectdate);
						if(control==true) {
							Helper.showMessage("success");
							updatewhourModel(doctor);
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
		btn_addwhour.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_addwhour.setBounds(252, 11, 123, 31);
		w_whour.add(btn_addwhour);
		
		JScrollPane w_scrollwhour = new JScrollPane();
		w_scrollwhour.setBounds(0, 53, 775, 266);
		w_whour.add(w_scrollwhour);
		
		table_whour = new JTable(whourModel);
		w_scrollwhour.setViewportView(table_whour);
		
		JButton btn_addwhour_1 = new JButton("Sil");
		btn_addwhour_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selrow=table_whour.getSelectedRow();
				if(selrow >=0) {
					String selectRow=table_whour.getModel().getValueAt(selrow,0).toString();
					int selectID=Integer.parseInt(selectRow);
					try {
						boolean control=doctor.deletewhour(selectID);
						if(control) {
							Helper.showMessage("success");
						}else{
							Helper.showMessage("error");
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				else {
					Helper.showMessage("Lütfen tarih seçiniz");
				}
			}
		});
		btn_addwhour_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_addwhour_1.setBounds(597, 11, 123, 31);
		w_whour.add(btn_addwhour_1);
	}
	public void updatewhourModel(Doktor doctor) throws SQLException {//eklendikten sonra görünen listenin güncellenmesi
		DefaultTableModel clearmodel=(DefaultTableModel) table_whour.getModel();
		clearmodel.setRowCount(0);
		for(int i=0;i<doctor.getwhourList(doctor.getId()).size();i++) {
			whourData[0]=doctor.getwhourList(doctor.getId()).get(i).getId();
			whourData[1]=doctor.getwhourList(doctor.getId()).get(i).getWdate();
			whourModel.addRow(whourData);
		}
		

		}

}
