package hexgame;

import javax.swing.*;

import modele.Hero;
import modele.Unit;

import java.awt.*;
import java.util.ArrayList;

public class BattleScreen extends JDialog {
	private final Hero attacker;
    private final Hero defender;
    private final JLabel attackerHpLabel = new JLabel();
    private final JLabel defenderHpLabel = new JLabel();
    private final JTextArea logArea = new JTextArea(5, 30);
    private int turn = 0;

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
    	ArrayList<Unit> atkArmy = attacker.getArmy();
    	ArrayList<Unit> defArmy = defender.getArmy();

    	    if (atkArmy.isEmpty() || defArmy.isEmpty()) {
    	        logArea.append("Koniec walki!\n");
    	        dispose();
    	        return;
    	    }

    	    Unit atkUnit = atkArmy.get(0);
    	    Unit defUnit = defArmy.get(0);

    	    int dmg = Math.max(5, atkUnit.getAttack() - defUnit.getDefense());
    	    defUnit.damage(dmg);
    	    logArea.append(atkUnit.getName() + " atakuje " + defUnit.getName() + " za " + dmg + " dmg\n");

    	    if (!defUnit.isAlive()) {
    	        logArea.append(defUnit.getName() + " ginie!\n");
    	        defArmy.remove(defUnit);
    	    }

    	    // Kontratak jeśli przeżył
    	    if (defUnit.isAlive()) {
    	        int counter = Math.max(5, defUnit.getAttack() - atkUnit.getDefense());
    	        atkUnit.damage(counter);
    	        logArea.append(defUnit.getName() + " kontratakuje za " + counter + " dmg\n");

    	        if (!atkUnit.isAlive()) {
    	            logArea.append(atkUnit.getName() + " ginie!\n");
    	            atkArmy.remove(atkUnit);
    	        }
    	    }

    	    updateHpLabels();

    	    if (atkArmy.isEmpty() || defArmy.isEmpty()) {
    	        logArea.append("Bitwa zakończona.\n");
    	        dispose();
    	    }
    }

    private void updateHpLabels() {
        attackerHpLabel.setText("Armia: " + attacker.getArmy().size() + " jednostek");
        defenderHpLabel.setText("Armia: " + defender.getArmy().size() + " jednostek");
    }
}
