//Name: Anay Shah
import java.util.ArrayList;

/*
 * An AudioBook is a type of AudioContent.
 * It is a recording made available on the internet of a book being read aloud by a narrator
 * 
 */
public class AudioBook extends AudioContent
{
	public static final String TYPENAME =	"AUDIOBOOK";
	
	private String author; 
	private String narrator;
	private ArrayList<String> chapterTitles;
	private ArrayList<String> chapters;
	private int currentChapter = 0;

	
	public AudioBook(String title, int year, String id, String type, String audioFile, int length,
									String author, String narrator, ArrayList<String> chapterTitles, ArrayList<String> chapters)
	{
		// Make use of the constructor in the super class AudioContent. 
		super(title, year, id, type, audioFile, length);
		// Initialize additional AudioBook instance variables. 
		this.author = author;
		this.narrator = narrator;
		this.chapterTitles = new ArrayList<String>(chapterTitles);
		this.chapters = new ArrayList<String>(chapters);
		

	}
	
	public String getType()
	{
		return TYPENAME;
	}

  // Print information about the audiobook. First print the basic information of the AudioContent 
	// by making use of the printInfo() method in superclass AudioContent and then print author and narrator
	// see the video
	public void printInfo()
	{
		//calling in printInfo method from super class AudioContent
		super.printInfo();
		//Printing additional information that is local to AudioBook class
		System.out.println("Author: " + author + " Narrated by: " + narrator);

	}
	
  // Play the audiobook by setting the audioFile to the current chapter title (from chapterTitles array list) 
	// followed by the current chapter (from chapters array list)
	// Then make use of the the play() method of the superclass
	public void play()
	{
		//getting currentChapterTitle
		String current_ChapterTitle = chapterTitles.get(currentChapter);
		//getting currentChapterContent
  		String current_ChapterContent = chapters.get(currentChapter);
		//Defining a string that concatnates both chapterTitle and chapterContent
		String current_AudioFile = current_ChapterTitle + " " + current_ChapterContent;
		//setting the audiofile to newely defined string that cocatnates both chapterTitle and chapterContent
  		setAudioFile(current_AudioFile);
		//call in super class method from AudioContent
  		super.play();
	}
	
	// Print the table of contents of the book - i.e. the list of chapter titles
	// See the video
	public void printTOC()
	{
		//iterate through the chapterTitles
		for(int i = 0; i<chapterTitles.size(); i++){
			//Print correct formating to display appropriate Chapter index with appropriate chapter name
			System.out.println("Chapter " + (i+1) + ". " + chapterTitles.get(i));
		}
	}

	// Select a specific chapter to play - nothing to do here
	public void selectChapter(int chapter)
	{
		if (chapter >= 1 && chapter <= chapters.size())
		{
			currentChapter = chapter - 1;
		}
	}
	
	//Two AudioBooks are equal if their AudioContent information is equal and both the author and narrators are equal
	public boolean equals(Object other)
	{
		//cast the other variable to an AudioBook object using a new variable
		AudioBook otherAudioBook = (AudioBook) other;
		//Check if AudioContent is equal and check if author and narrator is also equal
		return super.equals(otherAudioBook) && author.equals(otherAudioBook.author) && narrator.equals(otherAudioBook.narrator);
	}
	
	public int getNumberOfChapters()
	{
		return chapters.size();
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getNarrator()
	{
		return narrator;
	}

	public void setNarrator(String narrator)
	{
		this.narrator = narrator;
	}

	public ArrayList<String> getChapterTitles()
	{
		return chapterTitles;
	}

	public void setChapterTitles(ArrayList<String> chapterTitles)
	{
		this.chapterTitles = chapterTitles;
	}

	public ArrayList<String> getChapters()
	{
		return chapters;
	}

	public void setChapters(ArrayList<String> chapters)
	{
		this.chapters = chapters;
	}

}
