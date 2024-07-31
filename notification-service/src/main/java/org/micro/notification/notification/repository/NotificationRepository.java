package org.micro.notification.notification.repository;

import org.micro.notification.notification.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {}
