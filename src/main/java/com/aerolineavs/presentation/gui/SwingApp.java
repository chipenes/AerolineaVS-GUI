package com.aerolineavs.presentation.gui;

import com.aerolineavs.application.port.in.IPricingService;
import com.aerolineavs.application.service.PricingServiceImpl;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada y composición de dependencias de la interfaz Swing.
 */
public final class SwingApp {

    private SwingApp() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> crearVentana(new PricingServiceImpl()));
    }

    private static void crearVentana(IPricingService pricingService) {
        TarifaFrame ventana = new TarifaFrame(pricingService);
        ventana.setVisible(true);
    }
}
