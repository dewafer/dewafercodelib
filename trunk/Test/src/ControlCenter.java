import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

public class ControlCenter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ResourceEntity res = new ResourceEntity();
		createThreadEntityList(10, res);
		while (true) {
		}
	}

	public static List<ThreadEntity> createThreadEntityList(int num,
			ResourceEntity res) {
		List<ThreadEntity> list = new ArrayList<ThreadEntity>();
		for (int i = 0; i < num; i++) {
			ThreadEntity e = new ThreadEntity(res);
			list.add(e);
		}
		return list;
	}

}
