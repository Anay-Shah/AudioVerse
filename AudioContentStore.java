//Name: Anay Shah

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AudioContentStore
{

    private ArrayList<AudioContent> contents; 
	private Map<String, Integer> titleMap;
    private Map<String, ArrayList<Integer>> artistMap;
    private Map<String, ArrayList<Integer>> genreMap;

		public AudioContentStore()
		{
			//execute the readfile method (read the store.txt)
            try{
                contents = ReadFile("store.txt");
			//check for error
            }catch (IOException e){
                System.out.println("Error reading the file: store.txt: " + e.getMessage());
                System.exit(1);
            }
			//Creating 3 new HashMaps for Search, SearchA, SearchG, DownloadA and DownloadG
            titleMap = new HashMap<>();
            artistMap = new HashMap<>();
            genreMap = new HashMap<>();

			//Formatting so that it shows "Loading" with the content type
            for(AudioContent ct: contents){
                System.out.println("Loading " + ct.getType());
            }

			//The next 47 lines are for the functionality of the map for each case
            int i = 0;
			//create arraylist of integers
            ArrayList<Integer> list = new ArrayList<>();
			//loop through contents
            for(AudioContent content : contents){
				//Code for "Search" : add the title with the index
                titleMap.put(content.getTitle(), i);
				//check if content type is SONG
                if(content.getType().equals(Song.TYPENAME)){
					//create song object
                    Song song = (Song) content;
					//check to see if artistMap contains the target artist
                    if(artistMap.containsKey(song.getArtist())){
						//if it does set arraylist value to the index of the artist
                        list = artistMap.get(song.getArtist());
						//append i to the arraylist
                        list.add(i);
						//removes exisiting entry
                        artistMap.remove(song.getArtist());
						//add updated list as the indices, and artist as key
                        artistMap.put(song.getArtist(),list);    
                    }
                    else{
						//create new array list
                        list = new ArrayList<>();
						//append to i to the arraylist
                        list.add(i);
						//list as indices and artist as key
                        artistMap.put(song.getArtist(), list);   
                    }
					//check if the song genre (convert to string) is in genreMap
                    if(genreMap.containsKey(song.getGenre().toString())){
						//if it is set arraylist value to the index of the artist
                        list = genreMap.get(song.getGenre().toString());
						//append i to the arraylist
                        list.add(i);
						//removes exisiting entry
                        genreMap.remove(song.getGenre().toString());
						//add updated list as the indices, and genre as key
                        genreMap.put(song.getGenre().toString(), list);
                    }
                    else {
						//same as above else statement however with genre map 
                        list = new ArrayList<>();
                        list.add(i);   
                        genreMap.put(song.getGenre().toString(), list);
                    }
                }
				//same as artistMap when type is song however this is when type is audiobook
				else if(content.getType().equals(AudioBook.TYPENAME)){
					AudioBook book = (AudioBook) content;
					if(artistMap.containsKey(book.getAuthor())){
						list = artistMap.get(book.getAuthor());
                        list.add(i);
                        artistMap.remove(book.getAuthor());
                        artistMap.put(book.getAuthor(),list);
					}
					else{
						list = new ArrayList<>();
						list.add(i);
						artistMap.put(book.getAuthor(), list);
					}
				}
				//increment
                i++;
            }
			//increment
			i++;
        }
        
        

        private ArrayList<AudioContent> ReadFile(String filename) throws IOException
        {
			//create arraylist of AudioContent type
            ArrayList<AudioContent> audioType = new ArrayList<>();
			//filename = "store.txt"
			//create a file object
            File file = new File(filename);
			//scan throughout the file
            Scanner scan = new Scanner(file);
			//keep scanning if file has next line
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
    
				//check the first line of a new content (either SONG or AUDIOBOOK)
                if (line.equalsIgnoreCase("SONG")) 
                {
					//creating variables for each characterisitic of a Song
                    String id = scan.nextLine();
                    String title = scan.nextLine();
                    int year = Integer.parseInt(scan.nextLine());
                    int length = Integer.parseInt(scan.nextLine());
                    String artist = scan.nextLine();
                    String composer = scan.nextLine();
                    Song.Genre genre = Song.Genre.valueOf(scan.nextLine());
                    int lyricLines = Integer.parseInt(scan.nextLine());
					//read and add lyrics
                    String lyrics = "";
                    for (int i = 0; i < lyricLines; i++) {
                        lyrics += scan.nextLine() + "\n";
                    }
					//using all the scanned information from the txt file make a new song and add it
                    audioType.add(new Song(title, year, id, Song.TYPENAME, lyrics, length, artist, composer, genre, lyrics));   
                }
				//check the first line of a new content (either SONG or AUDIOBOOK)
                else if(line.equalsIgnoreCase("AUDIOBOOK"))
                {
					//creating variables for each characterisitic of a AudioBook
                    String id = scan.nextLine();
                    String title = scan.nextLine();
                    int year = Integer.parseInt(scan.nextLine());
                    int length = Integer.parseInt(scan.nextLine());
                    String author = scan.nextLine();
                    String narrator = scan.nextLine();
                    int numChapters = Integer.parseInt(scan.nextLine()); 
					//read and chapter titles
                    ArrayList<String> chapterTitle = new ArrayList<String>();
                    for(int i = 0; i<numChapters; i++){
                        chapterTitle.add(scan.nextLine()+"\n");
                    }
					//get values to make the chapters
                    ArrayList<String> chapters = new ArrayList<>();
                    for(int i = 0; i<numChapters; i++){
                        int NoOfLines = Integer.parseInt(scan.nextLine());
                        chapters = makechapters(NoOfLines,scan,chapters);
                    }
					//using all the scanned information from the txt file make a new song and add it
                    AudioBook book = new AudioBook(title, year, id, AudioBook.TYPENAME, "", 
                    length, author, narrator, chapterTitle, chapters);
                    audioType.add(book);
                }
            }
            return audioType;
        }
		//method used to create the chapters of all audiobooks
        public ArrayList<String> makechapters(int NoOfLines, Scanner scan, ArrayList<String> chapters){
            String line = "";
			//iterate through the number of lines and add each line
            for(int i = 0; i<NoOfLines; i++){
                line += scan.nextLine() + "\n";
            }
			//add to chapters
            chapters.add(line);
            return chapters;
        }

		//create method to get the artistMap
		public Map<String, ArrayList<Integer>> getArtistMap(){
			return artistMap;
		}

		//create method to get the genreMap
		public Map<String, ArrayList<Integer>> getGenreMap(){
			return genreMap;
		}

		
        public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}

		//New A2 Method for Download: getContent
		public ArrayList<AudioContent> getContent(int indexFrom, int indexTo)
		{
			//check for incorrect index and if none found, proceed to getting the content to and from
			if (indexFrom < 1 || indexFrom > contents.size())
			{
				return null;
			}
            if (indexTo < 1 || indexTo > contents.size())
			{
				return null;
			}
            if (indexTo < indexFrom)
            {
                return null;
            }

            ArrayList<AudioContent> download = new ArrayList<AudioContent>();
			//loop through the index from and index to values and add content accordingly
            for(int i=indexFrom-1;i<indexTo;i++){
                download.add(contents.get(i));
            }
			return download;
		}
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
        }
		//New A2 Method: Search
        public void search(String title)
        {
            int index = 0;
			//check if map containskey of title
            if(titleMap.containsKey(title)){
				//if it does set the index
                index = titleMap.get(title);
				//formatting
                System.out.print("" + (index+1) + ". ");
				//print the information
                contents.get(index).printInfo();
            }
			else{
				System.out.println("No matches for " + title);
			}
        } 
        //New A2 Method: SearchA
        public void searchA(String artist){
			//create list of integers as it is one of the values passed in the artistMap
            ArrayList<Integer> list = new ArrayList<>();
			//check if map containskey of artist
            if(artistMap.containsKey(artist)){
				//if it does set the list
                list = artistMap.get(artist);
				//loop through integer arraylist
                for(int i = 0; i<list.size(); i++){
					//formatting
                    System.out.print("" + (list.get(i)+1) + ". ");
					//print the information
                    contents.get(list.get(i)).printInfo();
					System.out.println();
                }  
            }
			//invoke if artist not in map
            else{
                System.out.println("No matches for " + artist);
            }
        }

		//New A2 Method: SearchG
        public void searchG(String genre){
			//create list of integers as it is one of the values passed in the genreMap
            ArrayList<Integer> list = new ArrayList<>();
			//check if map containskey of genre
            if(genreMap.containsKey(genre)){
				//if it does set the list
                list = genreMap.get(genre);
				//loop through integer arraylist
                for(int i = 0; i<list.size(); i++){
					//formatting
                    System.out.print("" + (list.get(i)+1) + ". ");
					//print the information
                    contents.get(list.get(i)).printInfo();
					System.out.println();
                }
            }
			//invoke if genre does not exist
			else{
				System.out.println("No matches for " + genre);
			}
            
        }

		//New A2 addition: method that loops inside of the chapterTitles and checks if SearchP target string is a substring of the chapterTitles
		public boolean getChapterTitleName (ArrayList<String> chapterTitle, String title){
			boolean flag = false;
			for(String x: chapterTitle){
				if(x.contains(title)){
					flag = true;
					break;
				}
			}
			return flag;
		}
		//New A2 addition: method that loops inside of the chapter contents and checks if SearchP target string is a substring of the chapters contents
		public boolean getChapterContent (ArrayList<String> chapter, String title){
			boolean flag = false;
			for(String x: chapter){
				if(x.contains(title)){
					flag = true;
					break;
				}
			}
			return flag;
		}
        
		//BONUS A2 Method: SearchP
        public void searchP(String title){
			//initliallize i for index
            int i = 0;
			//loop through contents
            for(AudioContent partial: contents){
				//check content type (SONG)
				if(partial.getType().equals(Song.TYPENAME)){
				//converting object to song object
				Song content = (Song) partial;
				//check if the target string is a substring of any of the properties of a Song
                if(content.getTitle().contains(title) || content.getLyrics().contains(title)
				|| content.getComposer().contains(title) || content.getArtist().contains(title)
				|| content.getGenre().toString().contains(title)){
                    contents.get(i).printInfo();
					System.out.println();
                }
				//increment
                i++;
            }
			//same as above however for AudioBook
			else if(partial.getType().equals(AudioBook.TYPENAME)){
				AudioBook content = (AudioBook) partial;
				if(content.getTitle().contains(title) || content.getAuthor().contains(title)
				|| getChapterTitleName(content.getChapterTitles(), title) || getChapterContent(content.getChapters(), title)
				|| content.getNarrator().contains(title)){
                    contents.get(i).printInfo();
					System.out.println();
                }
                i++;
			}
        }
	}
}