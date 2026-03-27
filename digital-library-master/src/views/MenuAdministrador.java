package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MenuAdministrador {
	public static void showMenu(String nombreUsuario, String rol) {
        JFrame window = new JFrame("Menu de Administrador");
        window.setSize(860, 560);
        window.setMinimumSize(new Dimension(820, 520));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Panel de Administrador");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel userNameLabel = new JLabel("Usuario: " + nombreUsuario);
        userNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userRoleLabel = new JLabel("Rol:"+ rol);
        userRoleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userRoleLabel.setForeground(new Color(95, 95, 95));
        userRoleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Seleccione una opcion");
        subtitleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton usersButton = new JButton("Panel de Usuarios");
        JButton booksButton = new JButton("Panel de Libros");
        JButton loginButton = new JButton("Regresar al inicio de sesion");

        usersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        booksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usersButton.setMaximumSize(new Dimension(380, 42));
        booksButton.setMaximumSize(new Dimension(380, 42));
        loginButton.setMaximumSize(new Dimension(380, 42));
        usersButton.setPreferredSize(new Dimension(380, 42));
        booksButton.setPreferredSize(new Dimension(380, 42));
        loginButton.setPreferredSize(new Dimension(380, 42));

        usersButton.setBackground(new Color(70, 130, 180));
        usersButton.setForeground(Color.WHITE);
        usersButton.setFocusPainted(false);

        booksButton.setBackground(new Color(46, 139, 87));
        booksButton.setForeground(Color.WHITE);
        booksButton.setFocusPainted(false);

        loginButton.setBackground(new Color(105, 105, 105));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        usersButton.addActionListener(e -> {
            Usuarios.showUserPanel();
            window.setVisible(false);
        });

        booksButton.addActionListener(e -> {
            Libros.showUserPanel();
            window.setVisible(false);
        });

        loginButton.addActionListener(e -> {
            Login.ShowLogin();
            window.setVisible(false);
        });

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(24, 36, 24, 36));
        contentPanel.setBackground(Color.WHITE);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(18));
        JPanel usersButtonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        usersButtonRow.setOpaque(false);
        usersButtonRow.add(usersButton);
        contentPanel.add(usersButtonRow);
        contentPanel.add(Box.createVerticalStrut(10));
        JPanel booksButtonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        booksButtonRow.setOpaque(false);
        booksButtonRow.add(booksButton);
        contentPanel.add(booksButtonRow);
        contentPanel.add(Box.createVerticalStrut(10));
        JPanel loginButtonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        loginButtonRow.setOpaque(false);
        loginButtonRow.add(loginButton);
        contentPanel.add(loginButtonRow);

        JPanel centeredContentPanel = new JPanel(new java.awt.GridBagLayout());
        centeredContentPanel.setOpaque(false);
        centeredContentPanel.add(contentPanel);

        JPanel infoBoxPanel = new JPanel();
        infoBoxPanel.setLayout(new BoxLayout(infoBoxPanel, BoxLayout.Y_AXIS));
        infoBoxPanel.setOpaque(false);
        userNameLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        userRoleLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        infoBoxPanel.add(userNameLabel);
        infoBoxPanel.add(Box.createVerticalStrut(8));
        infoBoxPanel.add(userRoleLabel);

        JPanel topInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        topInfoPanel.setOpaque(false);
        topInfoPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
        topInfoPanel.add(infoBoxPanel);

        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(28, 28, 28, 28),
                BorderFactory.createLineBorder(new Color(218, 224, 230))
        ));
        cardPanel.setBackground(new Color(246, 248, 250));
        cardPanel.add(topInfoPanel, BorderLayout.NORTH);
        cardPanel.add(centeredContentPanel, BorderLayout.CENTER);

        window.add(cardPanel, BorderLayout.CENTER);

        window.setVisible(true);
	}
}
