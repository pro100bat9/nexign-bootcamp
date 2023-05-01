# nexign-bootcamp

Выполнил: Дорошенко Семен 

## Стэк:
* a
* b

Разработаны 4 микросервиса 
* CDR
* BRT
* HRS
* CRM

## Запуск проекта

    ./build.sh
    docker compose up --build -d

##CDR 

##BRT

##HRS

##CRM

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
##




