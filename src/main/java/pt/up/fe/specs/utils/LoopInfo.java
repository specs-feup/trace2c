package pt.up.fe.specs.utils;

import java.util.ArrayList;

public class LoopInfo {

    ArrayList<SpecificLoopInfo> loopInfoList = new ArrayList<>();

    public LoopInfo() {}

    public void addSpecificLoopInfo(SpecificLoopInfo i) {
        loopInfoList.add(i);
    }

    public ArrayList<SpecificLoopInfo> getLoopList() {
        return loopInfoList;
    }

    public SpecificLoopInfo getLoopWithName(String loopName) {
        for (SpecificLoopInfo info : loopInfoList) {
            if (info.name.equals(loopName)) {
                return info;
            }
        }
        return null;
    }

    public boolean hasSpecificLoopInfo(String loopName) {
        for (SpecificLoopInfo info : loopInfoList) {
            if (info.name.equals(loopName)) {
                return true;
            }
        }
        return false;
    }
}
