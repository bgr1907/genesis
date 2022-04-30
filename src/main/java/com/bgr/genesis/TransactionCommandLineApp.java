package com.bgr.genesis;

import com.bgr.genesis.entity.Order;
import com.bgr.genesis.entity.Payment;
import com.bgr.genesis.service.OrderService;
import com.bgr.genesis.service.PaymentService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TransactionCommandLineApp implements CommandLineRunner {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @Override
    public void run(String... args) throws Exception {
        Payment payment = Payment.builder()
            .userId(1)
            .orderId(2)
            .price(BigDecimal.valueOf(23))
            .build();

        Order order = Order.builder()
            .productCode("sku1")
            .amount(3)
            .unitPrice(BigDecimal.valueOf(23.1))
            .userId(23)
            .build();

        //paymentService.pay(payment);
        //orderService.placeOrder(order);
    }

    /**
     * 1-)Propagation.REQUIRED: Eğer mevcutta bir transaction var ise yeni bir transaction açmadan bu transactionu
     * kullanır,Eğer transaction yoksa yeni bir transaction açar. @Transactional keywordu yazıldığında
     * davranış şekli otomatik olarak “REQUIRED” dır.
     *
     * 2-)Propagation.SUPPORTS:Eğer bir transaction var ise o transaction u kullanır .Yok ise transaction’sız çalışır.
     * Yeni bir transaction da açmaz
     *
     * 3-)Propagation.MANDATORY: Eğer bir transaction yok ise exception fırlatır.
     *
     * 4-)Propagation..NOT_SUPPORTED: Eğer bir transaction var ise o transaction’u suspend edilir ve
     * yeni bir transaction da açmaz.
     *
     * 5-)Propagation.NEVER: Eğer bir transaction var ise exception fırlatır
     *
     * source: https://medium.com/@dururyener/transaction-y%C3%B6netimi-ve-spring-boot-transactional-kullan%C4%B1m%C4%B1-f894cc66c9d9
     *
     * */

    /**
     * Spring Transaction Isolation Levels
     *
     * Neden Isolation’a ihtiyaç duyulur?
     * Isolation: Uygulama katmanında birden fazla transaction varsa her bir transaction altında yer alan iş
     * parçacıklarının kullanacağı veriyi yönetmek için kullanılır .
     *
     * Isolation teknolojisi sayesinde birden fazla eş zamanlı transactionları,
     * pararelde birbirlerini etkilemeden çalıştırmamızı sağlar.
     *
     * TRANSACTION_READ_UNCOMMITTED : Bu seviyede UNCOMMIT durumundaki transanctionlar okunabilir.
     * Yani veri tabanında kayıt atıp onu eş zamanlı olan diğer transactionda okuyabiliriz.
     * Bu first level ya da 1.Seviye olarak adlandırılmaktadır ve yukarıda bahsettiğimiz 3 anomalinin
     * gerçekleşmesine neden olur.
     *
     * TRANSACTION_READ_COMMITTED : Bu seviyede sadece COMMIT durumundaki transanction verisi okunabilir.
     * Veri tabanına kayıt atan bir transactiondaki veri, commit olduktan sonra eş zamanda çalışan diğer transaction
     * tarafından bu dataya ulaşılır.
     *
     * Bu durum second level ya da 2.Seviye olarak adlandırılır .Bu seviyede “Dirty reads” anomalisi gerçekleşmez
     * ama “non-repeatable_reads” ve “phantom_reads” anomalileri gerçekleşir.
     *
     * TRANSACTION_REPEATABLE_READ : Bu seviyede sadece non-repeatable_reads anomalisini çözmek için kullanılır.
     * Yani eş zamanlı iki transactionda select yapan transactionın iki farklı dataya erişimi olmaz. Bu third level
     * ya da 3.Seviye olarak adlandırılmaktadır. Bu seviyede sadece “non-repeatable_reads”anomalisi gerçekleşmez.
     * En çok kullanılan ve en popüler olan seviyedir.
     *
     * TRANSACTION_SERIALIZABLE : Bu seviye fourth level yada 4.Seviye olarak adlandırılmaktadır. Bu seviyede hiç bir
     * anomali gerçekleşmez ama transaction concurrency(eş zamanlılık) durumuna da izin vermez ve en az
     * performanslı olan seviyedir.
     *
     * source: https://medium.com/@dururyener/spring-transaction-isolation-seviyeleri-33821aa1980
     */
}
