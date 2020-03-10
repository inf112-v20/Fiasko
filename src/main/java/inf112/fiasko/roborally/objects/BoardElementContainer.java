package inf112.fiasko.roborally.objects;

import inf112.fiasko.roborally.element_properties.Position;

public class BoardElementContainer <K>{
    K obj;
    Position pos;

    BoardElementContainer(K obj, Position pos) {
        this.obj = obj;
        this.pos = pos;
    }

    public K getObject() {
        return obj;
    }

    public Position getPosition() {
        return pos;
    }
}
