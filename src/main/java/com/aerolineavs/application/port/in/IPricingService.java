package com.aerolineavs.application.port.in;

import com.aerolineavs.tarifas.ClientePotencial;
import com.aerolineavs.tarifas.ResultadoTarifa;

/**
 * Servicio de aplicación que evalúa la tarifa adecuada para un cliente.
 */
public interface IPricingService {

    /**
     * Evalúa las reglas de tarifas para un perfil de cliente.
     *
     * @param cliente perfil a evaluar
     * @return tarifa aplicable y suposiciones utilizadas
     */
    ResultadoTarifa evaluar(ClientePotencial cliente);
}
