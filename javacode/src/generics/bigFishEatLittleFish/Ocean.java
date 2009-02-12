package generics.bigFishEatLittleFish;

import generics.util.Generators;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Ocean {

	public static void eat(BigFish bf,LittleFish lf)
	{
		System.out.println(bf + " eat " + lf);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random(47);
		Queue<LittleFish> littleFishes = new LinkedList<LittleFish>();
		Generators.fill(littleFishes, LittleFish.birthFish(), 15);
		List<BigFish> bigFishes = new ArrayList<BigFish>();
		Generators.fill(bigFishes, BigFish.birthFish(), 4);
		for(LittleFish lf : littleFishes)
		{
			eat(bigFishes.get(rand.nextInt(bigFishes.size())),lf);
		}
	}

}
