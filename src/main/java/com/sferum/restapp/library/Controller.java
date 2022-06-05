package com.sferum.restapp.library;


import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class Controller {

    Logger logger = Logger.getLogger(Controller.class.getName());
    AtomicLong counter = new AtomicLong();

    //начальные данные аккаунта
    private List<Account> accountList = new ArrayList<>(){{
        add(new Account(new Book("Думай медленно... Решай быстро","Даниэль Канеман"), 1L));
        add(new Account(new Book("Философия Java","Брюс Эккель"), 2L));
    }};
    //начальные данные маркета
    private List<Market> marketList = new ArrayList<>(){{
        add(new Market(counter.getAndIncrement(), new Book("Философия Java","Брюс Эккель"), 1500, 1L));
        add(new Market(counter.getAndIncrement(), new Book("Думай медленно... Решай быстро","Даниэль Канеман"), 2000, 12L));
        add(new Market(counter.getAndIncrement(), new Book("Совершенный код","Стив Макконелл"), 1000, 7L));
        add(new Market(counter.getAndIncrement(), new Book("Effective Java","Joshua Bloch"), 2500, 10L));

    }};

    User user = new User(accountList);
    UserShop userShop = new UserShop(marketList);


    @GetMapping("/account")

    public User getAccount()
    {
        logger.log(Level.INFO, "Выдача информации об аккаунте пользователя");
         return user;
    }

    @GetMapping("/market")
    public UserShop getMarket()
    {
        logger.log(Level.INFO, "Выдача информации о маркете");
      return userShop;
    }

    @PostMapping("/market/deal")
    public ResponseEntity<String> makeDeal(@RequestBody Market market)
    {
        //проверка введенного id
        if (market.getId() > userShop.getProducts().size())
        {
            logger.log(Level.WARNING, "Книги с таким идентификатором нет в продаже");
            //выдаем статус 400 с сообщением в теле запроса
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Книги с таким идентификатором нет в продаже");
        }
        //обновление баланса пользователя и количества книг в магазине
        int newBalance = (int) (user.getBalance() - userShop.getProducts().get(Math.toIntExact(market.getId())).getPrice() * market.getAmount());
        long newAmount = userShop.getProducts().get(Math.toIntExact(market.getId())).getAmount() - market.getAmount();



        //если количество книг на маркете и баланс пользователя позволяют совершить покупку
        if (newAmount > 0 && newBalance >= 0)
        {
            //добавляем книгу пользователю в его список
            accountList.add(new Account(userShop.getProducts().get(Math.toIntExact(market.getId())).getBook(), market.getAmount()));
            //обновляем баланс на аккаунте
            user.setBalance(newBalance);
            //обновляем количество книг в маркете
            userShop.getProducts().get(Math.toIntExact(market.getId())).setAmount(newAmount);

            logger.log(Level.INFO, "\n Покупка совершена!"
            + "\n Дата покупки: " + DateTime.now().toString("dd-MM-yy | hh:mm")
            + " \n Книга: "
            + userShop.getProducts().get(Math.toIntExact(market.getId())).getBook().getName()
            + " "
            + userShop.getProducts().get(Math.toIntExact(market.getId())).getBook().getAuthor()
            + "\n Цена за книгу: " + String.valueOf(userShop.getProducts().get(Math.toIntExact(market.getId())).getPrice())
            + "\n Количество: " + String.valueOf(market.getAmount())
            + " \n Цена: " + String.valueOf(userShop.getProducts().get(Math.toIntExact(market.getId())).getPrice() * market.getAmount())
            + "\n Баланс: " + String.valueOf(newBalance));
        }

        //если книг на маркете не осталоссь
         else if (newAmount == 0L)
        {

            accountList.add(new Account(userShop.getProducts().get(Math.toIntExact(market.getId())).getBook(), market.getAmount()));
            user.setBalance(newBalance);
            userShop.getProducts().get(Math.toIntExact(market.getId())).setAmount(newAmount);
            //удаляем книгу из маркета
            userShop.getProducts().remove(Math.toIntExact(market.getId()));

            logger.log(Level.INFO, "\n Покупка совершена!"
                    + "\n Дата покупки: " + DateTime.now().toString("dd-MM-yy | hh:mm")
                    + " \n Книга: "
                    + userShop.getProducts().get(Math.toIntExact(market.getId())).getBook().getName()
                    + " "
                    + userShop.getProducts().get(Math.toIntExact(market.getId())).getBook().getAuthor()
                    + "\n Цена за книгу: " + String.valueOf(userShop.getProducts().get(Math.toIntExact(market.getId())).getPrice())
                    + "\n Количество: " + String.valueOf(market.getAmount())
                    + " \n Цена: " + String.valueOf(userShop.getProducts().get(Math.toIntExact(market.getId())).getPrice() * market.getAmount())
                    + "\n Баланс: " + String.valueOf(newBalance));
        }
        //если количество книг в маркете меньше чем книг, выбранных пользователем для покупки
         else if (newAmount < 0)
        {
            logger.log(Level.WARNING, "Книги есть в продаже, но в недостаточном количестве");
            //выдаем статус 400 с сообщением в теле запроса
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Книги есть в продаже, но в недостаточном количестве");
        }
        // если баланса не хвататет для покупки
         else {
            logger.log(Level.WARNING, "У аккаунта не хватает денег, чтобы оплатить покупку");
            //выдаем статус 400 с сообщением в теле запроса
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("У аккаунта не хватает денег, чтобы оплатить покупку");
        }
        //при успешной сделке выдаем статус 200 с сообщением в теле запроса
        return ResponseEntity.status(HttpStatus.OK).body("Сделка успешно завершена");
    }
}
