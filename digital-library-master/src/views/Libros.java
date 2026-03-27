package views;

import helpers.ShowMessageHelper;
import models.Administrador;
import models.Cuenta;
import models.Libro;
import models.Miembro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static views.Usuarios.refreshTable;

public class Libros {
    public static void showUserPanel() {
        JFrame window = new JFrame("Panel de Libros");
        window.setSize(500, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("Registro de libros");

        JLabel bookTitleLabel = new JLabel("Titulo");
        JTextField bookTitleTextBox = new JTextField(15);

        JLabel boorAuthorLabel = new JLabel("Autor");
        JTextField authorTextBox = new JTextField(15);

        JLabel bookEditorialLabel = new JLabel("Editorial");
        JTextField editorialTextBox = new JTextField(15);

        JLabel publicationDateLabel = new JLabel("Fecha de publicacion");
        JDate
        JTextField  publicationDateTextBox = new JTextField(15);

        JLabel bookCategoryLabel = new JLabel("Categoria");
        JTextField categoryTextBox = new JTextField(15);


        JButton addBookButton = new JButton("Agregar");
        JButton updateTableButton = new JButton("Actualizar");

        // Modelo de tabla
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Titulo");
        modelo.addColumn("Autor");
        modelo.addColumn("Editorial");
        modelo.addColumn("Publicacion");
        modelo.addColumn("Categoria");

        // Tabla
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(450, 200));

        addBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean textBoxEmpty = bookTitleTextBox.getText().isEmpty() || authorTextBox.getText().isEmpty() || editorialTextBox.getText().isEmpty()
                        || publicationDateTextBox.getText().isEmpty() || categoryTextBox.getText().isEmpty();
                if(textBoxEmpty) {
                    ShowMessageHelper.showWarningMessage("Porfavor, llena todos los campos");
                }else {
                    addBook(bookTitleTextBox.getText(), authorTextBox.getText(), editorialTextBox.getText(), publicationDateTextBox.getText(), categoryTextBox.getText(),  modelo);
                    bookTitleTextBox.setText("");
                    authorTextBox.setText("");
                    editorialTextBox.setText("");
                    publicationDateTextBox.setText("");
                    categoryTextBox.setText("");

                    ShowMessageHelper.showWarningMessage("revisa los archivos");
                }
            }
        });

        // CARGAR DATOS AL HACER CLICK EN LA TABLA
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                String rol = modelo.getValueAt(fila, 5).toString();

                bookTitleTextBox.setText(modelo.getValueAt(fila, 1).toString());
                authorTextBox.setText(modelo.getValueAt(fila, 2).toString());
                editorialTextBox.setText(modelo.getValueAt(fila, 3).toString());
                publicationDateTextBox.setText(modelo.getValueAt(fila, 4).toString());
                categoryTextBox.setText(modelo.getValueAt(fila, 5).toString());
            }
        });

        // EDITAR
        updateTableButton.addActionListener(e -> {
            int fila = tabla.getSelectedRow();

            if (fila != -1) {
                modelo.setValueAt(bookTitleTextBox.getText(), fila, 1);
                modelo.setValueAt(authorTextBox.getText(), fila, 2);
                modelo.setValueAt(editorialTextBox.getText(), fila, 3);
                modelo.setValueAt(publicationDateTextBox.getText(), fila, 4);
                modelo.setValueAt(categoryTextBox.getText(), fila, 5);

                updateBook(modelo.getValueAt(fila, 0).toString(), bookTitleTextBox.getText(), boorAuthorLabel.getText(), editorialTextBox.getText(),publicationDateTextBox.getText(), categoryTextBox.getText());

                bookTitleTextBox.setText("");
                authorTextBox.setText("");
                editorialTextBox.setText("");
                publicationDateTextBox.setText("");
                categoryTextBox.setText("");

            } else {
                ShowMessageHelper.showWarningMessage("Selecciona una fila");
            }
        });

        refreshTable(modelo);

        window.add(titleLabel);
        window.add(titleLabel);
        window.add(bookTitleLabel);
        window.add(bookTitleTextBox);
        window.add(boorAuthorLabel);
        window.add(authorTextBox);
        window.add(bookEditorialLabel);
        window.add(editorialTextBox);
        window.add(publicationDateLabel);
        window.add(publicationDateTextBox);
        window.add(bookCategoryLabel);
        window.add(categoryTextBox);

        window.add(addBookButton);
        window.add(updateTableButton);
        window.add(scroll);

        window.setVisible(true);
    }

    private static void addBook(String titulo, String autor, String editorial,String expedicion, String categoria, DefaultTableModel modelo) {
            Libro libro = new Libro(titulo, autor, editorial, expedicion, categoria);
            libro.guardarLibro();

        refreshTable(modelo);
    }
    private static void updateBook(String id, String titulo, String autor, String editorial,String expedicion, String categoria) {
        try {
            Libro.actualizarLibro(id, titulo, autor, editorial, expedicion,categoria);
        }catch(Error e){
            ShowMessageHelper.showErrorMessage(e.getMessage());
        }
    }
    //CAda vez que se agrega, actualzia la tabla
    public static void refreshTable(DefaultTableModel modelo) {
        //for(int i = 0; i< modelo.getRowCount(); i++) modelo.removeRow(i);
        modelo.setRowCount(0);

        ArrayList<Libro> libros = Libro.obtenerLibros();

        for(Libro libro : libros) {
            modelo.addRow(new Object[]{ libro.getUuid(), libro.getLTitulo(), libro.getAutor(), libro.getEditorial(), libro.getExpedicion(),libro.getCategoria()});
        }
    }
}
