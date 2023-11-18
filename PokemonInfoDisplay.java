import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public class PokemonInfoDisplay {
    public static void displayInfo(String selectedPokemon, String type, String selectedAbility,
                                   String selectedMove, String imageUrl, String moveProperties, String moveDescription) {
        JFrame frame = new JFrame("Información del Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String urlBackground = backgroundCheck(type);
        ImageIcon typeBackground = new ImageIcon(urlBackground);
        JLabel backgroundTypeLabel = new JLabel(typeBackground);

        frame.setSize(backgroundTypeLabel.getPreferredSize().width+20, backgroundTypeLabel.getPreferredSize().height+45);
        frame.setResizable(false);

        JLayeredPane layeredPane = new JLayeredPane();

        JLabel imageLabel = new JLabel();
        try {
            URL imageURL = new URL(imageUrl);
            BufferedImage img = ImageIO.read(imageURL);
            ImageIcon imageIcon = new ImageIcon(img);
            imageLabel.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }



        JLabel nameLabel = new JLabel("Nombre: " + selectedPokemon);
        JLabel typeLabel = new JLabel("Tipo: " + type);
        JLabel abilityLabel = new JLabel("Habilidad seleccionada: " + selectedAbility);
        JLabel moveLabel = new JLabel("Movimiento seleccionado: " + selectedMove);
        JLabel moveDescLabel = new JLabel("Descripcion del movimiento: " + moveDescription);
        JLabel movePropLabel = new JLabel("Propiedades del movimiento: " + moveProperties);

        // Establecer los límites de los componentes
        backgroundTypeLabel.setBounds(0,0, backgroundTypeLabel.getPreferredSize().width, backgroundTypeLabel.getPreferredSize().height);
        imageLabel.setBounds(230, 100, imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height);
        nameLabel.setBounds(10, 10, nameLabel.getPreferredSize().width, nameLabel.getPreferredSize().height);
        typeLabel.setBounds(10, 30, typeLabel.getPreferredSize().width, typeLabel.getPreferredSize().height);
        abilityLabel.setBounds(10, 50, abilityLabel.getPreferredSize().width, abilityLabel.getPreferredSize().height);
        moveLabel.setBounds(10, 70, moveLabel.getPreferredSize().width, moveLabel.getPreferredSize().height);
        moveDescLabel.setBounds(10, 90, moveDescLabel.getPreferredSize().width, moveDescLabel.getPreferredSize().height);
        movePropLabel.setBounds(10, 110, movePropLabel.getPreferredSize().width, movePropLabel.getPreferredSize().height);

        // Añadir los componentes al JLayeredPane con diferentes capas
        layeredPane.add(backgroundTypeLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(imageLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(nameLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(typeLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(abilityLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(moveLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(moveDescLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(movePropLabel, JLayeredPane.PALETTE_LAYER);

        // Establecer el tamaño preferido del JLayeredPane
        layeredPane.setPreferredSize(new Dimension(backgroundTypeLabel.getPreferredSize().width, backgroundTypeLabel.getPreferredSize().height));

        frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }



    public static String backgroundCheck(String tipo) {
        Pattern pattern = Pattern.compile("Primary Type: (\\w+)(, Secondary Type: \\d+)?");
        Matcher matcher = pattern.matcher(tipo);

        if (matcher.find()) {
            String tipoPrincipal = matcher.group(1);
            return "fondosTipos/" + tipoPrincipal + ".png";
        } else {
            return "";
        }
    }


    public static String typeCheck(String tipo){

        Pattern pattern = Pattern.compile("Type: (\\w+), Power: \\d+, Accuracy: \\d+");
        Matcher matcher = pattern.matcher(tipo);

        if (matcher.find()) {
            return "iconosTipos/" + (matcher.group(1)) + ".png";
        } else {
            return "";
        }

    }
}
