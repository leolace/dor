import Controler.Tela;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Tela tTela = new Tela();
                tTela.setVisible(false);
                tTela.createBufferStrategy(2);
                tTela.go();
            }
        });
    }
}

