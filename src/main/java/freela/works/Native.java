package freela.works;

public class Native {

	int pro;

	public Native(int k) {
		pro = k;
	}

	public native void fromC();

	public static void main(String args[]) {
		Native native1 = new Native(2);
	
		native1.fromC();
	}
}
