/* examples.di.impl.GreetingClientImpl.java */

package examples.di.impl;
import examples.di.Greeting;
import examples.di.GreetingClient;

//import java.io.FileWriter;


public class GreetingClientImpl implements GreetingClient {

    private Greeting greeting;

   public void setGreeting(Greeting greeting) {
      this.greeting = greeting;
   }

   public void execute() {
      System.out.println(greeting.greet());
      try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}
      long start = System.currentTimeMillis();
      for(int i = 3;i<2000000;i += 2)
      {
    	  int j = 1;
    	  double temp = Math.sqrt(i);
    	  for(j = 2;j<temp;j++)
    	  {
    		  if(i%j == 0)
    			  break;
    	  }
    	  if (j == (int)temp + 1)
    		  System.out.println(i);
      }
      long end = System.currentTimeMillis();
      System.out.println((end - start)+" milliseconds");

//      int n = 100;
//      try {
//		FileWriter fw = new FileWriter("prime.txt");
//	
//      long start = System.currentTimeMillis();
//      BitSet b = new BitSet(n + 1);
//      int count = 0;
//      int i;
//      for (i = 2; i <= n; i++)
//         b.set(i);
//      i = 2;
//      while (i * i <= n)
//      {  
//         if (b.get(i))
//         {
//        	 fw.write("  " + i);
//        	 fw.flush();
//        	System.out.println(i);
//            count++;
//            int k = 2 * i;
//            while (k <= n)
//            {  
//               b.clear(k);
//               k += i;
//            }
//         }
//         i++;
//      }
//      while (i <= n)
//      {  
//         if (b.get(i)){
//        	 fw.write("  " + i);
//        	 fw.flush();
//         	System.out.println(i);
//            count++;
//         }
//         i++;
//      }
//      long end =  System.currentTimeMillis();
//      System.out.println(count + " primes");
//      System.out.println((end - start) + " milliseconds");
//      } catch (IOException e) {
//  		// TODO 自動生成された catch ブロック
//  		e.printStackTrace();
//  	}
      
/*      long start = System.currentTimeMillis();
      List<Integer> result = new ArrayList<Integer>();
      result.add(2);
      System.out.println(2);
      for(int i = 3;i<200000;i += 2)
      {
    	  int j = 1;
    	  for(j = 0;j<result.size();j++)
    	  {
    		  if(i%result.get(j) == 0)break;
    	  }
		  if(j == result.size())
		  {
			  result.add(i);
			  System.out.println(i);
		  }
      }
      long end = System.currentTimeMillis();
      System.out.println((end - start)+" milliseconds");*/
	}
}
