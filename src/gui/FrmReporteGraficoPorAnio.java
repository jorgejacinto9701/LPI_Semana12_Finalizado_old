package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import entidad.ReporteBean;
import model.ReporteModel;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import util.GeneradorReporte;
import util.Validaciones;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class FrmReporteGraficoPorAnio extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAnioInicio;
	private JTextField txtAnioFin;
	private JPanel panelReporte;
	private JButton btnFiltrar;

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
					FrmReporteGraficoPorAnio frame = new FrmReporteGraficoPorAnio();
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
	public FrmReporteGraficoPorAnio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1149, 754);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("Reporte Gr\u00E1fico por a\u00F1o");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(25, 44, 775, 29);
		contentPane.add(lblTitulo);

		JLabel lblDesde = new JLabel("A\u00F1o(Desde)");
		lblDesde.setBounds(25, 100, 86, 14);
		contentPane.add(lblDesde);

		txtAnioInicio = new JTextField();
		txtAnioInicio.setBounds(148, 100, 86, 20);
		contentPane.add(txtAnioInicio);
		txtAnioInicio.setColumns(10);

		txtAnioFin = new JTextField();
		txtAnioFin.setBounds(404, 100, 86, 20);
		contentPane.add(txtAnioFin);
		txtAnioFin.setColumns(10);

		JLabel lblHasta = new JLabel("(Hasta)");
		lblHasta.setBounds(297, 100, 46, 14);
		contentPane.add(lblHasta);

		btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(this);
		btnFiltrar.setBounds(669, 100, 116, 23);
		contentPane.add(btnFiltrar);

		panelReporte = new JPanel();
		panelReporte.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Reporte", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelReporte.setBounds(25, 144, 1098, 541);
		contentPane.add(panelReporte);
		panelReporte.setLayout(new BorderLayout(0, 0));
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnFiltrar) {
			do_btnFiltrar_actionPerformed(e);
		}
	}

	protected void do_btnFiltrar_actionPerformed(ActionEvent e) {
		String inicio = txtAnioInicio.getText().trim();
		String fin = txtAnioFin.getText().trim();
		
		if (!inicio.matches(Validaciones.ANNO)) {
			mensaje("El año inicio tiene formato YYYY");	
		}else if (!fin.matches(Validaciones.ANNO)) {
			mensaje("El año fin tiene formato YYYY");	
		}else if ( Integer.parseInt(inicio) > Integer.parseInt(fin)) {
			mensaje("El año fin debe ser mayor o igual que el año inicio");	
		}else {
			ReporteModel model = new ReporteModel();
			ArrayList<ReporteBean> lista = model.reportePorAnio(Integer.parseInt(inicio), Integer.parseInt(fin));
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
			String jasper = "reporteGraficoPorAnio.jasper";	
			
			JasperPrint print = GeneradorReporte.genera(jasper, dataSource, null);
			
			JRViewer jRViewer = new JRViewer(print);
			
			panelReporte.removeAll();
			panelReporte.add(jRViewer);
			panelReporte.repaint();
			panelReporte.revalidate();
		}
	}
	
	void mensaje(String m) {
		JOptionPane.showMessageDialog(this, m);
	}
}









