/**
 * Идентификатор приложения хоста.
 */
var nativeApplicationName = 'com.croc.native_demo_app';

/**
 * Клик на иконке extension, просто сообщаем что клик был.
 */
chrome.browserAction.onClicked.addListener(function (tab) {
    console.log('Click Demo Browser Extension');
    console.log(extensionInstance.booksInfo);
});

/**
 * Подписываемся на обновление страницы.
 */
chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
    extensionInstance.processTab(tabId, changeInfo, tab);
});

/**
 * Подписываемся на события от inject.js
 */
chrome.runtime.onMessage.addListener(
    function (request, sender, sendResponse) {
        if(request.type == 'bookInfo'){
            var bookInfo = request.bookInfo;
            bookInfo.url = sender.url;
            extensionInstance.addBookInfo(bookInfo);
        }
    });

/**
 * Класс контроллера расширения.
 */
var ExtensionInstance = function (extensionId) {
    var me = this;
    /**
     * Коллекция закладок.
     */
    this.tabs = {};
    /**
     * Коллекция книг полученных со страниц.
     */
    this.booksInfo = {};
    /**
     * Идентификатор расширения.
     */
    this.extensionId = extensionId;

    /**
     *Обработать объект закладки.
     */
    this.processTab = function (tabId, changeInfo, tab) {
        if (changeInfo.status == 'complete') {
            me.addTab(tabId, changeInfo, tab);
        } else if (changeInfo.status == 'loading') {
            me.deleteTab(tabId, changeInfo, tab);
        }

        tab.extensionId = me.extensionId;

    };

    /**
     * Добавить закладку.
     */
    this.addTab = function (tabId, changeInfo, tab) {
        if (me.check(tabId, changeInfo, tab)) {
            var browserTab = new BrowserTab(tabId);
            me.tabs[tab.id] = browserTab;
            me.injectScript(browserTab);
        };
    }

    /**
     * Удалить закладку.
     */
    this.deleteTab = function (tabId, changeInfo, tab) {
        delete me.tabs[tab.id];
    }

    /**
     * Получить объект закладку.
     */
    this.getTab = function (tabId) {
        return me.tabs[tabId];
    };

    this.addBookInfo = function(bookInfo){
        var idx = bookInfo.author + '_' + bookInfo.title;
        me.booksInfo[idx] = bookInfo;
    }

    /**
     * Внедрить скрипт.
     */
    this.injectScript = function (browserTab) {
        chrome.tabs.executeScript(browserTab.id, {
            file: 'inject.js'
        },
            function () {
                browserTab.sendExtensionId(me.extensionId);
            });
    };

    /**
     * Условия внедрения скрипта.
     */
    this.check = function (tabId, changeInfo, tab) {
        return !me.getTab(tabId)
            && tab.url && tab.url.startsWith('https://www.amazon.com/');
    }
};

/**
 * Контроллер закладки.
 * @param {*} tabId 
 */
var BrowserTab = function (tabId) {
    var me = this;
    /**
     * Идентификатор закладки.
     */
    this.id = tabId;
    /**
         * Сообщим внедренному скрипту id расширения.
         */
    this.sendExtensionId = function (extensionId) {
        chrome.tabs.sendMessage(tabId, { type: 'extensionId', id: extensionId });
    };
};

/**
 * Приложение хоста.
 */
var NativApplication = function(){
    var me = this;

    /**
     * Соединение с приложением хоста.
     */
    var port;

    /**
     * Отправить сообщение приложению хоста.
     */
    this.postMessage = function(){
        var port = me.getPort();
        if(port){
            port.postMessage(json);
        }
    }

    /**
     * Получить соединение с приложением хоста.
     */
    this.getPort = function(){
        if (!me.port) {
            me.port = connectToPort();
        }
        return me.port;
    };

    /**
     * Подключиться к приложению хоста.
     */
    this.connectToPort = function(listener) {
        me.port = chrome.runtime.connectNative(nativeApplicationName);
        me.port.onDisconnect.addListener(function (e) {
            console.log(e);
            me.port = null;
        });
        me.port.onMessage.addListener(function portOnMessageListener(message) {
            console.log(message);
        });
        return port;
    }
}

/**
 * Тип сообщения.
 */
const MessageType = {
    extensionId: 'extensionId',
    bookInfo: 'bookInfo' 
};

/**
 * Экземпляр контроллера расширения.
 */
const extensionInstance = new ExtensionInstance(chrome.runtime.id);
