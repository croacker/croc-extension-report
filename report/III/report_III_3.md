## Взаимодействие контент-скрипта со страницей сайта открытой в браузере. Ограничения взаимодействия, почему так, как делать правильно. События фоновой страницы и подписка на них.

Итак, мы выполнили внедрение inject.js. Теперь нам нужно как-то обмениваться данными, чтобы взаимодействовать.
Как по вашему мы можем это сделать?
Доступа к js-объектам у нас нет.
Мы можем использовать популярную ныне модель отправки сообщений. Или событий. Кому как больше нравится. Чем хороша модель событий? А чем плоха?
Мы хотим отправить сообщение из background.js, 
Здесь, для отправки сообщения в inject.js используется id закладки которой мы хотим отправить сообщение.
Так же нам нужно отправлять сообщения в обратно - из inject.js в background.js, для этого используется id расширения.