package wyq.appengine.test;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.TimeUnit;

import wyq.appengine.tool.MuliThreadFileFinder;
import wyq.appengine.tool.MuliThreadFileFinder.FileHandler;
import wyq.appengine.tool.MuliThreadFileFinder.Result;
import wyq.appengine.tool.MuliThreadFileFinder.SearchResult;

public class MulitThreadTester {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		SearchResult search = MuliThreadFileFinder.search(new File("C:\\"),
				new FileFilter() {

					@Override
					public boolean accept(File pathname) {
						return pathname.isFile()
								&& pathname.getName().contains(".exe");
					}
				});

		// sleep for 1 seconds
		TimeUnit.SECONDS.sleep(1);

		search.stop();
		// while (!search.isFinished()) {
		System.out.println("finished?" + search.isFinished());
		// TimeUnit.SECONDS.sleep(2);
		// }

		for (File f : search.getResult()) {
			System.out.println(f);
		}
		TimeUnit.SECONDS.sleep(2);
		System.out.println("finished?" + search.isFinished());
		for (File f : search.getResult()) {
			System.out.println(f);
		}

		search.getResultAwait();
		System.out.println("finished?" + search.isFinished());
		for (File f : search.getResultAwait()) {
			System.out.println(f);
		}

		Result s2 = MuliThreadFileFinder.search(new File("C:\\"), null,
				new FileHandler() {

					@Override
					public void handle(File f) {
						System.out.println("\t" + f);
					}
				});

		TimeUnit.SECONDS.sleep(2);
		s2.stop();

		s2.await();
		System.out.println("done...");
	}

}
