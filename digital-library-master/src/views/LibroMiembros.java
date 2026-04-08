package views;

import helpers.ShowMessageHelper;
import models.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LibroMiembros extends BaseView{
    public static void show() {
        // Contenedor principal de la pantalla de libros.
        JFrame window = new JFrame("Panel de busqueda");
        window.setSize(900, 560);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        // Gaps horizontales/verticales entre regiones NORTH y CENTER.
        window.setLayout(new BorderLayout(12, 12));

        // Encabezado de sección (solo texto, región norte).
        JLabel titleLabel = new JLabel("Catalogo");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(8, 4, 0, 4));

        // Campos de texto por columna del libro (coinciden con columnas de tabla más abajo).
        JLabel bookTitleLabel = new JLabel("Busqueda:");
        JTextField bookTitleTextBox = new JTextField(15);

        JButton addBookButton = new JButton("Buscar");
        JButton preeScreenButton = new JButton("Menu anterior");

        addBookButton.setBackground(new Color(46, 139, 87));
        addBookButton.setForeground(Color.WHITE);
        addBookButton.setFocusPainted(false);

        preeScreenButton.setBackground(new Color(70, 130, 180));
        preeScreenButton.setForeground(Color.WHITE);
        preeScreenButton.setFocusPainted(false);


        // DefaultTableModel: filas dinámicas; columnas definidas manualmente.
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Titulo");
        modelo.addColumn("Autor");
        modelo.addColumn("Editorial");
        modelo.addColumn("Publicacion");
        modelo.addColumn("Categoria");

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tabla.setSelectionBackground(new Color(220, 238, 255));
        tabla.setSelectionForeground(Color.BLACK);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createTitledBorder("Listado de libros"));

        // Alta: convierte la fecha del spinner a String con el mismo patrón que el editor.
        addBookButton.addActionListener(e -> {
        });

        // Clic en fila: rellena el formulario para editar; -1 = ninguna fila válida.
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
          int fila = tabla.getSelectedRow();
          if (fila == -1) {
              return;
          }
          Contenido.show(modelo.getValueAt(fila, 0).toString());
          window.setVisible(false);
            }
        });

        preeScreenButton.addActionListener(e -> {
            MenuMiembros.show();
            window.setVisible(false);
        });

        // Carga inicial desde disco/archivo según implementación de Libro.obtenerLibros().
        refreshTable(modelo);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(8, 12, 0, 12));
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(preeScreenButton, BorderLayout.PAGE_START);

        // Formulario: GridBagConstraints.weightx=1 en columna 1 estira los JTextField.
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(0, 12, 0, 12));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(bookTitleLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(bookTitleTextBox, gbc);

        // Botones alineados a la derecha bajo el formulario.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        buttonPanel.setBorder(new EmptyBorder(8, 0, 8, 0));
        buttonPanel.add(addBookButton);

        // Formulario + botones en NORTH del centro; tabla en CENTER ocupa el resto (scroll).
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
    public static void refreshTable(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        ArrayList<Libro> libros = Libro.obtenerLibros();

        for(Libro libro : libros) {
            modelo.addRow(new Object[]{ libro.getUuid(), libro.getLTitulo(), libro.getAutor(), libro.getEditorial(), libro.getExpedicion(),libro.getCategoria()});
        }
    }
}
