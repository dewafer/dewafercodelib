package generics.bigFishEatLittleFish;

public class BigFish {

	private static long counter = 1;
	private final long id = counter++;
	private BigFish(){}
	public String toString(){
		return "BigFish " + id;
	}
	public static FishMother<BigFish> birthFish()
	{
		return new FishMother<BigFish>(){
			public BigFish birthFish(){
				return new BigFish();
			}
		};
		
	}
}
