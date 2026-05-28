package modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaTresEnRaya extends JFrame {

    private JButton[][] botones = new JButton[3][3];
    private boolean turnoX = true; // Empieza el jugador X
    private JLabel etiquetaEstado;

    public VentanaTresEnRaya() {
        // Configuración básica de la ventana
        setTitle("Tres en Raya");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para la cuadrícula
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(3, 3));

        // Inicializar botones y añadirlos al panel
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j] = new JButton("");
                botones[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                botones[i][j].setFocusPainted(false);
                botones[i][j].addActionListener(new ManejadorBotones(i, j));
                panelTablero.add(botones[i][j]);
            }
        }

        // Etiqueta de estado en la parte inferior
        etiquetaEstado = new JLabel("Turno de: X", SwingConstants.CENTER);
        etiquetaEstado.setFont(new Font("Arial", Font.PLAIN, 20));

        add(panelTablero, BorderLayout.CENTER);
        add(etiquetaEstado, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Centrar en pantalla
        setVisible(true);
    }

    // Clase interna para manejar los clics
    private class ManejadorBotones implements ActionListener {

        private int fila, col;

        public ManejadorBotones(int fila, int col) {
            this.fila = fila;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton botonPulsado = (JButton) e.getSource();

            // Si el botón ya tiene texto o alguien ya ganó, no hacer nada
            if (!botonPulsado.getText().equals("")) {
                return;
            }

            if (turnoX) {
                botonPulsado.setText("X");
                botonPulsado.setForeground(Color.BLUE);
                etiquetaEstado.setText("Turno de: O");
            } else {
                botonPulsado.setText("O");
                botonPulsado.setForeground(Color.RED);
                etiquetaEstado.setText("Turno de: X");
            }

            if (revisarGanador()) {
                String ganador = turnoX ? "X" : "O";
                JOptionPane.showMessageDialog(null, "¡El jugador " + ganador + " ha ganado!");
                reiniciarTablero();
            } else if (tableroLleno()) {
                JOptionPane.showMessageDialog(null, "¡Empate!");
                reiniciarTablero();
            } else {
                turnoX = !turnoX; // Cambiar turno
            }
        }
    }

    private boolean revisarGanador() {
        // Revisar filas y columnas
        for (int i = 0; i < 3; i++) {
            if (verificarLinea(botones[i][0], botones[i][1], botones[i][2])) {
                return true;
            }
            if (verificarLinea(botones[0][i], botones[1][i], botones[2][i])) {
                return true;
            }
        }
        // Diagonales
        return verificarLinea(botones[0][0], botones[1][1], botones[2][2])
                || verificarLinea(botones[0][2], botones[1][1], botones[2][0]);
    }

    private boolean verificarLinea(JButton b1, JButton b2, JButton b3) {
        String t1 = b1.getText();
        return !t1.equals("") && t1.equals(b2.getText()) && t1.equals(b3.getText());
    }

    private boolean tableroLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (botones[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private void reiniciarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText("");
            }
        }
        turnoX = true;
        etiquetaEstado.setText("Turno de: X");
    }

    public static void main(String[] args) {
        // Ejecutar en el hilo de despacho de eventos
        SwingUtilities.invokeLater(() -> new VentanaTresEnRaya());
    }
}
