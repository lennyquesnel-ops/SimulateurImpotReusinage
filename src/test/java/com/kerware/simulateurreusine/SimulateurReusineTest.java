package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimulateurReusineTest {

    @Test
    void calculeLesValeursMetierPourUnCelibataireSansEnfant() {
        // EXIG-SIM-01, EXIG-SIM-02, EXIG-SIM-04, EXIG-SIM-06
        SimulateurReusine simulateur = new SimulateurReusine();

        simulateur.calculer(20000, SituationFamiliale.CELIBATAIRE, 0, 0, false);

        assertEquals(2000, simulateur.getAbattement());
        assertEquals(18000, simulateur.getRevenuFiscalReference());
        assertEquals(2, simulateur.getNbDemiPartsFoyerFiscal());
        assertEquals(715, simulateur.getImpotAvantDecote());
        assertEquals(565, simulateur.getDecote());
        assertEquals(150, simulateur.getImpotSurRevenuNet());
    }

    @Test
    void calculeLesValeursMetierPourUnCoupleMarieSansEnfant() {
        // EXIG-SIM-01, EXIG-SIM-02, EXIG-SIM-04, EXIG-SIM-06
        SimulateurReusine simulateur = new SimulateurReusine();

        simulateur.calculer(40000, SituationFamiliale.MARIE, 0, 0, false);

        assertEquals(4000, simulateur.getAbattement());
        assertEquals(36000, simulateur.getRevenuFiscalReference());
        assertEquals(4, simulateur.getNbDemiPartsFoyerFiscal());
        assertEquals(1431, simulateur.getImpotAvantDecote());
        assertEquals(822, simulateur.getDecote());
        assertEquals(609, simulateur.getImpotSurRevenuNet());
    }

    @Test
    void appliqueLAbattementMinimumPourUnPetitRevenu() {
        // EXIG-SIM-01
        SimulateurReusine simulateur = new SimulateurReusine();

        simulateur.calculer(3000, SituationFamiliale.CELIBATAIRE, 0, 0, false);

        assertEquals(504, simulateur.getAbattement());
        assertEquals(2496, simulateur.getRevenuFiscalReference());
        assertEquals(0, simulateur.getImpotSurRevenuNet());
    }

    @Test
    void appliqueLAbattementMaximumPourUnTresGrandRevenu() {
        // EXIG-SIM-01
        SimulateurReusine simulateur = new SimulateurReusine();

        simulateur.calculer(200000, SituationFamiliale.CELIBATAIRE, 0, 0, false);

        assertEquals(14426, simulateur.getAbattement());
        assertEquals(185574, simulateur.getRevenuFiscalReference());
        assertEquals(60241, simulateur.getImpotSurRevenuNet());
    }

    @Test
    void parentIsoleAjouteUneDemiPartSupplementaire() {
        // EXIG-SIM-03
        SimulateurReusine simulateur = new SimulateurReusine();

        simulateur.calculer(40000, SituationFamiliale.DIVORCE, 1, 0, true);

        assertEquals(4, simulateur.getNbDemiPartsFoyerFiscal());
        assertEquals(1431, simulateur.getImpotAvantDecote());
        assertEquals(241, simulateur.getDecote());
        assertEquals(1190, simulateur.getImpotSurRevenuNet());
    }

    @Test
    void troisEnfantsAugmententFortementLeNombreDeParts() {
        // EXIG-SIM-03 et EXIG-SIM-05
        SimulateurReusine simulateur = new SimulateurReusine();

        simulateur.calculer(60000, SituationFamiliale.MARIE, 3, 0, false);

        assertEquals(8, simulateur.getNbDemiPartsFoyerFiscal());
        assertEquals(881, simulateur.getImpotAvantDecote());
        assertEquals(881, simulateur.getDecote());
        assertEquals(0, simulateur.getImpotSurRevenuNet());
    }

    @Test
    void pacseEstTraiteCommeUnCouple() {
        // EXIG-SIM-02
        SimulateurReusine simulateur = new SimulateurReusine();

        simulateur.calculer(40000, SituationFamiliale.PACSE, 0, 0, false);

        assertEquals(4, simulateur.getNbDemiPartsFoyerFiscal());
        assertEquals(609, simulateur.getImpotSurRevenuNet());
    }

    @Test
    void veufAvecEnfantConserveDeuxPartsDeclarants() {
        // EXIG-SIM-02 et EXIG-SIM-03
        SimulateurReusine simulateur = new SimulateurReusine();

        simulateur.calculer(50000, SituationFamiliale.VEUF, 1, 0, false);

        assertEquals(5, simulateur.getNbDemiPartsFoyerFiscal());
        assertEquals(1788, simulateur.getImpotAvantDecote());
        assertEquals(661, simulateur.getDecote());
        assertEquals(1127, simulateur.getImpotSurRevenuNet());
    }

    @Test
    void refuseUneSituationFamilialeAbsente() {
        // EXIG-SIM-07
        SimulateurReusine simulateur = new SimulateurReusine();

        assertThrows(IllegalArgumentException.class,
                () -> simulateur.calculer(20000, null, 0, 0, false));
    }

    @Test
    void refuseUnRevenuNegatif() {
        // EXIG-SIM-07
        SimulateurReusine simulateur = new SimulateurReusine();

        assertThrows(IllegalArgumentException.class,
                () -> simulateur.calculer(-1, SituationFamiliale.CELIBATAIRE,
                        0, 0, false));
    }

    @Test
    void refuseUnNombreEnfantsNegatif() {
        // EXIG-SIM-07
        SimulateurReusine simulateur = new SimulateurReusine();

        assertThrows(IllegalArgumentException.class,
                () -> simulateur.calculer(20000, SituationFamiliale.CELIBATAIRE,
                        -1, 0, false));
    }

    @Test
    void refuseUnNombreEnfantsHandicapesNegatif() {
        // EXIG-SIM-07
        SimulateurReusine simulateur = new SimulateurReusine();

        assertThrows(IllegalArgumentException.class,
                () -> simulateur.calculer(20000, SituationFamiliale.CELIBATAIRE,
                        1, -1, false));
    }

    @Test
    void refusePlusDEnfantsHandicapesQueDEnfantsACharge() {
        // EXIG-SIM-07
        SimulateurReusine simulateur = new SimulateurReusine();

        assertThrows(IllegalArgumentException.class,
                () -> simulateur.calculer(20000, SituationFamiliale.CELIBATAIRE,
                        1, 2, false));
    }
}