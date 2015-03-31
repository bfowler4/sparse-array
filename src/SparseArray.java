public interface SparseArray {
	public Object defaultValue();

	public RowIterator iterateRows();

	public ColumnIterator iterateColumns();

	public Object elementAt(int row, int col);

	public void setValue(int row, int col, Object value);
}