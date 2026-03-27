package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import helpers.ShowMessageHelper;

import views.MenuAdministrador;

public class Login {
	public static void ShowLogin() {
        JFrame window = new JFrame("Login");
        window.setSize(520, 380);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());
        
        JLabel userNameLabel = new JLabel("Usuario");
        JTextField userNameTextBox = new JTextField(15);

        JLabel userPassLabel = new JLabel("Contraseña");
        JPasswordField userPassBox = new JPasswordField(15);
        
        JButton loginButton = new JButton("Ingresar");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        loginButton.addActionListener(e -> {
            String userName = userNameTextBox.getText();
            char[] password = userPassBox.getPassword();
            String userPass = new String(password);

            if (!userName.isEmpty() && !userPass.isEmpty()) {
                userNameTextBox.setText("");
                userPassBox.setText("");
                showRolMenu(userName, userPass);
            } else {
                ShowMessageHelper.showWarningMessage("Los campos de Usuario y Contraseña no pueden estar vacios");
            }
        });

        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBorder(new EmptyBorder(24, 28, 24, 28));
        cardPanel.setBackground(new Color(248, 250, 252));

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 232)),
                new EmptyBorder(18, 18, 18, 18)
        ));
        containerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Bienvenido");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Inicia sesión para continuar");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 16, 0));
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        formPanel.add(userNameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(userNameTextBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        formPanel.add(userPassLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        formPanel.add(userPassBox, gbc);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(14, 6, 0, 6));
        buttonPanel.add(loginButton, BorderLayout.CENTER);

        containerPanel.add(headerPanel, BorderLayout.NORTH);
        containerPanel.add(formPanel, BorderLayout.CENTER);
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);
        cardPanel.add(containerPanel);

        window.add(cardPanel, BorderLayout.CENTER);
        
        window.setVisible(true);
	}

    private static void showRolMenu(String user, String pass){
        if(user.equals("root") && pass.equals("1234")){
            MenuAdministrador.showMenu("root", "Administrador");
        }

        ShowMessageHelper.showErrorMessage("Usuario o Contraseña incorrectos");
    }
}
