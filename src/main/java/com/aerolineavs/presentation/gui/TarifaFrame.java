package com.aerolineavs.presentation.gui;

import com.aerolineavs.application.port.in.IPricingService;
import com.aerolineavs.tarifas.ClaseVuelo;
import com.aerolineavs.tarifas.ClientePotencial;
import com.aerolineavs.tarifas.RegionDestino;
import com.aerolineavs.tarifas.ResultadoTarifa;
import com.aerolineavs.tarifas.TipoViajero;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Objects;

/**
 * Ventana de escritorio que obtiene recomendaciones exclusivamente mediante IPricingService.
 */
public final class TarifaFrame extends JFrame {

    private final IPricingService pricingService;
    private final JTextField edad = new JTextField("30", 12);
    private final JTextField vuelosAnuales = new JTextField("6", 12);
    private final JTextField ingresosAnuales = new JTextField("25000", 12);
    private final JComboBox<TipoViajero> tipoViajero = new JComboBox<>(TipoViajero.values());
    private final JComboBox<ClaseVuelo> clasePreferida = new JComboBox<>(ClaseVuelo.values());
    private final JComboBox<RegionDestino> destinoPreferido = new JComboBox<>(RegionDestino.values());
    private final JCheckBox viajaConNinos = new JCheckBox("Viaja con niños menores de 12 años");
    private final JCheckBox viveConPadres = new JCheckBox("Vive con sus padres");
    private final JTextArea resultado = new JTextArea(5, 36);

    public TarifaFrame(IPricingService pricingService) {
        super("Evaluador de tarifas aéreas");
        this.pricingService = Objects.requireNonNull(pricingService, "El servicio de tarifas es obligatorio.");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(crearContenido());
        pack();
        setLocationByPlatform(true);
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel(new BorderLayout(8, 8));
        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.insets = new Insets(4, 6, 4, 6);
        restricciones.anchor = GridBagConstraints.LINE_START;

        agregarCampo(formulario, restricciones, 0, "Edad:", edad);
        agregarCampo(formulario, restricciones, 1, "Vuelos relevantes al año:", vuelosAnuales);
        agregarCampo(formulario, restricciones, 2, "Tipo de viajero:", tipoViajero);
        agregarCampo(formulario, restricciones, 3, "Clase preferida:", clasePreferida);
        agregarCampo(formulario, restricciones, 4, "Destino objetivo:", destinoPreferido);
        agregarCampo(formulario, restricciones, 5, "Ingresos anuales (€):", ingresosAnuales);

        restricciones.gridx = 0;
        restricciones.gridy = 6;
        restricciones.gridwidth = 2;
        formulario.add(viajaConNinos, restricciones);
        restricciones.gridy = 7;
        formulario.add(viveConPadres, restricciones);

        JButton evaluar = new JButton("Evaluar tarifa");
        evaluar.addActionListener(evento -> evaluarPerfil());
        resultado.setEditable(false);
        resultado.setLineWrap(true);
        resultado.setWrapStyleWord(true);

        contenido.add(formulario, BorderLayout.NORTH);
        contenido.add(evaluar, BorderLayout.CENTER);
        contenido.add(new JScrollPane(resultado), BorderLayout.SOUTH);
        return contenido;
    }

    private static void agregarCampo(
            JPanel formulario,
            GridBagConstraints restricciones,
            int fila,
            String etiqueta,
            java.awt.Component campo
    ) {
        restricciones.gridy = fila;
        restricciones.gridwidth = 1;
        restricciones.gridx = 0;
        formulario.add(new JLabel(etiqueta), restricciones);
        restricciones.gridx = 1;
        formulario.add(campo, restricciones);
    }

    private void evaluarPerfil() {
        try {
            ClientePotencial cliente = new ClientePotencial(
                    Integer.parseInt(edad.getText().trim()),
                    Integer.parseInt(vuelosAnuales.getText().trim()),
                    seleccion(tipoViajero, "tipo de viajero"),
                    seleccion(clasePreferida, "clase preferida"),
                    seleccion(destinoPreferido, "destino"),
                    Double.parseDouble(ingresosAnuales.getText().trim()),
                    viajaConNinos.isSelected(),
                    viveConPadres.isSelected()
            );
            ResultadoTarifa recomendacion = pricingService.evaluar(cliente);
            resultado.setText("Tarifa: " + recomendacion.tarifa().getNombre()
                    + "\nDescuento: " + recomendacion.tarifa().getDescuentoPorcentaje() + "%"
                    + "\n" + recomendacion.suposiciones());
        } catch (IllegalArgumentException error) {
            resultado.setText("Entrada no válida: " + error.getMessage());
        }
    }

    private static <T> T seleccion(JComboBox<T> opciones, String campo) {
        int indice = opciones.getSelectedIndex();
        if (indice < 0) {
            throw new IllegalArgumentException("Seleccione un valor para " + campo + ".");
        }
        return opciones.getItemAt(indice);
    }
}
