package work.nich.mixeddemo;

import work.nich.mixeddemo.Book;
import work.nich.mixeddemo.IOnBookAddListener;

interface IBookManager {
    void addBook(in Book book);
    void registerListener(IOnBookAddListener listener);
}
