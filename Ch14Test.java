import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.*;
import java.lang.*;
/**
 * JUnit Tests for Chapter 14
 */
@SuppressWarnings("deprecation")
public class Ch14Test {
	/**
	 * Base Stacks and Queues
	 */
	static Queue<Integer> emptyQueue, singleQueue, palindrome, notPalindrome;
	static Stack<Integer> emptyStack, singleStack, sortedStack, notSortedStack, 
				equalStack, minStack, minRemovedStack, minRemovedStack2;
	
	/**
	 * Default constructor not used, added so Javadoc won't complain.
	 */
	public Ch14Test(){}
	
	/**
	 * Reset the base data structures just in case
	 */
	@BeforeEach 
	void reset(){
		emptyQueue = new LinkedList<Integer>();
		singleQueue = new LinkedList<Integer>(List.of(1));
		palindrome = new LinkedList<Integer>(List.of(3, 8, 17, 9, 17, 8, 3));
		notPalindrome = new LinkedList<Integer>(List.of(3, 17, 9, 4, 17, 3));
		
		emptyStack = new Stack<Integer>();
		singleStack = new Stack<Integer>();singleStack.push(1);
		sortedStack = new Stack<Integer>();
		equalStack = new Stack<Integer>();
		notSortedStack = new Stack<Integer>();notSortedStack.push(1);
		List<Integer> sortedList = List.of(20, 20, 17, 11, 8, 8, 3, 2);
		for(int i: sortedList){
			sortedStack.push(i);notSortedStack.push(i);equalStack.push(i);
		}
		
		List<Integer> minList = List.of(2, 8, 3, 19, 2, 3, 2, 7, 12, -8, 4);
		minStack = new Stack<Integer>();
		minRemovedStack = new Stack<Integer>();
		minRemovedStack2 = new Stack<Integer>();
		for(int i: minList){
			minStack.push(i);minRemovedStack.push(i);minRemovedStack2.push(i);
			if(i == -8){
				minRemovedStack.pop();
				minRemovedStack2.pop();
			} else if(i == 2){
				minRemovedStack2.pop();
			}
		}		
		return;
	}
	
	/**
	 * Tests 14.5 equals
	 */
	@Test 
	public void testEquals(){
		assertTrue(equals(sortedStack, equalStack), "sorted == equal");
		assertFalse(equals(sortedStack, minStack), "sorted != min");
		assertEquals(sortedStack, equalStack, "sorted == equal again, ensure nothing changed");//ensure sorted is "unchanged"
		assertFalse(equals(emptyStack, singleStack), "empty != single");
		assertFalse(equals(null, emptyStack), "null != single");
		assertTrue(equals(null,null), "null == null");
	}
	
	/**
	 * Tests 14.8 isPalindrome
	 */
	@Test 
	public void testIsPalindrome(){
		assertTrue(isPalindrome(emptyQueue), "empty is palindrom");
		assertTrue(isPalindrome(palindrome), "palindrome is palindrom");
		assertTrue(isPalindrome(palindrome), "palindrome is palindrom again");//ensure palindrome is "unchanged"
		assertNotEquals(emptyQueue, palindrome, "empty != palindrome");
		assertTrue(isPalindrome(singleQueue), "single is palindrome");
		assertFalse(isPalindrome(notPalindrome), "not palindrome is not palindrome");
		assertThrows(IllegalArgumentException.class, ()->{isPalindrome(null);}, "null is invalid input");
	}
	
	/**
	 * Tests 14.15 isSorted
	 */
	@Test 
	public void testIsSorted(){
		assertTrue(isSorted(sortedStack), "sorted is sorted");
		assertEquals(sortedStack, equalStack, "sorted should not have been changed");//ensure sortedStack is "unchanged"
		assertFalse(isSorted(notSortedStack), "not sorted is not sorted");
		assertTrue(isSorted(emptyStack),"empty is sorted");
		assertTrue(isSorted(singleStack), "single is sorted");
		assertThrows(IllegalArgumentException.class, ()->{isSorted(null);}, "null is invalid input");
	}
	
	/**
	 * Tests 14.19 removeMin
	 */
	@Test 
	public void testRemoveMin(){
		assertEquals(-8, removeMin(minStack), "should have removed -8");
		assertEquals(minStack, minRemovedStack, "min == minRemoved");
		assertEquals(2, removeMin(minStack), "should have removed 2");
		assertEquals(minStack, minRemovedStack2, "min == minRemoved2");
		assertThrows(IllegalArgumentException.class, ()->{removeMin(null);}, "null is invalid input");
		assertThrows(IllegalArgumentException.class, ()->{removeMin(emptyStack);}, "empty is invalid input");
	}

