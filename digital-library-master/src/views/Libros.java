package views;

import helpers.ShowMessageHelper;
import models.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Vista CRUD de libros: formulario, tabla y persistencia vía modelo {@link Libro}.
 */
public class Libros {
    /**
     * Arma la ventana completa: título, formulario en rejilla, botones y tabla con scroll.
     */
    public static void showUserPanel() {
        // Contenedor principal de la pantalla de libros.
        JFrame window = new JFrame("Panel de Libros");
        window.setSize(900, 560);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        // Gaps horizontales/verticales entre regiones NORTH y CENTER.
        window.setLayout(new BorderLayout(12, 12));

        // Encabezado de sección (solo texto, región norte).
        JLabel titleLabel = new JLabel("Registro de libros");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(8, 4, 0, 4));

        // Campos de texto por columna del libro (coinciden con columnas de tabla más abajo).
        JLabel bookTitleLabel = new JLabel("Titulo");
        JTextField bookTitleTextBox = new JTextField(15);

        JLabel boorAuthorLabel = new JLabel("Autor");
        JTextField authorTextBox = new JTextField(15);

        JLabel bookEditorialLabel = new JLabel("Editorial");
        JTextField editorialTextBox = new JTextField(15);

        // Fecha con JSpinner + modelo de fecha: evita texto libre y formatea con DateEditor.
        JLabel publicationDateLabel = new JLabel("Fecha de publicacion");
        JSpinner publicationDateSpinner = new JSpinner(new SpinnerDateModel());
        publicationDateSpinner.setEditor(new JSpinner.DateEditor(publicationDateSpinner, "dd-MM-yyyy"));
        publicationDateSpinner.setValue(new Date());

        JLabel bookCategoryLabel = new JLabel("Categoria");
        JTextField categoryTextBox = new JTextField(15);


        JButton addBookButton = new JButton("Agregar");
        JButton updateTableButton = new JButton("Actualizar");
        addBookButton.setBackground(new Color(46, 139, 87));
        addBookButton.setForeground(Color.WHITE);
        addBookButton.setFocusPainted(false);
        updateTableButton.setBackground(new Color(70, 130, 180));
        updateTableButton.setForeground(Color.WHITE);
        updateTableButton.setFocusPainted(false);

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
            String publicationDate = new SimpleDateFormat("dd-MM-yyyy").format((Date) publicationDateSpinner.getValue());
            boolean textBoxEmpty = bookTitleTextBox.getText().isEmpty() || authorTextBox.getText().isEmpty() || editorialTextBox.getText().isEmpty()
                    || publicationDate.isEmpty() || categoryTextBox.getText().isEmpty();
            if (textBoxEmpty) {
                ShowMessageHelper.showWarningMessage("Por favor, llena todos los campos");
            } else {
                addBook(bookTitleTextBox.getText(), authorTextBox.getText(), editorialTextBox.getText(), publicationDate, categoryTextBox.getText(),  modelo);
                bookTitleTextBox.setText("");
                authorTextBox.setText("");
                editorialTextBox.setText("");
                categoryTextBox.setText("");
                publicationDateSpinner.setValue(new Date());
            }
        });

        // Clic en fila: rellena el formulario para editar; -1 = ninguna fila válida.
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila == -1) {
                    return;
                }

                bookTitleTextBox.setText(modelo.getValueAt(fila, 1).toString());
                authorTextBox.setText(modelo.getValueAt(fila, 2).toString());
                editorialTextBox.setText(modelo.getValueAt(fila, 3).toString());
                try {
                    Date parsedDate = new SimpleDateFormat("dd-MM-yyyy").parse(modelo.getValueAt(fila, 4).toString());
                    publicationDateSpinner.setValue(parsedDate);
                } catch (Exception ex) {
                    publicationDateSpinner.setValue(new Date());
                }
                categoryTextBox.setText(modelo.getValueAt(fila, 5).toString());
            }
        });

        // Actualizar fila seleccionada en modelo y en archivo vía Libro.actualizarLibro.
        updateTableButton.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila != -1) {
                modelo.setValueAt(bookTitleTextBox.getText(), fila, 1);
                modelo.setValueAt(authorTextBox.getText(), fila, 2);
                modelo.setValueAt(editorialTextBox.getText(), fila, 3);
                String publicationDate = new SimpleDateFormat("dd-MM-yyyy").format((Date) publicationDateSpinner.getValue());
                modelo.setValueAt(publicationDate, fila, 4);
                modelo.setValueAt(categoryTextBox.getText(), fila, 5);

                updateBook(modelo.getValueAt(fila, 0).toString(), bookTitleTextBox.getText(), authorTextBox.getText(), editorialTextBox.getText(), publicationDate, categoryTextBox.getText());

                bookTitleTextBox.setText("");
                authorTextBox.setText("");
                editorialTextBox.setText("");
                categoryTextBox.setText("");
                publicationDateSpinner.setValue(new Date());

            } else {
                ShowMessageHelper.showWarningMessage("Selecciona una fila");
            }
        });

        // Carga inicial desde disco/archivo según implementación de Libro.obtenerLibros().
        refreshTable(modelo);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(8, 12, 0, 12));
        topPanel.add(titleLabel, BorderLayout.WEST);

        // Formulario: GridBagConstraints.weightx=1 en columna 1 estira los JTextField.
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(0, 12, 0, 12));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(bookTitleLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(bookTitleTextBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; formPanel.add(boorAuthorLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(authorTextBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; formPanel.add(bookEditorialLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(editorialTextBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; formPanel.add(publicationDateLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(publicationDateSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0; formPanel.add(bookCategoryLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(categoryTextBox, gbc);

        // Botones alineados a la derecha bajo el formulario.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBorder(new EmptyBorder(8, 0, 8, 0));
        buttonPanel.add(addBookButton);
        buttonPanel.add(updateTableButton);

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

    /**
     * Crea un {@link Libro}, lo persiste y refresca la tabla.
     */
    private static void addBook(String titulo, String autor, String editorial,String expedicion, String categoria, DefaultTableModel modelo) {
            Libro libro = new Libro(titulo, autor, editorial, expedicion, categoria);
            libro.guardarLibro();

        refreshTable(modelo);
    }

    /**
     * Delega la actualización al modelo; captura Error para mostrar mensaje (patrón actual del proyecto).
     */
    private static void updateBook(String id, String titulo, String autor, String editorial,String expedicion, String categoria) {
        try {
            Libro.actualizarLibro(id, titulo, autor, editorial, expedicion,categoria);
        }catch(Error e){
            ShowMessageHelper.showErrorMessage(e.getMessage());
        }
    }

    /**
     * Vuelve a leer la lista desde el modelo y repuebla filas (setRowCount(0) limpia sin bucle manual).
     */
    public static void refreshTable(DefaultTableModel modelo) {
        modelo.setRowCount(0);

        ArrayList<Libro> libros = Libro.obtenerLibros();

        for(Libro libro : libros) {
            modelo.addRow(new Object[]{ libro.getUuid(), libro.getLTitulo(), libro.getAutor(), libro.getEditorial(), libro.getExpedicion(),libro.getCategoria()});
        }
    }
}
