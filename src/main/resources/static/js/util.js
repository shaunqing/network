// 获取系统根路径
function getRootPath() {
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return projectName;
}

// 关闭layer
function closeLayer(reFlag) {
    parent.$('#reFlag').val(reFlag);
    //先得到当前iframe层的索引
    var index = parent.layer.getFrameIndex(window.name);

    //再执行关闭
    parent.layer.close(index);
}

// 显示ajax返回值显示
function showAjaxResult(result) {
    if (result['success']) {
        layer.msg(result['msg'], {
            icon: 1,
            time: 1000 //两秒后关闭
        }, function () {
            closeLayer('1');
        });
    } else {
        layer.alert(result['msg'], {
            icon: 2
        });
    }
}

function showAjaxResultAndForward(result, url) {
    if (result['success']) {
        layer.msg(result['msg'], {
            icon: 1,
            time: 1000 //两秒后关闭
        }, function () {
            window.location.href = getRootPath() + url;
        });
    } else {
        layer.alert(result['msg'], {
            icon: 2
        });
    }
}

// 显示系统异常
function showError(msg) {
    if (msg == '') {
        msg = '系统出现异常，请稍后重试！';
    }
    layer.alert(msg, {
        title: '异常',
        icon: 2,
        closeBtn: 0
    });
}

// 控制页面是否刷新（没想到其他好的办法，需要在父页面中定义一个值，保存是否刷新）
function refresh() {
    if ($("#reFlag").val() == '1') {
        window.location.href = window.location.href;
    }
}
