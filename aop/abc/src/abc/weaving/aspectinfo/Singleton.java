package arc.weaving.aspectinfo;

import polyglot.util.Position;

import soot.*;

/** A <code>singleton</code> per clause. */
public class Singleton extends AbstractPer {
    public Singleton(Position pos) {
	super(pos);
    }
}
