package views;

import helpers.CurrentUserHelper;
import helpers.ShowMessageHelper;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import models.Cuenta;

/**
 * Pantalla de inicio de sesión: construye la ventana, valida campos y delega el acceso al menú según credenciales.
 */
public class Login {
    /**
     * Crea y muestra la ventana de login con estilo tipo “tarjeta” centrada.
     */
	public static void ShowLogin() {
        // Ventana principal del formulario de acceso.
        JFrame window = new JFrame("Login");
        // Tamaño inicial razonable para el contenido sin recortes.
        window.setSize(520, 380);
        // Al cerrar la X se termina la aplicación (evita procesos huérfanos).
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Centra respecto a la pantalla para mejor UX en monitores grandes.
        window.setLocationRelativeTo(null);
        // Un solo contenedor raíz con zonas NORTH/CENTER/SOUTH vía tarjetas internas.
        window.setLayout(new BorderLayout());
        
        // Etiqueta y campo de usuario (texto visible).
        JLabel userNameLabel = new JLabel("Usuario");
        JTextField userNameTextBox = new JTextField(15);

        // Contraseña con JPasswordField para no mostrar caracteres en claro.
        JLabel userPassLabel = new JLabel("Contraseña");
        JPasswordField userPassBox = new JPasswordField(15);
        
        // Botón principal: color y fuente para jerarquía visual; sin borde de foco feo en algunos temas.
        JButton loginButton = new JButton("Ingresar");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        // Al pulsar: leer texto, validar no vacío, limpiar campos y abrir menú si corresponde.
        loginButton.addActionListener(e -> {
            String userName = userNameTextBox.getText();
            // getPassword() evita String intermedios en memoria (mejor práctica que getText en password).
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

        // Panel exterior con fondo suave; GridBagLayout centra el bloque interior.
        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBorder(new EmptyBorder(24, 28, 24, 28));
        cardPanel.setBackground(new Color(248, 250, 252));

        // “Tarjeta” blanca con borde para separar visualmente del fondo.
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 226, 232)),
                new EmptyBorder(18, 18, 18, 18)
        ));
        containerPanel.setBackground(Color.WHITE);

        // Cabecera: título grande y subtítulo en gris para contexto.
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

        // Formulario: columna 0 etiquetas, columna 1 campos; weightx en campos estira al redimensionar.
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

        // Botón ancho completo en la parte baja del formulario.
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(14, 6, 0, 6));
        buttonPanel.add(loginButton, BorderLayout.CENTER);

        containerPanel.add(headerPanel, BorderLayout.NORTH);
        containerPanel.add(formPanel, BorderLayout.CENTER);
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);
        cardPanel.add(containerPanel);

        window.add(cardPanel, BorderLayout.CENTER);
        
        // Hace visible el árbol de componentes; hasta aquí solo se construye en memoria.
        window.setVisible(true);
	}

    /**
     * Comprueba credenciales de demo y abre el menú de administrador si coinciden.
     * @param user nombre ingresado
     * @param pass contraseña ingresada
     */
    private static void showRolMenu(String user, String pass){
        // Credenciales de ejemplo: si coinciden, se muestra el panel de administración.
        if(user.equals("root") && pass.equals("1234")){
            MenuAdministrador.showMenu("root", "Administrador");

            return;
        }else{
            for(Cuenta item : Cuenta.obtenerUsuarios()) {
                if (item.getUsuario().equals(user) && item.comparar_contra(pass)) {
                    CurrentUserHelper.set(item);

                    if(item.getTipo().equals("administrador")){
                        MenuAdministrador.showMenu(user, pass);
                    }else{
                        //menumiembro
                    }

                    return;
                }
            }
        };
        // Mensaje de error cuando las credenciales no son las esperadas.
        ShowMessageHelper.showErrorMessage("Usuario o Contraseña incorrectos");
    }
}
