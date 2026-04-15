package com.kerware.simulateurreusine;

import java.util.Arrays;

/**
 * Contient les paramètres fiscaux utilisés par le simulateur.
 * Cette classe permet de rendre le calcul modulaire et paramétrable.
 */
public class ParametresImpot {

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

    public ParametresImpot(int[] limitesTranches,
                           double[] tauxTranches,
                           int abattementMinimum,
                           int abattementMaximum,
                           double tauxAbattement,
                           double plafondDemiPart,
                           double seuilDecoteDeclarantSeul,
                           double seuilDecoteDeclarantCouple,
                           double decoteMaxDeclarantSeul,
                           double decoteMaxDeclarantCouple,
                           double tauxDecote) {

        this.limitesTranches = Arrays.copyOf(limitesTranches, limitesTranches.length);
        this.tauxTranches = Arrays.copyOf(tauxTranches, tauxTranches.length);
        this.abattementMinimum = abattementMinimum;
        this.abattementMaximum = abattementMaximum;
        this.tauxAbattement = tauxAbattement;
        this.plafondDemiPart = plafondDemiPart;
        this.seuilDecoteDeclarantSeul = seuilDecoteDeclarantSeul;
        this.seuilDecoteDeclarantCouple = seuilDecoteDeclarantCouple;
        this.decoteMaxDeclarantSeul = decoteMaxDeclarantSeul;
        this.decoteMaxDeclarantCouple = decoteMaxDeclarantCouple;
        this.tauxDecote = tauxDecote;
    }

    /**
     * Paramètres repris du simulateur legacy.
     * On les garde d'abord pour conserver exactement les mêmes résultats.
     */
    public static ParametresImpot creerParametresLegacy() {
        return new ParametresImpot(
                new int[]{0, 11294, 28797, 82341, 177106, Integer.MAX_VALUE},
                new double[]{0.0, 0.11, 0.30, 0.41, 0.45},
                495,
                14171,
                0.10,
                1759,
                1929,
                3191,
                873,
                1444,
                0.4525
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