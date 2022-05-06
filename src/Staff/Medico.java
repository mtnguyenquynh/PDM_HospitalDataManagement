package Staff;

import PrefixState.Prefix;

public class Medico extends Staff {
    
    public Medico(String ID, String name, String description) throws Exception {
        super(ID, name, description);
        this.prefix = Prefix.Medico;

    }

    public Medico(String ID, String name) throws Exception {
        this(ID, name, null);
    }
    

    // ----------------------------------------------------------
    

}
