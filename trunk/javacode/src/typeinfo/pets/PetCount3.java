package typeinfo.pets;

import java.util.LinkedHashMap;
import java.util.Map;

public class PetCount3 {

	@SuppressWarnings("serial")
	static class PetCounter
	extends LinkedHashMap<Class<? extends Pet>,Integer>{
		public PetCounter(){
			//super(MapData.map(LiteralPetCreator.allTypes,0));
			// the upper sentence means the blow function
			for(Class<? extends Pet> type : LiteralPetCreator.allTypes)
			{
				put(type,0);
			}
		}
		public void count(Pet pet){
			for(Map.Entry<Class<? extends Pet>, Integer> pair : this.entrySet()){
				if(pair.getKey().isInstance(pet))
					put(pair.getKey(),pair.getValue()+1);
			}
		}
		public String toString()
		{
			StringBuilder result = new StringBuilder("{");
			for(Map.Entry<Class<?extends Pet>, Integer> pair : entrySet())
			{
				result.append(pair.getKey().getSimpleName());
				result.append("=");
				result.append(pair.getValue());
				result.append(", ");
			}
			result.delete(result.length()-2, result.length());
			result.append("}");
			return result.toString();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PetCounter petCount = new PetCounter();
		for(Pet pet : Pets.createArray(20)){
			PetCount.printnb(pet.getClass().getSimpleName() + " ");
			petCount.count(pet);
		}
		PetCount.print();
		PetCount.print(petCount);
	}

}
