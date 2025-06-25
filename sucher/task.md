# 1 Aufgabe: Körner zählen

|                  | **Callable** | **Runnable** |
|------------------|--------------|--------------|
| **Objekt**       | ThreadPool   | Thread       |
| **Ausführung**   | Parallel     | Seriell      |
| **Zuweisung**    | submit       | run          |
| **Start**        | call         | start/run    |
| **Rückgabewert** | Future       | None         |

## ExecutorService / Threadpool
Ein Threadpool ist eine Menge von Threads, die Runnable oder Callable Objekte ausführen.
Man kann sich mit der sogenannten Fabrikmethode
```java
ExecutorService pool =
        Executors.newFixedThreadPool(ZAHL_GLEICHZEITIGE_THREADS);
```
ein solches Threadpool Objekt vom Typ ExecutorService beschaffen. Den Threads im
Threadpool kann man mit submit(Callable) die Callables zum Ausführen übergeben.
Im Übergabeparameter der Methode newFixedThreadPool kann man bestimmen, wie viel
Threads im Pool auf Callable Objekte zum Ausführen warten sollen. **Die Threads werden
nach Ausführung von Callable Objekten nicht zerstört.** Sie können daher für weitere
Callable Objekte wiederverwendet werden. Dadurch wird die Zeit zur Erzeugung von neuen
Thread-Objekten gespart. **Selbst wenn alle Threads im Pool schon mit Callable Objekten
beschäftigt sind, kann man den Threads trotzdem immer neue Aufträge erteilen,** indem man
weiterhin die Methode submit(Callable) aufruft. Das neu übergebene Callable wird dann
in einer Queue gespeichert und später von den Threads ausgeführt.
Es ist sinnvoll, in einem Threadpool **nur so viele Threads zu halten, wie Ihr Computer Kerne
hat**. Eine Ausnahme bildet die Körner Esser Aufgabe.

## Rückgabewert(Future)
Wie kommen wir an den Rückgabewert heran, der von der call Methode eines Callable
Objekts zurückgegeben wird, wenn es vom Threadpool ausgeführt worden ist?
Dazu dient ein Future Objekt, welches wir beim Aufruf von submit zurückgeliefert
bekommen. Dieses Future Objekt hat eine get Methode, die das Ergebnis der Methode call
liefert. Wenn wir z.B. mit dem main Thread get aufrufen, wartet der main Thread in der get
Methode so lange, bis call ein Ergebnis liefert.
```java
ExecutorService pool =
        Executors.newFixedThreadPool(4);
Callable<Integer> callable = new Callable<>() {
    public Integer call() throws Exception {
        return 2 + 2;
    }
};
Future<Integer> future = pool.submit(callable);
System.out.println(future.get());
```