package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import helpers.ShowMessageHelper;
import models.Administrador;
import models.Cuenta;
import models.Miembro;

public class Usuarios {
	public static void showUserPanel() {
		JFrame window = new JFrame("Panel de usuarios");
        window.setSize(500, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("Registrar nuevo usuario");

        JLabel fullNameLabel = new JLabel("Nombre completo");
        JTextField fullNameTextBox = new JTextField(15);

        JLabel passLabel = new JLabel("Contraseña");
        JPasswordField passTextBox = new JPasswordField(15);

        JLabel emailLabel = new JLabel("Correo electronico");
        JTextField emailTextBox = new JTextField(15);

        JLabel adminLabel = new JLabel("Marque si este usuario es un administrador");
        JRadioButton adminRadioButton = new JRadioButton();

        JButton addUserButton = new JButton("Agregar");
        JButton updateTableButton = new JButton("Actualizar");

        // Modelo de tabla
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Contraseña");
        modelo.addColumn("Correo electronico");
        modelo.addColumn("Rol");

        // Tabla
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(450, 200));
        
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	boolean textBoxEmpty = fullNameTextBox.getText().isEmpty() || passTextBox.getText().isEmpty() || emailTextBox.getText().isEmpty();
            	String tipo = adminRadioButton.isSelected() ? "administrador" : "miembro";

            	if(textBoxEmpty) {
            		ShowMessageHelper.showWarningMessage("Los campos Nombre, Correo electronico y Contraseña no deben estar en blanco");
            	}else {
            		addUser(fullNameTextBox.getText(), passTextBox.getText(), emailTextBox.getText(), tipo, modelo);
            		fullNameTextBox.setText("");
            		passTextBox.setText("");
            		emailTextBox.setText("");
            		adminRadioButton.setSelected(false);
            		ShowMessageHelper.showWarningMessage("revisa los archivos");
            	}
            }
        });

        // CARGAR DATOS AL HACER CLICK EN LA TABLA
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                String rol = modelo.getValueAt(fila, 4).toString();

                if(rol.equals("administrador")) {
                	ShowMessageHelper.showWarningMessage("No es posible editar la informacion de otro Administrador");
                	return ;
                }

                fullNameTextBox.setText(modelo.getValueAt(fila, 1).toString());
                passTextBox.setText(modelo.getValueAt(fila, 2).toString());
                emailTextBox.setText(modelo.getValueAt(fila, 3).toString());
            }
        });

        // EDITAR
        updateTableButton.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila != -1) {
            	String tipo = adminRadioButton.isSelected() ? "administrador" : "miembro";

                modelo.setValueAt(fullNameTextBox.getText(), fila, 1);
                modelo.setValueAt("******", fila, 2);
                modelo.setValueAt(emailTextBox.getText(), fila, 3);
                modelo.setValueAt(tipo, fila, 4);

                updateUser(modelo.getValueAt(fila, 0).toString(), fullNameTextBox.getText(), passTextBox.getText(), emailTextBox.getText(), tipo);
                
                fullNameTextBox.setText("");
                emailTextBox.setText("");
                passTextBox.setText("");
                adminRadioButton.setSelected(false);
            } else {
            	ShowMessageHelper.showWarningMessage("Selecciona una fila");
            }
        });
        
        refreshTable(modelo);

        window.add(titleLabel);
        window.add(fullNameLabel);
        window.add(fullNameTextBox);
        window.add(passLabel);
        window.add(passTextBox);
        window.add(emailLabel);
        window.add(emailTextBox);
        window.add(adminLabel);
        window.add(adminRadioButton);
        window.add(addUserButton);
        window.add(updateTableButton);
        window.add(scroll);

        window.setVisible(true);
	}

	private static void addUser(String nombre, String contra, String correo, String tipo, DefaultTableModel modelo) {
	     if (tipo.equals("administrador")){
	         Administrador administrador = new Administrador(nombre, contra, correo, tipo);
	         administrador.guardarUsuario();
	     }else{
	          Miembro miembro = new Miembro(nombre, contra, correo, tipo);
	          miembro.guardarUsuario();
	     }

	     refreshTable(modelo);
	}

	private static void updateUser(String id, String nombre, String contra, String correo, String tipo) {
		try {
			Cuenta.actualizarUsuario(id, nombre, contra, correo, tipo);
		}catch(Error e){
			ShowMessageHelper.showErrorMessage(e.getMessage());
		}
	}

	private static void refreshTable(DefaultTableModel modelo) {
		//for(int i = 0; i< modelo.getRowCount(); i++) modelo.removeRow(i);
		modelo.setRowCount(0);

		ArrayList<Cuenta> cuentas = Cuenta.obtenerUsuarios();

		for(Cuenta cuenta : cuentas) {
			modelo.addRow(new Object[]{ cuenta.getUuid(), cuenta.getUsuario(), "******", cuenta.getCorreo(), cuenta.getTipo()});
		}
	}

	public static void ejecutar() {
        JFrame ventana = new JFrame("Sistema Académico");
        ventana.setSize(500, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new FlowLayout());

        JTextField campoTexto = new JTextField(15);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(450, 200));

        // 👉 AGREGAR
        btnAgregar.addActionListener(e -> {
            String nombre = campoTexto.getText();

            if (!nombre.isEmpty()) {
                modelo.addRow(new Object[]{nombre});
                campoTexto.setText("");
            }
        });

        // 👉 CARGAR DATOS AL HACER CLICK EN LA TABLA
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                campoTexto.setText(modelo.getValueAt(fila, 0).toString());
            }
        });

        // 👉 EDITAR
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila != -1) {
                modelo.setValueAt(campoTexto.getText(), fila, 0);
                campoTexto.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Selecciona una fila");
            }
        });

        ventana.add(new JLabel("Nombre:"));
        ventana.add(campoTexto);
        ventana.add(btnAgregar);
        ventana.add(btnEditar);
        ventana.add(scroll);

        ventana.setVisible(true);
    }
}
