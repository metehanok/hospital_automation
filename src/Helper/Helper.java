package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {
	public static void optionPaneTextChanged() {
		UIManager.put("OptionPane.cancelButtonText", "İptal");
		UIManager.put("OptionPane.yesButtonText", "Evet");
		UIManager.put("OptionPane.noButtonText", "Hayır");
		UIManager.put("OptionPane.okButtonText", "Tamam");
	}
	public static void showMessage(String str) {
		//doldurulması gereken,boş geçilmiş veya hatalı yerlerde uyarı verilmesi çin oluşturulan yardımcı metod
		String msj;
		optionPaneTextChanged();
		switch (str) {
		case "fill": 
			
			msj="Lütfen tüm alanları doldurunuz";
			break;
		case"success":
			msj="İşlem Başarılı";
			break;
		case "error":
			msj="Bir Hata gerçekleşti!";
			break;
			default:
			msj=str;
		}
		
			JOptionPane.showMessageDialog(null, msj,"Mesaj",JOptionPane.INFORMATION_MESSAGE);
		}
	public static boolean confirm(String str) {
		optionPaneTextChanged();
		String msj;
		switch (str) {
			
		case "sure": 
			msj="Bu işlemi gerçekleştirmek istiyor musun?";
			break;
			default:
				msj=str;
				break;
			
			
		}
		int res=JOptionPane.showConfirmDialog(null, msj,"Dikkat !",JOptionPane.YES_NO_OPTION);
		if(res==0) {//yes seçilmiş ise
			return true;
		}else {
			return false;
		}
				
	}
	}
	



