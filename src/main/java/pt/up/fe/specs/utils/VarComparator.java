package pt.up.fe.specs.utils;

import pt.up.fe.specs.Var;

import java.util.Comparator;

public class VarComparator implements Comparator<Var> {

    @Override
    public int compare(Var v1, Var v2) {
        return v1.getName().compareTo(v2.getName());
    }
}
