package com.aerolineavs.tarifas;

/**
 * Motor de reglas para determinar la tarifa más adecuada.
 */
public final class EvaluadorTarifas {

    private static final String SUPOSICIONES = "Suposiciones: curso universitario = 9 desplazamientos/año; "
            + "la frecuencia anual representa los viajes relevantes de cada regla (incluidos los viajes de ocio "
            + "para jóvenes profesionales); los requisitos de clase turista y tres viajes se aplican a ambas "
            + "tarifas de jóvenes profesionales; la tarifa con peques sustituye a la tarifa base y su descuento "
            + "del 10% es por billete; si no se cumple ninguna condición exacta, no se oferta tarifa.";

    private EvaluadorTarifas() {
    }

    /**
     * Evalúa una única tarifa aplicable en función de los datos del cliente.
     *
     * @param cliente datos del cliente
     * @return tarifa resultante junto con suposiciones
     */
    public static ResultadoTarifa evaluar(ClientePotencial cliente) {
        if (cliente.edad() < 18
                && cliente.tipoViajero() == TipoViajero.MENOR
                && cliente.vuelosAnuales() >= 6) {
            return new ResultadoTarifa(Tarifa.PAJARILLO, SUPOSICIONES);
        }

        if (cliente.edad() >= 18 && cliente.edad() <= 25) {
            if (cliente.tipoViajero() == TipoViajero.ESTUDIANTE_UNIVERSITARIO_DESPLAZADO
                    && cliente.clasePreferida() == ClaseVuelo.TURISTA
                    && cliente.vuelosAnuales() >= 9) {
                return new ResultadoTarifa(Tarifa.GORRION, SUPOSICIONES);
            }

            if (cliente.tipoViajero() == TipoViajero.TRABAJADOR_JOVEN
                    && cliente.clasePreferida() == ClaseVuelo.TURISTA
                    && cliente.vuelosAnuales() >= 3) {
                Tarifa tarifa = cliente.viveConPadres()
                        ? Tarifa.VIAJA_AHORA_QUE_PUEDES
                        : Tarifa.ATREVIENDOSE_A_SALTAR_DEL_NIDO;
                return new ResultadoTarifa(tarifa, SUPOSICIONES);
            }
        }

        if (cliente.edad() > 25) {
            if (cliente.ingresosAnuales() > 20000
                    && cliente.ingresosAnuales() < 35000
                    && cliente.vuelosAnuales() >= 6
                    && cliente.clasePreferida() == ClaseVuelo.TURISTA
                    && cliente.destinoPreferido() == RegionDestino.EUROPA) {
                Tarifa tarifa = cliente.viajaConNinos()
                        ? Tarifa.CONOCE_EUROPA_CON_TUS_PEQUES
                        : Tarifa.CONOCE_EUROPA;
                return new ResultadoTarifa(tarifa, SUPOSICIONES);
            }

            if (cliente.ingresosAnuales() > 35000
                    && cliente.vuelosAnuales() >= 6
                    && cliente.clasePreferida() == ClaseVuelo.BUSINESS
                    && (cliente.destinoPreferido() == RegionDestino.ASIA
                    || cliente.destinoPreferido() == RegionDestino.AMERICA)) {
                Tarifa tarifa = cliente.viajaConNinos()
                        ? Tarifa.CONOCE_EL_MUNDO_CON_TUS_PEQUES
                        : Tarifa.CONOCE_EL_MUNDO;
                return new ResultadoTarifa(tarifa, SUPOSICIONES);
            }
        }

        return new ResultadoTarifa(Tarifa.SIN_TARIFA, SUPOSICIONES);
    }
}
