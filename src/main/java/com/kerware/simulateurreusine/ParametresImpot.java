package com.kerware.simulateurreusine;

import java.util.Arrays;

/**
 * Contient les paramètres fiscaux utilisés par le simulateur.
 * Cette classe permet de rendre le calcul modulaire et paramétrable.
 */
public final class ParametresImpot {

    private static final int ZERO = 0;

    private static final int LIMITE_TRANCHE_1_LEGACY = 11294;
    private static final int LIMITE_TRANCHE_2_LEGACY = 28797;
    private static final int LIMITE_TRANCHE_3_LEGACY = 82341;
    private static final int LIMITE_TRANCHE_4_LEGACY = 177106;

    private static final int LIMITE_TRANCHE_1_2025 = 11497;
    private static final int LIMITE_TRANCHE_2_2025 = 29315;
    private static final int LIMITE_TRANCHE_3_2025 = 83823;
    private static final int LIMITE_TRANCHE_4_2025 = 180294;

    private static final double TAUX_TRANCHE_0 = 0.0;
    private static final double TAUX_TRANCHE_1 = 0.11;
    private static final double TAUX_TRANCHE_2 = 0.30;
    private static final double TAUX_TRANCHE_3 = 0.41;
    private static final double TAUX_TRANCHE_4 = 0.45;

    private static final int ABATTEMENT_MINIMUM_LEGACY = 495;
    private static final int ABATTEMENT_MAXIMUM_LEGACY = 14171;
    private static final int ABATTEMENT_MINIMUM_2025 = 504;
    private static final int ABATTEMENT_MAXIMUM_2025 = 14426;

    private static final int POURCENTAGE_TAUX_ABATTEMENT = 10;
    private static final double TAUX_DECOTE_DEFAUT = 0.4525;
    private static final double DIVISEUR_POURCENTAGE = 100.0;

    private static final double PLAFOND_DEMI_PART_LEGACY = 1759;
    private static final double SEUIL_DECOTE_DECLARANT_SEUL_LEGACY = 1929;
    private static final double SEUIL_DECOTE_DECLARANT_COUPLE_LEGACY = 3191;
    private static final double DECOTE_MAX_DECLARANT_SEUL_LEGACY = 873;
    private static final double DECOTE_MAX_DECLARANT_COUPLE_LEGACY = 1444;

    private static final double PLAFOND_DEMI_PART_2025 = 1791;
    private static final double SEUIL_DECOTE_DECLARANT_SEUL_2025 = 1964;
    private static final double SEUIL_DECOTE_DECLARANT_COUPLE_2025 = 3249;
    private static final double DECOTE_MAX_DECLARANT_SEUL_2025 = 889;
    private static final double DECOTE_MAX_DECLARANT_COUPLE_2025 = 1470;

    private static final int INDEX_ABATTEMENT_MINIMUM = 0;
    private static final int INDEX_ABATTEMENT_MAXIMUM = 1;
    private static final int INDEX_TAUX_ABATTEMENT = 2;

    private static final int INDEX_SEUIL_DECOTE_SEUL = 0;
    private static final int INDEX_SEUIL_DECOTE_COUPLE = 1;
    private static final int INDEX_DECOTE_MAX_SEUL = 2;
    private static final int INDEX_DECOTE_MAX_COUPLE = 3;
    private static final int INDEX_TAUX_DECOTE = 4;

    private final int[] limitesTranches;
    private final double[] tauxTranches;

    private final int abattementMinimum;
    private final int abattementMaximum;
    private final double tauxAbattement;

    private final double plafondDemiPart;

    private final double seuilDecoteDeclarantSeul;
    private final double seuilDecoteDeclarantCouple;
    private final double decoteMaxDeclarantSeul;
    private final double decoteMaxDeclarantCouple;
    private final double tauxDecote;

    private ParametresImpot(int[] limites,
                            double[] taux,
                            int[] abattements,
                            double[] quotientFamilial,
                            double[] decote) {
        this.limitesTranches = Arrays.copyOf(limites, limites.length);
        this.tauxTranches = Arrays.copyOf(taux, taux.length);

        this.abattementMinimum = abattements[INDEX_ABATTEMENT_MINIMUM];
        this.abattementMaximum = abattements[INDEX_ABATTEMENT_MAXIMUM];
        this.tauxAbattement = abattements[INDEX_TAUX_ABATTEMENT] / DIVISEUR_POURCENTAGE;

        this.plafondDemiPart = quotientFamilial[ZERO];

        this.seuilDecoteDeclarantSeul = decote[INDEX_SEUIL_DECOTE_SEUL];
        this.seuilDecoteDeclarantCouple = decote[INDEX_SEUIL_DECOTE_COUPLE];
        this.decoteMaxDeclarantSeul = decote[INDEX_DECOTE_MAX_SEUL];
        this.decoteMaxDeclarantCouple = decote[INDEX_DECOTE_MAX_COUPLE];
        this.tauxDecote = decote[INDEX_TAUX_DECOTE];
    }

