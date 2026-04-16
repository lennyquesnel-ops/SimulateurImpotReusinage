package com.kerware.simulateurreusine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParametresImpotTest {

    @Test
    void exposeCorrectementLesParametres2025() {
        ParametresImpot parametres = ParametresImpot.creerParametres2025();

        assertArrayEquals(new int[]{0, 11497, 29315, 83823, 180294, Integer.MAX_VALUE},
                parametres.getLimitesTranches());
        assertArrayEquals(new double[]{0.0, 0.11, 0.30, 0.41, 0.45},
                parametres.getTauxTranches());

        assertEquals(504, parametres.getAbattementMinimum());
        assertEquals(14426, parametres.getAbattementMaximum());
        assertEquals(0.10, parametres.getTauxAbattement());
        assertEquals(1791, parametres.getPlafondDemiPart());
        assertEquals(1964, parametres.getSeuilDecoteDeclarantSeul());
        assertEquals(3249, parametres.getSeuilDecoteDeclarantCouple());
        assertEquals(889, parametres.getDecoteMaxDeclarantSeul());
        assertEquals(1470, parametres.getDecoteMaxDeclarantCouple());
        assertEquals(0.4525, parametres.getTauxDecote());
    }

    @Test
    void exposeEncoreLesParametresLegacy() {
        ParametresImpot parametres = ParametresImpot.creerParametresLegacy();

        assertEquals(495, parametres.getAbattementMinimum());
        assertEquals(14171, parametres.getAbattementMaximum());
        assertEquals(1759, parametres.getPlafondDemiPart());
        assertEquals(1929, parametres.getSeuilDecoteDeclarantSeul());
        assertEquals(3191, parametres.getSeuilDecoteDeclarantCouple());
    }
}