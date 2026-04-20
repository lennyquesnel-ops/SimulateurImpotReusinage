package com.kerware.simulateurreusine;

import com.kerware.simulateur.ICalculateurImpot;
import com.kerware.simulateur.SituationFamiliale;

public final class AdaptateurCalculateurImpot implements ICalculateurImpot {

    private int revenusNet;
    private SituationFamiliale situationFamiliale;
    private int nbEnfantsACharge;
    private int nbEnfantsSituationHandicap;
    private boolean parentIsole;

    private SimulateurReusine simulateurReusine;

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
        simulateurReusine = new SimulateurReusine();
        simulateurReusine.calculer(
                revenusNet,
                situationFamiliale,
                nbEnfantsACharge,
                nbEnfantsSituationHandicap,
                parentIsole
        );
    }

    @Override
    public int getRevenuFiscalReference() {
        return simulateurReusine.getRevenuFiscalReference();
    }

    @Override
    public int getAbattement() {
        return simulateurReusine.getAbattement();
    }

    @Override
    public int getNbPartsFoyerFiscal() {
        return simulateurReusine.getNbDemiPartsFoyerFiscal();
    }

    @Override
    public int getImpotAvantDecote() {
        return simulateurReusine.getImpotAvantDecote();
    }

    @Override
    public int getDecote() {
        return simulateurReusine.getDecote();
    }

    @Override
    public int getImpotSurRevenuNet() {
        return simulateurReusine.getImpotSurRevenuNet();
    }
}