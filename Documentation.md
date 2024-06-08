# Документация REST API

**Содержание**

## Аутентификация
На данном этапе аутентификация пользователей не предусмотрена.

## Базовый URL
В качестве базового URL используется локальный урл: `http://127.0.0.1:8080`

## Методы
### Получение списка всех записей таблицы spending
`GET /fintracker`

Позволяет получить список всех записей из таблицы `spending`  

**Пример запроса:**
``` 
curl --location 'http://127.0.0.1:8080/fintracker'
```
**Пример ответа:**  
![Static Badge](https://img.shields.io/badge/200-OK-green)
```json
[
    {
        "id": 1,
        "amount": 1500.0,
        "type": "Доход",
        "comment": null
    },
    {
        "id": 2,
        "amount": 30.0,
        "type": "Расход",
        "comment": "Водичка"
    }
]
```

### Получение записи по её id
`GET /fintracker/{id}`

Позволяет получить конкретную запись из таблицы `spending` по её id

**Пример запроса:**
``` 
curl --location 'http://127.0.0.1:8080/fintracker/1'
```
**Пример ответа:**  
![Static Badge](https://img.shields.io/badge/200-OK-green)
```json
{
  "id": 1,
  "amount": 100.0,
  "type": "Расход",
  "comment": "Кофе"
}
```
**Список ошибок:**  
![Static Badge](https://img.shields.io/badge/404-NotFound-red) Записи с таким id не существует  
Ответ:
```json
{
    "errorCode": "spendingNotFound",
    "description": "Запись не найдена."
}
```

### Создание новой записи расхода/дохода
`POST /fintracker`

Позволяет создавать новую запись в таблице `spending`.

**Пример запроса:**
``` 
curl --location 'http://127.0.0.1:8080/fintracker' \
--header 'Content-Type: application/json' \
--data '{
    "amount": 100.00,
    "type": "Доход",
    "comment": "Сотка в кармашке"
}'
```
**Пример ответа:**  
![Static Badge](https://img.shields.io/badge/201-Created-green)
```json
{
  "code": "success",
  "id": 48,
  "message": "Запись успешно создана."
}
```

### Обновление существующей записи расхода/дохода
`PATCH /fintracker/{id}`

Позволяет редактировать существующую запись в таблице `spending` по её id.  
В запросе передаются только те поля, которые нужно изменить.

**Пример запроса:**
``` 
curl --location --request PATCH 'http://127.0.0.1:8080/fintracker/1' \
--header 'Content-Type: application/json' \
--data '{
    "comment": "Тест"
}'
```
**Пример ответа:**  
![Static Badge](https://img.shields.io/badge/200-OK-green)
```json
{
  "code": "success",
  "id": 1,
  "message": "Запись успешно обновлена."
}
```
**Список ошибок:**  
![Static Badge](https://img.shields.io/badge/404-NotFound-red) Записи с таким id не существует  
Ответ:
```json
{
    "errorCode": "spendingNotFound",
    "description": "Запись не найдена."
}
```

### Удаление существующей записи расхода/дохода
`DELETE /fintracker/{id}`

Позволяет удалить существующую запись в таблице `spending` по её id

**Пример запроса:**
``` 
curl --location --request DELETE 'http://127.0.0.1:8080/fintracker/3'
```
**Пример ответа:**  
![Static Badge](https://img.shields.io/badge/200-OK-green)
```json
{
  "code": "success",
  "id": 1,
  "message": "Запись успешно удалена."
}
```
**Список ошибок:**  
![Static Badge](https://img.shields.io/badge/404-NotFound-red) Записи с таким id не существует  
Ответ:
```json
{
    "errorCode": "spendingNotFound",
    "description": "Запись не найдена."
}
```
