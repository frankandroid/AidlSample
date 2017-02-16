// IBookManager.aidl
package com.hhly.ipcsample;
import com.hhly.ipcsample.Book;
// Declare any non-default types here with import statements

interface IBookManager {


    List<Book> getBookList();

    //注意要加上in
    void addBook(in Book book);


}
