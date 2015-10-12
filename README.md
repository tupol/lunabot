# lunabot

## Build & Run

```sh
$ cd lunabot
$ ./sbt
> container:start
> browse
```


If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.


## Standalone Deployment


```sh
sbt clean assembly
java -jar target/scala-2.11/lunabot-assembly-0.1.0-SNAPSHOT.jar
```

You can copy the generated assembly jar and run it on the server.
