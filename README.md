# **«Гипер-Линк»**
### **18.03.2024**

_Автор: **Kisapa**, Команда: **espada**_

_Задание: https://disk.yandex.ru/d/FanX7UdylyhBwA_

---

### Архитектура:

Приложение реализовано на **Java, Spring boot framework** с использованием **вертикальной** архитектуры web приложения.


### **Технологии:**
* Spring web
* Spring JPA
* PostgresSQL
* Spring test
* Spring kafka _(Apache kafka)_
* Spring security
* Spring validation

---

### Запуск программы:

1. Для генерации БД предусмотрены sql скрипты, которые находятся в папке "**sql**". Файлы скриптов названы в формате "**{schema}_script_1.sql**".
2. Запуск приложения происходит через **Dockerfile** (в корневой директории)
3. Файлы локализации находятся в папке **localization**
4. Файлы регионов находятся в папке **special_data/cities.json** - данные взяты из открытого github ресурса https://github.com/eugenart/russian-subjects/blob/master/native/cities.json

---
