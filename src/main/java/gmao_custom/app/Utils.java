package gmao_custom.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Utils {

	public static final String readFile(File file) throws IOException {
		return Files.readString(file.toPath());
	}

	public static final String readAllData(InputStream stream) throws IOException {
		BufferedInputStream in = new BufferedInputStream(stream);
		return new String(in.readAllBytes());
	}

}
