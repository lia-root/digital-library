package views;

import helpers.CurrentUserHelper;
import helpers.ShowMessageHelper;
import models.Miembro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuMiembros {
    public static void show() {
        // Ventana del menú; tamaño grande para que los botones no queden cortados al abrir.
        JFrame window = new JFrame("Menu de Miembros");
        window.setSize(860, 560);
        // Evita que el usuario reduzca tanto que desaparezcan controles.
        window.setMinimumSize(new Dimension(820, 520));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());

        // Título principal del panel.
        JLabel titleLabel = new JLabel("Panel de Miembros");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Textos de sesión (se reubican luego en la esquina superior derecha).
        JLabel userNameLabel = new JLabel("Usuario: " + CurrentUserHelper.get().getUsuario());
        userNameLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userRoleLabel = new JLabel("Correo:"+ CurrentUserHelper.get().getCorreo());
        userRoleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        userRoleLabel.setForeground(new Color(95, 95, 95));
        userRoleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo que indica la acción esperada.
        JLabel subtitleLabel = new JLabel("Seleccione una opcion");
        subtitleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tres acciones principales con colores distintos por función.
        JButton look4BookButton = new JButton("Buscar Libro");
        JButton perfilButton = new JButton("Mi perfil");
        JButton loginButton = new JButton("Regresar al inicio de sesion");

        // BoxLayout alinea hijos en eje Y; CENTER_ALIGNMENT centra cada componente horizontalmente.
        look4BookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        perfilButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Tamaño preferido + máximo fija el ancho visual de los botones (evita que queden “cortos”).
        look4BookButton.setMaximumSize(new Dimension(380, 42));
        perfilButton.setMaximumSize(new Dimension(380, 42));
        loginButton.setMaximumSize(new Dimension(380, 42));
        look4BookButton.setPreferredSize(new Dimension(380, 42));
        perfilButton.setMaximumSize(new Dimension(380, 42));
        loginButton.setPreferredSize(new Dimension(380, 42));

        look4BookButton.setBackground(new Color(70, 130, 180));
        look4BookButton.setForeground(Color.WHITE);
        look4BookButton.setFocusPainted(false);

        perfilButton.setBackground(new Color(90, 139, 120));
        perfilButton.setForeground(Color.WHITE);
        perfilButton.setFocusPainted(false);

        loginButton.setBackground(new Color(105, 105, 105));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        // Abre otro JFrame y oculta este para no acumular ventanas visibles.

        look4BookButton.addActionListener(e -> {
           Miembro current = (Miembro) CurrentUserHelper.get();

            if (current.ValidarAcceso()){
                LibroMiembros.show();
                window.setVisible(false);
            } else{
                ShowMessageHelper.showInfoMessage("Acceso expiriado, pide a un administrador que lo active");
            }
        });

        perfilButton.addActionListener(e -> {
            Perfil.show();
            window.setVisible(false);
        });

        loginButton.addActionListener(e -> {
            Login.show();
            window.setVisible(false);
        });

        // Contenido vertical: título, espacio, subtítulo, botones cada uno en fila centrada con FlowLayout.
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(24, 36, 24, 36));
        contentPanel.setBackground(Color.WHITE);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(18));

        // Fila dedicada por botón: FlowLayout.CENTER centra el botón dentro del ancho disponible.
        JPanel searchingButtonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        searchingButtonRow.setOpaque(false);
        searchingButtonRow.add(look4BookButton);
        contentPanel.add(searchingButtonRow);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel perfilButtonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        perfilButtonRow.setOpaque(false);
        perfilButtonRow.add(perfilButton);
        contentPanel.add(perfilButton);
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel loginButtonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        loginButtonRow.setOpaque(false);
        loginButtonRow.add(loginButton);
        contentPanel.add(loginButtonRow);

        // GridBagLayout sin restricciones extra centra el panel único hijo.
        JPanel centeredContentPanel = new JPanel(new java.awt.GridBagLayout());
        centeredContentPanel.setOpaque(false);
        centeredContentPanel.add(contentPanel);

        // Caja de usuario/rol alineada a la derecha; separación vertical entre líneas.
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

        // Marco exterior con borde y fondo gris muy claro.
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
