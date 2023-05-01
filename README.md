# nexign-bootcamp

## ссылка на dockerhub
    https://hub.docker.com/u/pro100bat9

Разработаны 4 микросервиса 
* CDR
* BRT
* HRS
* CRM

## Запуск проекта

    ./build.sh
    docker compose up --build -d

##CDR
Этот микросервис генерирует клиентов, и файл со звонками, после записывает в файл,
кол-во генерируемых данных указывается в application.properties.
##BRT
В этом микросервисе звонки записываются в базу данных и производится тарификация,
звонки отправляются в hrs, там рассчитывается стоимость каждого звонка, после это
все возвращается в BRT и записывается в базу данных.
При первом запуске если в БД пусто производится автоматическая тарификация
##HRS
Рассчитывает стоимость звонка, после возвращает в BRT
##CRM
Тут реализованы эндпоинты и сделана авторизация

на эндпоитнте billing стоит таймаут на 20 секунд, лучше запустить еще раз, 
потому что 20 секунд не хватит и выведется NULL


Команда для проверки эндпойнта billing:

    curl -X PATCH -H "Content-Type: application/json" -d '{"action" : "run"}' http://localhost:8080/manager/billing

Endpoint: 

    http://localhost:8080/manager/billing
 
Method:

    PATCH


Request body:

    {
    "action" : "run"
    }

Response body:
    
    {
        "numbers": [
            {
            "phoneNumber": "79129196945",
            "cost": "137.00"
            },
            {
            "phoneNumber": "79129196953",
            "cost": "45.00"
            },
            ...
        ]
    }
Команда для проверки эндпойнта createAbonent:

    curl -X POST -H "Content-Type: application/json" -d '{ "numberPhone" : "7123123126", "tariff_id" : "06",  "balance" : "400"}' http://localhost:8080/manager/createAbonent

Endpoint:

    http://localhost:8080/manager/createAbonent

Method:
    
    POST

Request body:

    {
    "numberPhone" : "7123123125",
    "tariff_id" : "06",
    "balance" : "400"
    }

Response body:

    {
    "numberPhone": "7123123125",
    "tariff_id": "06",
    "balance": "400"
    }

Команда для проверки эндпойнта changeTariff:
    
    curl -X PATCH -H "Content-Type: application/json" -d '{"numberPhone" : "7123123123","tariff_id" : "03"}' http://localhost:8080/manager/changeTariff

Endpoint:

    http://localhost:8080/manager/changeTariff

Method:

    PATCH

Request body:

    {
    "numberPhone" : "7123123125",
    "tariff_id" : "03"
    }

Response body:

    {
    "id": "182",
    "numberPhone": "7123123125",
    "tariff_id": "03"
    }

Команда для проверки эндпойнта changeTariff:

    curl -X PATCH -H "Content-Type: application/json" -d '{"numberPhone" : "79129196442", "money" : "1000"}' http://localhost:8080/abonent/pay

Endpoint:

    http://localhost:8080/abonent/pay

Method:

    PATCH

Request body:

    {
    "numberPhone" : "7123123125",
    "money" : "400"
    }

Response body:

    {
    "id": "182",
    "numberPhone": "7123123125",
    "money": "800.00"
    }

Команда для проверки эндпойнта report:

    curl http://localhost:8080/abonent/report/79129196442

Endpoint:

    http://localhost:8080/abonent/report/79129196442

Method:

    GET

Response body:

    {
    "id": "98",
    "numberPhone": "79129196234",
    "tariffIndex": "11",
    "payload": [
        {
            "callType": "01",
            "startTime": "2023-05-01T19:07:24",
            "endTime": "2023-05-01T20:02:51",
            "duration": "PT-55M-27S",
            "cost": "84.00"
        },
        {
            "callType": "01",
            "startTime": "2023-05-01T19:07:24",
            "endTime": "2023-05-01T19:22:40",
            "duration": "PT-15M-16S",
            "cost": "24.00"
        },
    ]
    }


##




