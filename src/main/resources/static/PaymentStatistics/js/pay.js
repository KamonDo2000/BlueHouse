
document.addEventListener('DOMContentLoaded', function() {
    var tablenot = document.querySelector('#notbill');
    var table = document.querySelector('#billpaid');
	var apa = document.querySelector('#apa');
    var finan = document.querySelector('#finan');
    var spa = document.querySelector('#spa');
    var vehi = document.querySelector('#vehi');
    // Ẩn cả hai bảng lúc đầu
    tablenot.style.display = 'none';
    table.style.display = 'none';
    apa.style.display = 'none';
    finan.style.display = 'none';
    spa.style.display = 'none';
    vehi.style.display = 'none';

    // Load Google Charts
    google.charts.load("current", {packages:["corechart"]});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        fetch('/IncomeBill/statistics')
            .then(response => response.json())
            .then(data => {
				 // Thêm tổng tiền vào biểu đồ
                var totalAmount = data['Bill Paid'] + data['Bill Not Paid'];
                var totalText = 'Tổng tiền: ' + new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalAmount);
                
                // Chuẩn bị dữ liệu cho biểu đồ
                var chartData = google.visualization.arrayToDataTable([
                    ['Task', 'Amount'],
                    ['Chưa thanh toán', data['Bill Not Paid']],
                    ['Thanh toán', data['Bill Paid']]
                ]);

                // Thiết lập biểu đồ
                var options = {
                    title: 'Cu Dan'+totalText,
                    pieHole: 0.4,
                    colors: ['#FF6384', '#36A2EB'], // Màu sắc tương ứng
                    legend: {
                        position: 'bottom'
                    },
                    annotations: {
                        alwaysOutside: true,
                        textStyle: {
                            fontSize: 12,
                            color: '#555',
                            auraColor: 'none'
                        }
                    }
                };

                // Tạo biểu đồ
                var chart = new google.visualization.PieChart(document.getElementById('donutchart'));

                // Xử lý sự kiện click trên biểu đồ
                google.visualization.events.addListener(chart, 'select', function() {
                    var selectedItem = chart.getSelection()[0];
                    if (selectedItem) {
                        var selectedLabel = chartData.getValue(selectedItem.row, 0);

                        if (selectedLabel === 'Chưa thanh toán') {
                            tablenot.style.display = 'table';
                            table.style.display = 'none';
                            apa.style.display = 'none';
						    finan.style.display = 'none';
						    spa.style.display = 'none';
						    vehi.style.display = 'none';
                        } else if (selectedLabel === 'Thanh toán') {
                            table.style.display = 'table';
                            tablenot.style.display = 'none';
                            apa.style.display = 'none';
    						finan.style.display = 'none';
    						spa.style.display = 'none';
    						vehi.style.display = 'none';
                        }
                    }
                });

                // Vẽ biểu đồ với dữ liệu và tùy chọn đã chuẩn bị
                chart.draw(chartData, options);

               
            })
            .catch(error => console.error('Error fetching data:', error));
    }
});
document.addEventListener('DOMContentLoaded', function() {
    var tablenot = document.querySelector('#notbill');
    var table = document.querySelector('#billpaid');
	var apa = document.querySelector('#apa');
    var finan = document.querySelector('#finan');
    var spa = document.querySelector('#spa');
    var vehi = document.querySelector('#vehi');
    // Ẩn cả hai bảng lúc đầu
    tablenot.style.display = 'none';
    table.style.display = 'none';
    apa.style.display = 'none';
    finan.style.display = 'none';
    spa.style.display = 'none';
    vehi.style.display = 'none';

    // Load Google Charts
    google.charts.load("current", {packages:["corechart"]});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
        fetch('/IncomeBill/statistics')
            .then(response => response.json())
            .then(data => {
              
                // Chuẩn bị dữ liệu cho biểu đồ
                var chartData = google.visualization.arrayToDataTable([
                    ['Task', 'Amount'],
                    ['Cu Dan', data['Bill']],
                    ['Tai Tro', data['fine']],
                    ['Phi Xe', data['totalfee']],
                    ['Phi Cho Thue', data['totalspa']]
                    
                ]);

                // Thiết lập biểu đồ
                var options = {
                    title: 'Phi Thu',
                    pieHole: 0.4,
                    colors: ['#FF6384', '#36A2EB', '#FFA500', '#4CAF50'], // Màu sắc tương ứng
                    legend: {
                        position: 'bottom'
                    },
                    annotations: {
                        alwaysOutside: true,
                        textStyle: {
                            fontSize: 12,
                            color: '#555',
                            auraColor: 'none'
                        }
                    }
                };

                // Tạo biểu đồ
                var chart = new google.visualization.PieChart(document.getElementById('donutchart1'));

                // Xử lý sự kiện click trên biểu đồ
                google.visualization.events.addListener(chart, 'select', function() {
                    var selectedItem = chart.getSelection()[0];
                    if (selectedItem) {
                        var selectedLabel = chartData.getValue(selectedItem.row, 0);

                        if (selectedLabel === 'Cu Dan') {
                            tablenot.style.display = 'none';
                            table.style.display = 'none';
                            apa.style.display = 'table';
    						finan.style.display = 'none';
    						spa.style.display = 'none';
    						vehi.style.display = 'none';
                        } else if (selectedLabel === 'Tai Tro') {
                            table.style.display = 'none';
                            tablenot.style.display = 'none';
                            apa.style.display = 'none';
    						finan.style.display = 'table';
    						spa.style.display = 'none';
    						vehi.style.display = 'none';
                        }else if (selectedLabel === 'Phi Xe') {
                            table.style.display = 'none';
                            tablenot.style.display = 'none';
                            apa.style.display = 'none';
    						finan.style.display = 'none';
    						spa.style.display = 'none';
    						vehi.style.display = 'table';
                        }else if (selectedLabel === 'Phi Cho Thue') {
                            table.style.display = 'none';
                            tablenot.style.display = 'none';
                            apa.style.display = 'none';
    						finan.style.display = 'none';
    						spa.style.display = 'table';
    						vehi.style.display = 'none';
                        }
                    }
                });

                // Vẽ biểu đồ với dữ liệu và tùy chọn đã chuẩn bị
                chart.draw(chartData, options);

               
            })
            .catch(error => console.error('Error fetching data:', error));
    }
});