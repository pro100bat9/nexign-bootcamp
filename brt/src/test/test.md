TEST CASE

Когда вызывается метод authorizeClient из BrtService с определнными номерами телефонов
Ожидается, что из базы данных будет взят соответствующий клиент.
Для каждого пользователя с балансом больше 0 отправаляется запрос в hrs по кафке, тест проверяет, что запрос в кафку ушел по пользователю с номером телефона из запроса