	/**
	 * Checks if two stacks of Integers have the same sequence of Integers.
	 * 
	 * You can use one stack as auxiliary storage. Both stacks should look 
	 * as they did before the method was called.
	 * 
	 * @param s1 The first stack
	 * @param s2 The second stack
	 * @return true if the stacks have the same sequence
	 */
	public static boolean equals(Stack<Integer> s1, Stack<Integer> s2)throws IllegalArgumentException{
		if(s1 == null && s2 == null){
			return true;
			
		 }
		if(s1 == null || s2 == null){
			return false;
			
		 }
		if(s1.size() != s2.size()){
			 return false;
		}else{
			Stack<Integer> s3 = new Stack<>();
			boolean same = true;
			while(same && !s1.isEmpty()){
				int num1 = s1.pop();
				int num2 = s2.pop();
				if(num1 != num2){
					same = false;
			}
			s3.push(num1);	
			s3.push(num2);	
		}
		while(!s3.isEmpty()){
			s2.push(s3.pop());
			s1.push(s3.pop());
		}	
		return same;
	}
}
	/**
	 * Checks if a queue of Integers has the same sequence forwards and backwards.
	 * 
	 * Use one stack as auxiliary storage. The queue needs to look as 
	 * it did prior to this function call after it returns.
	 * 
	 * @param q The candidate palindrome
	 * @return true if the queue is a palindrome
	 * @throws IllegalArgumentException if the queue is null
	 */
	public static boolean isPalindrome(Queue<Integer> q){
		if(q == null) throw new IllegalArgumentException();
		if(q.size() == 0){//empty is palindrome
			return true;
	    }
	    Stack<Integer> s = new Stack<>();
	    int size = q.size();
			boolean same = true;
			for (int i=0; i< size; i++){
				int num = q.remove();
				s.push(num);
				q.add(num);//readd to maintain original
			}
			for (int i=0; i< size; i++){//compare from s and q
				int qnum = q.remove();
				int snum = s.pop();
				if (qnum != snum){
					return false;
							
		}
		
			q.add(qnum);
			
		}	
		return true;
	}
	/**
	 * Checks if a stack of Integers is sorted
	 * 
	 * Use one queue or stack, but not both, as auxiliary storage. 
	 * The stack needs to look as it did prior to this function call after it returns.
	 * 
	 * @param s The candidate sorted stack
	 * @return true if the stack is sorted
	 * @throws IllegalArgumentException if the stack is null
	 */
	public static boolean isSorted(Stack<Integer> s){
		if(s == null) throw new IllegalArgumentException();
		
	    if(s.size() <= 1) return true;//empty or 1 element is sorted
	    Stack<Integer> s2 = new Stack<>();
	    int size = s.size();
		boolean sorted = true;
		int previous = s.pop();
		s2.push(previous);
			while (!s.isEmpty()){
				int current = s.pop();
				s2.push(current);
				
				if (current < previous){
					sorted = false;
			}
			 previous = current;
		}
			while (!s2.isEmpty()){
			  s.push(s2.pop());
			
		}	
		return sorted;
	}
	
	/**
	 * Removes the minimum value from a stack.
	 * 
	 * Use one queue as auxiliary storage. 
	 * The stack needs to maintain relative ordering sans min(s).
	 * 
	 * @param s The stack to have elements removed
	 * @return the smallest element in the stack
	 * @throws IllegalArgumentException if the stack is null or empty
	 */
	public static int removeMin(Stack<Integer> s){
		//System.out.println(s + "before")
		if(s == null || s.isEmpty()) throw new IllegalArgumentException();
		Queue<Integer> q = new LinkedList<>();
	    
		int min = s.pop();//start with first as min
		q.add(min);
			while (!s.isEmpty()){
				int current = s.pop();
				q.add(current);
				
				if (current < min){
					min = current;//find min

			}
			 
		}
		
			while (!q.isEmpty()){//remove all minimum values
				int current = q.remove();
				if(current != min){
			       s.push(current);
			  }
			
		}
			while (!s.isEmpty()){
				q.add(s.pop());
			}
			while (!q.isEmpty()){
				s.push(q.remove());
			}
				
		System.out.println(s + "after");	
		return min;
	}
}
