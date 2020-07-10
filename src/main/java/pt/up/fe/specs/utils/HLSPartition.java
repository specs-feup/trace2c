package pt.up.fe.specs.utils;

/**
 * Partition to write for HLS
 */
public class HLSPartition {

    private int dim;
    private int factor;

    public int getDim() {
        return dim;
    }

    public int getFactor() {
        return factor;
    }

    public HLSPartition(int dim, int factor) {
        this.dim = dim;
        this.factor = factor;
    }
}
