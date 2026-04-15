package com.kerware.simulateurreusine;

import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;
import com.kerware.simulateurreusine.legacy.SimulateurLegacy;

public class AdaptateurCalculateurImpot implements ICalculateurImpot {

    private int revenusNet;
    private SituationFamiliale situationFamiliale;
    private int nbEnfantsACharge;
    private int nbEnfantsSituationHandicap;
    private boolean parentIsole;

    private int impotSurRevenuNet;

    @Override
    public void setRevenusNet(int rn) {
        this.revenusNet = rn;
    }

    @Override
    public void setSituationFamiliale(SituationFamiliale sf) {
        this.situationFamiliale = sf;
    }

    @Override
    public void setNbEnfantsACharge(int nbe) {
        this.nbEnfantsACharge = nbe;
    }

    @Override
    public void setNbEnfantsSituationHandicap(int nbesh) {
        this.nbEnfantsSituationHandicap = nbesh;
    }

    @Override
    public void setParentIsole(boolean pi) {
        this.parentIsole = pi;
    }

    @Override
    public void calculImpotSurRevenuNet() {
        SimulateurLegacy simulateurLegacy = new SimulateurLegacy();
        this.impotSurRevenuNet = (int) simulateurLegacy.calculImpot(
                revenusNet,
                situationFamiliale,
                nbEnfantsACharge,
                nbEnfantsSituationHandicap,
                parentIsole
        );
    }

    @Override
    public int getRevenuFiscalReference() {
        throw new UnsupportedOperationException("Sera implémenté pendant le réusinage.");
    }

    @Override
    public int getAbattement() {
        throw new UnsupportedOperationException("Sera implémenté pendant le réusinage.");
    }

    @Override
    public int getNbPartsFoyerFiscal() {
        throw new UnsupportedOperationException("Sera implémenté pendant le réusinage.");
    }

    @Override
    public int getImpotAvantDecote() {
        throw new UnsupportedOperationException("Sera implémenté pendant le réusinage.");
    }

    @Override
    public int getDecote() {
        throw new UnsupportedOperationException("Sera implémenté pendant le réusinage.");
    }

    @Override
    public int getImpotSurRevenuNet() {
        return impotSurRevenuNet;
    }
}