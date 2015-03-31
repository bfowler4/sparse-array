public class RowElem {
	int index;
	RowElem next;
	MyMatrixElem data;

	public RowElem(MyMatrixElem data, int index) {
		this.index = index;
		this.data = data;
	}

	public void setNext(RowElem next) {
		this.next = next;
	}

	public RowElem getNext() {
		return next;
	}

	public int getIndex() {
		return index;
	}

	public MyMatrixElem getElem() {
		return data;
	}
	
	public void setElem(MyMatrixElem elem) {
		data = elem;
	}
}
