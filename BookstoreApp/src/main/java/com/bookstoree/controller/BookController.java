package com.bookstoree.controller;

import com.bookstoree.model.Book;
import com.bookstoree.model.User;
import com.bookstoree.repository.Booksrepository;
import com.bookstoree.repository.UsersRepository;
import com.bookstoree.service.BookService;


import com.bookstoree.service.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Controller
public class BookController {

    private final BookService bookService;
    @Autowired
    private Booksrepository booksrepository;
    private UsersRepository usersRepository;


    @Value("${uploaded.image.path}")
    private String fileDir;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/register")
    public String registerPage() {
        return "regPage";
    }

    @GetMapping("/view/{id}")
    public String getList(@PathVariable Long id, ModelMap modelMap) {
        modelMap.addAttribute("bookinfo", booksrepository.findById(id).get());
        return "singlebook";
    }

    @GetMapping("/singlebook")
    public String singlebookPage() {
        return "singlebook";
    }


    @PostMapping("/register")
    public String saveBook(Book book,
                           @RequestParam("image") MultipartFile multipartFile) {

        File imageDirUrl = new File(fileDir);

        if (!imageDirUrl.exists()) {
            boolean mkdir = imageDirUrl.mkdir();
            log.info("Non existing folder has been created, status : " + mkdir);
        }

        if (!multipartFile.isEmpty()) {

            String originalFilename = multipartFile.getOriginalFilename();

            if (originalFilename != null) {

                try {
                    originalFilename = new Date().toString() + originalFilename;
                    multipartFile.transferTo(new File(imageDirUrl, originalFilename));
                    book.setImageUrl(originalFilename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (book.getImageUrl() == null) {
            book.setImageUrl("no_image.jpeg");
        }

        bookService.saveBook(book);
        return "redirect:/bookslist";

    }

    @PostMapping("/saveBook")
    public String saveEditedbook(Book book) {

        bookService.saveBook(book);
        return "redirect:/bookslist";
    }


    @GetMapping("/getImage")
    public @ResponseBody
    ResponseEntity<?> image(@RequestParam("image") String image) throws IOException {
        InputStream in = new FileInputStream(fileDir + image);
        return ResponseEntity.status(HttpStatus.OK).body(IOUtils.toByteArray(in));
    }


    @GetMapping(value = "/editpage/{id}")
    public String editingPage(@PathVariable Long id, ModelMap modelMap) {

        Optional<Book> byId = booksrepository.findById(id);

        if (byId.isPresent()) {

            Book book = byId.get();
            modelMap.addAttribute("book", book);

            return "editpage";
        }

        return "redirect:/bookslist";

    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/bookslist";
    }

    @GetMapping("/bookslist")
    public String getList(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {

        if (currentUser != null) {
            User user = currentUser.getUser();
            modelMap.addAttribute("user1", user);
        }

        modelMap.addAttribute("books", booksrepository.findAll());
        return "bookslist";
    }

//    @GetMapping("/bookslistuser")
//    public String getListUser(ModelMap modelMap) {
//        modelMap.addAttribute("books", booksrepository.findAll());
//        return "bookslistuser";
//    }

}
