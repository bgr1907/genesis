package com.bgr.genesis.repository;

import com.bgr.genesis.entity.Channel;
import com.bgr.genesis.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {

}
