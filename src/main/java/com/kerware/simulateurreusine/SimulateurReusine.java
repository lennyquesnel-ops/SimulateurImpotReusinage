package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

/**
 * Version réusinée du simulateur.
 */
public final class SimulateurReusine {

    private static final double UNE_PART = 1.0;
    private static final double DEUX_PARTS = 2.0;
    private static final double DEMI_PART = 0.5;
    private static final int UNE_PART_EN_DEMI_PARTS = 2;
    private static final int DEUX_ENFANTS = 2;

    private final ParametresImpot parametresImpot;

    private int revenuNet;
    private SituationFamiliale situationFamiliale;
    private int nbEnfantsACharge;
    private int nbEnfantsSituationHandicap;
    private boolean parentIsole;

    private int abattement;
    private int revenuFiscalReference;
    private double nbPartsDeclarants;
    private double nbPartsFoyer;
    private int impotAvantDecote;
    private int decote;
    private int impotSurRevenuNet;

    public SimulateurReusine() {
        this(ParametresImpot.creerParametres2025());
    }

    public SimulateurReusine(ParametresImpot parametres) {
        this.parametresImpot = parametres;
    }

    public void calculer(int revenuNetImposable,
                         SituationFamiliale situation,
                         int nombreEnfantsACharge,
                         int nombreEnfantsSituationHandicap,
                         boolean estParentIsole) {

        this.revenuNet = revenuNetImposable;
        this.situationFamiliale = situation;
        this.nbEnfantsACharge = nombreEnfantsACharge;
        this.nbEnfantsSituationHandicap = nombreEnfantsSituationHandicap;
        this.parentIsole = estParentIsole;

        this.abattement = calculerAbattement();
        this.revenuFiscalReference = revenuNet - abattement;

        this.nbPartsDeclarants = calculerNbPartsDeclarants();
        this.nbPartsFoyer = calculerNbPartsFoyer();

        int impotAvecPartsDeclarants = calculerImpotProgressif(
                revenuFiscalReference,
                nbPartsDeclarants
        );
        int impotAvecPartsFoyer = calculerImpotProgressif(
                revenuFiscalReference,
                nbPartsFoyer
        );

        this.impotAvantDecote = appliquerPlafonnementQuotientFamilial(
                impotAvecPartsDeclarants,
                impotAvecPartsFoyer
        );

        this.decote = calculerDecote();
        this.impotSurRevenuNet = impotAvantDecote - decote;
    }

    private int calculerAbattement() {
        double abattementCalcule = revenuNet * parametresImpot.getTauxAbattement();

        if (abattementCalcule < parametresImpot.getAbattementMinimum()) {
            abattementCalcule = parametresImpot.getAbattementMinimum();
        }

        if (abattementCalcule > parametresImpot.getAbattementMaximum()) {
            abattementCalcule = parametresImpot.getAbattementMaximum();
        }

        return (int) Math.round(abattementCalcule);
    }

    private double calculerNbPartsDeclarants() {
        if (situationFamiliale == null) {
            throw new IllegalArgumentException("La situation familiale est obligatoire.");
        }

        switch (situationFamiliale) {
            case CELIBATAIRE:
            case DIVORCE:
                return UNE_PART;
            case MARIE:
            case PACSE:
                return DEUX_PARTS;
            case VEUF:
                return nbEnfantsACharge > 0 ? DEUX_PARTS : UNE_PART;
            default:
                throw new IllegalArgumentException("Situation familiale inconnue.");
        }
    }

    private double calculerNbPartsFoyer() {
        double parts;

        if (nbEnfantsACharge <= DEUX_ENFANTS) {
            parts = nbPartsDeclarants + (nbEnfantsACharge * DEMI_PART);
        } else {
            parts = nbPartsDeclarants + UNE_PART + (nbEnfantsACharge - DEUX_ENFANTS);
        }

        if (parentIsole && nbEnfantsACharge > 0) {
            parts += DEMI_PART;
        }

        parts += nbEnfantsSituationHandicap * DEMI_PART;

        return parts;
    }

    private int calculerImpotProgressif(int revenuFiscalRef, double nbParts) {
        double revenuParPart = revenuFiscalRef / nbParts;
        double impot = 0;

        int[] limites = parametresImpot.getLimitesTranches();
        double[] taux = parametresImpot.getTauxTranches();

        for (int i = 0; i < taux.length; i++) {
            double borneBasse = limites[i];
            double borneHaute = limites[i + 1];

            if (revenuParPart >= borneBasse && revenuParPart < borneHaute) {
                impot += (revenuParPart - borneBasse) * taux[i];
                break;
            }

            impot += (borneHaute - borneBasse) * taux[i];
        }

        return (int) Math.round(impot * nbParts);
    }

    private int appliquerPlafonnementQuotientFamilial(int impotAvecPartsDeclarants,
                                                      int impotAvecPartsFoyer) {

        double baisseImpot = impotAvecPartsDeclarants - impotAvecPartsFoyer;
        double ecartParts = nbPartsFoyer - nbPartsDeclarants;
        double plafond = (ecartParts / DEMI_PART) * parametresImpot.getPlafondDemiPart();

        if (baisseImpot >= plafond) {
            return (int) Math.round(impotAvecPartsDeclarants - plafond);
        }

        return impotAvecPartsFoyer;
    }

    private int calculerDecote() {
        double montantDecote = 0;

        if (nbPartsDeclarants == UNE_PART
                && impotAvantDecote < parametresImpot.getSeuilDecoteDeclarantSeul()) {
            montantDecote = parametresImpot.getDecoteMaxDeclarantSeul()
                    - (impotAvantDecote * parametresImpot.getTauxDecote());
        }

        if (nbPartsDeclarants == DEUX_PARTS
                && impotAvantDecote < parametresImpot.getSeuilDecoteDeclarantCouple()) {
            montantDecote = parametresImpot.getDecoteMaxDeclarantCouple()
                    - (impotAvantDecote * parametresImpot.getTauxDecote());
        }

        montantDecote = Math.round(montantDecote);

        if (impotAvantDecote <= montantDecote) {
            montantDecote = impotAvantDecote;
        }

        return (int) montantDecote;
    }

    public int getRevenuFiscalReference() {
        return revenuFiscalReference;
    }

    public int getAbattement() {
        return abattement;
    }

    public int getImpotAvantDecote() {
        return impotAvantDecote;
    }

    public int getDecote() {
        return decote;
    }

    public int getImpotSurRevenuNet() {
        return impotSurRevenuNet;
    }

    public int getNbDemiPartsFoyerFiscal() {
        return (int) Math.round(nbPartsFoyer * UNE_PART_EN_DEMI_PARTS);
    }
}