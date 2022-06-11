package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import entidad.Campeonato;
import model.CampeonatoModel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import util.GeneradorReporte;
import util.Validaciones;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class FrmReporteCampeonato extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnFiltrar;
	private JTextField txtNombre;
	private JPanel panelReporte;
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
					FrmReporteCampeonato frame = new FrmReporteCampeonato();
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
	public FrmReporteCampeonato() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1190, 755);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Reporte de campeonato");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 1154, 48);
		contentPane.add(lblTitulo);
		
		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(this);
		btnFiltrar.setBounds(959, 67, 162, 23);
		contentPane.add(btnFiltrar);
		
		panelReporte = new JPanel();
		panelReporte.setBorder(new TitledBorder(null, "Reportes", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelReporte.setBounds(20, 204, 1144, 501);
		contentPane.add(panelReporte);
		panelReporte.setLayout(new BorderLayout());
		
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(38, 71, 69, 14);
		contentPane.add(lblNewLabel);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(135, 67, 149, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("A\u00F1o");
		lblNewLabel_1.setBounds(38, 112, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblFechaDeInicio = new JLabel("Estado");
		lblFechaDeInicio.setBounds(38, 153, 104, 20);
		contentPane.add(lblFechaDeInicio);
		
		cboEstado = new JComboBox<String>();
		cboEstado.setModel(new DefaultComboBoxModel<String>(new String[] {"[Todos]", "Activo", "Inactivo"}));
		cboEstado.setBounds(135, 152, 149, 22);
		contentPane.add(cboEstado);
		
		txtAnio = new JTextField();
		txtAnio.setColumns(10);
		txtAnio.setBounds(135, 109, 149, 20);
		contentPane.add(txtAnio);
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

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstCampeonato);
		String jasper = "reporteCampeonato.jasper";	
		
		JasperPrint print = GeneradorReporte.genera(jasper, dataSource, null);
		
		JRViewer jRViewer = new JRViewer(print);
		
		panelReporte.removeAll();
		panelReporte.add(jRViewer);
		panelReporte.repaint();
		panelReporte.revalidate();
		
		
	}
}







