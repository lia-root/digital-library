package views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MenuAdministrador {
	public static void showMenu(String nombreUsuario) {
        JFrame window = new JFrame("Menu de Administrador");
        window.setSize(500, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());

        JLabel userNameLabel = new JLabel(nombreUsuario);
        JLabel userRoleLabel = new JLabel("Administrador");
        JLabel titleLabel = new JLabel("Seleccione una opcion");

        JButton usersButton = new JButton("Panel de Usuarios");
        JButton booksButton = new JButton("Panel de Libros");
        JButton loginButton = new JButton("Regresar al inicio de sesion");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Login.ShowLogin();
                window.setVisible(false);
            }
        });

        window.add(titleLabel);
        window.add(userNameLabel);
        window.add(userRoleLabel);
        window.add(usersButton);
        window.add(booksButton);
        window.add(loginButton);

        window.setVisible(true);
	}
}
