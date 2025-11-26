import javax.swing.UnsupportedLookAndFeelException;
import view.TelaFuncionario;

public class Main {

    public static void main(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Erro ao aplicar LookAndFeel: " + e.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> {
            new TelaFuncionario().setVisible(true);
        });
    }
}
