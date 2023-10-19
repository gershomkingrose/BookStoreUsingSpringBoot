package com.bookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;

@Controller
public class BookController {
	@Autowired
	private BookService service;
	@Autowired
	private MyBookListService myBookService;
@GetMapping("/")
public String home()
{
	return "home.html";
}
@GetMapping("/book_register")
public String bookRegister()
{
	return "bookRegister.html";
}
@GetMapping("/available_book")
public ModelAndView getAllBook()
{
	List<Book>list=service.getAllBook();
	/*
	 * ModelAndView m=new ModelAndView(); 
	 * m.setViewName("bookList")
	 * m.addObject("book",list)
	 */
	
	return new ModelAndView("bookList","book",list);
}
@PostMapping("/save")
public String addBook(@ModelAttribute Book b) {
	service.save(b);
   return "redirect:/available_book";	
}
@GetMapping("/my_books")
public String getMyBookS(Model model) {
	List<MyBookList>list=myBookService.getAllMyBooks();
	model.addAttribute("book",list);
	return "myBooks.html";
}
@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
	Book b=service.getBookByID(id);
	MyBookList mb=new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
	myBookService.saveMyBooks(mb);
  return "redirect:/my_books";
}
@RequestMapping("/editBook/{id}")
public String editBook(@PathVariable("id") int id, Model model) {
	Book b= service.getBookByID(id);
	model.addAttribute("book", b);
	  return "bookEdit.html";

}
@RequestMapping("/deleteBook/{id}")
public String deleteBook(@PathVariable("id") int id) {
	service.deleteById(id);
	   return "redirect:/available_book";	

}

}
