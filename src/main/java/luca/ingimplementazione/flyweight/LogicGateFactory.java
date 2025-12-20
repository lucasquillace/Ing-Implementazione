package luca.ingimplementazione.flyweight;

import java.util.HashMap;

public class LogicGateFactory {
    private static final HashMap<String, LogicGate> flyweights = new HashMap();

    private static final String IMAGEPATH = "/luca/ingimplementazione/images/";

    public static LogicGate getGate(String gateType) {
        if (flyweights.containsKey(gateType)) {
            return flyweights.get(gateType);
        }
        else{
            ConcreteLogicGate concreteLogicGate = null;
            switch (gateType) {
                case "AND" -> concreteLogicGate = new ConcreteLogicGate(gateType , IMAGEPATH+"AND_GATE.png" );
                case "OR" -> concreteLogicGate = new ConcreteLogicGate(gateType , IMAGEPATH+"OR_GATE.png" );
                case "NOT" -> concreteLogicGate = new ConcreteLogicGate(gateType , IMAGEPATH+"NOT_GATE.png" );
                case "NOR" -> concreteLogicGate = new ConcreteLogicGate(gateType , IMAGEPATH+"NOR_GATE.png" );
                case "NAND" -> concreteLogicGate = new ConcreteLogicGate(gateType , IMAGEPATH+"NAND_GATE.png" );
                case "XOR" -> concreteLogicGate = new ConcreteLogicGate(gateType , IMAGEPATH+"XOR_GATE.png");
                default -> concreteLogicGate = new ConcreteLogicGate("Impossibile visualizzare" , "path");
            }
            flyweights.put(gateType, concreteLogicGate);
            return concreteLogicGate;
        }
    }

}
