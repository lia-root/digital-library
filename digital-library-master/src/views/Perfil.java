package views;

import helpers.CurrentUserHelper;
import models.Cuenta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Perfil {
    public static void showPerfilMenu() {
        JFrame window = new JFrame("Perfil de miembros ");
        window.setSize(900, 560);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout(12, 12));

        JLabel titleLabel = new JLabel("Mi perfil");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(8, 4, 0, 4));

       Cuenta usuarioActual =  CurrentUserHelper.get();

        titleLabel.setText( usuarioActual.getTipo());


    }

}
