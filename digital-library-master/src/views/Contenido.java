package views;

import models.Libro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Contenido extends BaseView {

    public static void show(String idBook){
        Libro book = searchBook(idBook);

        JFrame window = new JFrame("Panel de contenido");
        window.setSize(900, 560);
        window.setMinimumSize(new Dimension(820, 520));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout(12, 12));

        if (book == null) {
            JOptionPane.showMessageDialog(window, "No se encontro el libro seleccionado", "Contenido", JOptionPane.WARNING_MESSAGE);
            MenuMiembros.show();
            window.setVisible(false);
            return;
        }

        final Color BG_PAGE = new Color(248, 250, 252);
        final Color BORDER_CARD = new Color(220, 226, 232);
        final Color BTN_PRIMARY = new Color(70, 130, 180);
        final Color TEXT_MUTED = new Color(100, 100, 100);

        JButton backButton = new JButton("Salir");
        backButton.setBackground(BTN_PRIMARY);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        backButton.addActionListener(e -> {
            LibroMiembros.show();
            window.setVisible(false);
        });

        JLabel titleLabel = new JLabel("Contenido");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(8, 4, 0, 4));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(8, 12, 0, 12));
        topPanel.add(backButton, BorderLayout.PAGE_START);
        topPanel.add(titleLabel, BorderLayout.WEST);

        Font metaLabelFont = new Font("SansSerif", Font.PLAIN, 13);
        Font metaValueFont = new Font("SansSerif", Font.PLAIN, 13);

        JLabel bookTitleLabel = new JLabel("Titulo:");
        bookTitleLabel.setFont(metaLabelFont);
        bookTitleLabel.setForeground(TEXT_MUTED);
        JLabel bookTitleValue = new JLabel(book.getLTitulo());
        bookTitleValue.setFont(metaValueFont);

        JLabel boorAuthorLabel = new JLabel("Autor:");
        boorAuthorLabel.setFont(metaLabelFont);
        boorAuthorLabel.setForeground(TEXT_MUTED);
        JLabel authorValue = new JLabel(book.getAutor());
        authorValue.setFont(metaValueFont);

        JLabel bookEditorialLabel = new JLabel("Editorial:");
        bookEditorialLabel.setFont(metaLabelFont);
        bookEditorialLabel.setForeground(TEXT_MUTED);
        JLabel editorialValue = new JLabel(book.getEditorial());
        editorialValue.setFont(metaValueFont);

        JLabel bookCategorylLabel = new JLabel("Categoria:");
        bookCategorylLabel.setFont(metaLabelFont);
        bookCategorylLabel.setForeground(TEXT_MUTED);
        JLabel categoryValue = new JLabel(book.getCategoria());
        categoryValue.setFont(metaValueFont);

        JTextArea textArea = new JTextArea();
        textArea.setText(area());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane textScroll = new JScrollPane(textArea);
        textScroll.setBorder(BorderFactory.createTitledBorder("Contenido del libro"));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 2 columnas (4 celdas por fila): etiqueta/valor | etiqueta/valor
        gbc.gridy = 0;
        gbc.gridx = 0; gbc.weightx = 0; formPanel.add(bookTitleLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(bookTitleValue, gbc);
        gbc.gridx = 2; gbc.weightx = 0; formPanel.add(boorAuthorLabel, gbc);
        gbc.gridx = 3; gbc.weightx = 1; formPanel.add(authorValue, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0; gbc.weightx = 0; formPanel.add(bookEditorialLabel, gbc);
        gbc.gridx = 1; gbc.weightx = 1; formPanel.add(editorialValue, gbc);
        gbc.gridx = 2; gbc.weightx = 0; formPanel.add(bookCategorylLabel, gbc);
        gbc.gridx = 3; gbc.weightx = 1; formPanel.add(categoryValue, gbc);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 12));
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(textScroll, BorderLayout.CENTER);

        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBorder(new EmptyBorder(0, 12, 12, 12));
        cardPanel.setBackground(BG_PAGE);

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_CARD),
                new EmptyBorder(20, 22, 22, 22)
        ));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.add(centerPanel, BorderLayout.CENTER);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 1;
        gbcMain.weighty = 1;
        gbcMain.fill = GridBagConstraints.BOTH;
        cardPanel.add(containerPanel, gbcMain);

        window.add(topPanel, BorderLayout.NORTH);
        window.add(cardPanel, BorderLayout.CENTER);
        window.setVisible(true);
    }
    public static String area(){
        return " Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin condimentum bibendum nibh, ut faucibus risus accumsan quis. Nullam efficitur sagittis lorem vel lacinia. Nunc fringilla, elit in sodales lobortis, ex justo porta lectus, a fringilla erat dolor vitae arcu. Integer euismod enim ac libero interdum, nec volutpat ipsum hendrerit. Vestibulum nisl sapien, rhoncus eu quam interdum, tincidunt euismod risus. Integer sodales ipsum a enim condimentum, a tempor nunc luctus. Nullam elementum hendrerit bibendum. In ac vulputate mi, et tincidunt nisi. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nulla non diam a felis sagittis rutrum non quis augue. Nulla non purus accumsan libero pretium malesuada. Morbi ullamcorper mauris leo.\n" +
                "\n" +
                "Nullam sed sollicitudin nisi. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Proin lorem eros, efficitur id neque vel, sollicitudin mattis massa. Duis sed arcu nec sem efficitur euismod. Nunc efficitur, tortor sit amet vehicula vestibulum, ante orci sollicitudin odio, eleifend rutrum quam mi rutrum erat. Maecenas eget urna efficitur, pharetra sapien at, tincidunt odio. In tincidunt orci eu fermentum faucibus. Donec euismod vulputate rhoncus. Maecenas euismod massa eget pharetra cursus. Maecenas eu nisi nec metus efficitur suscipit placerat ut neque. Etiam id risus eget mauris pretium lobortis et at tortor. Sed quis ornare quam.\n" +
                "\n" +
                "Interdum et malesuada fames ac ante ipsum primis in faucibus. In pellentesque dapibus semper. Quisque fringilla ultrices felis quis porttitor. Suspendisse eget purus id nulla porttitor lobortis. Mauris posuere, nunc eu dictum posuere, massa tortor blandit massa, ut bibendum elit quam bibendum nisi. Donec convallis nibh tortor, id maximus enim fermentum vel. Suspendisse tempus aliquam est, a fringilla mi tincidunt sed. Etiam non tincidunt odio, eu pulvinar ante. Suspendisse potenti. Fusce pulvinar mattis purus, sit amet egestas ante ultricies in. Integer a ex pellentesque, euismod nisl vitae, aliquet tortor.\n" +
                "\n" +
                "Nunc iaculis pretium ipsum at placerat. Mauris quis nulla orci. Donec mollis, arcu id laoreet tristique, augue odio rutrum ante, eget gravida tortor nisi non augue. Duis hendrerit sit amet urna ut iaculis. Nulla sit amet viverra dolor. Fusce auctor risus nec lacus egestas, eget lacinia mauris tempus. Vestibulum sed fringilla justo. Nullam in lacus dui. Proin nec nunc id metus hendrerit maximus. Donec dapibus feugiat ligula, quis tristique ex tempus eu. Cras tincidunt ultrices turpis, vitae hendrerit nisl condimentum in. Nullam libero sapien, facilisis sit amet purus in, tincidunt facilisis risus.\n" +
                "\n" +
                "Mauris placerat vehicula erat, eget dapibus nisl auctor non. Donec congue enim ac odio molestie mattis. Integer pulvinar lorem sit amet diam iaculis, eget maximus nibh euismod. Duis eget hendrerit ante, at consequat erat. Quisque euismod, elit at eleifend blandit, eros tortor accumsan mauris, maximus auctor augue risus at diam. Fusce nec sollicitudin enim, non tempor tellus. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n" +
                "\n" +
                "Donec et mi ante. Cras viverra leo vitae gravida commodo. Pellentesque eu nisi augue. Ut sed augue elementum, pulvinar purus vitae, eleifend dui. Etiam feugiat cursus odio ut tempus. Donec at lobortis elit. Praesent sed varius enim, quis aliquet enim. Donec pulvinar quis sapien nec congue. Aliquam erat volutpat. Praesent dapibus commodo elit eget aliquet. Praesent sagittis scelerisque aliquam. Maecenas condimentum libero a faucibus tincidunt. Fusce elit neque, blandit vitae fringilla ac, viverra ac urna.\n" +
                "\n" +
                "Nam quis hendrerit felis, vitae dignissim nisi. Suspendisse potenti. Aenean euismod suscipit mauris, vitae posuere lacus efficitur nec. Pellentesque eu viverra eros, sit amet venenatis tellus. Sed dictum, diam et blandit commodo, nulla nisi eleifend ligula, sed tincidunt arcu tortor a quam. In ac varius lectus. Pellentesque erat mi, tristique ut lacus vel, venenatis feugiat nunc. Phasellus vel dolor dignissim, condimentum enim fringilla, consectetur tortor. Nullam auctor viverra enim vitae molestie. Sed malesuada euismod ornare. Mauris sit amet mi tempor, scelerisque ex in, imperdiet arcu. ";
    }
    public static Libro searchBook(String id) {
        ArrayList<Libro> catalogo = Libro.obtenerLibros();
        Libro libro=null;

        for (Libro item : catalogo) {
            if (item.getUuid().equals(id)) {
               libro=item;
               break;
            }
        }
        return libro;
    }
}
