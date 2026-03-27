package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import helpers.ShowMessageHelper;
import models.Administrador;
import models.Cuenta;

public class Usuarios {
	public static void showUserPanel() {
		JFrame window = new JFrame("Panel de usuarios");
        window.setSize(900, 560);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout(12, 12));

        JLabel titleLabel = new JLabel("Registrar nuevo usuario");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(8, 4, 0, 4));

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
        addUserButton.setBackground(new Color(46, 139, 87));
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setFocusPainted(false);
        updateTableButton.setBackground(new Color(70, 130, 180));
        updateTableButton.setForeground(Color.WHITE);
        updateTableButton.setFocusPainted(false);

        // Modelo de tabla
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Contraseña");
        modelo.addColumn("Correo electronico");
        modelo.addColumn("Rol");

        // Tabla
        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tabla.setSelectionBackground(new Color(220, 238, 255));
        tabla.setSelectionForeground(Color.BLACK);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Listado de usuarios"));
        
        addUserButton.addActionListener(e -> {
            boolean textBoxEmpty = fullNameTextBox.getText().isEmpty() || passTextBox.getText().isEmpty() || emailTextBox.getText().isEmpty();
            String tipo = adminRadioButton.isSelected() ? "administrador" : "miembro";

            if (textBoxEmpty) {
                ShowMessageHelper.showWarningMessage("Los campos Nombre, Correo electronico y Contraseña no deben estar en blanco");
            } else {
                addUser(fullNameTextBox.getText(), passTextBox.getText(), emailTextBox.getText(), tipo, modelo);
                fullNameTextBox.setText("");
                passTextBox.setText("");
                emailTextBox.setText("");
                adminRadioButton.setSelected(false);
            }
        });

        // CARGAR DATOS AL HACER CLICK EN LA TABLA
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila == -1) {
                    return;
                }

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

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(8, 12, 0, 12));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(0, 12, 0, 12));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(fullNameLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(fullNameTextBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; formPanel.add(passLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(passTextBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; formPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(emailTextBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; formPanel.add(adminLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(adminRadioButton, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(8, 0, 8, 0));
        buttonPanel.add(addUserButton);
        buttonPanel.add(updateTableButton);

        JPanel formAndButtonsPanel = new JPanel(new BorderLayout());
        formAndButtonsPanel.add(formPanel, BorderLayout.CENTER);
        formAndButtonsPanel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new EmptyBorder(0, 12, 12, 12));
        centerPanel.add(formAndButtonsPanel, BorderLayout.NORTH);
        centerPanel.add(scroll, BorderLayout.CENTER);

        window.add(topPanel, BorderLayout.NORTH);
        window.add(centerPanel, BorderLayout.CENTER);

        window.setVisible(true);
	}

	private static void addUser(String nombre, String contra, String correo, String tipo, DefaultTableModel modelo) {
	    Administrador administrador = new Administrador(nombre, contra, correo, tipo);
	    administrador.guardarUsuario();

	    refreshTable(modelo);
	}

	private static void updateUser(String id, String nombre, String contra, String correo, String tipo) {
		try {
			Cuenta.actualizarUsuario(id, nombre, contra, correo, tipo);
		}catch(Error e){
			ShowMessageHelper.showErrorMessage(e.getMessage());
		}
	}

	public static void refreshTable(DefaultTableModel modelo) {
		//for(int i = 0; i< modelo.getRowCount(); i++) modelo.removeRow(i);
		modelo.setRowCount(0);

		ArrayList<Cuenta> cuentas = Cuenta.obtenerUsuarios();

		for(Cuenta cuenta : cuentas) {
			modelo.addRow(new Object[]{ cuenta.getUuid(), cuenta.getUsuario(), "******", cuenta.getCorreo(), cuenta.getTipo()});
		}
	}
}
