(function (doc, win) {
    var docEl = doc.documentElement,
        //resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            // if (!clientWidth || clientWidth < 1024) clientWidth = 1024;
            docEl.style.fontSize = clientWidth * (100 / 1920) + 'px';
        };
    if (doc.addEventListener){
        win.addEventListener('resize', recalc, false);
        //doc.addEventListener('DOMContentLoaded', recalc, false);
    } else{
        win.attachEvent('onresize', recalc);
        //doc.attachEvent('onDOMContentLoaded', recalc);
    }
    recalc();
})(document, window);