package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import entidad.Campeonato;
import model.CampeonatoModel;
import util.Validaciones;

public class FrmConsultaCampeonato extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JButton btnFiltrar;
	private JTextField txtNombre;
	private JTextField txtAnio;
	private JComboBox<String> cboEstado;

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
					FrmConsultaCampeonato frame = new FrmConsultaCampeonato();
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
	public FrmConsultaCampeonato() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 998, 545);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Consulta de campeonato");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 950, 48);
		contentPane.add(lblTitulo);
		
		JLabel lblFechaDeInicio = new JLabel("Estado");
		lblFechaDeInicio.setBounds(38, 156, 104, 20);
		contentPane.add(lblFechaDeInicio);
		
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
				"Código", "Nombre", "Año","Estado", 
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
		
		JLabel lblNewLabel_1 = new JLabel("A\u00F1o");
		lblNewLabel_1.setBounds(38, 115, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtAnio = new JTextField();
		txtAnio.setBounds(135, 112, 149, 20);
		contentPane.add(txtAnio);
		txtAnio.setColumns(10);
		
		cboEstado = new JComboBox<String>();
		cboEstado.setModel(new DefaultComboBoxModel<String>(new String[] {"[Todos]", "Activo", "Inactivo"}));
		cboEstado.setBounds(135, 155, 149, 22);
		contentPane.add(cboEstado);
	}
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnFiltrar) {
			do_btnFiltrar_actionPerformed(arg0);
		}
	}
	protected void do_btnFiltrar_actionPerformed(ActionEvent arg0) {
		String nombre = txtNombre.getText();
		String anio = txtAnio.getText();
		int estado = cboEstado.getSelectedIndex();
	
		int varAnio = 0;
		if (anio.matches(Validaciones.ANNO)) {
			varAnio = Integer.parseInt(anio);
		}
		
		int varEstado = 0;
		switch (estado) {
			case 0: varEstado = -1;	break;
			case 1: varEstado = 1;	break;
			case 2: varEstado = 0;	break;
		}
		
		CampeonatoModel model = new CampeonatoModel();
		List<Campeonato> lstCampeonato = model.listaCampeonatoPorNombreAnioEstado(nombre, varAnio, varEstado);
		
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		
		Object[] fila = null; 
		for (Campeonato x : lstCampeonato) {
			fila = new Object[] {x.getIdCampeonato(), x.getNombre(), x.getAnnio(), x.getEstado()==1?"Activo":"Inactivo"};
			dtm.addRow(fila);
		}
	}
}







