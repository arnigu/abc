package p;
class Inner {
	/** Comment */
	private A a;

	void f(){
		///this.
		a.i= 1;
	}

	/**
	 * @param a
	 */
	Inner(A a) {
		this.a = a;
	}
}