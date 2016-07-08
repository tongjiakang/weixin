/**
 * Created by PanJM on 2016/3/3.
 */
/**
 * 设置饼图参数
 * @param title 标题
 * @param arr 数组
 * @returns {{title: {text: *}, tooltip: {}, legend: {data: *[]}, series: *[]}}
 */
function getPieOption(title, arr) {
    var notInSelected = {};
    $.each(arr, function (k, v) {
        if (k > 0) {
            notInSelected[v.name] = false;
        } else {
            notInSelected[v.name] = true;
        }
    });
    var lengendArr = [];
    for (var i = 0; i < arr.length; i++) {
        lengendArr.push(arr[i].name);
    }
    var pieOption = {
        title: {
            text: title
        },
        tooltip: {},
        legend: {
            data: lengendArr,
            selected: notInSelected
        },
        series: arr
    };
    return pieOption;
}

/**
 * 设置柱状图或折线图参数
 * @param title 标题
 * @param XData X轴类别名字
 * @param arr 类别值数组
 * @returns {{title: {text: *}, tooltip: {}, legend: {data: Array}, xAxis: {data: *}, yAxis: {}, series: *}}
 */
function getBarOrLineOption(title, XData, arr) {
    var lengendArr = [];

    for (var i = 0; i < arr.length; i++) {
        lengendArr.push(arr[i].name);
    }

    var barOption = {
        title: {
            text: title
        },
        tooltip: {},
        legend: {
            data: lengendArr
        },
        xAxis: [
            {
                type: 'category',
                position: 'bottom',
                boundaryGap: true,
                axisLabel: {
                    show: true,
                    interval: 'auto',
                    rotate: 45,
                    margin: 8,
                    formatter: '{value}',
                    textStyle: {
                        color: '#000000',
                        fontFamily: 'sans-serif',
                        fontSize: 15,
                        //fontStyle: '宋体',
                        fontWeight: 'bold'
                    }
                },
                data: XData
            }
        ],
        yAxis: {},
        series: arr,
        grid: {
            x: 40,
            x2: 20,
            y2: 100,
        },
        dataZoom: {
            orient: "horizontal", //水平显示
            show: true, //显示滚动条
            start: 0, //起始值为20%
            end: 10  //结束值为60%
        }
    };
    return barOption;
}

function getChartDatas(data, type) {
    var names = [];
    names = data.obj.names;
    var dataArr = [];
    if (type == "bar" || type == "line") {
        $.each(data.obj.values, function (k, v) {
            dataArr.push(new addSeries(k, v, type));
        });
        return new addBarOrLineChartData(names, dataArr);
    } else if (type = "pie") {
        var index = 0;
        $.each(data.obj.values, function (k, v) {
            var arr = [];
            var nameArr = [];
            var valueArr = [];
            $.each(data.obj.values, function (k, v) {
                nameArr.push(k);
                valueArr.push(v);
            });
            var valueSplit = valueArr[index];
            for (var i = 0; i < valueSplit.length; i++) {
                arr.push(new addPieChartData(valueSplit[i], names[i]));
            }
            dataArr.push(new addSeries(k, arr, type));
            index = index + 1;
        });
        return dataArr;
    }
}

function loadChart(idName, title, chartData, type) {
    var myChart = echarts.init(document.getElementById(idName));
    myChart.clear();
    if (type == "bar" || type == "line") {
        myChart.setOption(getBarOrLineOption(title, chartData.datas, chartData.arr));
    } else if (type == "pie") {
        myChart.setOption(getPieOption(title, chartData));
    }
}

function addSeries(name, data, type) {
    this.name = name;
    this.data = data;
    this.type = type;
}

function addBarOrLineChartData(datas, arr) {
    this.datas = datas;
    this.arr = arr;
}

function addPieChartData(value, name) {
    this.value = value;
    this.name = name;
}