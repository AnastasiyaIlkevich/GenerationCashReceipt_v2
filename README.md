<h1 align="center">Generation cash receip v2</h1>
<h2 align="center">Тестовое задание на курс backend-разработка</h2>



## Содержание
* Описание задачи
* Инструменты и технологии
* Старт программы
* API
* LFU и LRU кэширование
* Задачи дальнейшей реализации проекта

## Описание задачи
### [📝 Задание](./Docum/Backend_dev_course_tesy.pdf) 

## Инструменты и технологии
  - IntelliJ IDEA
  - Docker Desktop
  - Postman
- Внедрённые:
  - JDK 17
  - Gradl 7.5
  - Springboot
  - Spring Web
  - Spring Data JPA
  - Hibernate
  - PostgreSQL
  - Logging
  - Liquibase
  - LFU and LRU cache
  
  - Внедряемые:
  - JUnit
  - Docker (Первое освоение. Разбираюсь с настройками.)
  
  
  
  ## Старт программы

1. Клонируйте этот репозиторий командой:
```sh
$ git clone https://github.com/AnastasiyaIlkevich/GenerationCashReceipt_v2.git
```
2. В IntelliJ IDEA соберите проект в формате jar. Для этого выполните следующие действия:
	- откройте build.gradle:
	- запустите bootJar - ___Run___.

3. Запуск:
	- откройте папку с проектом …\GenerationCashReceipt_v2\build\libs
	- Запустите командную строку в этой папке и введите для старта команду:
  ```sh
java -jar generation-0.0.1-SNAPSHOT.jar
```
  - Для тестирования rest controller пройдите по одному из ниже предложенных вариантов http://localhost:8080/"..."
  
  |Значение “...”|RestController|
|----|-----|
|receipt|CashReceiptController| 
|discount|DiscountCardController| 
|product|ProductController| 
|shop|ShopInfoController|

  ## API

## CashReceiptController localhost:8080/receipt: 
Методы работы GET/POST/PUT/DELETE.
### Method : GET
localhost:8080/receipt - представляет список Cash Receipt (далее - чек).

localhost:8080/receipt/{id} - предоставляет конкретный чек.
### Method : POST
localhost:8080/receipt - создаёт новый чек, распечатывает (не до конца реализовано) и сохраняет в txt файле (не до конца реализовано).
На данный момент, в чек будет записана информация дефолтного магазина.
__RequestBody__ :
 {
 "setProduct": [{
                "id","name"
            }],
 "discountCard": {
            "id",
            "cardNumber"
        }
 }
 
 ### Method : POST
localhost:8080/receipt/shop/{id} - создаёт новый чек, распечатывает (не до конца реализовано) и сохраняет в txt файле (не до конца реализовано).
Указания Id shop изменяет дефолтное значение магазина на нужное вам. На для этого сначала эту информацию нужно внести.
__RequestBody__ :
 {
 "setProduct": [{
                "id","name"
            }],
 "discountCard": {
            "id",
            "cardNumber"
        }
 }


### Method : PUT
localhost:8080/receipt/{id} - изменяет чек (не реализовано)

__RequestBody__ :
  {
        "dateCreation",
        "setProduct": [
            {
                "id"
                "name"
                "price"
            }
        ],
        "shopInfo": {
            "id",
            "shopName",
            "address",
            "phoneNumber"
        },
        "discountCard": {
            "id",
            "cardNumber",
            "discount"
        }
    },
   }

### Method : DELETE
localhost:8080/receipt/{id} - удаляет чек по id

## DiscountCardController localhost:8080/discount: 
Методы работы GET/POST/PUT/DELETE.
### Method : GET
localhost:8080/discount - представляет список Discount Card (далее - карт).

localhost:8080/discount/{id} - предоставляет конкретный карту.
### Method : POST
localhost:8080/discount - создаёт новую карту с указанной скидкой.

__RequestBody__ :
 {
 "discountCard": {
            "cardNumber",
            "discount"
        }
 }

### Method : PUT
localhost:8080/discount/{id} - изменяет карту

