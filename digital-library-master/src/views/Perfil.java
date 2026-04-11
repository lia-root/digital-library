package views;

import helpers.CurrentUserHelper;
import helpers.UserHistorialHelper;
import models.Cuenta;
import models.Libro;
import models.Miembro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * Vista de perfil de miembro: foto, datos de cuenta e historial de lectura, con el mismo lenguaje visual que Login y paneles CRUD.
 */
public class Perfil extends BaseView{
    private static JLabel photoLabel;

    private static final Color BG_PAGE = new Color(248, 250, 252);
    private static final Color BORDER_CARD = new Color(220, 226, 232);
    private static final Color TEXT_MUTED = new Color(100, 100, 100);
    private static final Color BTN_PRIMARY = new Color(70, 130, 180);
    private static final Color TABLE_SELECT_BG = new Color(220, 238, 255);

    public static void show() {

        Miembro user = (Miembro) CurrentUserHelper.get();

        JFrame window = new JFrame("Mi Perfil");
        window.setSize(1040, 640);
        window.setMinimumSize(new Dimension(920, 580));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout(12, 12));

        JButton backButton = new JButton("Menu anterior");
        backButton.setBackground(BTN_PRIMARY);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        backButton.addActionListener(e -> {
            MenuMiembros.show();
            window.setVisible(false);
        });

        JLabel titleLabel = new JLabel("Mi perfil");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(8, 4, 0, 4));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(8, 12, 0, 12));
        topPanel.add(backButton, BorderLayout.PAGE_START);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBorder(new EmptyBorder(0, 12, 12, 12));
        cardPanel.setBackground(BG_PAGE);

        JPanel containerPanel = new JPanel(new BorderLayout(0, 16));
        containerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_CARD),
                new EmptyBorder(20, 22, 22, 22)
        ));

        containerPanel.setBackground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Consulta tu informacion y actividad reciente");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitleLabel.setForeground(TEXT_MUTED);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(180, 0));
        leftPanel.setBorder(new EmptyBorder(0, 0, 0, 20));

        photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(140, 140));
        photoLabel.setMaximumSize(new Dimension(140, 140));
        photoLabel.setMinimumSize(new Dimension(140, 140));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        photoLabel.setBorder(BorderFactory.createLineBorder(BORDER_CARD, 1));
        photoLabel.setOpaque(true);
        photoLabel.setBackground(new Color(250, 251, 252));

        loadDefaultProfileImage();

        JButton changePhotoBtn = new JButton("Cambiar imagen");
        changePhotoBtn.setBackground(BTN_PRIMARY);
        changePhotoBtn.setForeground(Color.WHITE);
        changePhotoBtn.setFocusPainted(false);
        changePhotoBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        changePhotoBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        changePhotoBtn.addActionListener(Perfil::changeImage);

        JPanel photoRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        photoRow.setOpaque(false);
        photoRow.add(photoLabel);

        leftPanel.add(photoRow);
        leftPanel.add(Box.createVerticalStrut(10));
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnRow.setOpaque(false);
        btnRow.add(changePhotoBtn);
        leftPanel.add(btnRow);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("SansSerif", Font.PLAIN, 13);
        Font valueFont = new Font("SansSerif", Font.PLAIN, 13);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel l1 = new JLabel("Nombre:");
        l1.setFont(labelFont);
        l1.setForeground(TEXT_MUTED);
        infoPanel.add(l1, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        JLabel v1 = new JLabel(user.getUsuario());
        v1.setFont(valueFont);
        infoPanel.add(v1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel l2 = new JLabel("Correo:");
        l2.setFont(labelFont);
        l2.setForeground(TEXT_MUTED);
        infoPanel.add(l2, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        JLabel v2 = new JLabel(user.getCorreo());
        v2.setFont(valueFont);
        infoPanel.add(v2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        JLabel l3 = new JLabel("Rol:");
        l3.setFont(labelFont);
        l3.setForeground(TEXT_MUTED);
        infoPanel.add(l3, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        JLabel v3 = new JLabel(user.getTipo());
        v3.setFont(valueFont);
        infoPanel.add(v3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        JLabel l5 = new JLabel("Fecha vencimiento:");
        l5.setFont(labelFont);
        l5.setForeground(TEXT_MUTED);
        infoPanel.add(l5, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        JLabel v5 = new JLabel(user.getFechaVencimiento());
        v5.setFont(valueFont);
        infoPanel.add(v5, gbc);

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Titulo");
        model.addColumn("Autor");
        model.addColumn("Editorial");
        model.addColumn("Publicacion");
        model.addColumn("Categoria");
        model.addColumn("Fecha/Hora de consulta");

        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        table.setSelectionBackground(TABLE_SELECT_BG);
        table.setSelectionForeground(Color.BLACK);
        table.setPreferredScrollableViewportSize(new Dimension(780, 240));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historial de lectura"));

        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setOpaque(false);
        rightPanel.add(infoPanel, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout(25, 25));
        centerPanel.setOpaque(false);
        centerPanel.add(leftPanel, BorderLayout.WEST);
        centerPanel.add(rightPanel, BorderLayout.CENTER);

        containerPanel.add(headerPanel, BorderLayout.NORTH);
        containerPanel.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 1;
        gbcMain.weighty = 1;
        gbcMain.fill = GridBagConstraints.BOTH;

        cardPanel.add(containerPanel, gbcMain);

        refreshTable(model);

        window.add(topPanel, BorderLayout.NORTH);
        window.add(cardPanel, BorderLayout.CENTER);

        window.setVisible(true);
    }

    private static void changeImage(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            setImage(file.getAbsolutePath());
        }
    }

    /**
     * Carga la imagen por defecto desde el classpath (carpeta {@code images} en la raíz de compilación),
     * no desde una ruta relativa al disco: esas rutas fallan si el directorio de trabajo no es el del proyecto.
     */
    private static void loadDefaultProfileImage() {
        URL url = Perfil.class.getResource("/images/porfile.png");
        if (url == null) {
            url = Perfil.class.getResource("/images/profile.png");
        }
        if (url != null) {
            setImage(new ImageIcon(url));
        }
    }

    private static void setImage(String path) {
        setImage(new ImageIcon(path));
    }

    private static void setImage(ImageIcon icon) {
        if (icon == null || photoLabel == null) {
            return;
        }
        Image raw = icon.getImage();
        if (raw == null) {
            return;
        }
        Image scaled = raw.getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        photoLabel.setIcon(new ImageIcon(scaled));
    }

    public static void refreshTable(DefaultTableModel model) {
        model.setRowCount(0);
        Miembro miembro = (Miembro) CurrentUserHelper.get();
        ArrayList<Libro> libros = UserHistorialHelper.get(miembro);

        for(Libro libro : libros) {
            model.addRow(new Object[]{ libro.getUuid(), libro.getLTitulo(), libro.getAutor(), libro.getEditorial(), libro.getExpedicion(), libro.getCategoria(), libro.getFechaDeLectua()});
        }
    }
}
