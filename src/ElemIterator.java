abstract class ElemIterator implements java.util.Iterator {
	public abstract boolean iteratingRow();

	public abstract boolean iteratingCol();

	public abstract int nonIteratingIndex();

	public abstract MatrixElem next();

	public abstract boolean hasNext();

	public void remove() {
		throw new UnsupportedOperationException();
	}
}