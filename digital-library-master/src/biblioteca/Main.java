package digital_librery;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import views.Login;
import views.MenuAdministrador;
import views.Usuarios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
    	Usuarios.showUserPanel();
    	//Login.ShowLogin();
    	//MenuAdministrador.showMenu("Agmando");

    	/*
        JFrame ventana = new JFrame("Sistema Académico");
        ventana.setSize(500, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout());

        // Campo de texto
        JTextField campoTexto = new JTextField(15);

        // Botón
        JButton boton = new JButton("Agregar");

        // Modelo de tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");

        // Tabla
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(450, 200));

        // Acción del botón
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = campoTexto.getText();

                if (!nombre.isEmpty()) {
                    modelo.addRow(new Object[]{nombre});
                    campoTexto.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Escribe un nombre");
                }
            }
        });

        // Agregar componentes
        ventana.add(new JLabel("Nombre:"));
        ventana.add(campoTexto);
        ventana.add(boton);
        ventana.add(scroll);

        ventana.setVisible(true);
        */
    }
}