import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class main2 {
	public static void main(String[] args) {
		EnglishNumberToWords word = new EnglishNumberToWords();
		ArrayList<String> num = new ArrayList<String>();

		Set<String> set = new HashSet<String>(num);
		for(int x = 0; x < 9999999; x++) {
			System.out.println(x);
			set.addAll(Arrays.asList(word.convert(x).split("\\s+")));
		}
		
		System.out.println(set.toString());
		
	}
}
