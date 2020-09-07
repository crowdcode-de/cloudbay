package io.crowdcode.jopt.joptbay.effects;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Slf4j
public class MemoryGuzzler {

    private final OutputStream out;

    private volatile boolean keepRunning;

    private volatile Thread currentThread;

    private final List<Map<BigDecimal, BigDecimal>> memoryLeakStore = new LinkedList<>();

    public MemoryGuzzler() throws IOException {
        this.out = Files.newOutputStream(Paths.get("memory-guzzler.csv"));
    }

    public static Map<BigDecimal, BigDecimal> createHashMap(long size) {
        Map<BigDecimal, BigDecimal> map = new HashMap<>();

        Random randomGenerator = new Random();

        for (long i = 0; i < size; i++) {
            map.put(new BigDecimal(randomGenerator.nextDouble()), new BigDecimal(randomGenerator.nextDouble()));
        }

        return map;
    }

    public synchronized void start() {
        keepRunning = true;
        if (currentThread == null) {
            currentThread = new Thread(() -> {
                List<Map<BigDecimal, BigDecimal>> list = memoryLeakStore;
                long start = System.currentTimeMillis();
                try {
                    runUntilBreak(list, start);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, "Memory Guzzler");
            currentThread.start();
        }
    }

    public synchronized void stop() {
        keepRunning = false;
        currentThread = null;
    }


    public void runUntilBreak(final List<Map<BigDecimal, BigDecimal>> list, final long tsStart) throws IOException {
        long ts = System.currentTimeMillis();
        long counter = 1;
        int level = 1;


        try {
            do {
                final Map<BigDecimal, BigDecimal> speicherMap = createHashMap(1000);
                if (counter % 2 == 0) {
                    list.add(speicherMap);
                }
                if (counter % 20 == 0) {
                    removeFirstElement(list);
                }
                if (counter % 500 == 0) {
                    ts = log(tsStart, ts, counter, level);
                    level++;
                }
                counter++;
            } while (keepRunning);
        } finally {
            out.close();
        }
    }

    private Long log(final long tsStart, Long ts, final long counter, final int level) throws IOException {
        long durationSinceSystemStart = (System.currentTimeMillis() - tsStart);
        long durationSinceLastLevel = (System.currentTimeMillis() - ts);
        System.out.println(counter + " Time System started:" + durationSinceSystemStart + "ms" + " Since last level:" + (durationSinceLastLevel) + "ms");
        out.write((counter + ";" + durationSinceSystemStart + ";" + durationSinceLastLevel + "\n").getBytes());
        out.flush();

        ts = System.currentTimeMillis();
        return ts;
    }

    private void removeFirstElement(final List<Map<BigDecimal, BigDecimal>> list) {

        for (int i = 0; i < list.size(); i++) {
            final Map<BigDecimal, BigDecimal> e = list.get(i);
            if (e != null) {
                list.add(i, null);
                return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new MemoryGuzzler().start();
    }

}
