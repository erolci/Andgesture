package and.gesture.eiger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.inputmethodservice.Keyboard.Key;
import android.util.Log;


public final class Gesture 
{
	
	public final boolean isVisible;
	
	public final String value;
	private final int id;
	public final String pattern;
	private static ArrayList<Gesture> instances = new ArrayList<Gesture>();
	
	public Gesture(String value,boolean isVisible,String pattern)
	{
		this.value = value;
		this.pattern = pattern;
		this.isVisible = isVisible;
		id = instances.size();
		Log.i("ID", String.valueOf(id) + value);
		instances.add(this);
		
	}
	
	
	/*Simple gestures that consists of 10 points or less */
	
	public static final Gesture POINT = new Gesture(".",true,"POINT");
	public static final Gesture COMMA = new Gesture(",",true,"(SW)");
	public static final Gesture Letter_I = new Gesture("i",true,"(N)");
	public static final Gesture BACKSPACE = new Gesture("",false,"(W)");
	public static final Gesture SPACE = new Gesture("",false,"(E)");
	public static final Gesture CAPS_LOCK = new Gesture("",false,"SE");
	public static final Gesture SHIFT_NUMERIC = new Gesture("",false,"NW");
	public static final Gesture SHIFT_PUNC = new Gesture("",false,"NE");
	public static final Gesture ENTER = new Gesture("",false,"S");
	
	/* Alphabet gestures */
	public static final Gesture Letter_A = new Gesture("a",true,"T"); 
	public static final Gesture Letter_B = new Gesture("b",true,"^(T|(LT))(R)(L)(R|(RB))$");				
	public static final Gesture Letter_C = new Gesture("c",true,"(L)|(LB)|( SW SE)");		
	public static final Gesture Letter_D = new Gesture("d",true,"^((LB)|B)(T)(R|(RB))$"); 
	public static final Gesture Letter_E = new Gesture("e",true,"^((TL)|L)(R)(L|(LB))$");
	public static final Gesture Letter_F = new Gesture("f",true,"^( W)( S|( SW))$");
	public static final Gesture Letter_G = new Gesture("g",true,"^(L)(B)(R)((TL)|L)$");
	public static final Gesture Letter_H = new Gesture("h",true,"^((LB)|B)(T)$");
	public static final Gesture Letter_J = new Gesture("j",true,"^( S)(( W)|( SW))$");	
	public static final Gesture Letter_K = new Gesture("k",true,"^(B)(L)(T)$"); 
	public static final Gesture Letter_L = new Gesture("l",true,"^( S)(( E)|( SE))$");	
	public static final Gesture Letter_M = new Gesture("m",true,"^((LT)|T|(TR))(B)(T|(TR))$");
	public static final Gesture Letter_N = new Gesture("n",true,"^((LT)|T)(B)$");
	public static final Gesture Letter_O = new Gesture("o",true,"^((TL)|L)(B)(R|(RT))$");
	public static final Gesture Letter_Q = new Gesture("q",true,"^((TR)|R)(B)(L|(LT)|(LR))$");
	public static final Gesture Letter_P = new Gesture("p",true,"^((LT)|T)(R)$");
	public static final Gesture Letter_R = new Gesture("r",true,"^^((LT)|T)(R)(L)$");
	public static final Gesture Letter_S = new Gesture("s",true,"^((TL)|L)(R|(RB))$");
	public static final Gesture Letter_T = new Gesture("t",true,"( E)(( S)|( SE))");
	public static final Gesture Letter_U = new Gesture("u",true,"^(B|(BR))$");
	public static final Gesture Letter_V = new Gesture("v",true,"^ SE NE$"); 
	public static final Gesture Letter_W = new Gesture("w",true,"^((LB)|B|(BR))(T)(B|(BR))$");
	public static final Gesture Letter_X = new Gesture("x",true,"^(R)(T)(L)$"); 
	public static final Gesture Letter_Y = new Gesture("y",true,"^((LB)|B)(T)((LRB)|(RB)|B)(L)$"); 
	public static final Gesture Letter_Z = new Gesture("z",true,"^(R)(L)$"); 
	
	
	/* Numeric gestures */
	public static final Gesture NUMBER_0 = new Gesture("0",true,"^((TR)|R)(B)(L)$");
	public static final Gesture NUMBER_1 = new Gesture("1",true,"(T)");
	public static final Gesture NUMBER_2 = new Gesture("2",true,"^((LT)|(TR))(L)$");
	public static final Gesture NUMBER_3 = new Gesture("3",true,"^((TR)|R)(L)(R|(RB))$");
	public static final Gesture NUMBER_4 = new Gesture("4",true,"^ SW E");
	public static final Gesture NUMBER_5 = new Gesture("5",true,"^(L|(LBT)|(LT))(R|RB)$");
	public static final Gesture NUMBER_6 = new Gesture("6",true,"^(L)(B)(R|(RT))$");
	public static final Gesture NUMBER_7 = new Gesture("7",true,"((R)|(( E)( SW)))");
	public static final Gesture NUMBER_8 = new Gesture("8",true,"^((TL)|L)(R)(B)(L|(LR)|(LRT))$");
	public static final Gesture NUMBER_9 = new Gesture("9",true,"^((TL)|L)(B)(T|(RT)|B|(BL)|(RL))$");
	public static final Gesture MINUS = new Gesture("-",true,"E");
	public static final Gesture PLUS = new Gesture("+",true,"^(B)(L)(T)$");
	public static final Gesture MULTIPLY = new Gesture("*",true,"SW");
	public static final Gesture DIVIDE = new Gesture("/",true,"^(R)(B)(L)(T)(R)(B)(L)$");
	public static final Gesture EQUAL = new Gesture("=",true,"^(R)(L)$");
	
	
	
	
	
