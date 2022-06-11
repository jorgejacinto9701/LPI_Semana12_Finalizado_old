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

import entidad.Docente;
import model.DocenteModel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import util.GeneradorReporte;

public class FrmReporteDocente extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInicio;
	private JTextField txtFin;
	private JButton btnFiltrar;
	private JTextField txtNombre;
	private JTextField txtDNI;
	private JPanel panelReporte;
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
					FrmReporteDocente frame = new FrmReporteDocente();
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
	public FrmReporteDocente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1190, 755);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitulo = new JLabel("Reporte de docente");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 11, 1154, 48);
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
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstDocente);
		String jasper = "reporteDocente.jasper";	
		
		JasperPrint print = GeneradorReporte.genera(jasper, dataSource, null);
		
		JRViewer jRViewer = new JRViewer(print);
		
		panelReporte.removeAll();
		panelReporte.add(jRViewer);
		panelReporte.repaint();
		panelReporte.revalidate();
		
		
	}
}