    /**
     * Paramètres repris du simulateur legacy.
     * On les garde d'abord pour conserver exactement les mêmes résultats.
     *
     * @return les paramètres fiscaux du simulateur historique
     */
    public static ParametresImpot creerParametresLegacy() {
        return new ParametresImpot(
                new int[]{
                        ZERO,
                        LIMITE_TRANCHE_1_LEGACY,
                        LIMITE_TRANCHE_2_LEGACY,
                        LIMITE_TRANCHE_3_LEGACY,
                        LIMITE_TRANCHE_4_LEGACY,
                        Integer.MAX_VALUE
                },
                new double[]{
                        TAUX_TRANCHE_0,
                        TAUX_TRANCHE_1,
                        TAUX_TRANCHE_2,
                        TAUX_TRANCHE_3,
                        TAUX_TRANCHE_4
                },
                new int[]{
                        ABATTEMENT_MINIMUM_LEGACY,
                        ABATTEMENT_MAXIMUM_LEGACY,
                        POURCENTAGE_TAUX_ABATTEMENT
                },
                new double[]{PLAFOND_DEMI_PART_LEGACY},
                new double[]{
                        SEUIL_DECOTE_DECLARANT_SEUL_LEGACY,
                        SEUIL_DECOTE_DECLARANT_COUPLE_LEGACY,
                        DECOTE_MAX_DECLARANT_SEUL_LEGACY,
                        DECOTE_MAX_DECLARANT_COUPLE_LEGACY,
                        TAUX_DECOTE_DEFAUT
                }
        );
    }

    /**
     * Crée les paramètres fiscaux applicables au calcul 2025.
     *
     * @return les paramètres fiscaux 2025
     */
    public static ParametresImpot creerParametres2025() {
        return new ParametresImpot(
                new int[]{
                        ZERO,
                        LIMITE_TRANCHE_1_2025,
                        LIMITE_TRANCHE_2_2025,
                        LIMITE_TRANCHE_3_2025,
                        LIMITE_TRANCHE_4_2025,
                        Integer.MAX_VALUE
                },
                new double[]{
                        TAUX_TRANCHE_0,
                        TAUX_TRANCHE_1,
                        TAUX_TRANCHE_2,
                        TAUX_TRANCHE_3,
                        TAUX_TRANCHE_4
                },
                new int[]{
                        ABATTEMENT_MINIMUM_2025,
                        ABATTEMENT_MAXIMUM_2025,
                        POURCENTAGE_TAUX_ABATTEMENT
                },
                new double[]{PLAFOND_DEMI_PART_2025},
                new double[]{
                        SEUIL_DECOTE_DECLARANT_SEUL_2025,
                        SEUIL_DECOTE_DECLARANT_COUPLE_2025,
                        DECOTE_MAX_DECLARANT_SEUL_2025,
                        DECOTE_MAX_DECLARANT_COUPLE_2025,
                        TAUX_DECOTE_DEFAUT
                }
        );
    }

    public int[] getLimitesTranches() {
        return Arrays.copyOf(limitesTranches, limitesTranches.length);
    }

    public double[] getTauxTranches() {
        return Arrays.copyOf(tauxTranches, tauxTranches.length);
    }

    public int getAbattementMinimum() {
        return abattementMinimum;
    }

    public int getAbattementMaximum() {
        return abattementMaximum;
    }

    public double getTauxAbattement() {
        return tauxAbattement;
    }

    public double getPlafondDemiPart() {
        return plafondDemiPart;
    }

    public double getSeuilDecoteDeclarantSeul() {
        return seuilDecoteDeclarantSeul;
    }

    public double getSeuilDecoteDeclarantCouple() {
        return seuilDecoteDeclarantCouple;
    }

    public double getDecoteMaxDeclarantSeul() {
        return decoteMaxDeclarantSeul;
    }

    public double getDecoteMaxDeclarantCouple() {
        return decoteMaxDeclarantCouple;
    }

    public double getTauxDecote() {
        return tauxDecote;
    }
}