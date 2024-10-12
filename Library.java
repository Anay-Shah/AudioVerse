//Name: Anay Shah
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;



/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 		songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions

	//not used anymore
	String errorMsg = "";
	
	//not use anymore - replaced for exceptions (A2 requirement)
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 		= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */

	//Update A2 method: Download method - IndexFrom & IndexTo
	public void download(AudioContent content) throws AlreadyExists
	{
			//compare to see if audiocontent is of Song type
			if(content.getType().equals(Song.TYPENAME)){
				//cast the content object as a song object and assign a variable
				Song download = (Song) content;
				//checks arraylist songs to see if it contains download
				if(songs.contains(download)){
					//if it contains then throw Already Exists exception
					throw new AlreadyExists("SONG " + content.getTitle() + " already downloaded");
				} else {
					//if songs does not contain download then add it to the library
					songs.add(download);
					System.out.println("SONG " + content.getTitle() + " Added to the library");
				}	
			}
			//Same as above however this time it checking for Audiobook type
			else if(content.getType().equals(AudioBook.TYPENAME)){
				//cast the content object as audiobook object and assign a variable
				AudioBook download = (AudioBook) content;
				//checks arraylist audi0books to see if it contains download
				if(audiobooks.contains(download)){
					//if it contains then throw Already Exists exception
					throw new AlreadyExists("AUDIOBOOK " + content.getTitle() + " already downloaded");
				}else {
					//if audiobooks does not contain download then add it to the library
					audiobooks.add(download);
					System.out.println("AUDIOBOOK " + content.getTitle() + " Added to the library");
				}	
			}
		}
		
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		//iterate through audiobooks arraylist
		for (int i = 0; i < audiobooks.size(); i++)
		{
			//create proper formatting for audiobooks arraylist when we print it
			int index = i + 1;
			System.out.print("" + index + ". ");
			//invoke print method from audiobooks
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		//same as listAllAudioBooks() method
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.println(playlists.get(i).getTitle());	
			
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arraylist is complete, print the artists names
		
		//create new arraylist
		ArrayList<String> artistList = new ArrayList<>();		
		//loop through songs arraylist of type Song
		for(Song x: songs){
			//Create string to carry artist when iterating 
			String exisitingArtist = x.getArtist();
			//Check to see if the list does not contain the exisiting artist
			if (!artistList.contains(exisitingArtist)) {
				//if not then add to the list
				artistList.add(exisitingArtist);
			}
		}
		//iterate through the new arraylist
		for (int i = 0; i < artistList.size(); i++) {
			//printout the artist list for each of the stored songs
			System.out.println((i + 1) + ". " + artistList.get(i));
		}
		
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index) throws AudioContentNotFoundException
	{
		//check if index is valid and if not throw exception
		if(index<1 || index>songs.size()){
			throw new AudioContentNotFoundException("Song not found");
		}

		//Delete Song from Playlist
		//iterate through playlists
		for(int i=0; i<playlists.size(); i++){
			//the next 4 lines: 
			//iterate through contents within the playlist
			//check id of song
			//then remove
			for(int j=0; j<playlists.get(i).getContent().size(); j++){
				if (playlists.get(i).getContent().get(j).getId().equals(songs.get(index-1).getId())){
					delContentFromPlaylist(j+1, playlists.get(i).getTitle());
					System.out.println("song removed successfully from playlist");
				}
			}
		}
		//remove froms songs 
		songs.remove(songs.get(index-1));
	}
	


	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		//create object for song comparison
		SongYearComparator compare = new SongYearComparator();
		//sort the songs by year
    	Collections.sort(songs, compare); 
	
	}
  	// Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		//compare method between 2 songs
		public int compare(Song a, Song b){
			//return the int value for the two years getting compared
			return Integer.compare(a.getYear(), b.getYear());
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
		SongLengthComparator compare = new SongLengthComparator();
    	Collections.sort(songs, compare);
	}
 	// Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song a, Song b){
			return Integer.compare(a.getLength(), b.getLength());
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  	// Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		//sorts the songs
		Collections.sort(songs);
		
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index) throws AudioContentNotFoundException
	{
		//check if index is valid and if not throw exception
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song Not Found");
		}
		//play the song at the index
		songs.get(index-1).play();
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public void playPodcast(int index, int season, int episode)
	{
		
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public void printPodcastEpisodes(int index, int season)
	{
		
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter) throws AudioContentNotFoundException
	{
		//check if index is valid and if not throw exception
		if(index<1 || index>audiobooks.size()){
			throw new AudioContentNotFoundException("Book Not Found");
		}
		//check if chapter is valid
		if (chapter<1 || chapter>audiobooks.get(index-1).getNumberOfChapters()) {
			throw new AudioContentNotFoundException("Chapter Not Found");
		}
		//using the selectChapter method we can play the chapter value that is passed
		audiobooks.get(index-1).selectChapter(chapter);
		audiobooks.get(index-1).play();
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index) throws AudioContentNotFoundException
	{
		//check if index valid and if not throw exception
		if(index<1 || index>audiobooks.size()){
			throw new AudioContentNotFoundException("Book Number Not Found");
		}
		//using the printToc method we can display the audiobook table of content
		audiobooks.get(index-1).printTOC();
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title) throws AlreadyExists
	{
		//iterate through playlist
		for(Playlist x: playlists){
			//check if user is trying to make a playlist that already exisits
			if(x.getTitle().equalsIgnoreCase(title)){
				throw new AlreadyExists("Playlist " + title + " already exists");
			}
		}
		//if not then invoke this code for the user to make a new playlist
		Playlist newPlaylist = new Playlist(title);
		playlists.add(newPlaylist);
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title) throws AudioContentNotFoundException
	{
		//the next 10 lines check if title already exists and if it does it will throw a exception
		boolean playlistExists = false;
		for(int i = 0; i < playlists.size(); i++){
			if (playlists.get(i).getTitle().equalsIgnoreCase(title)){
				playlistExists = true;
				break;
			}
		}
		if(!playlistExists){
			throw new AudioContentNotFoundException("Playlist " + title + " does not exist");
		}
		//iterate through the playlists
		for(int i = 0; i < playlists.size(); i++) {
			//if passed string title is in playlists then print the contents
			if(playlists.get(i).getTitle().equalsIgnoreCase(title)){
				playlists.get(i).printContents();
			}
		}
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle) throws AudioContentNotFoundException
	{
		//the next 10 lines check if title already exists and if it does it will throw a exception
		boolean playlistExists = false;
		for(int i = 0; i < playlists.size(); i++){
			if (playlists.get(i).getTitle().equalsIgnoreCase(playlistTitle)){
				playlistExists = true;
				break;
			}
		}
		if(!playlistExists){
			throw new AudioContentNotFoundException("Playlist " + playlistTitle + " does not exist");
		}
		//iterate through the playlists
		for (int i = 0; i < playlists.size(); i++)
		{
			//if passed string playlistTitle exists in playlists then play all the content inside of the playlist
			if(playlists.get(i).getTitle().equalsIgnoreCase(playlistTitle)){
				System.out.println("playlist found");
				playlists.get(i).playAll();
				System.out.println();
			}
		}
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL) throws AudioContentNotFoundException
	{
		//check if index in playlist is valid and if not then throw exception
		if(indexInPL<=0 || indexInPL>playlists.size()){
			throw new AudioContentNotFoundException("Invalid Index for playlists");
		}
		//the next 10 lines check if title already exists and if it does it will throw a exception
		boolean playlistExists = false;
		for(int i = 0; i < playlists.size(); i++){
			if (playlists.get(i).getTitle().equalsIgnoreCase(playlistTitle)){
				playlistExists = true;
				break;
			}
		}
		if(!playlistExists){
			throw new AudioContentNotFoundException("Playlist " + playlistTitle + " does not exist");
		}
		//iterate through the playlists
		for (int i = 0; i < playlists.size(); i++)
		{
			//check to see if playlistTitle exists and then play selected content from that playlist
			if(playlists.get(i).getTitle().equalsIgnoreCase(playlistTitle)){
				playlists.get(i).play(indexInPL);
			}
		}
		 
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle) throws AudioContentNotFoundException
	{
		//check to see if index is valid and if not than invoke exception
		if ((songs.size() > 0) && (index<=0 || index>songs.size())){
			throw new AudioContentNotFoundException("Invalid Index for Songs");
		}
		//the next 10 lines check if title already exists and if it does it will throw a exception
		boolean playlistExists = false;
		for(int j = 0; j < playlists.size(); j++){
			if (playlists.get(j).getTitle().equalsIgnoreCase(playlistTitle)){
				playlistExists = true;
				break;
			}
		}
		if(!playlistExists){
			throw new AudioContentNotFoundException("Playlist " + playlistTitle + " does not exist");
		}
		//check if type is either song or audiobook and if not then invoke exception
		if(!type.equalsIgnoreCase("SONG") && !type.equalsIgnoreCase("AUDIOBOOK")){
			throw new AudioContentNotFoundException("Invalid Content Type");
		}
		//iterate through songs
		for(int i = 0; i<songs.size(); i++){
			//check index
			if((i+1)==index){
				//check if the type is of Song type
				if(songs.get(i).getType().equalsIgnoreCase(type)){
					//iterate through playlists
					for(int j = 0; j < playlists.size(); j++){
						//check to see if the title in the playlists equals the passed playlistTitle
						if (playlists.get(j).getTitle().equalsIgnoreCase(playlistTitle)){
							//if it is then add the song content to the playlist
							playlists.get(j).addContent(songs.get(i));
						}
					}
				}
			}
		}
		//check to see if index is valid and if not than invoke exception
		if((audiobooks.size() > 0) && (index<=0 || index>audiobooks.size())){
			throw new AudioContentNotFoundException("Invalid Index for AudioBook");
		}
		//iterate through audiobooks
		for(int i = 0; i<audiobooks.size(); i++){
			//check index
			if((i+1)==index){
				//check if the type is of audiobook type
				if(audiobooks.get(i).getType().equalsIgnoreCase(type)){
					//iterate through playlists
					for(int j = 0; j < playlists.size(); j++){
						//check to see if the title in the playlists equals the passed playlistTitle
						if (playlists.get(j).getTitle().equalsIgnoreCase(playlistTitle)){
							//if it is then add the audiobooks content to the playlist
							playlists.get(j).addContent(audiobooks.get(i));
						}	
					}
				}
			}
		}
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title) throws AudioContentNotFoundException
	{
		//the next 10 lines check if title already exists and if it does it will throw a exception
		boolean playlistExists = false;
		for(int i = 0; i < playlists.size(); i++){
			if (playlists.get(i).getTitle().equalsIgnoreCase(title)){
				playlistExists = true;
				break;
			}
		}
		if(!playlistExists){
			throw new AudioContentNotFoundException("Playlist " + title + " does not exist");
		}
		//check if valid index and if not then invoke exception
		for(int i=0;i<playlists.size();i++){
			if (index<1 || index>playlists.get(i).getContent().size()){
				throw new AudioContentNotFoundException("Invalid Index");
			}
		}
		//iterate through playlists
		for(int i = 0; i<playlists.size(); i++){
			//check to see if passed title is a playlist
			if(playlists.get(i).getTitle().equalsIgnoreCase(title)){
				//if it is then delete the passed index and the audiocontent at that value from the playlist
				playlists.get(i).deleteContent(index);
			}
		}
	}
	
}

//1st custom exception class made that checks if AudioContent exists
class AudioContentNotFoundException extends RuntimeException {
    public AudioContentNotFoundException(String message) {
        super(message);
    }
}

//2nd custom exception class made that checks if value Already exists
class AlreadyExists extends RuntimeException {
	public AlreadyExists(String message) {
		super(message);
	}
}


