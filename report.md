# Структура 

## 1. Что такое расширения. (возм.:Кто знает что такое расширения!?)
Как вы знаете, современная концепция производства продуктов подразумевает включению в базовый состав минимальной функциональности. Это касается не только ПО, но и материальных вещей. Данный подход позволяет во первых экономить на этапе проектирования и производства, во вторых не добавлять то, что сейчас кажется перспективным, а конечному пользователю будет видется странными и ненужными. Пример: агенты в старых версиях MS Office, помните скрепку? 
Гораздо удобнее создать точки интеграции и переложить ответственность за новаторские идеия на плечи посторонних людей, с посторонним бюджетом. Это добавляет производителю очков престижа, особенно, если точка интергарации получилась удобной и сторонние разработчики хвалят вендора за удачное решение. Со временем, если функциональность приживается, ее довольно часто переносят в базовое приложение.
Пример: прикуриватели сначала появились в авиации, а затем перебрались в автомобили. Или более современный - можно купить авто без парктроника и установить самому.
Современные браузеры предлагают такую точку интеграции, для сторонних компаний и одиноко стоящих разработчиков, в виде возможности создания расширений(Extensions). Если углубиться в историю, возможность создания расширений существует давно, но сейчас Browser extension community group(https://www.w3.org/community/browserext/) занимается стандартизацией расширений как технологии.


## 2. Что из себя представляет расширение
Это небольшая программа, написанная с использованием web-технологий, таких как html, JavaScript и CSS. Эта программа может модифицировать и дополнять функциональность браузера, обладать собственным интерфейсом, либо вообще обходиться без интерфейса.  
Средой исполнения расширения является браузер.
Есть один важный нюанс, с точки зрения браузера js-код не может находится "в воздухе", т.е. существовать и исполняться как js-код например в NodeJS (возм.:Как вы думает где он находится!?). 
Расширение, само по себе является отдельной html-стрницей. И у него есть такой же document как у страницы сайта, но пустой. Пустой document нужен для того, чтобы было куда прикреплять script's.  

## 3. Пишем простое расширение, которое делает какой-то фокус. Еще не придумал какой будет лучше, но важно поразить зрителя в самое сердце
В процессе расказываю, что главный файл расширения - это метаописание manifest.json. manifest.json обладает такими ключевыми атрибутами:
manifest_version - версия манифеста, используется магическое число 2.
name, description, version - наименование, описание, версия. То что будет отображаться на странице chrome://extensions. version так же нужен для автоматического обновления расширения.
permissions - разрешения на действия для расширения.
Есть не ключевые атрибуты, которые важны уже только для нашего примера.
Есть большое количество других атрибутов, вот пожалуйста самые интересные и необычные.
Например, мы используем некоторое дополнительное окно, которое будет отображаться при нажатии на иконку расширения. Это окно - обычный html файл. К этому файлу мы добавляем js, который такой же обычный. То же про css. Упоминаем, что можно использовать сторонний js-код, т.е. например указать ссылку для загрузки js с официального сайта.
На страницу возможно внедрение контент-скрипта, он может взаимодействовать с элементами страницы через DOM, но при этом не может обращаться к переменным и функциям определенным на странице. 

Нюансы изготовления расширения
* Доступ к странице и ограничения которые накладываются
* Время жизни
* Cобытия фоновой страницы.
* Напрямую нельзя обратиться к фоновой странице, а как быть?
* Внедрение js-кода на страницу. Ограничения которые при этом присутствуют.
* Отладка, как случайно не развалить стек вызова наведя указатель мыши на переменную.
* Особенности установки расширения
* id - расширения, как он формируется, как его "зафиксировать"

## 4.	Что такое приложение хоста
Тем из вас, кто занимается разработкой web-приложений, временами хотелось бы получить доступ к операционной и файловой системе, или сделать что то вне браузера. Напрямую сделать это нельзя и никогда не будет можно.
Пример №1
Если вам приходилось работать с профессиональными банковскими ПО, к примеру, приложениями, вы знаете что там необходима двухфакторная аутентификация посерьезнее чем смс-код.(возм.Какой способ используется? кто знает?). Задачи строгой аутентификации и усиленной электронной подписи решаются с использованием аппаратных средств, выполненных в виде токенов и Trust Screen-устройства. Для общения с web-приложением эти аппаратные средства раньше использовали NPAPI(Netscape Plugin Application Programming Interface). Java-апплеты и ActiveX-компоненты работали через NPAPI.
Но, эпоха NPAPI закончилась 3 года назад. Сначала он пропал из Chrome, на текущий момент практически из всех современных браузеров.
Возм.Как бы вы поступили в этой ситуации? Можно, использовать приложение установленное на рабочей станции пользователя, которое по http-протоколу общается с web-приложением. Но, это первое - ненадежно, второе - небезопасно, третье - небыстро.
Пример №2
Вы разрабатываете рабочее место кассира с web-интерфейсом. Нужно будет работать с оборудованием - касса, сканер ШК, дисплей покупателя. В этом случае про работу по http-можно забыть, т.к. необходимая скорость работы обеспечена не будет.
Здесь на помощь современному разработчику приходит Native Messaging.
Native Messaging - технология которая позволяет соблюдая определенные правила, организовать взаимодействие между приложением способным запустится на рабочей станции. Приложение запускается в отдельном процессе и полностью независимо от браузера. Т.е. это не т.н. sandbox. Взаимодействие осуществляется с помощью потока ввода-вывода, как буд-то вы запустили приложение в консоли. Поток общение организовано синхронно.      

## 5. Приделываем к расширению изоготовленному в п.3. простое java(?) приложение
В приложении акцентируем внимание на двух моментах - формат обмена(возм.Кто из вас работал с TLV-образными протоколами?), как лучше - потоки или RxJava.
Рассматриваем файл метаописания manifest.json - атрибуты которые должны быть заданы и какие значения должны иметь.
Рассматриваем особенности регистрации приложения в операционных системах MS Windows и Linux. В Windows на конкретном примере, а Linux нет, по этому просто расскажу иначе бы я вам показал.
Рассматриваем особенность запуска java-приложения.
Запускаем, проверяем обмен данными между приложением и расширением. 
Нюансы изготовления приложение хоста
* Нюанс с запуском java-приложения(echo), как можно подколоться а потом долго думать что не так.
* С чем приходилось сталкиваться в разных версиях Chrome.
* Особенности запуска и окружение приложения в момент запуска(какой каталог является каталогом запуска)
* Какие расширения имеют доступ к приложению.  
* Что делать если не запускается? Sysinternals Suite, режим трассировки в браузере.
* Можно ли в приложении использовать UI? Можно, это же не sandbox, ограничений нет.

## 6. Сравнение с другими технологиями
Продолжаем развивать тему с примерами в п.5. Есть например Java Web Start. Все вроде хорошо, но общаться можно только с помощью http или websocket. Плюс раздражающие пользователей вопросы безопасности - скачать? запустить? Когда-то это можно было отключить, но теперь нет. 

## 7. Резюмируя
 Самая большая проблема - трудности диагностики. Даже с расширениями, если что-то пошло не так, трудно понять почему. Вроде все правила выполнены, но не работает. Потом находишь в документации ограничение, что именно для твоего случая есть специальное ограничение. Пример - доступ по протоколам http и file для различных случаев. Сейчас чтобы избежать проблемы, при разработке, лучше не загружать html и js файлы напрямую с диска в браузер, а испльзовать простейший сервер, который будет отдавать необходимый контент. 
 В остальном, технология расширений в связке с native messaging выглядит гораздо перспективнее своих собратьев, которые к тому же отживают век и в модерновых версиях браузеров могут просто не появится в очередной версии. 
