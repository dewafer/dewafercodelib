package typeinfo.pets;

import java.util.*;

public class PetCount {

	public static void printnb(Object o) {
		System.out.print(o);
	}

	public static void print(Object o) {
		System.out.print(o);
	}

	public static void print() {
		System.out.println();
	}

	@SuppressWarnings("serial")
	static class PetCounter extends HashMap<String, Integer> {
		public void count(String type) {
			Integer quantity = get(type);
			if (quantity == null)
				put(type, 1);
			else
				put(type, quantity + 1);
		}
	}

	public static void countPets(PetCreator creator) {
		PetCounter counter = new PetCounter();
		for (Pet pet : creator.createArray(20)) {
			printnb(pet.getClass().getSimpleName() + " ");
			if (pet instanceof Pet)
				counter.count("Pet");
			if (pet instanceof Dog)
				counter.count("Dog");
			if (pet instanceof Mutt)
				counter.count("Mutt");
			if (pet instanceof Pug)
				counter.count("Pug");
			if (pet instanceof Cat)
				counter.count("Cat");
			if (pet instanceof Manx)
				counter.count("Manx");
			if (pet instanceof EgyptianMau)
				counter.count("EgypetionMau");
			if (pet instanceof Cymric)
				counter.count("Cymric");
			if (pet instanceof Rodent)
				counter.count("Rodent");
			if (pet instanceof Rat)
				counter.count("Rat");
			if (pet instanceof Mouse)
				counter.count("Mouse");
			if (pet instanceof Hamster)
				counter.count("Hamster");
		}
		print();
		print(counter);
	}

	public static void main(String[] args) {
		countPets(new ForNameCreator());
	}
}
