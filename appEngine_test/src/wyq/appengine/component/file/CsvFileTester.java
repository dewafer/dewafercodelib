package wyq.appengine.component.file;

public class CsvFileTester {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		CsvFile svcFile = new CsvFile("testcsvfile.csv");
		System.out.println(svcFile.readAllCsv());
	}

}
