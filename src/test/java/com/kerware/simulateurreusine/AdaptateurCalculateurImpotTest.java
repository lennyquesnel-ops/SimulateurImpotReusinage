package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdaptateurCalculateurImpotTest {

    @Test
    void celibataireSansEnfantAvec20000EurosDonne150Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                20000,
                SituationFamiliale.CELIBATAIRE,
                0,
                0,
                false
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(150, calculateur.getImpotSurRevenuNet());
    }

    @Test
    void celibataireSansEnfantAvec40000EurosDonne3965Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                40000,
                SituationFamiliale.CELIBATAIRE,
                0,
                0,
                false
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(3965, calculateur.getImpotSurRevenuNet());
    }

    @Test
    void marieSansEnfantAvec40000EurosDonne609Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                40000,
                SituationFamiliale.MARIE,
                0,
                0,
                false
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(609, calculateur.getImpotSurRevenuNet());
    }

    @Test
    void divorceParentIsoleAvecUnEnfantEt40000EurosDonne1190Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                40000,
                SituationFamiliale.DIVORCE,
                1,
                0,
                true
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(1190, calculateur.getImpotSurRevenuNet());
    }

    @Test
    void marieAvecUnEnfantDontUnHandicapeEt70000EurosDonne3085Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                70000,
                SituationFamiliale.MARIE,
                1,
                1,
                false
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(3085, calculateur.getImpotSurRevenuNet());
    }

    @Test
    void pacseSansEnfantAvec40000EurosDonne609Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                40000,
                SituationFamiliale.PACSE,
                0,
                0,
                false
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(609, calculateur.getImpotSurRevenuNet());
    }

    private AdaptateurCalculateurImpot creerCalculateur(
            int revenusNet,
            SituationFamiliale situationFamiliale,
            int nbEnfantsACharge,
            int nbEnfantsSituationHandicap,
            boolean parentIsole
    ) {
        AdaptateurCalculateurImpot calculateur = new AdaptateurCalculateurImpot();
        calculateur.setRevenusNet(revenusNet);
        calculateur.setSituationFamiliale(situationFamiliale);
        calculateur.setNbEnfantsACharge(nbEnfantsACharge);
        calculateur.setNbEnfantsSituationHandicap(nbEnfantsSituationHandicap);
        calculateur.setParentIsole(parentIsole);
        return calculateur;
    }
}