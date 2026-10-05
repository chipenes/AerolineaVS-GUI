package com.aerolineavs.application.service;

import com.aerolineavs.application.port.in.IPricingService;
import com.aerolineavs.tarifas.ClientePotencial;
import com.aerolineavs.tarifas.EvaluadorTarifas;
import com.aerolineavs.tarifas.ResultadoTarifa;

/**
 * Implementación del servicio de evaluación de tarifas, sin dependencias de presentación.
 */
public final class PricingServiceImpl implements IPricingService {

    @Override
    public ResultadoTarifa evaluar(ClientePotencial cliente) {
        return EvaluadorTarifas.evaluar(cliente);
    }
}
