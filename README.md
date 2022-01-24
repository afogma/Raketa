# Backend test for Raketa

Этот проект реализует операции CRUD для работы с сущностью Value, содержащей id, дату и текстовое поле, в базе данных (
PostgreSQL). Помимо стандартных методов создания, удаления и редактирования сущностей, также содержит поиск с фильтрами
и порядком расположения найденных в базе объектов.

PostgreSQL ожидает на порту 5432, логин: «postgres», пароль «pgadmin»

Проект построен с помощью jdk8. Он включает библиотеки lombok, flyway и openapi. Проверить все методы можно по
URL-адресу: http://server-ip:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

Команда Curl для тестирования: curl -u admin:admin http://server-ip:8080/api/values

### `POST /api/values?number=3`

Добавляет в базу 3 объекта Value и возвращает список добавленных объектов

Пример ответа:

```json
[
  {
    "id": 1,
    "date": "2022-01-23T08:22:21.846733Z",
    "value": "Запись 1"
  },
  {
    "id": 2,
    "date": "2022-01-23T08:22:21.899716Z",
    "value": "Запись 2"
  },
  {
    "id": 3,
    "date": "2022-01-23T08:22:21.902715Z",
    "value": "Запись 3"
  }
]
```

Response : `200 OK`


### `GET /api/values`

Возвращает полный список объектов из базы данных

Пример ответа:

```json
[
  {
    "id": 1,
    "date": "2022-01-23T08:22:21.846733Z",
    "value": "Запись 1"
  },
  {
    "id": 2,
    "date": "2022-01-23T08:22:21.899716Z",
    "value": "Запись 2"
  },
  {
    "id": 3,
    "date": "2022-01-23T08:22:21.902715Z",
    "value": "Запись 3"
  }
]
```

Response : `200 OK`


### `GET /api/values?filter.id.eq=3`

Возвращает объект с id равным 3

Пример ответа:

```json
{
  "id": 3,
  "date": "2022-01-23T08:22:21.846733Z",
  "value": "Запись 3"
}
```

Response : `200 OK`


### `GET /api/values?order.id=desc`

Возвращает все объекты из базы, упорядоченные в обратной последовательности по полю id

Пример ответа:

```json
[
  {
    "id": 3,
    "date": "2022-01-23T08:22:21.902715Z",
    "value": "Запись 3"
  },
  {
    "id": 2,
    "date": "2022-01-23T08:22:21.899716Z",
    "value": "Запись 2"
  },
  {
    "id": 1,
    "date": "2022-01-23T08:22:21.846733Z",
    "value": "Запись 1"
  }
]
```

Response : `200 OK`


### `GET /api/values?filter.id.gt=1&order.id=asc&filter.value.ctn=Запись`

Возвращает список объектов из базы данных, у который id больше 1, в поле value содержится значение "Запись" и
упорядочивает список по возрастанию по полю id. Порядок и количество фильтров может быть любым.

Пример ответа:

```json
[
  {
    "id": 2,
    "date": "2022-01-23T08:22:21.899716Z",
    "value": "Запись 2"
  },
  {
    "id": 3,
    "date": "2022-01-23T08:22:21.902715Z",
    "value": "Запись 3"
  }
]
```

Response : `200 OK`


### `PUT /api/values/:2`

Возвращает объект с указанным id, для которого было изменено поле value с указанием нового значения в виде json
параметра

Пример запроса:

```json
{
  "value": "Новая запись"
}
```

Пример ответа: 

```json
  {
  "id": 2,
  "date": "2022-01-23T08:22:21.899716Z",
  "value": "Новая запись"
}
```

Response : `200 OK`


### `DELETE /api/values`

Удаляет все объекты из базы данных

Response : "all values removed from database"
