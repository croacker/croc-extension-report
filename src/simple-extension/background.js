/**
 * Клик на иконке extension, просто сообщаем что клик был.
 */
chrome.browserAction.onClicked.addListener(function (tab) {
    console.log('Click Simple Browser Extension');
    tab.window.document.getElementById('');
});

/**
 * Подписываемся на обновление страницы.
 */
chrome.tabs.onUpdated.addListener(function (tabId, changeInfo, tab) {
    extensionInstance.processTab(tabId, changeInfo, tab);
});


