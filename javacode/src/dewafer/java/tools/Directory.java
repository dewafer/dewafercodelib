package dewafer.java.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public final class Directory {

	public static File[] local(File dir, final String regex) {
		return dir.listFiles(new DirFilter(regex));
	}

	public static File[] local(String path, final String regex) {
		return local(new File(path), regex);
	}

	public static class TreeInfo implements Iterable<File> {
		public List<File> files = new ArrayList<File>();
		public List<File> dirs = new ArrayList<File>();

		@Override
		public Iterator<File> iterator() {
			return files.iterator();
		}

		private void addAll(TreeInfo other) {
			files.addAll(other.files);
			dirs.addAll(other.dirs);
		}

		@Override
		public String toString() {
			return "dirs: " + PPrint.pformat(dirs) + "\n\nfiles:"
					+ PPrint.pformat(files);
		}
	}

	public static TreeInfo walk(String start, String regex) {
		return recurseDirs(new File(start), regex);
	}

	public static TreeInfo walk(File start, String regex) {
		return recurseDirs(start, regex);
	}

	public static TreeInfo walk(File start) {
		return recurseDirs(start, ".*");
	}

	public static TreeInfo walk(String start) {
		return recurseDirs(new File(start), ".*");
	}

	private static TreeInfo recurseDirs(File startDir, String regex) {
		TreeInfo result = new TreeInfo();
		for (File item : startDir.listFiles()) {
			if (item.isDirectory()) {
				result.dirs.add(item);
				result.addAll(recurseDirs(item, regex));
			} else if (item.getName().matches(regex))
				result.files.add(item);
		}
		return result;
	}

	public static void main(String[] args) {
		TreeInfo t = walk("G:\\workspace\\dewafercodelib\\", ".*\\.class");
		/*
		if (args.length == 0)
			System.out.println(walk("."));
		else
			for (String arg : args)
				System.out.println(walk(arg));
		*/
		for (File f : t)
			Print.printf(f.getAbsolutePath() + "\n");
	}

}
