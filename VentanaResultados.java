import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaResultados extends JFrame implements ActionListener {
    public JPanel jpSouth;
    public JButton btnCerrar;

    public VentanaResultados() {
        super();
        configurarVentana();
        inicializarComponentes();
    }

    //Configuracion general de la ventana principal
    private void configurarVentana() {
        this.setTitle("Resultados"); //Titulo de la ventana 
        this.setSize(1000, 600); // Tamaño de la ventana
        //this.setLocationRelativeTo(null); //Localiza la ventana en el centro de la pantalla
        //this.setResizable(false); //Evitar que se pueda cambiar el tamaño de la ventana
        //|this.setDefaultCloseOperation(this.EXIT_ON_CLOSE); //Permite que se cierre la ventana
        //El JFrame (VentanaResultados) usara el layout BorderLayout

    }

    //Inicializar los componentes que estaran dentro de VentanaResultados
    private void inicializarComponentes() {
        jpSouth = new JPanel();

        setLayout(new BorderLayout());
        add(jpSouth, BorderLayout.SOUTH);

        btnCerrar = new JButton("Cerrar");

        jpSouth.add(btnCerrar);

        btnCerrar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose(); 

    }
}