package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdaptateurCalculateurImpotTest {

    @Test
    void celibataireSansEnfantAvec20000EurosDonne199Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                20000,
                SituationFamiliale.CELIBATAIRE,
                0,
                0,
                false
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(199, calculateur.getImpotSurRevenuNet());
    }

    @Test
    void celibataireSansEnfantAvec40000EurosDonne4086Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                40000,
                SituationFamiliale.CELIBATAIRE,
                0,
                0,
                false
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(4086, calculateur.getImpotSurRevenuNet());
    }

    @Test
    void marieSansEnfantAvec40000EurosDonne698Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                40000,
                SituationFamiliale.MARIE,
                0,
                0,
                false
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(698, calculateur.getImpotSurRevenuNet());
    }

    @Test
    void divorceParentIsoleAvecUnEnfantEt40000EurosDonne1269Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                40000,
                SituationFamiliale.DIVORCE,
                1,
                0,
                true
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(1269, calculateur.getImpotSurRevenuNet());
    }

    @Test
    void marieAvecUnEnfantDontUnHandicapeEt70000EurosDonne3203Euros() {
        AdaptateurCalculateurImpot calculateur = creerCalculateur(
                70000,
                SituationFamiliale.MARIE,
                1,
                1,
                false
        );

        calculateur.calculImpotSurRevenuNet();

        assertEquals(3203, calculateur.getImpotSurRevenuNet());
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