import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

public class VentanaPrincipal extends JFrame implements ActionListener {
	private JPanel jpNorth, jpSouth, jpCenter, jpPendientes, jpHorario, jpControl;
	//private JMenuBar jMB;
	//private JMenu jM1, jM2, jM3;
	private JTabbedPane JTvista;
	private DefaultTableModel modelPendientes;
	private JTable jtbPendientes;
	private GridBagConstraints constraints;
	private JLabel lblInfoSouth;
	private JScrollPane scrollPane;
	public static void main(String[] args) {
		VentanaPrincipal vp = new VentanaPrincipal();
		vp.setVisible(true);
	}
    public VentanaPrincipal() {
        super();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        this.setTitle("Solver Simplex");
        this.setSize(1000, 600);
        this.setLocationRelativeTo(null);
        //$this.setLayout(null);
        //$this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {
    	//jMB = new JMenuBar();
    	jpNorth = new JPanel();
    	jpCenter = new JPanel();
    	jpSouth = new JPanel();
    	// jM1 = new JMenu("Menu1");
    	// jM2 = new JMenu("Menu2");
    	// jM3 = new JMenu("Menu3");
    	JTvista = new JTabbedPane();
    	jpPendientes = new JPanel();
    	jpHorario = new JPanel();
    	jpControl = new JPanel();
    	constraints = new GridBagConstraints();
    	lblInfoSouth = new JLabel( "" + new java.util.Date() );

    	jtbPendientes = new JTable(modelPendientes);
    	jtbPendientes.setFillsViewportHeight(true);
    	scrollPane = new JScrollPane(jtbPendientes);    	
    	//setJMenuBar(jMB);
    	jpNorth.setBackground(Color.GRAY);
    	//jMB.add(jM1); jMB.add(jM2); jMB.add(jM3);
    	add(jpNorth, BorderLayout.NORTH);
    	add(jpCenter, BorderLayout.CENTER);
    	add(jpSouth, BorderLayout.SOUTH);
    	JTvista.addTab("Pendientes", scrollPane);
    	JTvista.addTab("Horario", jpHorario);
    	jpCenter.setLayout(new GridBagLayout());
    	constraints.weighty = 1.0;
    	constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 3;
		constraints.weightx = 5.0;
		constraints.fill = GridBagConstraints.BOTH;
		jpCenter.add(JTvista, constraints);
		constraints.weightx = 1.0;
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.BOTH;
		jpCenter.add(jpControl, constraints);
		jpControl.setLayout(new GridBagLayout());
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		jpSouth.add(lblInfoSouth);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}