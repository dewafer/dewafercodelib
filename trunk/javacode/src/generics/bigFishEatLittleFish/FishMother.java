package generics.bigFishEatLittleFish;

import generics.util.Generator;

public abstract class FishMother<T> implements Generator<T>{

	public T next(){
		return birthFish();
	}
	public abstract T birthFish() ;
}
