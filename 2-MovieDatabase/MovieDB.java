import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Genre, Title 을 관리하는 영화 데이터베이스.
 * 
 * MyLinkedList 를 사용해 각각 Genre와 Title에 따라 내부적으로 정렬된 상태를  
 * 유지하는 데이터베이스이다. 
 */
public class MovieDB extends MyLinkedList<Genre> {
    public MovieDB() {
        // FIXME implement this
		
    	// HINT: MovieDBGenre 클래스를 정렬된 상태로 유지하기 위한 
    	// MyLinkedList 타입의 멤버 변수를 초기화 한다.
		super();
    }

    public void insert(MovieDBItem item) {
        // FIXME implement this
        // Insert the given item to the MovieDB.
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        // System.err.printf("[trace] MovieDB: INSERT [%s] [%s]\n", item.getGenre(), item.getTitle());

		String genreOfItem = item.getGenre();
		String titleOfItem = item.getTitle();
		Node<Genre> curr = this.head;
		Node<Genre> next = curr.getNext();

		if(this.isEmpty()){
			Genre newGenre = new Genre(genreOfItem);
			newGenre.getMovieList().add(titleOfItem);
			this.add(newGenre);
		}
		else {
			// stop if reach end or confront bigger one or equal one
			while(next != null && genreOfItem.compareTo(next.getItem().getItem()) > 0){
				curr = curr.getNext();
				next = curr.getNext();
			}

			if(next != null && genreOfItem.compareTo(next.getItem().getItem()) == 0){
				next.getItem().getMovieList().add(titleOfItem);
			}
			else {
				Genre newGenre = new Genre(genreOfItem);
				newGenre.getMovieList().add(titleOfItem);
				Node<Genre> newNodeGenre = new Node<>(newGenre);
				newNodeGenre.setNext(curr.getNext());
				curr.setNext(newNodeGenre);
				this.numItems++;
			}
		}

    }

    public void delete(MovieDBItem item) {
        // FIXME implement this
        // Remove the given item from the MovieDB.
    	
    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        // System.err.printf("[trace] MovieDB: DELETE [%s] [%s]\n", item.getGenre(), item.getTitle());

		String genreOfItem = item.getGenre();
		String titleOfItem = item.getTitle();
		Node<Genre> curr = this.head;
		Node<Genre> next = curr.getNext();

		while(next != null && genreOfItem.compareTo(next.getItem().getItem()) > 0){
			curr = curr.getNext();
			next = curr.getNext();
		}
		
		if(next != null && genreOfItem.compareTo(next.getItem().getItem()) == 0){
			next.getItem().getMovieList().delete(titleOfItem);
			if(next.getItem().getMovieList().size() == 0){
				curr.removeNext();
			}
		}
		else{
			;
		}
    }

    public MyLinkedList<MovieDBItem> search(String term) {
        // FIXME implement this
        // Search the given term from the MovieDB.
        // You should return a linked list of MovieDBItem.
        // The search command is handled at SearchCmd class.
    	
    	// Printing search results is the responsibility of SearchCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.
    	
        // This tracing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
    	// System.err.printf("[trace] MovieDB: SEARCH [%s]\n", term);
    	
    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.   

        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();

		for(Genre genre: this){
			for(String title: genre.getMovieList()){
				if(title.contains(term)){
					MovieDBItem item = new MovieDBItem(genre.getItem(), title);
					results.add(item);
				}
			}
		}

        return results;
    }
    
    public MyLinkedList<MovieDBItem> items() {
        // FIXME implement this
        // You should return a linked list of QueryResult.
        // The print command is handled at PrintCmd class.

    	// Printing movie items is the responsibility of PrintCmd class. 
    	// So you must not use System.out in this method to achieve specs of the assignment.

    	// Printing functionality is provided for the sake of debugging.
        // This code should be removed before submitting your work.
        // System.err.printf("[trace] MovieDB: ITEMS\n");

    	// FIXME remove this code and return an appropriate MyLinkedList<MovieDBItem> instance.
    	// This code is supplied for avoiding compilation error.  

        MyLinkedList<MovieDBItem> results = new MyLinkedList<MovieDBItem>();
        
		for(Genre genre: this){
			for(String title: genre.getMovieList()){
				MovieDBItem item = new MovieDBItem(genre.getItem(), title);
				results.add(item);
			}
		}

    	return results;
    }
}

class Genre extends Node<String> implements Comparable<Genre> {
	
	private MovieList movieListOfGenre = new MovieList();

	public MovieList getMovieList(){
		return this.movieListOfGenre;
	}

	public Genre(String genreName) {
		super(genreName);
	}

	@Override
	public int compareTo(Genre o) {
		return this.getItem().compareTo(o.getItem());
	}

	// WHAT IS IT
	// @Override
	// public int hashCode() {
	// 	throw new UnsupportedOperationException("not implemented yet");
	// }

	// @Override
	// public boolean equals(Object obj) {
	// 	// the case input is string
	// 	// the case input is genre

	// 	throw new UnsupportedOperationException("not implemented yet");
	// }
}

class MovieList extends MyLinkedList<String>{	
	public MovieList() {
		super();
	}

	// @Override
	// public Iterator<String> iterator() {
	// 	throw new UnsupportedOperationException("not implemented yet");
	// }

	// @Override
	// public boolean isEmpty() {
	// 	throw new UnsupportedOperationException("not implemented yet");
	// }

	// @Override
	// public int size() {
	// 	throw new UnsupportedOperationException("not implemented yet");
	// }

	// 자기 자리 찾아서 들어가도록 override
	@Override
	public void add(String item) {

		Node<String> curr = this.head;
		Node<String> next = curr.getNext();

		if(this.isEmpty()){
			super.add(item);
		}
		else{
			// stop if reach end or confront bigger one or equal one
			while(next != null && item.compareTo(next.getItem()) > 0){
				curr = curr.getNext();
				next = curr.getNext();
			}
			
			if(next != null && item.compareTo(next.getItem()) == 0){
				// duplicate one
				;
			}
			else{
				Node<String> newNode = new Node<>(item);
				newNode.setNext(curr.getNext());
				curr.setNext(newNode);
				this.numItems++;
			}
		}
	}

	public void delete(String item){

		MyLinkedListIterator<String> it = new MyLinkedListIterator<>(this);

		while(it.hasNext()){
			String next = it.next();
			if(item.compareTo(next) == 0){
				it.remove();
				break;
			}
			if(item.compareTo(next) < 0){
				break;
			}
		}
	}

	// @Override
	// public String first() {
	// 	throw new UnsupportedOperationException("not implemented yet");
	// }

	// @Override
	// public void removeAll() {
	// 	throw new UnsupportedOperationException("not implemented yet");
	// }
}