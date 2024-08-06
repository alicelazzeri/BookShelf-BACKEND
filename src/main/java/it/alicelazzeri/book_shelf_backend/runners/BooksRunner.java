package it.alicelazzeri.book_shelf_backend.runners;

import it.alicelazzeri.book_shelf_backend.entities.User;
import it.alicelazzeri.book_shelf_backend.exceptions.NotFoundException;
import it.alicelazzeri.book_shelf_backend.payloads.entities.BookDTO;
import it.alicelazzeri.book_shelf_backend.services.BookService;
import it.alicelazzeri.book_shelf_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Order(2)
public class BooksRunner implements CommandLineRunner {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        Random random = new Random();

        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            throw new NotFoundException("No users were found in the DB.");
        }

        List<BookDTO> books = new ArrayList<>();
        books.add(
                new BookDTO("Harry Potter and the Philosopher's Stone",
                        "J. K. Rowling",
                        9780747532699L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "Harry Potter's first year at Hogwarts, where he learns he's a famous wizard and faces the Dark Lord.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722924417/book%20covers/vdrlw9raq4afrozoxrfw.jpg"
                )
        );
        books.add(
                new BookDTO("Harry Potter and the Chamber of Secrets",
                        "J. K. Rowling",
                        9780747538493L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "Harry returns to Hogwarts and discovers the dark secrets of the Chamber of Secrets.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722924416/book%20covers/dkmypxvrp0me7eifj9rp.jpg"
                )
        );
        books.add(
                new BookDTO("Harry Potter and the Prisoner of Azkaban",
                        "J. K. Rowling",
                        9780747542155L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "Harry learns about Sirius Black, a dangerous prisoner who has escaped from Azkaban.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722924416/book%20covers/k7jnb1rv4gdzpapn4yqe.jpg"
                )
        );
        books.add(
                new BookDTO("Harry Potter and the Goblet of Fire",
                        "J. K. Rowling",
                        9780747551003L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "Harry competes in the dangerous Triwizard Tournament and faces Voldemort's return.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926795/book%20covers/harry%20potter/dso0a4tzebmzudtmwzvo.jpg"
                )
        );
        books.add(
                new BookDTO("Harry Potter and the Order of the Phoenix",
                        "J. K. Rowling",
                        9780747551004L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "Harry battles the Ministry of Magic and the dark forces of Voldemort in his fifth year.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926794/book%20covers/harry%20potter/xpahaobgzpw6ve0bxlih.jpg"
                )
        );
        books.add(
                new BookDTO("Harry Potter and the Half-Blood Prince",
                        "J. K. Rowling",
                        9780747581086L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "Harry discovers more about Voldemort's past and searches for Horcruxes.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926794/book%20covers/harry%20potter/qrje8zvkjbgjolkwpihl.jpg"
                )
        );
        books.add(
                new BookDTO("Harry Potter and the Deathly Hallows",
                        "J. K. Rowling",
                        9780747591054L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "Harry, Ron, and Hermione go on a mission to destroy the Horcruxes and defeat Voldemort.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926794/book%20covers/harry%20potter/tvtbbcae0rudpzhfxlka.jpg"
                )
        );
        books.add(
                new BookDTO("Quidditch Through the Ages",
                        "J. K. Rowling",
                        9780747551002L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "A comprehensive guide to Quidditch, the favorite sport of the wizarding world.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926794/book%20covers/harry%20potter/lpsei7o2coc4mvxp4oni.jpg"
                )
        );
        books.add(
                new BookDTO("The Tales of Beedle the Bard",
                        "J. K. Rowling",
                        9780747599876L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "A collection of fairy tales for young wizards and witches, including 'The Tale of the Three Brothers'.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926794/book%20covers/harry%20potter/nsrfaibiegzvzcuhaw2k.jpg"
                )
        );
        books.add(
                new BookDTO("Fantastic Beasts and Where to Find Them",
                        "J. K. Rowling",
                        9780747591056L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "An A-Z guide to magical creatures in the wizarding world.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722927221/book%20covers/harry%20potter/eyuxynotjbqnpn467bfd.jpg"
                )
        );
        books.add(
                new BookDTO("The Hobbit",
                        "J. R. R. Tolkien",
                        9780345339683L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "Bilbo Baggins' unexpected journey to the Lonely Mountain with a group of dwarves to reclaim their home from Smaug the dragon.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926794/book%20covers/lord%20of%20the%20rings/wzmslpz0dihgwcpf1242.jpg"
                )
        );
        books.add(
                new BookDTO("The Lord of the Rings: The Fellowship of the Ring",
                        "J. R. R. Tolkien",
                        9780345339706L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "Frodo Baggins sets out on a quest to destroy the One Ring and save Middle-earth from the Dark Lord Sauron.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926794/book%20covers/lord%20of%20the%20rings/h0mq8eigie80doxuh7ol.jpg"
                )
        );
        books.add(
                new BookDTO("The Lord of the Rings: The Two Towers",
                        "J. R. R. Tolkien",
                        9780345339713L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "The Fellowship is scattered, and Frodo and Sam continue their journey to Mordor with the help of Gollum.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926793/book%20covers/lord%20of%20the%20rings/ankvxaaodepzkvxihimf.jpg"
                )
        );
        books.add(
                new BookDTO("The Lord of the Rings: The Return of the King",
                        "J. R. R. Tolkien",
                        9780345339737L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "The final battle for Middle-earth begins, and Frodo reaches Mount Doom to destroy the One Ring.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926793/book%20covers/lord%20of%20the%20rings/uitdzsvcpa70ywpxg8nl.jpg"
                )
        );
        books.add(
                new BookDTO("My Brilliant Friend",
                        "Elena Ferrante",
                        9781609450786L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "The first book in the Neapolitan Quartet, chronicling the lives of two childhood friends in Naples.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926793/book%20covers/my%20brilliant%20friend/zcxfgechsha2fu33e10a.jpg"
                )
        );
        books.add(
                new BookDTO("The Story of a New Name",
                        "Elena Ferrante",
                        9781609451349L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "The second book in the Neapolitan Quartet, continuing the story of Elena and Lila.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926793/book%20covers/my%20brilliant%20friend/twyqcohgoeb3jur0yjbu.jpg"
                )
        );
        books.add(
                new BookDTO("Those Who Leave and Those Who Stay",
                        "Elena Ferrante",
                        9781609452339L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "The third book in the Neapolitan Quartet, exploring the complexities of friendship and personal growth.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926793/book%20covers/my%20brilliant%20friend/k3u4sglskb6mkez9shjh.jpg"
                )
        );
        books.add(
                new BookDTO("The Story of the Lost Child",
                        "Elena Ferrante",
                        9781609452865L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "The final book in the Neapolitan Quartet, concluding the epic saga of Elena and Lila.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926793/book%20covers/my%20brilliant%20friend/tmt18bq1p9advh6m5svu.jpg"
                )
        );
        books.add(
                new BookDTO(
                        "Twilight",
                        "Stephenie Meyer",
                        9780316015844L,
                        generateRandomPastDate(random),
                        generateRandomFutureDate(random),
                        "A teenage girl falls in love with a vampire, facing challenges and danger as their worlds collide.",
                        generateRandomReadings(random),
                        "https://res.cloudinary.com/dopblayrc/image/upload/v1722926795/book%20covers/twilight/c6fonr0ciy458nghg9sy.jpg"
                )
        );
        books.add(new BookDTO(
                "New Moon",
                "Stephenie Meyer",
                9780316024969L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The second book in the Twilight series, exploring themes of love, loss, and danger.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926795/book%20covers/twilight/hunytmjpepah3agiwh1s.jpg"
        ));
        books.add(new BookDTO(
                "Eclipse",
                "Stephenie Meyer",
                9780316160209L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The third book in the Twilight series, where a love triangle complicates matters.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926795/book%20covers/twilight/gxt8vqaq9qpxhpwwpgpd.jpg"
        ));
        books.add(new BookDTO(
                "Breaking Dawn",
                "Stephenie Meyer",
                9780316067928L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The final book in the Twilight series, bringing the saga to a thrilling conclusion.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926795/book%20covers/twilight/pqxu1ikzocteucm6uaec.jpg"
        ));
        books.add(new BookDTO(
                "A Game of Thrones",
                "George R. R. Martin",
                9780553103540L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The first book in the A Song of Ice and Fire series, introducing the struggle for the Iron Throne.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926792/book%20covers/game%20of%20thrones/zs36da8muonrrviup7gf.jpg"
        ));
        books.add(new BookDTO(
                "A Clash of Kings",
                "George R. R. Martin",
                9780553108033L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The second book in the series, where kings clash and the battle for power intensifies.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926792/book%20covers/game%20of%20thrones/r9rk1u77jx71ggznkvif.jpg"
        ));
        books.add(new BookDTO(
                "A Storm of Swords",
                "George R. R. Martin",
                9780553106633L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The third book, filled with betrayals, battles, and unexpected twists.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926793/book%20covers/game%20of%20thrones/tp4jglzsxdvhbfhgru5v.jpg"
        ));
        books.add(new BookDTO(
                "A Feast for Crows",
                "George R. R. Martin",
                9780553801507L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The fourth book in the series, where the aftermath of war shapes the future of Westeros.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926793/book%20covers/game%20of%20thrones/mzdzjwpw6dxadcg4dob9.jpg"
        ));
        books.add(new BookDTO(
                "A Dance with Dragons",
                "George R. R. Martin",
                9780553801477L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The fifth book, where dragons rise and the stakes are higher than ever.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926793/book%20covers/game%20of%20thrones/kt6mukdufcg6stiqvfqy.jpg"
        ));
        books.add(new BookDTO(
                "The Winds of Winter",
                "George R. R. Martin",
                9780553801484L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The sixth book in the series, where winter brings new challenges and threats.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926792/book%20covers/game%20of%20thrones/z1dsbawbjdy7fliq8zv5.jpg"
        ));
        books.add(new BookDTO(
                "A Dream of Spring",
                "George R. R. Martin",
                9780553801491L,
                generateRandomPastDate(random),
                generateRandomFutureDate(random),
                "The final book, concluding the epic saga of A Song of Ice and Fire.",
                generateRandomReadings(random),
                "https://res.cloudinary.com/dopblayrc/image/upload/v1722926792/book%20covers/game%20of%20thrones/z1dsbawbjdy7fliq8zv5.jpg"
        ));

        for (User user : users) {
            int numBooks = random.nextInt(books.size()) + 1;
            List<BookDTO> userBooks = getRandomSubsetOfBooks(books, numBooks, random);
            for (BookDTO book : userBooks) {
                bookService.saveBook(book, user.getId());
            }
        }

        System.out.println("Books have been successfully added to the DB.");
    }

    private LocalDate generateRandomPastDate(Random random) {
        return LocalDate.now().minusDays(random.nextInt(1000));
    }

    private LocalDate generateRandomFutureDate(Random random) {
        return LocalDate.now().plusDays(random.nextInt(1000));
    }

    private int generateRandomReadings(Random random) {
        return random.nextInt(10);
    }

    private List<BookDTO> getRandomSubsetOfBooks(List<BookDTO> books, int numBooks, Random random) {
        List<BookDTO> copy = new ArrayList<>(books);
        List<BookDTO> subset = new ArrayList<>();
        for (int i = 0; i < numBooks; i++) {
            subset.add(copy.remove(random.nextInt(copy.size())));
        }
        return subset;
    }
}
