package wyq.appengine.tool;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MuliThreadFileFinder {

	private ExecutorService exec = Executors.newCachedThreadPool();
	private File baseDir;
	private FileFilter condition;
	private TerminationMonitor monitor;
	private List<SearchThread> threadPool = Collections
			.synchronizedList(new ArrayList<SearchThread>());

	protected MuliThreadFileFinder(File baseDir, FileFilter condition) {
		this.baseDir = baseDir;
		this.condition = condition;
	}

	protected SearchResult search() {
		final SearchResult result = new SearchResult();
		SearchThread t = new SearchThread(baseDir, result, condition);
		threadPool.add(t);
		exec.execute(t);
		monitor = new TerminationMonitor(result);
		exec.execute(monitor);
		synchronized (monitor) {
			monitor.notifyAll();
		}
		return result;
	}

	public static SearchResult search(File baseDir, FileFilter condition) {
		return new MuliThreadFileFinder(baseDir, condition).search();
	}

	public static SearchResult search(String baseDir, final String fileNameRegex) {
		return search(new File(baseDir), new FileFilter() {

			@Override
			public boolean accept(File arg0) {
				return arg0.getName().matches(fileNameRegex);
			}
		});

	}

	class SearchThread implements Runnable {

		private File baseDir;
		private SearchResult result;
		private FileFilter condition;

		@Override
		public void run() {
			File[] listFiles = baseDir.listFiles(condition);
			if (listFiles != null) {
				result.addResult(listFiles);
			}

			File[] dirs = baseDir.listFiles(new FileFilter() {

				@Override
				public boolean accept(File arg0) {
					return arg0.isDirectory();
				}
			});

			if (dirs != null) {
				for (File dir : dirs) {
					SearchThread t = new SearchThread(dir, result, condition);
					threadPool.add(t);
					if (!exec.isShutdown()) {
						exec.execute(t);
					} else {
						threadPool.remove(t);
						break;
					}
				}
			}
			threadPool.remove(this);
			synchronized (monitor) {
				monitor.notifyAll();
			}
		}

		public SearchThread(File baseDir, SearchResult result,
				FileFilter condition) {
			this.baseDir = baseDir;
			this.result = result;
			this.condition = condition;
		}

	}

	class TerminationMonitor implements Runnable {

		private SearchResult result;

		public TerminationMonitor(SearchResult result) {
			this.result = result;
		}

		@Override
		public void run() {
			try {
				while (!threadPool.isEmpty()) {
					synchronized (this) {
						wait();
					}
				}
				result.finished();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				exec.shutdown();
			}
		}
	}

	public class SearchResult {
		private boolean isFinished = false;
		private List<File> result = new ArrayList<File>();

		public synchronized boolean isFinished() {
			return isFinished;
		}

		public List<File> getResult() {
			return getResultCopy();
		}

		protected synchronized void finished() {
			this.isFinished = true;
		}

		protected synchronized void addResult(File[] files) {
			Collections.addAll(result, files);
		}

		public void stop() {
			exec.shutdown();
		}

		public List<File> getResultAwait() {
			try {
				while (!isFinished) {
					synchronized (monitor) {
						monitor.wait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return getResultCopy();
		}

		private synchronized List<File> getResultCopy() {
			List<File> copy = new ArrayList<File>(result.size());
			copy.addAll(result);
			return copy;
		}
	}
}
