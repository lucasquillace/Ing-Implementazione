package luca.ingimplementazione.flyweight;

import java.util.HashMap;

public class LogicGateFactory {
    private static final HashMap<String, LogicGate> flyweights = new HashMap();


    public static LogicGate getGate(String gateType) {
        if (flyweights.containsKey(gateType)) {
            return flyweights.get(gateType);
        }
        else{
            ConcreteLogicGate concreteLogicGate = null;
            switch (gateType) {
                case "AND" -> concreteLogicGate = new ConcreteLogicGate(gateType , "path_and" );
                case "OR" -> concreteLogicGate = new ConcreteLogicGate(gateType , "path_or" );
                case "NOT" -> concreteLogicGate = new ConcreteLogicGate(gateType , "path_or" );
                case "NOR" -> concreteLogicGate = new ConcreteLogicGate(gateType , "path_or" );
                case "NAND" -> concreteLogicGate = new ConcreteLogicGate(gateType , "path_or" );
                case "XOR" -> concreteLogicGate = new ConcreteLogicGate(gateType , "path_or" );
                default -> concreteLogicGate = new ConcreteLogicGate("Impossibile visualizzare" , "path");
            }
            flyweights.put(gateType, concreteLogicGate);
            return concreteLogicGate;
        }
    }

}
