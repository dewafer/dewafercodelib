package dewafer.wyq.games.sudu;

public interface Event<T> {

	T invoker();
	
	Object[] arguments(); 
}
