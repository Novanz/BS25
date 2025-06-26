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
System.out.

println(future.get());
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

# 3 Aufgabe: Körnerzahlen von oberer und unterer Zeile tauschen

## Threadpool für Runnable Objekte

Siehe 1.2.2. An dieser Aufgabe ist zu erkennen, dass man bei einem ExecutorService den
dort vorhandenen Threads auch Runnable Objekte (und nicht nur Callable Objekte) zum
Abarbeiten übergeben kann. Dies geschieht mit der Methode execute(Runnable). Werden
dem ExecutorService mehr Runnable Objekte übergeben, als gerade abgearbeitet werden
können, dann werden die überschüssigen Runnable Objekte in einer Queue gespeichert.
Sobald die Threads im ExecutorService mit einem Runnable fertig sind, holen sie das
nächste Runnable aus der Queue und bearbeiten es.

## Exchanger

Mit einem Exchanger Objekt können zwei Threads zwei Werte austauschen. Wenn ein
Thread A die exchange(Integer) Methode eines Exchanger<Integer> Objekts aufruft,
wird er so lange in der Methode festgehalten, bis ein weiterer Thread B ebenfalls die
exchange(Integer) Methode desselben Exchanger Objekts aufruft. Angenommen Thread A
hat der exchange Methode den Wert 2 übergeben und Thread B hat der exchange Methode
den Wert 3 übergeben. Dann erhält Thread A den Wert 3 von Thread B als Rückgabewert der
exchange Methode. Umgekehrt erhält Thread B den Wert 2von Thread A als Rückgabewert.
Damit haben die Threads die Werte ausgetauscht und können der exchange Methode beide
entkommen.

## bearbeteKachel()

Wir tauschen schrittweise die Körnerzahlen der Kachel mit der Kachel darunter. Dazu nutzen wit das Exchanger-Objekt.

## main()

Einen Pool mit 2 Threads erzeugen. Dann die Tauscher initialisieren und starten(mittels execute, weil es sich um
Runnables und ncht um Callables handelt). Den Pool mit shutdown() beenden, damit keine neue Aufgaben reinkommen. Dann
warten, bis alle Threads fertig sind(mittels awaitTermination). Dann die Kachelmatrix ausgeben.
