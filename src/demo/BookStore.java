package demo;

import java.util.concurrent.Semaphore;

public class BookStore {
  private static final int MAX_AVAILABLE = 3;
  private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);
  public static BookStore bookStore;
  protected Book[] books = new Book[MAX_AVAILABLE];
  protected boolean[] booked = new boolean[MAX_AVAILABLE];

  public static BookStore getBookStore() {
    if (bookStore == null) {
      bookStore = new BookStore();
    }
    return bookStore;
  }

  private BookStore() {
    for (int i = 0; i < MAX_AVAILABLE; ++i) {
      books[i] = new Book(i);
    }
  }

  public Book borrowBook() throws InterruptedException {
    available.acquire();
    return getBookInBookStore();
  }

  protected Book getBookInBookStore() {
    for (int i = 0; i < MAX_AVAILABLE; ++i) {
      if (!booked[i]) {
        booked[i] = true;
        return books[i];
      }
    }
    return null;
  }

  public boolean returnBook(Book book, int id) {
    boolean returnSucceed = markAsUnused(book);
    if (returnSucceed) {
      System.out.println("Person " + id + " returns book " + book.getId() + " nicely");
      available.release();
      book = null;
    }
    return returnSucceed;
  }
  protected synchronized boolean markAsUnused(Book book) {
    for (int i = 0; i < MAX_AVAILABLE; ++i) {
      if (book == books[i]) {
        if (booked[i]) {
          booked[i] = false;
          return true;
        }
        else
          return false;
      }
    }
    return false;
  }


}

