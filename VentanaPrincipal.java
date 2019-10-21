import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class VentanaPrincipal extends JFrame implements ActionListener {
	private JPanel jpNorth; //Panel superior donde estaran los parametros del metodo
    private JPanel jpCenter; //Panel central donde el usuario colocara las restricciones
    private JPanel jpSouth; // Panel inferior que contendra el boton de Resolver

    private JButton btnResolver; //Boton que indicara al programa que comienze el metodo

    private JLabel lblNumVar; private JComboBox jcbMAXVAR; //ComboBox para seleccionar el numero de variables
    private JLabel lblNumRes; private JComboBox jcbMAXRES; //ComboBox para seleccionar el numero de restricciones
    private JLabel lblFuncionObj; private JPanel jpFuncionObj; 
    private JLabel lblMaxOMin; private JRadioButton jrMax; private JRadioButton jrMin;  
    private JLabel lblIngresaRes;

    static final int MAXVAR = 15; //Maximo numero de variables 
    static final int MAXRES = 15; //Maximo numero de restricciones

    //Cajas de texto para que el usuario escriba el valor de las variables de la FO
    private ArrayList<JTextField> txtVectorFO; 
    //Cajas de texto para que el usuario escriba el valor de las variables en cada una de las restricciones
    private List<List<JTextField>> txtListRes = new ArrayList<List<JTextField>>(); 
    private ArrayList<JComboBox> jcbdesigualdad; private HashMap<String, Integer> des; 

    private ArrayList<Integer> valFO; //Vector con los valores de las variables de la funcion objetivo
    private List<List<Integer>> valRES = new ArrayList<List<Integer>>(); 
    private ArrayList<Integer> desigualdades;

    private ButtonGroup bg; //Grupo de radiobutton

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
    	jpCenter = new JPanel();
    	jpSouth = new JPanel();

    	add(jpNorth, BorderLayout.NORTH); //Colocamos jpNorth en la parte superior
    	add(jpCenter, BorderLayout.CENTER); //Colocamos jpCenter en la parte central
    	add(jpSouth, BorderLayout.SOUTH); //Colocamos jpSouth en la parte inferior

        constraints = new GridBagConstraints();
        jpNorth.setLayout(new GridBagLayout()); //Para el panel superior usaremos el GridBagLayout
        
        //Ubicamos la etiqueta "Numero de variables:" en el layout
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;  
        //constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.BOTH;
        lblNumVar = new JLabel("Numero de variables:");
        jpNorth.add(lblNumVar, constraints);

        //Ubicamos el campo de texto para ingresa el numero de variables en el layout
        constraints.weightx = 2.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        jcbMAXVAR = new JComboBox();
        for (int i = 2; i <= MAXVAR;  i++) {
            jcbMAXVAR.addItem(i);            
        }
        jpNorth.add(jcbMAXVAR, constraints);

        //Ubicamos la etiqueta "Selecciona una opcion" en el layout
        constraints.weightx = 4.0;
        constraints.gridx = 3;
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
        constraints.weightx = 2.0;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        jcbMAXRES = new JComboBox();
        for (int i = 2; i <= MAXRES;  i++) {
            jcbMAXRES.addItem(i);            
        }
        jpNorth.add(jcbMAXRES, constraints);

        //Ubicamos el radiobutton de minimizar en el layout
        constraints.weightx = 2.0;
        constraints.gridx = 3;  
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        jrMin = new JRadioButton("Minimizar", true);
        jpNorth.add(jrMin, constraints);

        //Ubicamos el radiobutton de maximizar en el layout
        constraints.weightx = 2.0;
        constraints.gridx = 5;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        jrMax = new JRadioButton("Maximizar");
        jpNorth.add(jrMax, constraints);

        bg = new ButtonGroup();
        bg.add(jrMax); bg.add(jrMin); // Añadimos los 2 radio button al grupo

        //Ubicamos la etiqueta "Ingresa las restricciones:" en el layout
        constraints.weightx = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.BOTH;
        lblIngresaRes = new JLabel("Ingresa las restricciones:");
        jpNorth.add(lblIngresaRes, constraints);
        
        //Parte inferior de la ventana
        jpSouth.setLayout(new BorderLayout());
        lblFuncionObj = new JLabel("Ingresa la F.O         Z =");
        jpFuncionObj = new JPanel();
        btnResolver = new JButton("Resolver");
        jpSouth.add(lblFuncionObj, BorderLayout.WEST);
        jpSouth.add(jpFuncionObj, BorderLayout.CENTER);
        jpSouth.add(btnResolver, BorderLayout.EAST);

        btnResolver.addActionListener(this); //Añadimos el boton de Resolver a la escucha
        jcbMAXVAR.addActionListener(this); 
        jcbMAXRES.addActionListener(this); 

        //Valor seleccionado actual del numero de variables
        int numActualVar = (Integer)jcbMAXVAR.getSelectedItem();
        //Valor seleccionado actual del numero de restricciones
        int numActualRes = (Integer)jcbMAXRES.getSelectedItem();

        txtVectorFO = new ArrayList<JTextField>();

        jcbdesigualdad = new ArrayList<JComboBox>();

        des = new HashMap<String, Integer>();

        des.put("<=", new Integer(0));
        des.put(">=", new Integer(1));
        des.put("=",  new Integer(2));

        //Funciones para crear las restriciones y la funcion objetivo
        creaUIfuncionObjetivo(numActualVar);
        crearUIresticciones(numActualRes, numActualVar);

        valFO = new ArrayList<Integer>();
        desigualdades = new ArrayList<Integer>();
    }

    public void crearUIresticciones(int numRes, int numVar){
        txtListRes.clear();
        jcbdesigualdad.clear();
        for(int i = 0; i < numRes; i++)
            txtListRes.add(new ArrayList<JTextField>());

        jpCenter.removeAll();
        jpCenter.setLayout(new GridLayout(numRes, (numVar*2) + 2));

        for (int i = 1; i <= numRes; i++) {
            jpCenter.add(new JLabel("r"+i+":"));
            for (int j = 1, x=0; j <= (numVar * 2) + 2; j++) {
                if(j <= (numVar * 2)){
                    if(j%2 == 0){
                        if(j == numVar*2)
                            jpCenter.add(new JLabel("X"+(j/2)));
                        else
                            jpCenter.add(new JLabel("X"+(j/2)+"+"));
                    }else{
                        JTextField aux = new JTextField();
                        txtListRes.get(i-1).add(aux);
                        jpCenter.add(txtListRes.get(i-1).get(x));
                        x++;
                    }
                }else{
                    if(j == (numVar*2)+1){
                        JComboBox aux = new JComboBox();
                        aux.addItem("<=");
                        aux.addItem(">=");
                        aux.addItem("=");                        
                        jcbdesigualdad.add(aux);
                        jpCenter.add(aux);
                    }else if(j == (numVar*2)+2){
                        JTextField aux = new JTextField();
                        txtListRes.get(i-1).add(aux);
                        jpCenter.add(txtListRes.get(i-1).get(x));
                    }
                }
                
            }
        }
        jpCenter.updateUI();
    }

    public void creaUIfuncionObjetivo(int numVar){
        txtVectorFO.clear();
        jpFuncionObj.removeAll();
        jpFuncionObj.setLayout(new GridLayout(1, numVar));
        for (int i = 1, j = 0; i <= numVar * 2; i++) {
            if(i%2 == 0){
                if(i == numVar*2)
                    jpFuncionObj.add(new JLabel("X"+(i/2)));
                else
                    jpFuncionObj.add(new JLabel("X"+(i/2)+"+"));
            }else{
                JTextField aux = new JTextField();
                txtVectorFO.add(aux);
                jpFuncionObj.add(txtVectorFO.get(j));
                j++;
            }
        }   
        jpFuncionObj.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        try{
            JButton aux = (JButton)e.getSource();
             boolean camposLlenos = true;
            //Guardar los valores de la variables de la FO en una lista
            for (int i = 0; i < txtVectorFO.size(); i++)  {
                if(txtVectorFO.get(i).getText().equals("")){ //Validar que los campos esten llenos
                    JOptionPane.showMessageDialog(this, "Llena todos los campos de la F.O", "Error!", JOptionPane.ERROR_MESSAGE);
                    camposLlenos = false;
                    break;// En caso de encontrar un campo vacio rompemos el ciclo
                }else{
                    valFO.add(Integer.parseInt(txtVectorFO.get(i).getText()));
                }
            }

            for(int x = 0; x < txtListRes.size(); x++)
                valRES.add(new ArrayList<Integer>());

            //Guardar los valores de la variables de las restricciones
            for (int i = 0; i < txtListRes.size(); i++) {
                for (int j = 0; j < txtListRes.get(i).size(); j++) {
                    if(txtListRes.get(i).get(j).getText().equals("")){ //Validar que los campos esten llenos
                        JOptionPane.showMessageDialog(this, "Llena todos los campos de las restricciones", "Error!", JOptionPane.ERROR_MESSAGE);
                        camposLlenos = false;
                        break;
                    }else{
                        valRES.get(i).add(Integer.parseInt(txtListRes.get(i).get(j).getText()));
                    }
                }
                if(!camposLlenos)
                    break;
            }

            //Guardamos las desigualdades en un lista
            for (int i = 0; i < jcbdesigualdad.size(); i++) {
                desigualdades.add(des.get( (String) jcbdesigualdad.get(i).getSelectedItem() ) );
            }
            boolean maxi = true;
            if (jrMin.isSelected() )
                maxi = false;
            else
                maxi = true;

            if(camposLlenos){ //Si todos los campos estan llenos podemos empezar con el metodo
                Simplex metodoSimplex = new Simplex(valFO, valRES, desigualdades, maxi);
                VentanaResultados res = new VentanaResultados(metodoSimplex.obeterVectorSol(), metodoSimplex.obeterTablasIter());
                res.setVisible(true);
            }
        }catch(Exception excep){
            JComboBox aux = (JComboBox)e.getSource();
            if (aux == jcbMAXRES) {
                 crearUIresticciones((Integer)jcbMAXRES.getSelectedItem(), (Integer)jcbMAXVAR.getSelectedItem());
            }else if (aux == jcbMAXVAR) {
                 crearUIresticciones((Integer)jcbMAXRES.getSelectedItem(), (Integer)jcbMAXVAR.getSelectedItem());
                 creaUIfuncionObjetivo((Integer)jcbMAXVAR.getSelectedItem());creaUIfuncionObjetivo((Integer)jcbMAXVAR.getSelectedItem());
            }
        }   
    }

    //Funcion principal
    public static void main(String[] args) {
        VentanaPrincipal vp = new VentanaPrincipal(); //Se crea la ventana principal
        vp.setVisible(true); // Se hace visible la ventana
    }
}