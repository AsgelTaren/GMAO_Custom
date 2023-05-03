package gmao_custom.app;

import java.io.File;

import com.formdev.flatlaf.FlatLightLaf;

public class Main {

	public static void main(String[] args) throws Exception {
		FlatLightLaf.setup();
		App app = new App(new File("testZone/test.db"));
		app.create();
	}

}