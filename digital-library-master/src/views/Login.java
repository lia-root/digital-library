package views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import helpers.ShowMessageHelper;

public class Login {
	public static void ShowLogin() {
        JFrame window = new JFrame("Login");
        window.setSize(500, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());
        
        JLabel userNameLabel = new JLabel("Usuario");
        JTextField userNameTextBox = new JTextField(15);

        JLabel userPassLabel = new JLabel("Contraseña");
        JPasswordField userPassBox = new JPasswordField(15);
        
        JButton loginButton = new JButton("Ingresar");
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = userNameTextBox.getText();
                char[] password = userPassBox.getPassword();
                String userPass = new String(password);

                if (!userName.isEmpty() && !userPass.isEmpty()) {
                	userNameTextBox.setText("");
                	userPassBox.setText("");
                	ShowMessageHelper.showErrorMessage("Ingresase: "+userName+" y "+userPass);
                } else {
                    ShowMessageHelper.showWarningMessage("Los campos de Usuario y Contraseña no pueden estar vacios");
                }
            }
        });

        window.add(userNameLabel);
        window.add(userNameTextBox);
        window.add(userPassLabel);
        window.add(userPassBox);
        window.add(loginButton);
        
        window.setVisible(true);
	}
}
