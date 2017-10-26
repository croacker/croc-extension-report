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
        function(){
            /**
             * Сообщим внедренному скрипту id расширения.
             */
            chrome.tabs.sendMessage(tabId, {type:'extensionId', id: chrome.runtime.id});;
        });
        console.log('Insert to tab:' + tabId);
});

/**
 * Подписываемся на события от inject.js
 */
chrome.runtime.onMessage.addListener(
    function (request, sender, sendResponse) {
        console.log('background.js: Incoming message');
    });