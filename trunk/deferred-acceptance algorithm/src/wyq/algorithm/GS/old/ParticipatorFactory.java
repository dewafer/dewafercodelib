package wyq.algorithm.GS.old;

import java.util.ArrayList;
import java.util.List;

public class ParticipatorFactory {

	private int MAX_NUM_BOYS = 10;
	private int MAX_NUM_GIRLS = 10;

	public List<Participator> createboyList() {
		List<Participator> list = new ArrayList<Participator>();
		for (int i = 0; i < MAX_NUM_BOYS; i++) {
			Boy boy = new Boy();
			boy.setName("Boy" + i);
			list.add(boy);
		}
		return list;
	}

	public List<Participator> createGirlList() {
		List<Participator> list = new ArrayList<Participator>();
		for (int i = 0; i < MAX_NUM_GIRLS; i++) {
			Girl girl = new Girl();
			girl.setName("Girl" + i);
			list.add(girl);
		}
		return list;
	}
}
