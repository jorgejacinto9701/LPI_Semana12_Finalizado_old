package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import entidad.Docente;
import model.DocenteModel;

public class FrmConsultaDocente extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInicio;
	private JTextField txtFin;
	private JTable table;
	private JButton btnFiltrar;
	private JTextField txtNombre;
	private JTextField txtDNI;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmConsultaDocente frame = new FrmConsultaDocente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmConsultaDocente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Consulta de docente");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 950, 48);
		contentPane.add(lblTitulo);
		
		JLabel lblFechaDeInicio = new JLabel("Fecha de Inicio");
		lblFechaDeInicio.setBounds(38, 156, 104, 20);
		contentPane.add(lblFechaDeInicio);
		
		JLabel lblFechaDeFin = new JLabel("Fecha de Fin");
		lblFechaDeFin.setBounds(359, 159, 85, 14);
		contentPane.add(lblFechaDeFin);
		
		txtInicio = new JTextField();
		txtInicio.setBounds(135, 156, 149, 20);
		contentPane.add(txtInicio);
		txtInicio.setColumns(10);
		
		txtFin = new JTextField();
		txtFin.setBounds(454, 156, 149, 20);
		contentPane.add(txtFin);
		txtFin.setColumns(10);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(this);
		btnFiltrar.setBounds(760, 67, 162, 23);
		contentPane.add(btnFiltrar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 204, 940, 268);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Código", "Nombre", "DNI","Fecha Nacimiento", 
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(38, 71, 69, 14);
		contentPane.add(lblNewLabel);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(135, 67, 149, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("DNI");
		lblNewLabel_1.setBounds(38, 115, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtDNI = new JTextField();
		txtDNI.setBounds(135, 112, 149, 20);
		contentPane.add(txtDNI);
		txtDNI.setColumns(10);
	}
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnFiltrar) {
			do_btnFiltrar_actionPerformed(arg0);
		}
	}
	protected void do_btnFiltrar_actionPerformed(ActionEvent arg0) {
		String nombre = txtNombre.getText();
		String dni = txtDNI.getText();
		String desde = txtInicio.getText();
		String hasta = txtFin.getText();
		
		DocenteModel model = new DocenteModel();
		List<Docente> lstDocente = model.consultaPorNombreDNIFecha(nombre, dni, desde, hasta);
		
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		
		Object[] fila = null; 
		for (Docente x : lstDocente) {
			fila = new Object[] {x.getIdDocente(), x.getNombre(), x.getDni(), x.getFechaNacimiento()};
			dtm.addRow(fila);
		}
		
		
	}
}













