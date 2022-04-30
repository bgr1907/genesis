package com.bgr.genesis;

import com.bgr.genesis.entity.Cart;
import com.bgr.genesis.entity.CartItem;
import com.bgr.genesis.entity.Channel;
import com.bgr.genesis.entity.Passport;
import com.bgr.genesis.entity.Person;
import com.bgr.genesis.entity.Subscriber;
import com.bgr.genesis.repository.CartItemRepository;
import com.bgr.genesis.repository.CartRepository;
import com.bgr.genesis.repository.ChannelRepository;
import com.bgr.genesis.repository.PassportRepository;
import com.bgr.genesis.repository.PersonRepository;
import com.bgr.genesis.repository.SubscriberRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JPACommandLineApp implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final PassportRepository passportRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ChannelRepository channelRepository;
    private final SubscriberRepository subscriberRepository;

    @Override
    public void run(String... args) throws Exception {
        //oneToOneExample();
        //oneToManyExample();
        //manyToManyExample();
        //deleteExample();
    }

    private void deleteExample() {
        cartRepository.deleteById(1);
    }

    private void manyToManyExample() {
        Channel channel1 = new Channel();
        channel1.setName("ch1");
        channel1.setUrl("url1");

        Channel channel2 = new Channel();
        channel2.setName("ch2");
        channel2.setUrl("url2");

        Subscriber subscriber1 = new Subscriber();
        subscriber1.setUsername("user1");

        Subscriber subscriber2 = new Subscriber();
        subscriber2.setUsername("user2");

        channel1.setSubscribers(List.of(subscriber1, subscriber2));
        subscriberRepository.saveAll(List.of(subscriber1, subscriber2));
        channelRepository.saveAll(List.of(channel1, channel2));

        List<Channel> channels = channelRepository.findAll();
        List<Subscriber> subscribers = subscriberRepository.findAll();

        System.out.println(subscribers);
        System.out.println(channels);
    }

    private void oneToManyExample() {
        Cart cart = new Cart();
        cart.setUserId(5);

        cartRepository.save(cart);

        CartItem cartItem1 = new CartItem();
        cartItem1.setCart(cart);
        cartItem1.setProductCode("code1");
        cartItem1.setQuantity(2);
        cartItem1.setUnitPrice(BigDecimal.valueOf(24.5));

        CartItem cartItem2 = new CartItem();
        cartItem2.setCart(cart);
        cartItem2.setProductCode("code2");
        cartItem2.setQuantity(5);
        cartItem2.setUnitPrice(BigDecimal.valueOf(124.5));

        cartItemRepository.save(cartItem1);
        cartItemRepository.save(cartItem2);

        //List<Cart> carts = cartRepository.findAll();
        List<Cart> carts = cartRepository.findByUserId(5);
        List<CartItem> cartItems = cartItemRepository.findAll();

        System.out.println(carts.get(0).getCartItems());
        System.out.println(cartItems.get(0).getCart());
    }

    private void oneToOneExample() {
        Person person = Person.builder()
            .name("ahmet")
            .surname("buğra")
            .build();
        Passport passport = Passport.builder()
            .expireDate(Instant.now().plus(10, ChronoUnit.DAYS).getEpochSecond())
            .person(person)
            .build();
        passportRepository.save(passport);
        System.out.println(passport);
    }
}
