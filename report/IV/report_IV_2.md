## Экскурс в предыдущие способы взаимодействия.
 Взаимодействие в эпоху NPAPI(Netscape Plugin Application Programming Interface). Эпоха закончилась.
JAVA апплеты работали через это. Они внедрялись в DOM и с ними можно было работать через js. 
Чем было плохо - постоянно находились альтернативные способы, которые позволяли создавать что-то вредоносное, потом производители браузеров добавляли безопасности в виде бесконечных диалогов на тему того "вы уверены?" и "вы хотите?". Все это делало работу с апплетами чем дальше, тем неудобнее.

 Пример необхлдимости №1 со строгой аутентификации использующей токены и Trust Screen-устройства. Способ решения с помощью предустановленного приложения и общения по http. Так делали, но есть такие-то нюансы. Например при взаимодействии по http нужно найти свободный порт

 Пример №2 работа с оборудованием, взаимодействия с файловой системой. Способ из п.3. небыстрый, по этому не подходит.