package wyq.algorithm.GS;

import java.util.ArrayList;
import java.util.List;

import wyq.algorithm.GS.mulitthread.Boy2;
import wyq.algorithm.GS.mulitthread.Girl2;

public class ParticipatorFactory {

	static int MAX_NUM_BOYS = 10;
	static int MAX_NUM_GIRLS = 10;

	public List<Boy> createBoyList() {
		return createList(Boy.class, "Boy", MAX_NUM_BOYS);
	}

	public List<Girl> createGirlList() {
		return createList(Girl.class, "Girl", MAX_NUM_GIRLS);
	}

	public List<Boy2> createMulitThreadBoyList() {
		return createList(Boy2.class, "Boy", MAX_NUM_BOYS);
	}

	public List<Girl2> createMulitThreadGirlList() {
		return createList(Girl2.class, "Girl", MAX_NUM_GIRLS);
	}

	protected <T extends Participator> List<T> createList(Class<T> clazz,
			String Name, int MAX) {
		List<T> list = new ArrayList<T>();
		try {
			for (int i = 0; i < MAX; i++) {
				T girl = clazz.newInstance();
				girl.setName(Name + i);
				list.add(girl);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return list;
	}
}
