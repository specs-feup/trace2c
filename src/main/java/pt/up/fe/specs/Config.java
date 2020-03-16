package pt.up.fe.specs;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class that stores the information from the configuration file.
 * 
 * @author Afonso
 *
 */
public class Config {

    int loadstores;
    List<String> inputs = new ArrayList<>();
    List<String> input_types = new ArrayList<>();
    String outputs;
    String output_type;
    boolean arithmetic;
    String folding;
    boolean acc;
    boolean full_part;
    String graph;
    boolean full_connect;

    public Config() {
        // TODO Auto-generated constructor stub
    }

}
