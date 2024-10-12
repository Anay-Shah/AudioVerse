//Name: Anay Shah
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;


// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		// Create my music mylibrary
		Library mylibrary = new Library();
		//create new arraylist for contents downloaded - used in download action
		ArrayList<AudioContent> contentsDownloaded = new ArrayList<>();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			/*else if (action.equalsIgnoreCase("DOWNLOADOLD")) 
			{
				int index = 0;
				
				System.out.print("Store Content #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				AudioContent content = store.getContent(index);
				if (content == null)
					System.out.println("Content Not Found in Store");
				else if (!mylibrary.download(content))
						System.out.println(mylibrary.getErrorMessage());
									
			}*/
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int indexFrom = 0;
				int indexTo = 0;
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					indexFrom = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					indexTo = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//set contentsDownloaded arrayList to the content from and to
				contentsDownloaded = store.getContent(indexFrom, indexTo);
				//check if empty
				if (contentsDownloaded == null)
					//print error
					System.out.println("Content Not Found in Store");
				else{			
					//loop through
					for(AudioContent content: contentsDownloaded){
						//execute
						try{
							mylibrary.download(content);
						}
						//check for error
						catch(AlreadyExists e){
							System.out.println(e.getMessage());
						}
					}	
				}
				}					
			


			else if(action.equalsIgnoreCase("DOWNLOADA"))
			{
				String artist = "";
				System.out.print("Artist Name: ");
				if(scanner.hasNextLine())
				{
					artist = scanner.nextLine();
				}
				//set artistMap to getArtistMap
				Map<String, ArrayList<Integer>> artistMap = store.getArtistMap();
				//create list of integers
				ArrayList<Integer> list = new ArrayList<>();
				//check if map contains target artist
				if(artistMap.containsKey(artist))
				{
					//set list to index of artist in map
					list = artistMap.get(artist);
				}	
				else 
				{
					System.out.println("ARTIST " + artist + " not found");
				}
				//loop through list of indexes
				for(Integer id: list){
					AudioContent content = store.getContent(id+1);
					//execute
					try{
						mylibrary.download(content);
					}
					//check for error
					catch(AlreadyExists e){
						System.out.println(e.getMessage());
					}
				}
			}

			//comments are simillar to downloadA however use of genre in this action
			else if(action.equalsIgnoreCase("DOWNLOADG"))
			{
				String genre = "";
				System.out.print("Genre: ");
				if(scanner.hasNextLine())
				{
					genre = scanner.nextLine();
				}
				Map<String, ArrayList<Integer>> genreMap = store.getGenreMap();
				ArrayList<Integer> list = new ArrayList<>();
				if(genreMap.containsKey(genre))
				{
					list = genreMap.get(genre);
				}
				else
				{
					System.out.println("GENRE " + genre + " not found");
				}
				for(Integer id: list){
					AudioContent content = store.getContent(id+1);
						try{
							mylibrary.download(content);	
						}
						catch(AlreadyExists e){
							System.out.println(e.getMessage());
						}
					}
				}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				// Print error message if the song doesn't exist in the library
				int index = 0;
				//ask user for Song number
				System.out.print("Song Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute
				try{
					mylibrary.playSong(index);
				}
				//check for error
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
				}
		
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
			// Print error message if the book doesn't exist in the library
				int book = 0;
				//ask user for book number
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					book = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute
				try{
					mylibrary.printAudioBookTOC(book);
				}
				//check for error
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int book = 0;
				//ask user for book number
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					book = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				int chapter = 0;
				//ask user for chapter
				System.out.print("Chapter: ");
				if (scanner.hasNextInt())
				{
					chapter = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute
				try{
					mylibrary.playAudioBook(book, chapter);
				}
				//check for error
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String playlist = "";
				//ask user for playlist title
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					playlist = scanner.next();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute
				try{
					mylibrary.playPlaylist(playlist);
				}
				//check for error
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String playlist = "";
				//ask user for playlist title
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					playlist = scanner.next();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				int index = 0;
				//ask user for content number
				System.out.print("Content Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute 
				try{
					mylibrary.playPlaylist(playlist, index);
				}
				//check for error
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}

			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int index = 0;
				//ask user for library song #
				System.out.print("Library Song #: ");	
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute
				try{
					mylibrary.deleteSong(index);
				}
				//check for error
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String playlist = "";
				//ask user for playlist title
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					playlist = scanner.next();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute
				try{
					mylibrary.makePlaylist(playlist);
				}
				//check for error
				catch(AlreadyExists e){
					System.out.println(e.getMessage());
				}
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title = "";
				//ask user for title
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.next();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute
				try{
					mylibrary.printPlaylist(title);
				}
				//check for error
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				String title = "";
				//ask user for title
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.next();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//ask user for type
				String type = "";
				System.out.print("content type [SONG, AUDIOBOOK]: ");
				if (scanner.hasNextLine())
				{
					type = scanner.next();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//ask user for content
				int content = 0;
				System.out.print("Library Content: ");
				if (scanner.hasNextInt())
				{
					content = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute
				try{
					mylibrary.addContentToPlaylist(type, content, title);
				}
				//check for error
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String title = "";
				//ask user for title
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.next();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				int index = 0;
				//ask user for content
				System.out.print("Library Content: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				//execute
				try{
					mylibrary.delContentFromPlaylist(index, title);
				}
				//check for error
				catch(AudioContentNotFoundException e){
					System.out.println(e.getMessage());
				}
			}

			// Assignment 2 additions
			else if(action.equalsIgnoreCase("SEARCH"))
			{
				String title = "";
				System.out.print("Title: ");
				if(scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				store.search(title);
			}

			else if(action.equalsIgnoreCase("SEARCHA"))
			{
				String artist = "";
				System.out.print("Artist/Author: ");
				if(scanner.hasNextLine())
				{
					artist = scanner.nextLine();
				}
				store.searchA(artist);
			}
			
			else if(action.equalsIgnoreCase("SEARCHG"))
			{
				String genre = "";
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");
				if(scanner.hasNextLine())
				{
					genre = scanner.next();
					scanner.nextLine();
				}
				store.searchG(genre);
			}

			//bonus
			else if(action.equalsIgnoreCase("SEARCHP"))
			{
				String title = "";
				System.out.print("Enter Partial Value: ");
				if(scanner.hasNextLine())
				{
					title = scanner.next();
					scanner.nextLine();
				}
				store.searchP(title);
			}
			// -------------------------------------------------------------------------

			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}

			System.out.print("\n>");
		}
	}
}
