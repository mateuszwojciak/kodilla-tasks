package com.crud.tasks.service;

import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("previewMessage", "here is preview message");
        context.setVariable("goodbyeMessage", "Goodbye!");
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("companyDetails", "here is company details info");
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyInformationEmail() {
        long numberOfTasks = taskRepository.count();
        String dailyMessage = "Liczba zadań dostępnych w bazie danych na dziś: " + numberOfTasks;
        Context context = new Context();
        context.setVariable("message", dailyMessage);
        context.setVariable("welcomeMessage", "Witaj!");
        context.setVariable("goodbyeMessage", "Z poważaniem, ");
        context.setVariable("mailSender", "Zespół ds Powiadomień Dziennych");
        context.setVariable("companyDetails", "here is company details info");
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("show_functionality", false);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
