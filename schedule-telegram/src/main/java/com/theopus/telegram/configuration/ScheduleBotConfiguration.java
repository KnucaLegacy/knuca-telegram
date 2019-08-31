package com.theopus.telegram.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theopus.entity.schedule.Building;
import com.theopus.entity.schedule.Faculty;
import com.theopus.schedule.backend.configuration.StorageConfiguration;
import com.theopus.schedule.backend.repository.DepartmentRepository;
import com.theopus.schedule.backend.repository.GroupRepository;
import com.theopus.schedule.backend.repository.Repository;
import com.theopus.schedule.backend.repository.RoomRepository;
import com.theopus.schedule.backend.repository.TeacherRepository;
import com.theopus.schedule.backend.search.Storage;
import com.theopus.schedule.backend.search.StorageUpdater;
import com.theopus.schedule.backend.service.LessonService;
import com.theopus.telegram.FormatManager;
import com.theopus.telegram.TelegramSerDe;
import com.theopus.telegram.backend.configuration.TelegramBackendConfig;
import com.theopus.telegram.backend.repository.ChatRepository;
import com.theopus.telegram.backend.repository.UserRepository;
import com.theopus.telegram.bot.Bot;
import com.theopus.telegram.bot.MessageSender;
import com.theopus.telegram.bot.TelegramDispatcher;
import com.theopus.telegram.bot.handlers.EchoHandler;
import com.theopus.telegram.bot.interceptors.LoggingInterceptor;
import com.theopus.telegram.bot.interceptors.NotCommandCallbackInterceptor;
import com.theopus.telegram.handler.BaseSearchHandler;
import com.theopus.telegram.handler.HelloHandler;
import com.theopus.telegram.handler.MetricsInterceptor;
import com.theopus.telegram.handler.ScheduleHandler;
import com.theopus.telegram.handler.SearchCommandConfigurer;
import com.theopus.telegram.handler.SearchHandler;
import com.theopus.telegram.handler.UserMonitoringInterceptor;
import com.theopus.telegram.metric.GoogleMetricsService;
import com.theopus.telegram.metric.MetricService;

@Configuration
@Import({StorageConfiguration.class, TelegramBackendConfig.class})
public class ScheduleBotConfiguration {

    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.token}")
    private String token;
    @Value("${telegram.bot.metrics.google.tag}")
    private String metricsTag;

    @Bean
    public TelegramSerDe serDe(ObjectMapper mapper) {
        return new TelegramSerDe(mapper);
    }

    @Bean
    public TelegramDispatcher dispatcher(Storage storage,
                                         TelegramSerDe telegramSerDe,
                                         LessonService service,
                                         FormatManager manager,
                                         MetricService metricService,
                                         UserRepository userRepository,
                                         ChatRepository chatRepository,
                                         SearchCommandConfigurer commandConfigurer) {
        TelegramDispatcher telegramDispatcher = new TelegramDispatcher();
        FormatManager formatManager = new FormatManager();
        telegramDispatcher.register(new EchoHandler());
        telegramDispatcher.register(new BaseSearchHandler(storage, telegramSerDe, formatManager));
        telegramDispatcher.register(new LoggingInterceptor());
        telegramDispatcher.register(new NotCommandCallbackInterceptor());
        telegramDispatcher.register(new MetricsInterceptor(metricService));
        telegramDispatcher.register(new UserMonitoringInterceptor(userRepository, chatRepository));
        telegramDispatcher.register(new ScheduleHandler(telegramSerDe, service, manager));
        telegramDispatcher.register(new SearchHandler(telegramSerDe, manager, commandConfigurer));
        telegramDispatcher.register("hello", new HelloHandler(formatManager));
        telegramDispatcher.register("help", new HelloHandler(formatManager));
        telegramDispatcher.register("start", new HelloHandler(formatManager));
        telegramDispatcher.register("init", new HelloHandler(formatManager));
        return telegramDispatcher;
    }

    @Bean
    public FormatManager formatManager() {
        return new FormatManager();
    }

    @Bean
    public SearchCommandConfigurer searchCommandConfigurer(TelegramSerDe telegramSerDe,
                                                           Repository<Faculty> facultyRepository,
                                                           GroupRepository groupRepository,
                                                           DepartmentRepository departmentRepository,
                                                           TeacherRepository teacherRepository,
                                                           RoomRepository roomRepository,
                                                           Repository<Building> buildingRepository,
                                                           FormatManager formatManager) {
        return new SearchCommandConfigurer(telegramSerDe, formatManager,
                groupRepository, facultyRepository,
                departmentRepository, teacherRepository, roomRepository, buildingRepository);
    }

    @Bean(destroyMethod = "close")
    public MetricService googleMetrics() {
        return new GoogleMetricsService(metricsTag);
    }

    @Bean(destroyMethod = "onClosing")
    @DependsOn({"storageUpdater"})
    public Bot bot(StorageUpdater updater, TelegramDispatcher dispatcher, MessageSender sender) {
        Bot bot = new Bot(username, token, dispatcher, sender);
        return bot;
    }
}
