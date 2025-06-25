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

# 2 Aufgabe: Größte Körnerzahl in einer Zeile ermitteln

## CountDownLatch
Ein CountDownLatch ist ein Objekt, das einen Integer Wert hat. Dieser Wert wird zunächst
mit dem Konstruktor festgelegt. Mit der Methode countDown() können Threads diesen Wert
jeweils um eins erniedrigen. Dies geschieht so lange, bis der Wert null beträgt. Wenn Threads
die Methode await() aufrufen, bleiben sie so lange in der Methode gefangen, bis der Wert auf
null abgesenkt wurde. Dann kommen alle gefangenen Threads gleichzeitig aus der await()
Methode wieder heraus. Ein CountDownLatch, dessen Wert auf null abgesunken ist, kann
nicht mehr zurückgesetzt werden.

## Idee
Eine CountDownLatch Objekt wird mit der Anzahl der Sucher initialisiert. Wenn ein Thread eine Wand 
erreicht, ruft er countDown() auf. Dann wartet er mit await() bis alle andere fertig sind. Sobald 
der CountDownLatch-Wert null erreicht hat, können die Threads gleichzeitig aus der await() 
Methode herauskommen und ihre Ergebnisse verarbeiten.

## Unsetzung

### bearbeteKachel()

Aktuelle Anzahl der Körner holen und mit max vergleichen. Wenn die aktuelle Anzahl größer ist, dann
max auf die aktuelle Anzahl setzen.

### run()

Nach dem Durchlauf der Schleife(Sucher hat eine Wand erreicht) wird countDown() aufgerufen.

### main()

 Sucher initialisieren und starteen. Dann wird await() aufgerufen, um zu warten, bis alle Sucher fertig sind.
 Dann max ermitteln(z.B. mit Math.max()) und ausgeben.