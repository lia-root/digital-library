package views;

import java.awt.*;
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

import helpers.CurrentUserHelper;
import helpers.ShowMessageHelper;
import models.Administrador;
import models.Cuenta;

/**
 * Vista de gestión de cuentas de usuario: alta, edición en tabla y sincronización con archivos vía {@link Cuenta}.
 */
public class Usuarios extends BaseView{
    /**
     * Construye la ventana con el mismo patrón visual que {@link Libros}: norte título, centro formulario+botones, tabla abajo.
     */
	public static void show() {
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

      // JPasswordField oculta caracteres; en tabla se muestra "******" por seguridad.
      JLabel passLabel = new JLabel("Contraseña");
      JPasswordField passTextBox = new JPasswordField(15);

      JLabel emailLabel = new JLabel("Correo electronico");
      JTextField emailTextBox = new JTextField(15);

      // Radio sin grupo: solo indica si el nuevo/actualizado usuario es administrador o miembro.
      JLabel adminLabel = new JLabel("Marque si este usuario es un administrador");
      JRadioButton adminRadioButton = new JRadioButton();

      JButton preeScreenButton = new JButton("Menu anterior");
      JButton dellUserButton = new JButton("Eliminar");
      JButton addUserButton = new JButton("Agregar");
      JButton updateTableButton = new JButton("Actualizar");

      addUserButton.setBackground(new Color(46, 139, 87));
      addUserButton.setForeground(Color.WHITE);
      addUserButton.setFocusPainted(false);

      updateTableButton.setBackground(new Color(70, 130, 180));
      updateTableButton.setForeground(Color.WHITE);
      updateTableButton.setFocusPainted(false);

      dellUserButton.setBackground(new Color(150, 130, 180));
      dellUserButton.setForeground(Color.WHITE);
      dellUserButton.setFocusPainted(false);

      preeScreenButton.setBackground(new Color(70, 130, 180));
      preeScreenButton.setForeground(Color.WHITE);
      preeScreenButton.setFocusPainted(false);

      DefaultTableModel modelo = new DefaultTableModel();

      modelo.addColumn("ID");
      modelo.addColumn("Nombre");
      modelo.addColumn("Contraseña");
      modelo.addColumn("Correo electronico");
      modelo.addColumn("Rol");

      JTable tabla = new JTable(modelo);
      tabla.setRowHeight(24);
      tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
      tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
      tabla.setSelectionBackground(new Color(220, 238, 255));
      tabla.setSelectionForeground(Color.BLACK);
      JScrollPane scroll = new JScrollPane(tabla);
      scroll.setBorder(BorderFactory.createTitledBorder("Listado de usuarios"));
        
      // Alta: tipo según radio; Administrador guarda vía clase Administrador (patrón existente).
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

      dellUserButton.addActionListener(e -> {
          int fila = tabla.getSelectedRow();

          if (fila != -1) {
              dellUser(modelo.getValueAt(fila, 0).toString(),modelo);

              fullNameTextBox.setText("");
              emailTextBox.setText("");
              passTextBox.setText("");
              adminRadioButton.setSelected(false);
              ShowMessageHelper.showInfoMessage("Usuario eliminado");
          } else {
              ShowMessageHelper.showWarningMessage("Selecciona una fila");
          }

      });

      // Doble uso: cargar datos al formulario; bloquea edición si la fila es otro administrador.
      tabla.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
              int fila = tabla.getSelectedRow();
              if (fila == -1) return;

              String rol = modelo.getValueAt(fila, 4).toString();

              if(rol.equals("administrador") && !CurrentUserHelper.get().getUsuario().equals("root")) {
                  ShowMessageHelper.showWarningMessage("No es posible editar la informacion de otro Administrador");
                  return ;
              }

              fullNameTextBox.setText(modelo.getValueAt(fila, 1).toString());
              passTextBox.setText("");
              emailTextBox.setText(modelo.getValueAt(fila, 3).toString());
          }
      });

      // Edición: la columna contraseña en tabla pasa a "******" visualmente; el modelo sigue usando passTextBox real al persistir.
      updateTableButton.addActionListener(e -> {
        boolean textBoxEmpty = fullNameTextBox.getText().isEmpty() || passTextBox.getText().isEmpty() || emailTextBox.getText().isEmpty();
        if (textBoxEmpty) {
          ShowMessageHelper.showWarningMessage("Los campos Nombre, Correo electronico y Contraseña no deben estar en blanco");
          return;
        }
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

      preeScreenButton.addActionListener(e -> {
          MenuAdministrador.show();
          window.setVisible(false);
      });
        
      refreshTable(modelo);

      JPanel topPanel = new JPanel(new BorderLayout());
      topPanel.setBorder(new EmptyBorder(8, 12, 0, 12));
      topPanel.add(preeScreenButton, BorderLayout.PAGE_START);
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
      buttonPanel.add(dellUserButton);

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

    /**
     * Instancia {@link Administrador} y delega el guardado; luego refresca la tabla.
     */
	private static void addUser(String nombre, String contra, String correo, String tipo, DefaultTableModel modelo) {
	    Cuenta user = new Cuenta(nombre, contra, correo, tipo);
	    user.guardarUsuario();

	    refreshTable(modelo);
	}

	private static void updateUser(String id, String nombre, String contra, String correo, String tipo) {
		try {
			Cuenta.actualizarUsuario(id, nombre, contra, correo, tipo);
		}catch(Error e){
			ShowMessageHelper.showErrorMessage(e.getMessage());
		}
	}
    /*
     * Persiste cambios vía {@link Cuenta#eliminaUsuario}; errores se muestran al usuario.
     */
    private static void dellUser(String id, DefaultTableModel modelo){
        try {
            Cuenta.eliminarUsuario(id);
        }catch(Error e){
            ShowMessageHelper.showErrorMessage(e.getMessage());
        }
        refreshTable(modelo);
    }

    /**
     * Reconstruye filas desde la fuente de datos; contraseña mostrada como máscara fija.
     */
	public static void refreshTable(DefaultTableModel modelo) {
		modelo.setRowCount(0);

		ArrayList<Cuenta> cuentas = Cuenta.obtenerUsuarios();

		for(Cuenta cuenta : cuentas) {
			modelo.addRow(new Object[]{ cuenta.getUuid(), cuenta.getUsuario(), "******", cuenta.getCorreo(), cuenta.getTipo()});
		}
	}
}
