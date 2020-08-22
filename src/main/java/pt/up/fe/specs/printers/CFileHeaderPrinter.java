package pt.up.fe.specs.printers;

import pt.up.fe.specs.Config;

import java.io.BufferedWriter;
import java.io.IOException;

public class CFileHeaderPrinter {
    private final BufferedWriter outBuffer;

    public CFileHeaderPrinter(BufferedWriter outBuffer) {
        this.outBuffer = outBuffer;
    }

    public void print() {

        if (Config.hasIncludes()) {
            for (String include : Config.getIncludes()) {
                try {
                    outBuffer.append("#include " + include + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (Config.hasDefines()) {
            try {
                outBuffer.append("\n");
                for (String define : Config.getDefines()) {
                    outBuffer.append("#define " + define + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
