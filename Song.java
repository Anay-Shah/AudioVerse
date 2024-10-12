//Name: Anay Shah
public class Song extends AudioContent implements Comparable<Song>
{
	public static final String TYPENAME =	"SONG";
	
	public static enum Genre {POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL}; 
	private String artist; 		// Can be multiple names separated by commas
	private String composer; 	// Can be multiple names separated by commas
	private Genre  genre; 
	private String lyrics;
	
	
	
	public Song(String title, int year, String id, String type, String audioFile, int length, String artist,
			String composer, Song.Genre genre, String lyrics)
	{
		// Make use of the constructor in the super class AudioContent. 
		// Initialize additional Song instance variables. 
		super(title, year, id, type, audioFile, length);
		this.artist = artist;
		this.composer = composer;
		this.genre = genre;
		this.lyrics = lyrics;
	}
	
	public String getType()
	{
		return TYPENAME;
	}
	
	// Print information about the song. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print artist, composer, genre 
	public void printInfo()
	{
		//call printInfo method from superclass - AudioContent
		super.printInfo();
		//add aditional information such as Artist, Composer and Genre
		System.out.println("Artist: " + artist + " Composer: " + composer + " Genre: " + genre);
	}
	
	// Play the song by setting the audioFile to the lyrics string and then calling the play() method of the superclass
	public void play()
	{
		//use the setAudioFile method and pass in lyrics
		setAudioFile(lyrics);
		//call in play method from superclass - AudioContent
		super.play();
	}
	
	public String getComposer()
	{
		return composer;
	}
	public void setComposer(String composer)
	{
		this.composer = composer;
	}
	
	public String getArtist()
	{
		return artist;
	}
	public void setArtist(String artist)
	{
		this.artist = artist;
	}
	
	public String getLyrics()
	{
		return lyrics;
	}
	public void setLyrics(String lyrics)
	{
		this.lyrics = lyrics;
	}

	public Genre getGenre()
	{
		return genre;
	}

	public void setGenre(Genre genre)
	{
		this.genre = genre;
	}	
	
	// Two songs are equal if their AudioContent information is equal and both the composer and artists are the same
	// Make use of the superclass equals() method
	public boolean equals(Object other)
	{
		//cast the other variable to the Song object using a new variable
		Song otherSong = (Song) other;
		//Check if AudioContent is equal and check if composer and artists are also equal
		return super.equals(otherSong) && composer.equals(otherSong.composer) && artist.equals(otherSong.artist);
	}
	
	// Implement the Comparable interface 
	// Compare two songs based on their title
	// This method will allow songs to be sorted alphabetically
	public int compareTo(Song other)
	{
		//uses a comparable interface that is defined on line 8 (implements)
		return this.getTitle().compareTo(other.getTitle());
	}
}
