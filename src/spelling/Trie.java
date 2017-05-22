package spelling;

import java.lang.String;

public class Trie {
	public Trie[] myLinks;
	public boolean myIsWord;
	public int ALPH;
	
	public Trie() {
		myLinks = new Trie[ALPH];
		myIsWord = false;
	}
	
	public void addString(String s) {
		Trie t = this;
		int k;
		int limit = s.length();
		for (k=0; k< limit; k++) {
			int index = s.charAt(k) - 'a';
			if (t.myLinks[index] == null) {
				t.myLinks[index] = new Trie();
			}
			t = t.myLinks[index];
		}
		t.myIsWord = true;
	}
	
	public void addCString(char s[]) {
		Trie t = this;
		int k=0;
		while(s[k] != '\0') {
			int index = s[k] - 'a';
			if (t.myLinks[index] == null) {
				t.myLinks[index] = new Trie();
			}
			t = t.myLinks[index];
			k++;
		}
		t.myIsWord = true;
	}
	
	public boolean isWord(String s) {
		Trie t = this;
		int k;
		int limit = s.length();
		for (k=0; k<limit; k++) {
			int index = s.charAt(k) - 'a';
			if (t.myLinks[index] == null) {
				return false;
			}
			t = t.myLinks[index];
		}
		return t.myIsWord;
	}
}
