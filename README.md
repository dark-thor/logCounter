# logCounter
Minutely log counter of http logs

A directory on the local machine holds about 1000 files that are HTTP access logs in common log format.  Eg.,
```
123.65.150.10 - - [23/Aug/2010:03:50:59 +0000] "POST /wordpress3/wp-admin/admin-ajax.php HTTP/1.1" 200 2 "http://www.example.com/wordpress3/wp-admin/post-new.php" "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_4; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.25 Safari/534.3"
```

Each log file is from yesterday, contains 24 hours of data and is about 500MB.  Write a script that will produce a minute-by-minute CSV that contains an aggregate count of the accesses organzied by HTTP status code (200 in the above example).  If the above example was the only input, the result would be

```
# time, 200
23/Aug/2010:03:50, 1
```

## Build the code

```
git clone https://github.com/gimley/logCounter.git
cd logCounter
gradle build
```

## Running the code
Pass the directory containing log files as first argument to program.
The program parses all files with 'log' extension under specified directory.

```
$ java -jar build/libs/logCounter-1.0-SNAPSHOT.jar /path/to/logsdir/
```

Some sample access logs are present in *src/test/resources*
```
$ java -jar build/libs/logCounter-1.0-SNAPSHOT.jar src/test/resources/
```

## Output
Result is currently stored in output.csv
