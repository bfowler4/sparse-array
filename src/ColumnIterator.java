abstract class ColumnIterator implements java.util.Iterator {
	public abstract ElemIterator next();

	public abstract boolean hasNext();

	public void remove() {
		throw new UnsupportedOperationException();
	}
}