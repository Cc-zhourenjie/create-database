
$(function () {
    var oImg = document.getElementsByClassName('f1')
    var oImg2 = document.getElementsByClassName('f2')
    var oImg3 = document.getElementsByClassName('f3')
    var len = oImg.length;
    console.log(len)
    var deg = 360 / len;

    var oWrap = document.getElementById("imgwrap");
    // var oWrap=document.querySelector('.wrap');

    //页面加载完毕在执行的代码
    window.onload = function () {
        Array.prototype.forEach.call(oImg, function (ele, index, self) {
            // 旋转并沿Z轴平移
            ele.style.transform = "rotateY(" + deg * index + "deg) translateZ(645.75px)";
            //过渡时间1s
            ele.style.transition = "1s " + (len - index) * 0.1 + "s";

        });
        Array.prototype.forEach.call(oImg2, function (ele, index, self) {
            // 旋转并沿Z轴平移
            ele.style.transform = "rotateY(" + deg * index + "deg) translateZ(645.75px) translateY(240px)";
            //过渡时间1s
            ele.style.transition = "1s " + (len - index) * 0.1 + "s";

        });
        Array.prototype.forEach.call(oImg3, function (ele, index, self) {
            // 旋转并沿Z轴平移
            ele.style.transform = "rotateY(" + deg * index + "deg) translateZ(645.75px) translateY(480px)";
            //过渡时间1s
            ele.style.transition = "1s " + (len - index) * 0.1 + "s";

        });
        // Array.prototype.forEach.call(oImg, function (ele, index, self) {
        //       // 旋转并沿Z轴平移
        //       ele.style.transform = "rotateY(" + deg * index + "deg) translateZ(350px)";
        //       //过渡时间1s
        //       ele.style.transition = "1s " + (len - index) * 0.1 + "s";

        // });

    }
    //翻动3D相册
    var newX, newY, lastX, lastY, minusX, minusY, rotX = -20, rotY = 0;

    document.onmousedown = function (e) {
        // 点击设置初值
        lastX = e.clientX;
        lastY = e.clientY;

        this.onmousemove = function (e) {
            newX = e.clientX;
            newY = e.clientY;
            minusX = newX - lastX;
            minusY = newY - lastY;

            rotX -= minusY * 0.2;
            rotY += minusX * 0.1;
            oWrap.style.transform = "rotateX(" + rotX + "deg) rotateY(" + rotY + "deg)";
            lastX = newX;
            lastY = newY;

        }
        this.onmouseup = function (e) {
            //鼠标松开
            this.onmousemove = null;//清除鼠标移动
        }
    }
});
