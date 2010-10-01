package wyq.infrastructure.test;

import java.io.IOException;

import wyq.infrastructure.FileTool;

public class FileToolTest extends FileTool {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		FileToolTest test = new FileToolTest();
		test.setWorkspace("D:\\test");
//		copyFile(("AIO_CDB_NonNet_Full_Win_WW_130_141.exe"),
//				("tarFileName.exe"));
		test.copyResFileToWorkspace(FileToolTest.class, "VBA_.pdf");
	}

}
