package info.androidhive.loginandregistration.activity;

/**
 * Created by passw on 09-05-2017.
 */

public class KONDITIONSTRAENING {

    private String kondi_type;
    private int helbredstilstand;
    private int evaluering;
    private int kondi_tid;
    private int beloenninger;
    private int afstand;

    KONDITIONSTRAENING(){

    }


    public String getKondi_type() {
        return kondi_type;
    }

    public void setKondi_type(String kondi_type) {
        this.kondi_type = kondi_type;
    }

    public int getHelbredstilstand() {
        return helbredstilstand;
    }

    public void setHelbredstilstand(int helbredstilstand) {
        this.helbredstilstand = helbredstilstand;
    }

    public int getEvaluering() {
        return evaluering;
    }

    public void setEvaluering(int evaluering) {
        this.evaluering = evaluering;
    }

    public int getKondi_tid() {
        return kondi_tid;
    }

    public void setKondi_tid(int kondi_tid) {
        this.kondi_tid = kondi_tid;
    }

    public int getBeloenninger() {
        return beloenninger;
    }

    public void setBeloenninger(int beloenninger) {
        this.beloenninger = beloenninger;
    }

    public int getAfstand() {
        return afstand;
    }

    public void setAfstand(int afstand) {
        this.afstand = afstand;
    }
}
