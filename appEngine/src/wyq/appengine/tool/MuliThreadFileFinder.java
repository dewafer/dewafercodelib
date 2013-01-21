package wyq.appengine.tool;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MuliThreadFileFinder {

	public static final long WAIT_TIME_OUT = 800;
	private ExecutorService exec = Executors.newCachedThreadPool();
	private File baseDir;
	private FileFilter condition;
	private FileHandler fileHandler;
	private TerminationMonitor monitor;
	private List<Future<?>> threadPool = Collections
			.synchronizedList(new ArrayList<Future<?>>());

	protected MuliThreadFileFinder(File baseDir, FileFilter condition,
			FileHandler fileHandler) {
		this.baseDir = baseDir;
		this.condition = condition;
		this.fileHandler = fileHandler;
	}

	protected Result search() {
		final Result result;
		if (fileHandler == null) {
			result = new SearchResult();
			fileHandler = new FileHandler() {

				@Override
				public void handle(File f) {
					((SearchResult) result).add(f);
				}

			};
		} else {
			result = new Result();
		}
		SearchThread t = new SearchThread(baseDir, result, condition);
		Future<?> f = exec.submit(t);
		threadPool.add(f);
		monitor = new TerminationMonitor(result);
		exec.execute(monitor);
		synchronized (monitor) {
			monitor.notifyAll();
		}
		return result;
	}

	public static SearchResult search(File baseDir, FileFilter condition) {
		return (SearchResult) new MuliThreadFileFinder(baseDir, condition, null)
				.search();
	}

	public static Result search(File baseDir, FileFilter condition,
			FileHandler handler) {
		return new MuliThreadFileFinder(baseDir, condition, handler).search();
	}

	class SearchThread implements Runnable {

		private File baseDir;
		private Result result;
		private FileFilter condition;

		@Override
		public void run() {
			try {
				File[] listFiles = baseDir.listFiles(condition);
				if (listFiles != null && fileHandler != null) {
					for (File f : listFiles) {
						synchronized (fileHandler) {
							fileHandler.handle(f);
						}
					}
				}

				File[] dirs = baseDir.listFiles(new FileFilter() {

					@Override
					public boolean accept(File arg0) {
						return arg0.isDirectory();
					}
				});

				if (dirs != null) {
					for (File dir : dirs) {
						SearchThread t = new SearchThread(dir, result,
								condition);
						if (!exec.isShutdown()) {
							Future<?> f = exec.submit(t);
							threadPool.add(f);
						} else {
							break;
						}
					}
				}
			} finally {
				synchronized (monitor) {
					monitor.notifyAll();
				}
			}
		}

		public SearchThread(File baseDir, Result result, FileFilter condition) {
			this.baseDir = baseDir;
			this.result = result;
			this.condition = condition;
		}

	}

	class TerminationMonitor implements Runnable {

		private Result result;

		public TerminationMonitor(Result result) {
			this.result = result;
		}

		@Override
		public void run() {
			try {
				do {
					synchronized (this) {
						wait(WAIT_TIME_OUT);
					}
					synchronized (threadPool) {
						List<Future<?>> deadThreads = new ArrayList<Future<?>>();
						for (Future<?> f : threadPool) {
							if (f.isDone()) {
								deadThreads.add(f);
							}
						}
						threadPool.removeAll(deadThreads);
					}
				} while (!threadPool.isEmpty());
				result.finished();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				exec.shutdown();
			}
		}
	}

	public class Result {

		protected boolean isFinished = false;

		public synchronized boolean isFinished() {
			return isFinished;
		}

		protected synchronized void finished() {
			this.isFinished = true;
		}

		public void await() {
			try {
				while (!isFinished) {
					synchronized (monitor) {
						monitor.wait(WAIT_TIME_OUT);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void stop() {
			exec.shutdown();
		}

	}

	public class SearchResult extends Result {
		private List<File> result = new ArrayList<File>();

		public List<File> getResult() {
			return getResultCopy();
		}

		protected void add(File files) {
			result.add(files);
		}

		public List<File> getResultAwait() {
			await();
			return getResultCopy();
		}

		private List<File> getResultCopy() {
			List<File> copy = new ArrayList<File>(result.size());
			copy.addAll(result);
			return copy;
		}
	}

	public interface FileHandler {
		public void handle(File f);
	}
}
