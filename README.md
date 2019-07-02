# logparser

### Built with SpringBoot v2.1.6 release

### Prerequisite
1. Make sure connection to MySQL server is successful
2. Login MySQL server and create schema named "demo"

### How to Run The Report
There are 3 files, including:
1. parser.jar : executable file
2. application.properties: used to configure URL connection string, default is: <b>jdbc:mysql://127.0.0.1:3306/demo</b>
3. access.log (optional)

4. Execute jar file with following command <br>
<b>java -jar parser.jar --accesslog=/path/to/file --startDate=2017-01-01.13:00:00 --duration=hourly|daily --threshold=100</b>

### Output
192.168.228.188 has 100 or more requests between 2017-01-01.13:00:00 and 2017-01-01.14:00:00<br>
192.168.77.101 has 100 or more requests between 2017-01-01.13:00:00 and 2017-01-01.14:00:00<br>
