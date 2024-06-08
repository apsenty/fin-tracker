# Мой первый проект. Приложение для учета финансов.

Привет!👋 Это моё первое в жизни приложение, которое я пишу сама. Я пишу его чисто для себя, но решила делать всё 
красиво, поэтому README быть.

**Цель данного проекта:** получше разобраться в языке Kotlin, на который я перешла с Java сравнительно недавно. 
Моя основная профессия - это автоматизатор тестирования, поэтому мои знания ЯП ограничиваются базовыми понятиями, 
которые могут пригодиться во время написания автотестов.
Изначально мне хотелось сделать базовый проект, на котором я могла бы оттачивать навыки автоматизации 
(и, в дальнейшем, нагрузочного тестирования), но в последствии в голове появилась идея сделать не просто болванку, 
а полноценное (по моему субъективному мнению) приложение.

Я перепробовала множество приложений для учета финансов, и единственным, которое мне понравилось, 
оказалось приложение [YNAB](https://www.ynab.com/). Именно на его функционал я и ориентируюсь, продумывая дальнейшее развитие своего проекта.  
У меня нет цели сделать лучшее приложение в мире, я даже не собираюсь его никуда выкладывать вне этой страницы. 
Я просто хочу проверить, получится ли у меня в рамках самообучения сотворить рабочее приложение.

## Описание проекта
Данное RESTful приложение предназначено для учета расходов и доходов. На начальном уровне оно представляет из себя 
набор базовых API методов, реализующих CRUD. Документация методов представлена ниже.  
Приложение может работать только локально.


### Стек проекта
- ![Static Badge](https://img.shields.io/badge/Kotlin-7f52ff?logo=Kotlin&logoColor=white)
- ![Static Badge](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)
- ![Static Badge](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)

### Фреймворк
- ![Static Badge](https://img.shields.io/badge/Spring%20Boot%20Web-6DB33F?logo=spring&logoColor=white)
- ![Static Badge](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?logo=spring&logoColor=white)

### Тесты
- ![Static Badge](https://img.shields.io/badge/JUnit%205-25A162?logo=junit5&logoColor=white)
- ![Static Badge](https://img.shields.io/badge/Spring%20Boot%20Test-6DB33F?logo=spring&logoColor=white)

## Установка и запуск
Для установки и запуска необходимо, чтобы у вас была установлена СУБД [PostgreSQL](https://www.postgresql.org/download/)
(ссылка ведет на оф. сайт PostgreSQL).

1. Скачайте данный репозиторий.
2. Перед запуском приложения необходимо создать БД.
   1. Выполните SQL-запрос для создания БД finance_tracker:
      ```sql 
      CREATE DATABASE finance_tracker
      WITH
      OWNER = your_db_user_name
      ENCODING = 'UTF8'
      LC_COLLATE = 'Russian_Russia.1251'
      LC_CTYPE = 'Russian_Russia.1251'
      LOCALE_PROVIDER = 'libc'
      TABLESPACE = pg_default
      CONNECTION LIMIT = -1
      IS_TEMPLATE = False;

   2. Выполните SQL-запрос для создания таблицы spending:
      ```sql
      CREATE TABLE IF NOT EXISTS public.spending
      (
      id integer NOT NULL DEFAULT nextval('spending_id_seq'::regclass),
      amount numeric NOT NULL,
      type character varying COLLATE pg_catalog."default" NOT NULL,
      comment character varying COLLATE pg_catalog."default",
      CONSTRAINT spending_pk PRIMARY KEY (id)
      )
   
      TABLESPACE pg_default;
   
      ALTER TABLE IF EXISTS public.spending
      OWNER to your_db_user_name;
3. В файле `src/main/resources/application.yml` замените значения `your_name` и `your_password` на ваш логин и пароль пользователя БД.
4. Запустите приложение `FinTrackerApplication.kt`.
5. Теперь вы можете отправлять api-запросы в соответствии с документацией, представленной ниже.

## Документация
С наглядной документацией можно ознакомиться, скачав репозиторий и открыв в браузере файл `apidoc.html`.  
Краткий список методов представлен в файле [Documentation](docs/Documentation.md)
