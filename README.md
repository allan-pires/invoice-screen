# BillScreen
This app is a implementation of a Bill Screen. Developed with Android Studio + Gradle + JUnit + Mockito

## Description
Customers from a bank have to pay their bill monthly. The bill have four potential states: “overdue” (paid); ”closed”
(ready for payment but not yet paid); “open” (not yet closed, charges are still being added); and “future” (installments, that will be charged to bills in future months).

## Images
![alt tag](http://i.imgur.com/XDA56Nu.png)

## Class diagram
![alt tag](http://i.imgur.com/YUCJ3cE.jpg?1)

## Relevant Files
- [MainActivity](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/view/MainActivity.java): Main screen
- [ErrorActivity](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/view/ErrorActivity.javaa): Error screen
- [MonthFragment](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/view/fragment/MonthFragment.java): Component that show a month in MainActivity
- [Bill](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/model/Bill.java): Model of a Bill
- [Summary](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/model/Summary.java): Model of a Summary (component of the bill)
- [LineItem](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/model/LineItem.java): Model of a LineItem (component of the bill)
- [JSONHandler](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/controller/handler/JSONHandler.java): Controller of JSONs requests
- [HTTPConnectionHandler](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/controller/handler/HTTPConnectionHandler.java): Controller of connections to fetch data
- [ExceptionHandler](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/controller/handler/ExceptionHandler.java): Controller of exceptions
- [RESTTask](https://github.com/doisLan/BillScreen/blob/master/app/src/main/java/com/nubank/allan/billscreen/controller/task/RESTTask.java): Asynchronous task that consumes a REST API
- [Test Files](https://github.com/doisLan/BillScreen/tree/master/app/src/androidTest/java/com/nubank/allan/billscreen): Unit and UI tests
