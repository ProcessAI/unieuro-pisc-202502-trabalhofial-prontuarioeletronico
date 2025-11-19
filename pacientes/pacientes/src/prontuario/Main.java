package prontuario;

import view.TelaPaciente;
/**
 *
 * @author aluno
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Apenas inicia a TelaLogin
        System.out.println("Iniciando Prontuario");
        TelaPaciente tl = new TelaPaciente();
        tl.setVisible(true);
    }

}