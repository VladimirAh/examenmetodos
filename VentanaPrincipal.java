import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

public class VentanaPrincipal extends JFrame implements ActionListener {
	private JPanel jpNorth; //Panel superior donde estaran los parametros del metodo
    private JScrollPane jpCenter; //Panel central donde el usuario colocara las restricciones
    private JPanel jpSouth; // Panel inferior que contendra el boton de Resolver

    private JButton btnResolver; //Boton que indicara al programa que comienze el metodo

    private JLabel lblNumVar; private JTextField txtNumVar;
    private JLabel lblNumRes; private JTextField txtNumRes;
    private JLabel lblFuncionObj; private JScrollPane jpFuncionObj;
    private JLabel lblMaxOMin; 
    private JLabel lblIngresaRes;



    private GridBagConstraints constraints; //Para el GridBagLayout

    public VentanaPrincipal() {
        super();
        configurarVentana();
        inicializarComponentes();
    }

    //Configuracion general de la ventana principal
    private void configurarVentana() {
        this.setTitle("Solver Simplex"); //Titulo de la ventana 
        this.setSize(1000, 600); // Tamaño de la ventana
        this.setLocationRelativeTo(null); //Localiza la ventana en el centro de la pantalla
        //this.setResizable(false); //Evitar que se pueda cambiar el tamaño de la ventana
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Permite que se cierre la ventana
        //El JFrame (VentanaPrincipal) usara el layout BorderLayout
    }

    //Inicializar los componentes que estaran dentro de VentanaPrincipal
    private void inicializarComponentes() {
    	jpNorth = new JPanel(); 
    	jpCenter = new JScrollPane();
    	jpSouth = new JPanel();

    	add(jpNorth, BorderLayout.NORTH); //Colocamos jpNorth en la parte superior
    	add(jpCenter, BorderLayout.CENTER); //Colocamos jpCenter en la parte central
    	add(jpSouth, BorderLayout.SOUTH); //Colocamos jpSouth en la parte inferior

        constraints = new GridBagConstraints();
        jpNorth.setLayout(new GridBagLayout()); //Para el panel superior usaremos el GridBagLayout
        
        //Ubicamos la etiqueta "Numero de variables:" en el layout
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 10;
        constraints.weightx = 4.0;
        constraints.fill = GridBagConstraints.BOTH;
        lblNumVar = new JLabel("Numero de variables:");
        jpNorth.add(lblNumVar, constraints);

        //Ubicamos el campo de texto para ingresa el numero de variables en el layout
        constraints.weightx = 1.0;
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        txtNumVar = new JTextField();
        jpNorth.add(txtNumVar, constraints);

        //Ubicamos la etiqueta "Selecciona una opcion" en el layout
        constraints.weightx = 5.0;
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        lblMaxOMin = new JLabel("Selecciona una opcion");
        jpNorth.add(lblMaxOMin, constraints);

        //Ubicamos la etiqueta "Numero de restricciones:" en el layout
        constraints.weightx = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        lblNumRes = new JLabel("Numero de restricciones:");
        jpNorth.add(lblNumRes, constraints);

        //Ubicamos el campo de texto para ingresa el numero de restricciones en el layout
        constraints.weightx = 1.0;
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        txtNumRes = new JTextField();
        jpNorth.add(txtNumRes, constraints);
        
        

        lblIngresaRes = new JLabel("Ingresa las restricciones:");



        btnResolver = new JButton("Resolver");
        jpSouth.add(btnResolver);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    //Funcion principal
    public static void main(String[] args) {
        VentanaPrincipal vp = new VentanaPrincipal(); //Se crea la ventana principal
        vp.setVisible(true); // Se hace visible la ventana
    }
}