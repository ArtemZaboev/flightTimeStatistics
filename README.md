Тестовое задание:
Напишите программу на языке программирования java, которая прочитает файл tickets.json и рассчитает:
- среднее время полета между городами Владивосток и Тель-Авив
- 90-й процентиль времени полета между городами  Владивосток и Тель-Авив

Программа должна вызываться из командной строки Linux, результаты должны быть представлены в текстовом виде. 


Запуск приложения через командную строку
java -jar flightTimeStatistics.jar [JSON file path]

Результат выполнения:
art1@art1-VirtualBox:~/Загрузки$ java -jar flightTimeStatistics.jar /home/art1/Загрузки/tickets.json

Average flight time = 452 minutes

90th percentile of flight time = 605 minutes
