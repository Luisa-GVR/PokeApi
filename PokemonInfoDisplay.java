import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public class PokemonInfoDisplay {
    public static void displayInfo(String selectedPokemon, String type, String selectedAbility,
                                   String selectedMove, String imageUrl, String moveProperties, String moveDescription,
                                   String preEvolution, String spritePreEvolutionURL) {
        JFrame frame = new JFrame("Información del Pokémon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //imagenes
        String urlBackground = backgroundCheck(type);
        ImageIcon typeBackground = new ImageIcon(urlBackground);
        JLabel backgroundTypeLabel = new JLabel(typeBackground);

        String urlType = "";
        //Hace especial si no hay properties
        if (!moveProperties.equals("")){
            urlType = typeCheck(moveProperties);
        } else {
            urlType = "iconosTipos/especial.png";
        }

        ImageIcon typeIcon = new ImageIcon(urlType);
        JLabel typeIconLabel = new JLabel(typeIcon);



        frame.setSize(backgroundTypeLabel.getPreferredSize().width+20, backgroundTypeLabel.getPreferredSize().height+80);
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


        //mas texto que nada
        JLabel nameLabel = new JLabel("Nombre: " + selectedPokemon);
        JLabel typeLabel = new JLabel("Tipo: " + type);
        JLabel abilityLabel = new JLabel("Habilidad seleccionada: " + selectedAbility);
        JLabel moveLabel = new JLabel("Movimiento seleccionado: " + selectedMove);
        JLabel moveDescLabel = new JLabel("Descripcion del movimiento: " + moveDescription);
        JLabel movePropLabel = new JLabel("Propiedades del movimiento: " + moveProperties);

        // Establecer los límites de los componentes
        backgroundTypeLabel.setBounds(0,0, backgroundTypeLabel.getPreferredSize().width, backgroundTypeLabel.getPreferredSize().height);
        imageLabel.setBounds(230, 100, imageLabel.getPreferredSize().width, imageLabel.getPreferredSize().height);
        typeIconLabel.setBounds(80, 650, typeIconLabel.getPreferredSize().width, typeIconLabel.getPreferredSize().height);
        nameLabel.setBounds(10, 10, nameLabel.getPreferredSize().width, nameLabel.getPreferredSize().height);
        typeLabel.setBounds(10, 30, typeLabel.getPreferredSize().width, typeLabel.getPreferredSize().height);
        abilityLabel.setBounds(10, 50, abilityLabel.getPreferredSize().width, abilityLabel.getPreferredSize().height);
        moveLabel.setBounds(10, 70, moveLabel.getPreferredSize().width, moveLabel.getPreferredSize().height);
        moveDescLabel.setBounds(10, 90, moveDescLabel.getPreferredSize().width, moveDescLabel.getPreferredSize().height);
        movePropLabel.setBounds(10, 110, movePropLabel.getPreferredSize().width, movePropLabel.getPreferredSize().height);

        // Añadir los componentes al JLayeredPane con diferentes capas
        layeredPane.add(backgroundTypeLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(imageLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(typeIconLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(nameLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(typeLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(abilityLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(moveLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(moveDescLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(movePropLabel, JLayeredPane.PALETTE_LAYER);

        //Pre evolución
        if (!preEvolution.equals("") && !spritePreEvolutionURL.equals("")) {
            JLabel preEvoName = new JLabel("Evolves from: " + preEvolution);
            preEvoName.setBounds(10,120, preEvoName.getPreferredSize().width, preEvoName.getPreferredSize().height);
            layeredPane.add(preEvoName, JLayeredPane.PALETTE_LAYER);

            JLabel spriteLabel = new JLabel();
            try {
                URL spriteURL = new URL(spritePreEvolutionURL);
                BufferedImage img = ImageIO.read(spriteURL);
                ImageIcon imageIcon = new ImageIcon(img);
                spriteLabel.setIcon(imageIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }

            spriteLabel.setBounds(50,100, spriteLabel.getPreferredSize().width,spriteLabel.getPreferredSize().height);

            //circulo
            CirclePanel circlePanel = new CirclePanel();
            circlePanel.setBounds(spriteLabel.getX(), spriteLabel.getY(),
                    spriteLabel.getPreferredSize().width, spriteLabel.getPreferredSize().height);
            circlePanel.setOpaque(false);

            layeredPane.add(spriteLabel, JLayeredPane.PALETTE_LAYER);
            layeredPane.add(circlePanel, JLayeredPane.PALETTE_LAYER);


        }


        //boton de guardado
        JButton saveButton = new JButton("Save");
        saveButton.setBounds((backgroundTypeLabel.getPreferredSize().width/2)-25, backgroundTypeLabel.getPreferredSize().height+5,
                saveButton.getPreferredSize().width, saveButton.getPreferredSize().height);
        saveButton.addActionListener(e -> saveLayeredPaneAsImage(layeredPane));

        layeredPane.add(saveButton, JLayeredPane.DEFAULT_LAYER);

        // Establecer el tamaño preferido del JLayeredPane
        layeredPane.setPreferredSize(new Dimension(backgroundTypeLabel.getPreferredSize().width, backgroundTypeLabel.getPreferredSize().height));

        frame.getContentPane().add(layeredPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void saveLayeredPaneAsImage(JLayeredPane layeredPane) {
        BufferedImage image = new BufferedImage(layeredPane.getWidth(), layeredPane.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        layeredPane.printAll(g2d);

        g2d.dispose();

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getName().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }

            try {
                ImageIO.write(image, "png", fileToSave);
                System.out.println("Image saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String backgroundCheck(String tipo) {
        Pattern pattern = Pattern.compile("Primary Type: (\\w+)(, Secondary Type: \\d+)?");
        Matcher matcher = pattern.matcher(tipo);

        if (matcher.find()) {
            String maintType = matcher.group(1);
            return "fondosTipos/" + maintType + ".png";
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

    //Solo es para que se vea bonito la pre evolución jaja
    public static class CirclePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int diameter = Math.min(getWidth(), getHeight());
            Graphics2D g2d = (Graphics2D) g;
            float thick = 5.0f;
            g2d.setStroke(new BasicStroke(thick));
            Color colorOutline = new Color(20, 0, 20);
            g2d.setColor(colorOutline);
            g2d.drawOval(0, 0, diameter, diameter);
            g2d.setColor(Color.WHITE);
            g2d.fillOval(0, 0, diameter, diameter);
        }
    }

}
