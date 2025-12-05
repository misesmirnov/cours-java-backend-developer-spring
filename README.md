# Управление задачами API

## Запуск приложения

### 1. Запуск базы данных (PostgreSQL)

```
# Перейдите в корневую директорию проекта
docker-compose up -d postgres
```

### 2. Запуск приложения
- Откройте проект в IntelliJ IDEA
- Найдите главный класс `Application.java`
- Запустите метод `main()`

## Порядок использования API

### Шаг 1: Регистрация пользователя
`POST /api/auth/register`

### Шаг 2: Аутентификация (получение токена)
`POST /api/auth/login`
**Ответ:** JWT токен для использования в следующих запросах.

### Шаг 3: Работа с задачами (требуется токен)

**Создать группу задач:**
`POST /api/user/group/create`

**Создать задачу:**
`POST /api/user/task/create`

**Получить свои задачи:**
`GET /api/user/tasks`

### Шаг 4: Администрирование (требуется роль ADMIN)

**Просмотр всех пользователей:**
`GET /api/admin/users`

**Просмотр статистики:**
`GET /api/admin/statistics`


## Основные эндпоинты

### Публичные (без авторизации)
- `POST /api/auth/register` - регистрация
- `POST /api/auth/login` - вход (получение токена)

### Пользовательские (требуется роль USER/ADMIN)
- `GET /api/user/tasks` - получить свои задачи
- `POST /api/user/task/create` - создать задачу
- `GET /api/user/groups` - получить свои группы
- `POST /api/user/group/create` - создать группу

### Административные (требуется роль ADMIN)
- `GET /api/admin/users` - все пользователи
- `GET /api/admin/tasks` - все задачи системы
- `GET /api/admin/statistics` - статистика

## Настройки по умолчанию

**База данных:**
- Хост: `localhost:5432`
- База: `demo`
- Пользователь: `postgres`
- Пароль: `root`

**JWT токен:**
- Действует: 1 час


## Дополнительная информация

- **Swagger:** http://localhost:8080/swagger-ui/index.html#
