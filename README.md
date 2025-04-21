# Sprint_7

Проект автоматизированного тестирования API для сервиса доставки самокатов.

## Описание проекта

Проект содержит автоматизированные тесты для проверки API сервиса доставки самокатов. Тесты покрывают основные функции:
- Создание курьера
- Авторизация курьера
- Удаление курьера
- Обработка ошибок

## Технологический стек

- Java 11
- JUnit 4
- RestAssured
- Allure Framework
- Maven

## Требования

Для запуска проекта необходимо:

1. Java JDK 11 или выше
2. Apache Maven 3.6.0 или выше
3. Allure Commandline (для генерации отчетов)

## Установка и настройка


1. Запуск всех тестов
```zsh
mvn clean test
```
2. Запуск конкретного тестового класса
```zsh
mvn clean test -Dtest=CourierLoginTest
```
```zsh
mvn clean test -Dtest=CreateColorOrdersTest
```
```zsh
mvn clean test -Dtest=CreateNewCourierTest
```
```zsh
mvn clean test -Dtest=GetListOfOrdersTest

```
3. Генерация отчетов Allure
```sh
mvn allure:serve
```
# Sprint_7
