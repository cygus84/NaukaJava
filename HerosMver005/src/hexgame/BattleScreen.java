package hexgame;

import javax.swing.*;

import modele.Hero;

import java.awt.*;

public class BattleScreen extends JDialog {
	private final Hero attacker;
    private final Hero defender;
    private final JLabel attackerHpLabel = new JLabel();
    private final JLabel defenderHpLabel = new JLabel();
    private final JTextArea logArea = new JTextArea(5, 30);

    public BattleScreen(JFrame parent, Hero attacker, Hero defender) {
        super(parent, "Bitwa!", true);
        this.attacker = attacker;
        this.defender = defender;

        setLayout(new BorderLayout());

        // Panel info
        JPanel infoPanel = new JPanel(new GridLayout(2, 2));
        infoPanel.add(new JLabel("Atakujący:"));
        infoPanel.add(new JLabel("Obrońca:"));

        updateHpLabels();
        infoPanel.add(attackerHpLabel);
        infoPanel.add(defenderHpLabel);

        // Logi walki
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        // Przycisk ataku
        JButton attackButton = new JButton("Atakuj");
        attackButton.addActionListener(e -> performAttack());

        add(infoPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(attackButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void performAttack() {
        int damage = Math.max(5, attacker.getAttack() - defender.getDefense());
        defender.damage(damage);
        logArea.append("Atak! Zadano " + damage + " obrażeń.\n");
        updateHpLabels();

        if (!defender.isAlive()) {
            logArea.append("Obrońca poległ!\n");
            dispose();
        } else {
            // Obrońca kontratakuje
            int counter = Math.max(5, defender.getAttack() - attacker.getDefense());
            attacker.damage(counter);
            logArea.append("Kontratak! Zadano " + counter + " obrażeń.\n");
            updateHpLabels();

            if (!attacker.isAlive()) {
                logArea.append("Atakujący poległ!\n");
                dispose();
            }
        }
    }

    private void updateHpLabels() {
        attackerHpLabel.setText("HP: " + attacker.getHp());
        defenderHpLabel.setText("HP: " + defender.getHp());
    }
}
