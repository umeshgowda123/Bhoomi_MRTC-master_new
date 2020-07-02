package app.bmc.com.BHOOMI_MRTC.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Treedetails Data.
 */
public class Treedetails {
    private List<Tree> tree;

    public List<Tree> getTree() {
        return tree == null ? new ArrayList<Tree>() : tree;
    }
    public void setTree(List<Tree> tree) {
        this.tree = tree;
    }
}
