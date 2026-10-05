package com.aerolineavs.application.service;

import com.aerolineavs.application.port.in.IPricingService;
import com.aerolineavs.tarifas.ClaseVuelo;
import com.aerolineavs.tarifas.ClientePotencial;
import com.aerolineavs.tarifas.RegionDestino;
import com.aerolineavs.tarifas.Tarifa;
import com.aerolineavs.tarifas.TipoViajero;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceImplTest {

    @Test
    void debeEvaluarLaTarifaAInstanciaDeLaInterfazDeServicio() {
        IPricingService servicio = new PricingServiceImpl();
        ClientePotencial cliente = new ClientePotencial(
                38, 6, TipoViajero.GENERAL, ClaseVuelo.BUSINESS,
                RegionDestino.AMERICA, 42000, true, false
        );

        assertEquals(Tarifa.CONOCE_EL_MUNDO_CON_TUS_PEQUES, servicio.evaluar(cliente).tarifa());
    }
}
