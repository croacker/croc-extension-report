/**
 * 
 * @param {*} options 
 */
function EmployeeCard(options){
    var me = this;

    var inputFields = ["surnameName","firstName", "middleName", "post"].map(function(id) { 
        return new InputField({
            el: document.getElementById(id)
        });
    });

    var selectPlace = new InputField({
        el: document.getElementById("place")
    });
    
    var btnOkEl = document.getElementById("ok-button");
    var sendData = function(){
        var employeeData = inputFields.reduce(function(data, current) {
            data[current.getName()] = current.getValue();
            return data;
          }, {});
        employeeData[selectPlace.getName()] = selectPlace.getValue();
        ExtensionService.sendMessage(employeeData);
    }
    
    var btnOk = new OkButton(
        {
            el: btnOkEl,
            onclick: sendData
        }
    );
}

/**
 * Поле ввода.
 * @param {*} options 
 */
function InputField(options){
    var el = options.el;

    this.getName = function(){
        return el.id;
    };

    this.getValue = function(){
        return el.value;
    };
}

/**
 * 
 * @param {*} options 
 */
function OkButton(options){
    var me = this;
    var el = options.el;
    el.onclick = options.onclick;
}

/**
 * 
 */
var ExtensionService = {
    sendMessage: function(data){
        console.log(data);
    }
};

document.addEventListener('DOMContentLoaded', function () {
    var employeeCard = new EmployeeCard();
});