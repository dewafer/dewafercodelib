package generics.bigFishEatLittleFish;

public class LittleFish {

	private static long counter = 1;
	private final long id = counter++;
	private LittleFish(){}
	public String toString(){
		return "LittleFish " +id;
	}
	public static FishMother<LittleFish> birthFish()
	{
		return new FishMother<LittleFish>(){
			public LittleFish birthFish(){ return new LittleFish(); }
		};
	}
}
