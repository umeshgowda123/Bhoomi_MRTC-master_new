package app.bmc.com.BHOOMI_MRTC.model;

public class Tree {
    private String treename;
    private String numberoftrees;

    public String getTreename() {
        return treename == null ? "" : treename;
    }

    public void setTreename(String treename) {
        this.treename = treename;
    }

    public String getNumberoftrees() {
        return numberoftrees == null ? "" : numberoftrees;
    }

    public void setNumberoftrees(String numberoftrees) {
        this.numberoftrees = numberoftrees;
    }
}
