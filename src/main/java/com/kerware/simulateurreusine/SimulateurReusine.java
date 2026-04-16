package com.kerware.simulateurreusine;

import com.kerware.simulateur.SituationFamiliale;

/**
 * Version réusinée du simulateur.
 */
public class SimulateurReusine {

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

    public SimulateurReusine(ParametresImpot parametresImpot) {
        this.parametresImpot = parametresImpot;
    }

    public void calculer(int revenuNet,
                         SituationFamiliale situationFamiliale,
                         int nbEnfantsACharge,
                         int nbEnfantsSituationHandicap,
                         boolean parentIsole) {

        this.revenuNet = revenuNet;
        this.situationFamiliale = situationFamiliale;
        this.nbEnfantsACharge = nbEnfantsACharge;
        this.nbEnfantsSituationHandicap = nbEnfantsSituationHandicap;
        this.parentIsole = parentIsole;

        this.abattement = calculerAbattement();
        this.revenuFiscalReference = revenuNet - abattement;

        this.nbPartsDeclarants = calculerNbPartsDeclarants();
        this.nbPartsFoyer = calculerNbPartsFoyer();

        int impotAvecPartsDeclarants = calculerImpotProgressif(revenuFiscalReference, nbPartsDeclarants);
        int impotAvecPartsFoyer = calculerImpotProgressif(revenuFiscalReference, nbPartsFoyer);

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
                return 1;
            case MARIE:
            case PACSE:
                return 2;
            case VEUF:
                return nbEnfantsACharge > 0 ? 2 : 1;
            default:
                throw new IllegalArgumentException("Situation familiale inconnue.");
        }
    }

    private double calculerNbPartsFoyer() {
        double parts;

        if (nbEnfantsACharge <= 2) {
            parts = nbPartsDeclarants + (nbEnfantsACharge * 0.5);
        } else {
            parts = nbPartsDeclarants + 1.0 + (nbEnfantsACharge - 2);
        }

        if (parentIsole && nbEnfantsACharge > 0) {
            parts += 0.5;
        }

        parts += nbEnfantsSituationHandicap * 0.5;

        return parts;
    }

    private int calculerImpotProgressif(int revenuFiscalReference, double nbParts) {
        double revenuParPart = revenuFiscalReference / nbParts;
        double impot = 0;

        int[] limites = parametresImpot.getLimitesTranches();
        double[] taux = parametresImpot.getTauxTranches();

        for (int i = 0; i < taux.length; i++) {
            double borneBasse = limites[i];
            double borneHaute = limites[i + 1];

            if (revenuParPart >= borneBasse && revenuParPart < borneHaute) {
                impot += (revenuParPart - borneBasse) * taux[i];
                break;
            } else {
                impot += (borneHaute - borneBasse) * taux[i];
            }
        }

        return (int) Math.round(impot * nbParts);
    }

    private int appliquerPlafonnementQuotientFamilial(int impotAvecPartsDeclarants,
                                                      int impotAvecPartsFoyer) {

        double baisseImpot = impotAvecPartsDeclarants - impotAvecPartsFoyer;
        double ecartParts = nbPartsFoyer - nbPartsDeclarants;
        double plafond = (ecartParts / 0.5) * parametresImpot.getPlafondDemiPart();

        if (baisseImpot >= plafond) {
            return (int) Math.round(impotAvecPartsDeclarants - plafond);
        }

        return impotAvecPartsFoyer;
    }

    private int calculerDecote() {
        double montantDecote = 0;

        if (nbPartsDeclarants == 1 && impotAvantDecote < parametresImpot.getSeuilDecoteDeclarantSeul()) {
            montantDecote = parametresImpot.getDecoteMaxDeclarantSeul()
                    - (impotAvantDecote * parametresImpot.getTauxDecote());
        }

        if (nbPartsDeclarants == 2 && impotAvantDecote < parametresImpot.getSeuilDecoteDeclarantCouple()) {
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
        return (int) Math.round(nbPartsFoyer * 2);
    }
}