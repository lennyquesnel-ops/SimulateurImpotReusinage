package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

/**
 * Lance manuellement un calcul pour observer la progression du simulateur.
 */
public final class MainSimulateurReusine {

    private static final int REVENU_NET_IMPOSABLE = 20000;
    private static final int NOMBRE_ENFANTS_A_CHARGE = 0;
    private static final int NOMBRE_ENFANTS_HANDICAP = 0;

    private MainSimulateurReusine() {
    }

    public static void main(String[] args) {
        SimulateurReusine simulateur = new SimulateurReusine();

        System.out.println("Lancement du simulateur réusiné");
        System.out.println("Entrées :");
        System.out.println("- revenu net imposable = " + REVENU_NET_IMPOSABLE);
        System.out.println("- situation familiale = " + SituationFamiliale.CELIBATAIRE);
        System.out.println("- enfants à charge = " + NOMBRE_ENFANTS_A_CHARGE);
        System.out.println("- enfants handicapés = " + NOMBRE_ENFANTS_HANDICAP);
        System.out.println("- parent isolé = false");

        simulateur.calculer(
                REVENU_NET_IMPOSABLE,
                SituationFamiliale.CELIBATAIRE,
                NOMBRE_ENFANTS_A_CHARGE,
                NOMBRE_ENFANTS_HANDICAP,
                false
        );

        System.out.println("Résultats calculés :");
        System.out.println("- abattement = " + simulateur.getAbattement());
        System.out.println("- revenu fiscal de référence = "
                + simulateur.getRevenuFiscalReference());
        System.out.println("- nombre de demi-parts = "
                + simulateur.getNbDemiPartsFoyerFiscal());
        System.out.println("- impôt avant décote = " + simulateur.getImpotAvantDecote());
        System.out.println("- décote = " + simulateur.getDecote());
        System.out.println("- impôt sur le revenu net = " + simulateur.getImpotSurRevenuNet());
    }
}