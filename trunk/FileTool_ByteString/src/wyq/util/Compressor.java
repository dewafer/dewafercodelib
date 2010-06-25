package wyq.util;

import java.io.IOException;

public interface Compressor {

	public String compressFile(String input) throws IOException;

	public String decompressFile(String input) throws IOException;

	public String compressLine(String input) throws IOException;

	public String decompressLine(String input) throws IOException;
}