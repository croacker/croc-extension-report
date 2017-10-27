/**
 * Экземпляр контроллера расширения.
 */
const extensionInstance = new ExtensionInstance(chrome.runtime.id);

/**
 * Клик на иконке extension, просто сообщаем что клик был.
 */
chrome.browserAction.onClicked.addListener(function (tab) {
    console.log('Click Demo Browser Extension');
    console.log(tab);
});

/**
 * Подписываемся на обновление страницы.
 */
chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
    chrome.tabs.executeScript(tabId, {
        file: 'inject.js'
    },
        function () {
            /**
             * Сообщим внедренному скрипту id расширения.
             */
            chrome.tabs.sendMessage(tabId, { type: 'extensionId', id: chrome.runtime.id });;
        });
    console.log('Insert to tab:' + tabId);
});

/**
 * Подписываемся на события от inject.js
 */
chrome.runtime.onMessage.addListener(
    function (request, sender, sendResponse) {
        console.log('background.js: Incoming message');
        console.log('request ' + request);
        console.log('sender ' + sender);
        console.log('sendResponse ' + sendResponse);
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
     * Идентификатор расширения.
     */
    this.extensionId = extensionId;

    /**
     * Добавить объект закладки.
     */
    this.addTab = function (tab) {
        me.tabs[tab.id] = tab;
        tab.extensionId = me.extensionId;
        me.injectScript(tab);
    };

    /**
     * Получить объект закладку.
     */
    this.getTab = function (tabId) {
        return me.tabs[tab.id];
    };

    this.processTab = function (tabId) {
        var tab = me.getTab(tabId);
        if (!tab) {
            me.addTab(new BrowserTab(tabId));
        }
    };

    this.injectScript = function (tab) {
        chrome.tabs.executeScript(tab.id, {
            file: 'inject.js'
        },
            function () {
                tab.sendExtensionId();
            });
    };
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
    this.sendExtensionId = function () {
        chrome.tabs.sendMessage(tabId, { type: 'extensionId', id: me.extensionId});;
    };
}