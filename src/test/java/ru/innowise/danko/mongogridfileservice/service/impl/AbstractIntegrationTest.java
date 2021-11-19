package ru.innowise.danko.mongogridfileservice.service.impl;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = MongoDBInitializer.class)
public abstract class AbstractIntegrationTest {
}
