import java.io.*;
import java.util.ArrayList;

class Main {
	public static void main(String[] args) throws Exception {
		Test t = new Test();
		//XmlSerializer.serialize(t);
		JsonSerializer.serialize(t);
	}
}
