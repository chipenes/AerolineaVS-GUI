package com.aerolineavs.tarifas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EvaluadorTarifasTest {

    @Test
    void debeAplicarPajarilloAMenorConSeisVuelos() {
        ClientePotencial cliente = new ClientePotencial(
                17, 6, TipoViajero.MENOR, ClaseVuelo.TURISTA, RegionDestino.OTRA, 0, false, true
        );

        assertEquals(Tarifa.PAJARILLO, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void noDebeAplicarPajarilloSiTipoNoEsMenor() {
        ClientePotencial cliente = new ClientePotencial(
                17, 6, TipoViajero.GENERAL, ClaseVuelo.TURISTA, RegionDestino.OTRA, 0, false, true
        );

        assertEquals(Tarifa.SIN_TARIFA, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeAplicarGorrionAEstudianteEntre18y25() {
        ClientePotencial cliente = new ClientePotencial(
                21, 9, TipoViajero.ESTUDIANTE_UNIVERSITARIO_DESPLAZADO, ClaseVuelo.TURISTA,
                RegionDestino.OTRA, 0, false, true
        );

        assertEquals(Tarifa.GORRION, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeAplicarViajaAhoraQuePuedesAJovenTrabajadorConPadres() {
        ClientePotencial cliente = new ClientePotencial(
                23, 3, TipoViajero.TRABAJADOR_JOVEN, ClaseVuelo.TURISTA,
                RegionDestino.OTRA, 15000, false, true
        );

        assertEquals(Tarifa.VIAJA_AHORA_QUE_PUEDES, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeAplicarAtreviendoseASaltarDelNidoAJovenTrabajadorIndependizado() {
        ClientePotencial cliente = new ClientePotencial(
                24, 4, TipoViajero.TRABAJADOR_JOVEN, ClaseVuelo.TURISTA,
                RegionDestino.OTRA, 18000, false, false
        );

        assertEquals(Tarifa.ATREVIENDOSE_A_SALTAR_DEL_NIDO, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeAplicarConoceEuropaConPeques() {
        ClientePotencial cliente = new ClientePotencial(
                35, 6, TipoViajero.GENERAL, ClaseVuelo.TURISTA,
                RegionDestino.EUROPA, 25000, true, false
        );

        assertEquals(Tarifa.CONOCE_EUROPA_CON_TUS_PEQUES, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeAplicarConoceEuropaSinPeques() {
        ClientePotencial cliente = new ClientePotencial(
                35, 6, TipoViajero.GENERAL, ClaseVuelo.TURISTA,
                RegionDestino.EUROPA, 25000, false, false
        );

        assertEquals(Tarifa.CONOCE_EUROPA, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeAplicarConoceMundoSinPeques() {
        ClientePotencial cliente = new ClientePotencial(
                40, 7, TipoViajero.GENERAL, ClaseVuelo.BUSINESS,
                RegionDestino.ASIA, 45000, false, false
        );

        assertEquals(Tarifa.CONOCE_EL_MUNDO, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeDevolverSinTarifaCuandoNoCumpleReglas() {
        ClientePotencial cliente = new ClientePotencial(
                19, 1, TipoViajero.GENERAL, ClaseVuelo.BUSINESS,
                RegionDestino.OTRA, 10000, false, true
        );

        assertEquals(Tarifa.SIN_TARIFA, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeExcluirLimitesDeIngresosParaConoceEuropa() {
        ClientePotencial ingresoMinimo = new ClientePotencial(
                30, 6, TipoViajero.GENERAL, ClaseVuelo.TURISTA,
                RegionDestino.EUROPA, 20000, false, false
        );
        ClientePotencial ingresoMaximo = new ClientePotencial(
                30, 6, TipoViajero.GENERAL, ClaseVuelo.TURISTA,
                RegionDestino.EUROPA, 35000, false, false
        );

        assertEquals(Tarifa.SIN_TARIFA, EvaluadorTarifas.evaluar(ingresoMinimo).tarifa());
        assertEquals(Tarifa.SIN_TARIFA, EvaluadorTarifas.evaluar(ingresoMaximo).tarifa());
    }

    @Test
    void debeExcluirIngresoIgualA35000ParaConoceMundo() {
        ClientePotencial cliente = new ClientePotencial(
                30, 6, TipoViajero.GENERAL, ClaseVuelo.BUSINESS,
                RegionDestino.AMERICA, 35000, false, false
        );

        assertEquals(Tarifa.SIN_TARIFA, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeIncluirIngresoMayorA35000ParaConoceMundo() {
        ClientePotencial cliente = new ClientePotencial(
                30, 6, TipoViajero.GENERAL, ClaseVuelo.BUSINESS,
                RegionDestino.AMERICA, 35000.01, false, false
        );

        assertEquals(Tarifa.CONOCE_EL_MUNDO, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeAplicarConoceMundoConPequesCuandoIngresoEsMayorA35000() {
        ClientePotencial cliente = new ClientePotencial(
                30, 6, TipoViajero.GENERAL, ClaseVuelo.BUSINESS,
                RegionDestino.AMERICA, 35000.01, true, false
        );

        assertEquals(Tarifa.CONOCE_EL_MUNDO_CON_TUS_PEQUES, EvaluadorTarifas.evaluar(cliente).tarifa());
    }

    @Test
    void debeIndicarLasSuposicionesAplicadasAlResultado() {
        ClientePotencial cliente = new ClientePotencial(
                23, 3, TipoViajero.TRABAJADOR_JOVEN, ClaseVuelo.TURISTA,
                RegionDestino.OTRA, 18000, false, false
        );

        String suposiciones = EvaluadorTarifas.evaluar(cliente).suposiciones();

        assertAll(
                () -> assertTrue(suposiciones.contains("9 desplazamientos/año")),
                () -> assertTrue(suposiciones.contains("viajes de ocio")),
                () -> assertTrue(suposiciones.contains("por billete"))
        );
    }

    @Test
    void debeRechazarDatosNumericosNegativos() {
        assertThrows(IllegalArgumentException.class, () -> new ClientePotencial(
                -1, 0, TipoViajero.GENERAL, ClaseVuelo.TURISTA, RegionDestino.OTRA, 0, false, false
        ));
        assertThrows(IllegalArgumentException.class, () -> new ClientePotencial(
                20, -1, TipoViajero.GENERAL, ClaseVuelo.TURISTA, RegionDestino.OTRA, 0, false, false
        ));
        assertThrows(IllegalArgumentException.class, () -> new ClientePotencial(
                20, 0, TipoViajero.GENERAL, ClaseVuelo.TURISTA, RegionDestino.OTRA, -1, false, false
        ));
    }

    @Test
    void debeRechazarIngresosNoFinitos() {
        assertThrows(IllegalArgumentException.class, () -> new ClientePotencial(
                20, 0, TipoViajero.GENERAL, ClaseVuelo.TURISTA,
                RegionDestino.OTRA, Double.NaN, false, false
        ));
    }
}