__RequestBody__ :
 {
 "discountCard": {
            "cardNumber",
            "discount"
        }
 }

### Method : DELETE
localhost:8080/discount/{id} - удаляет карту по id

## ProductController localhost:8080/product: 
Методы работы GET/POST/PUT/DELETE.
### Method : GET
localhost:8080/product - представляет список Product (далее - товар).

localhost:8080/product/{id} - предоставляет конкретный товар.
### Method : POST
localhost:8080/product - создаёт новый товар.

__RequestBody__ :
{
                "name",
                "price"
            }

### Method : PUT
localhost:8080/product/{id} - изменяет данные товара.

__RequestBody__ :
 {
                "name",
                "price"
            }


### Method : DELETE
localhost:8080/product/{id} - удаляет товар по id

## ShopInfoController localhost:8080/shop: 
Методы работы GET/POST/PUT/DELETE.
### Method : GET
localhost:8080/shop - представляет список  Shop (далее - магазин).

localhost:8080/shop/{id} - предоставляет конкретный магазинов.
### Method : POST
localhost:8080/shop - создаёт новый магазин.

__RequestBody__ :
{
                "hopName", 
                 "address", 
                  "phoneNumber"
            }

### Method : PUT
localhost:8080/shop/{id} - изменяет данные магазина.

__RequestBody__ :
 {
                "hopName", 
                 "address", 
                  "phoneNumber"
            }


### Method : DELETE
localhost:8080/shop/{id} - удаляет магазин по id

## LFU и LRU кэширование

### Least recently used - LRU (Вытеснение давно неиспользуемых)
LRU - это алгоритм, при котором вытесняются элементы, которые дольше всего не запрашивались.
Данный алгоритм реализован методам:get(), put(), remove(), values(), size(), containsKey() в src\main\java\ru\clevertec\ilkevich\receipt\utill\cache\impl\LruCache.java
### Method : V get(K key)
Принимает ключ и возвращает значение (кэшируемый объект)
### Method : void put(K key, V value)
Принимает ключ(id), значение(кэшируемый объект). 
Если ключ есть в кэше, то обновляем значение в кэше. 
Если кэш достиг максимальной ёмкости то удаляем самый раннее значение в кэше.
Поизводит сохранение нового значения
### Method : void remove(K key)
Принимает ключ(id)
Удаляем значение (кэшируемый объект)
### Method : Collection<V> values()
Возвращает коллекцию значений
### Method : int size()
Возращет колличество значений хранящихся в кэше
### Method : boolean containsKey(K key)
Принимает ключ(id) возвращает true or false
Возращет true если ключ присутствует в Map
Возращет false если ключ отсутствует в Map

### Least-Frequently Used - LFU (Наименее часто используемый)
LFU - алгоритм, который подсчитывает частоту использования каждого элемента и удаляет те, к которым обращаются реже всего.
get(), put(), remove(), values(), size(), containsKey() в src\main\java\ru\clevertec\ilkevich\receipt\utill\cache\impl\LfuCache.java
### Method : V get(K key)
Принимает ключ и возвращает значение (кэшируемый объект)
Увеличивает счётчик вызова (frequency) этого значения на 1
### Method : void put(K key, V value)
Принимает ключ(id), значение(кэшируемый объект). 
Если ключ есть в кэше, то обновляем значение в кэше. 
Если кэш достиг максимальной ёмкости то удаляем самый самое мало вызываемое значение (minKeyFrequency).
Поизводит сохранение нового значения
Увеличивает счётчик вызова (frequency) этого значения на 1
### Method : void remove(K key)
Принимает ключ(id) вызова (frequency) этого значения
Удаляем значение (кэшируемый объект)
Удаляем счётчик 
### Method : Collection<V> values()
Возвращает коллекцию значений
### Method : int size()
Возращет колличество значений хранящихся в кэше
### Method : boolean containsKey(K key)
Принимает ключ(id) возвращает true or false
Возращет true если ключ присутствует в Map
Возращет false если ключ отсутствует в Map



  ## Задачи дальнейшей реализации проекта 
  -JUnit 5
