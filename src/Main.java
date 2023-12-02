import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Accept two arguments from the command line.
        // If either argument is missing, you should output the following message:
        if (args.length < 2){
            System.out.println("Usage: java WordCounter <filename> <searchTerm>");
            return;
        }
        String filename = args[0];
        ArrayList<String> searchTerm = new ArrayList<>(Arrays.asList(args));
        searchTerm.remove(0);
        File file = new File(filename);
        // If the file does not exist, print the message
        if (!file.exists()){
            System.out.println("File not found: " + file.getName());
            return;
        }

        ArrayList<String> wordList = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)){
            // 找到非A-Z且非a-z且非0-9且...
            // Scanner does not use linebreak as delimiter by default.

            // +: match one or more (Could not match zero, so a delimiter after a delimiter can not be matched!
            scanner.useDelimiter("[^A-Za-z0-9_]+");
            while (scanner.hasNext()) {
                wordList.add(scanner.next());
            }


            if (searchTerm.size() == 1){
                int wordCount = countSingleWord(wordList, searchTerm.get(0));
                System.out.println("The word '" + searchTerm.get(0) + "' appears " + wordCount + " times.");
            } else {
                // The number of word needed to search is greater than 1.

                // adapt to the lengths of the words and numbers shown.
                // get the max length of searchTerm and corresponding count for formatting.
                // initial max length depends on the word 'TOTAL' and 'COUNT', so the first is 4 and the second is five.
                ArrayList<Integer> countList = new ArrayList<>();
                int maxWordLength = 5;
                for (String item : searchTerm){
                    if (item.length() > maxWordLength){
                        maxWordLength = item.length();
                    }
                    int count = countSingleWord(wordList, item);
                    countList.add(count);
                }

                int maxCountLength = 5;
                int sum = 0;
                for (Integer integer: countList){
                    int length = Integer.toString(integer).length();
                    if (length > maxCountLength){
                        maxCountLength = length;
                    }
                    sum = sum + integer;
                }

                String tableLine = "|" + "-".repeat(maxWordLength + 2) + "|" + "-".repeat(maxCountLength + 2) + "|";
                // Print the table now.
                System.out.println(tableLine);
                System.out.printf("| %-" + maxWordLength + "s | %" + maxCountLength + "s |\n", "WORD", "COUNT");
                System.out.println(tableLine);
                for (int i = 0; i < searchTerm.size(); i++){
                    System.out.printf("| %-" + maxWordLength + "s | %" + maxCountLength + "d |\n", searchTerm.get(i), countList.get(i));
                }
                System.out.println(tableLine);
                System.out.printf("| %-" + maxWordLength + "s | %" + maxCountLength + "d |\n", "TOTAL", sum);
                System.out.println(tableLine);
            }


        } catch (IOException e) {
            System.out.println("ERROR");
        }

    }

    // Search through the file for occurrences of the specified word.
    private static int countSingleWord(ArrayList<String> wordList, String searchTerm){
        int count = 0;
        for (String word : wordList){
            if (word.equals(searchTerm)){
                count++;

            }
        }
        return count;
    }
}