	/* Punctuation gestures */
	public static final Gesture QUESTION_MARK = new Gesture("?",true,"^(T|(TR)|(TRL)|(LTRL))$");
	public static final Gesture LEFT_PARANTHESIS = new Gesture("(",true,"L|( SW SE)|( S SE)");
	public static final Gesture RIGHT_PARANTHESIS = new Gesture(")",true,"R|( SE SW)|( S SW)");
	public static final Gesture LEFT_CURLY_BRACKET= new Gesture("{",true,"^((TL)|L)(R)(L|(LB))$");
	public static final Gesture RIGHT_CURLY_BRACKET= new Gesture("}",true,"^((TR)|R)(L)(R|(RB))$");
	public static final Gesture AT_SIGN = new Gesture("@",true,"^((TL)|L)(B)(R|(RT))$");
	public static final Gesture STERLIN = new Gesture("£",true,"^( S)(( E)|( SE))$");







	

	
	
	//public static final Gesture2 NUMBER_6 = new Gesture2("6",true,true,"");
	@Override
	public String toString()
	{
		return value;
	}
	
	
	
	public static final Set<Gesture> loadPunctuation(){
		HashSet<Gesture> puncSet = new HashSet<Gesture>();
		range(puncSet,QUESTION_MARK,STERLIN);
		return puncSet;
	}
	
	public static final Set<Gesture> loadAlphabet()
	{
		HashSet<Gesture> alphabetSet = new HashSet<Gesture>();
		range(alphabetSet,Letter_A,Letter_Z);
		return alphabetSet;
		
		
		
	}
	public static final Set<Gesture> loadNumbers()
	{
		HashSet<Gesture> numberSet = new HashSet<Gesture>();
		range(numberSet,NUMBER_0,EQUAL);
		return numberSet;
	}
	public static final Set<Gesture> loadAll()
	{
		HashSet<Gesture> all = new HashSet<Gesture>();
		range(all,Letter_A,SPACE);
		return all;
	}
	
	private static final void range(Set<Gesture> g,Gesture start, Gesture end)
	{
		
		for(int i = start.id;i<=end.id;i++)
		{
			g.add(getByID(i));
		}
	}
	
	private static Gesture getByID(int id)
	{
		if(id < 0 || id >= instances.size()) throw new EigerException("wrong  id!");
		return instances.get(id);
	}

	
}
