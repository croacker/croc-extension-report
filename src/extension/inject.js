(function () {
    /**
     * Пишем что внедрились
     */
    console.log('injected to tab');
    /**
     * Подписываемся на события
     */
    subscribe();
})();

var browserPage;

/**
* Подписываемся на события от browser extension.
*/
function subscribe() {
    chrome.runtime.onMessage.addListener(
        function (request, sender, sendResponse) {
            console.log(request);
            if(request.type == 'extensionId'){
                browserPage = new BrowserPage(request.id);
                browserPage.process();
            }
            // chrome.runtime.sendMessage(request.id, { msg: 'injected' });
        });
};

/**
 * Информация о книге.
 */
var BookInfo = function(){
}

/**
 * 
 */
var BrowserPage = function(extensionId){
    var me = this;

    this.extensionId = extensionId;

    this.process = function(){
        var bookInfo = me.getBookInfo();
        if(bookInfo.author && bookInfo.title){
            me.sendBookInfo(bookInfo);
        }
    }

    this.sendBookInfo = function(bookInfo){
        chrome.runtime.sendMessage(me.extensionId, { type: 'bookInfo', bookInfo: bookInfo });
    }

    this.getBookInfo = function(){
        var bookInfo = {};
        var contributors = document.getElementsByClassName('contributorNameID')
        if(contributors.length != 0){
            bookInfo.author = contributors[0].innerHTML;
        }
        var productTitle = document.getElementById('productTitle');
        if(productTitle){
            bookInfo.title = productTitle.innerHTML;
        }
        return bookInfo;
    }
}