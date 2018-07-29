import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Sol {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/burakfirik/Documents/analysis/98.txt");

        Sol sol = new Sol();

        System.out.println("A-Tale-of-Two-Cities_98");

        System.out.println("Total words in novel : "+ sol.getTotalNumberOfWords(file));
        System.out.println("Total unique words in novel : "+ sol.getTotalUniqueWords(file));

        System.out.println();
        Word[] mostFreqWords = sol.get20MostFrequentWords(file);
        System.out.println("Most frequent words:");
        for (int i = 0; i < mostFreqWords.length; i++) {
            System.out.println((i+1)+". "+mostFreqWords[i].word+" "+mostFreqWords[i].f);
        }
        System.out.println();
        Word[] mostInterestingWords = sol.get20MostInterestingFrequentWords(file);
        System.out.println("Most interesting frequent words: ");
        for (int i = 0; i < mostInterestingWords.length; i++) {
            System.out.println((i+1)+". "+mostInterestingWords[i].word+" "+mostInterestingWords[i].f);
        }

        System.out.println();
        Word[] leastFreqWords = sol.get20LeastFrequentWords(file);
        System.out.println("20 Least Frequent words:  ");
        for (int i = 0; i < leastFreqWords.length; i++) {
            System.out.println((i+1)+". "+leastFreqWords[i].word+" "+leastFreqWords[i].f);
        }

        System.out.println();

        System.out.println("Interesting word usage pattern: ");
        int[] freq = sol.getFrequencyOfWord(file, "Madame");
        for (int i = 0; i < freq.length; i++) {
            System.out.print(freq[i] + " ");
        }

        System.out.println("\n");

        System.out.print("Favorite quote:\n ");
        String quote = "But, he had never seen his friend in his present aspect";
        System.out.println("\""+quote+"\" - Chapter "+sol.getChapterQuoteAppears(file, quote));
        System.out.println();
        System.out.println("Generated sentence:");
        System.out.println("\""+sol.generateSentence(file)+"\"");


    }
    class Word{
        String word;
        int f;
        public Word(String w, int f) {
            word = w;
            this.f = f;
        }
    }

    Comparator<Word> wordComparator = new Comparator<Word>() {
        @Override
        public int compare(Word o1, Word o2) {
            return o2.f - o1.f;
        }
    };

    public int getTotalNumberOfWords(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int res =  0;
        String st;
        while ((st = br.readLine()) != null)
            res += st.replaceAll("\\s+"," ").split(" ").length;

        return res;
    }

    public int getTotalUniqueWords(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        int res =  0;
        String st;
        Set<String> set = new HashSet<>();
        while ((st = br.readLine()) != null) {
            String[] words = st.replaceAll("\\s+", " ").split(" ");
            for (String w : words) {
                set.add(w);
            }
        }
        return set.size();
    }

    public Word[] get20MostFrequentWords(File file) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        Map<String, Word> wordMap = new HashMap<>();
        while ((st = br.readLine()) != null) {
            String[] words = st.replaceAll("\\s+", " ").split(" ");
            for (String w : words) {
                if (w.length() == 0) {
                    continue;
                }
                if (!wordMap.containsKey(w)) {
                    wordMap.put(w, new Word(w, 0));
                }

                wordMap.put(w, new Word(w, wordMap.get(w).f + 1));
            }
        }
        List<Word> wordList = new ArrayList<>(wordMap.values());
        Collections.sort(wordList, wordComparator);

        Word [] result = new Word[Math.min(20, wordList.size())];
        for (int i = 0; i < result.length; i++) {
            result[i] = wordList.get(i);
        }
        return result;

    }


    public Word[] get20MostInterestingFrequentWords(File file) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        Map<String, Word> wordMap = new HashMap<>();
        while ((st = br.readLine()) != null) {
            String[] words = st.replaceAll("\\s+", " ").split(" ");
            for (String w : words) {
                if (w.length() == 0) {
                    continue;
                }
                if (!wordMap.containsKey(w)) {
                    wordMap.put(w, new Word(w, 0));
                }

                wordMap.put(w, new Word(w, wordMap.get(w).f + 1));
            }
        }
        List<Word> wordList = new ArrayList<>(wordMap.values());
        Collections.sort(wordList, wordComparator);
        if (wordList.size() < 101) {
            return null;
        }
        Word [] result = new Word[Math.min(20, wordList.size() - 100)];
        int len = wordList.size() - 1;
        for (int i = 0; i < result.length; i++) {
            result[i] = wordList.get(100 + i);
        }
        return result;
    }


    public Word[] get20LeastFrequentWords(File file) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        Map<String, Word> wordMap = new HashMap<>();
        while ((st = br.readLine()) != null) {
            String[] words = st.replaceAll("\\s+", " ").split(" ");
            for (String w : words) {
                if (w.length() == 0) {
                    continue;
                }
                if (!wordMap.containsKey(w)) {
                    wordMap.put(w, new Word(w, 0));
                }

                wordMap.put(w, new Word(w, wordMap.get(w).f + 1));
            }
        }
        List<Word> wordList = new ArrayList<>(wordMap.values());
        Collections.sort(wordList, wordComparator);

        Word [] result = new Word[Math.min(20, wordList.size() - 100)];
        int len = wordList.size() - 1;
        for (int i = 0; i < result.length; i++) {
            result[i] = wordList.get(len - i);
        }
        return result;
    }

    public int[] getFrequencyOfWord (File file, String word) throws IOException{
        String[] chapters = {
                ". The Period", ". The Mail",". The Night Shadows",
                ". The Preparation", ". The Wine-shop", ". The Shoemaker", ". Five Years Later",
                ". A Sight", ". A Disappointment", ". Congratulatory", ". The Jackal", ". Hundreds of People",". Monseigneur in Town",
                ". Monseigneur in the Country", ". The Gorgon's Head", ". Two Promises", ". A Companion Picture", ". The Fellow of Delicacy",
                ". The Fellow of no Delicacy", ". The Honest Tradesman", ". Knitting", ". Still Knitting", ". One Night",
                ". Nine Days", ". An Opinion", ". A Plea", ". Echoing Footsteps", ". The Sea Still Rises", ". Fire Rises",
                ". Drawn to the Loadstone Rock", ". In Secret", ". The Grindstone", ". The Shadow", ". Calm in Storm",". The Wood-sawyer",
                ". Triumph", ". A Knock at the Door", ". A Hand at Cards", ". The Game Made", ". The Substance of the Shadow",". Dusk",
                ". Darkness", ". Fifty-two", ". The Knitting Done", ". The Footsteps Die Out For Ever"
        };
        int[] freq = new int[chapters.length];
        int curChapter = -1;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            String line  = st.replaceAll("\\s+", " ");
            if (curChapter + 1 < chapters.length && line.toLowerCase().indexOf(chapters[curChapter + 1].toLowerCase()) >= 0) {
                curChapter++;
            }
            if (curChapter < 0) {
                continue;
            }
            String[] words = line.split(" ");
            for (String w : words) {
                if (w.length() == 0) {
                    continue;
                }
                if (w.equals(word)) {
                    freq[curChapter]++;
                }
            }
        }
        return freq;
    }


    public int getChapterQuoteAppears(File file, String quote) throws IOException{
        String[] chapters = {
                ". The Period", ". The Mail",". The Night Shadows",
                ". The Preparation", ". The Wine-shop", ". The Shoemaker", ". Five Years Later",
                ". A Sight", ". A Disappointment", ". Congratulatory", ". The Jackal", ". Hundreds of People",". Monseigneur in Town",
                ". Monseigneur in the Country", ". The Gorgon's Head", ". Two Promises", ". A Companion Picture", ". The Fellow of Delicacy",
                ". The Fellow of no Delicacy", ". The Honest Tradesman", ". Knitting", ". Still Knitting", ". One Night",
                ". Nine Days", ". An Opinion", ". A Plea", ". Echoing Footsteps", ". The Sea Still Rises", ". Fire Rises",
                ". Drawn to the Loadstone Rock", ". In Secret", ". The Grindstone", ". The Shadow", ". Calm in Storm",". The Wood-sawyer",
                ". Triumph", ". A Knock at the Door", ". A Hand at Cards", ". The Game Made", ". The Substance of the Shadow",". Dusk",
                ". Darkness", ". Fifty-two", ". The Knitting Done", ". The Footsteps Die Out For Ever"
        };
        int[] freq = new int[chapters.length];
        int curChapter = -1;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        String prev = "";
        while ((st = br.readLine()) != null) {
            String line  = st.replaceAll("\\s+", " ");
            if (curChapter + 1 < chapters.length && line.toLowerCase().indexOf(chapters[curChapter + 1].toLowerCase()) >= 0) {
                curChapter++;
            }
            if (curChapter < 0) {
                continue;
            }
            if ((prev + line).toLowerCase().indexOf(quote.toLowerCase()) >= 0) {
                return curChapter + 1;
            }
            if (line.length() > 0) {
                prev = line;
            }
        }
        return -1;

    }


    public String generateSentence(File file) throws IOException {
        BufferedReader br;
        String st;
        String result = "The";
        Set<String> wordSet = new HashSet<>();
        String prev = "the";
        String realPrev = "";
        int repeat = 0;
        Random rand = new Random();
        while (repeat < 20) {
            br = new BufferedReader(new FileReader(file));
            while ((st = br.readLine()) != null) {

                String[] words = st.replaceAll("\\s+", " ").split(" ");
                for (int i = 0; i < words.length; i++) {
                    if (words[i].length() == 0) {
                        continue;
                    }
                    if (realPrev.length() == 0) {
                        realPrev = words[i].toLowerCase();
                    } else {

                        if (realPrev.equals(prev) && words[i].length() > 0) {
                            wordSet.add(words[i].toLowerCase());
                        }
                        if (words[i].length() > 0) {
                            realPrev = words[i].toLowerCase();
                        }


                    }
                }


            }
            int randNum = rand.nextInt(wordSet.size());
            Iterator it = wordSet.iterator();
            while (randNum-- >= 0) {
                prev = (String) it.next();
            }

            result = result + " " + prev;
            repeat++;
            wordSet = new HashSet<>();
        }
        return result;
    }
}
