package com.kerware.simulateurreusine;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainSimulateurReusineTest {

    @Test
    void lanceLeCalculEtAfficheLaProgression() {
        // EXIG-SIM-08 : la fonction main permet de lancer le code
        // et d'observer sa progression avec des println.
        PrintStream sortieOriginale = System.out;
        ByteArrayOutputStream tampon = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(tampon, true, StandardCharsets.UTF_8));

            MainSimulateurReusine.main(new String[0]);

            String sortie = tampon.toString(StandardCharsets.UTF_8);
            assertTrue(sortie.contains("Lancement du simulateur réusiné"));
            assertTrue(sortie.contains("Résultats calculés :"));
            assertTrue(sortie.contains("- impôt sur le revenu net = 150"));
        } finally {
            System.setOut(sortieOriginale);
        }
    }
}