import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class PokemonInfoDisplay {
    public static void displayInfo(String selectedPokemon, String type, String selectedAbility,
                                   String selectedMove, String imageUrl, String moveProperties, String moveDescription) {
        JFrame frame = new JFrame("Información del Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JLabel imageLabel = new JLabel();
        try {
            URL imageURL = new URL(imageUrl);
            BufferedImage img = ImageIO.read(imageURL);
            ImageIcon imageIcon = new ImageIcon(img);
            imageLabel.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear etiquetas para mostrar la información del Pokémon
        JLabel nameLabel = new JLabel("Nombre: " + selectedPokemon);
        JLabel typeLabel = new JLabel("Tipo: " + type);
        JLabel abilityLabel = new JLabel("Habilidad seleccionada: " + selectedAbility);
        JLabel moveLabel = new JLabel("Movimiento seleccionado: " + selectedMove);
        JLabel moveDescLabel = new JLabel("Descripcion del movimiento: " + moveDescription);
        JLabel movePropLabel = new JLabel("Propiedades del movimiento: " + moveProperties);




        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(imageLabel);
        panel.add(nameLabel);
        panel.add(typeLabel);
        panel.add(abilityLabel);
        panel.add(moveLabel);
        panel.add(moveDescLabel);
        panel.add(movePropLabel);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
