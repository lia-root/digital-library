package helpers;

import javax.swing.JOptionPane;

public class ShowMessageHelper {
	public static void showWarningMessage(String message) {
		showMessage(message, "Advertencia", JOptionPane.WARNING_MESSAGE);
	}

	public static void showErrorMessage(String message) {
		showMessage(message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void showInfoMessage(String message) {
		showMessage(message, "Informacion", JOptionPane.INFORMATION_MESSAGE);
	}

	private static void showMessage(String message, String titlePanel, int messageType) {
		JOptionPane.showMessageDialog(null, message, titlePanel, messageType);
	}
}
