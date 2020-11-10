import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 */
public class Homework03 {
    public static class Flag {
        volatile boolean sumDone = false;
        volatile boolean sumRunning = false;
    }

    private volatile int result = 0;


    /**
     *  - Future
     *  - FutureTask
     *  - CompletableFuture
     *
     *  - Join
     *  - Wait/notify
     *  - CAS
     *
     *  - Semaphore
     *  - LockSupport
     *  - ReentrantLock
     *  - ReentrantReadWriteLock
     *  - CountDownLatch
     *  - CyclicBarrier
     *  - StampLock
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException, BrokenBarrierException, InvocationTargetException, IllegalAccessException {
        Method[] declaredMethods = Homework03.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {

            if (declaredMethod.getName().endsWith("Demo")) {
                System.out.println(declaredMethod.getName());
                declaredMethod.invoke(new Homework03());
                System.out.println();
            }
        }
    }

    private void reentrantLockDemo() throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        ReentrantLock lock = new ReentrantLock();
        Condition sumDone = lock.newCondition();

        final Flag flag = new Flag();
        singleThreadExecutor.execute(() -> {
            lock.lock();
            try {
                flag.sumRunning = true;
                result = sum();
            } finally {
                sumDone.signalAll();
                lock.unlock();
            }
        });


        lock.lock();
        try {
            sumDone.await();

            singleThreadExecutor.shutdown();
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } finally {
            lock.unlock();
        }
    }

    private void stampLockDemo() {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        StampedLock stampedLock = new StampedLock();
        final Flag flag = new Flag();
        singleThreadExecutor.execute(() -> {
            long stamp = stampedLock.writeLock();
            try {
                flag.sumRunning = true;
                result = sum();
            } finally {
                stampedLock.unlock(stamp);
            }
        });

        // 等线程池先拿到锁；
        while (!flag.sumRunning) { }


        long stamp = stampedLock.readLock();
        try {
            singleThreadExecutor.shutdown();
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        } finally {
            stampedLock.unlock(stamp);
        }
    }

    private void semaphoreDemo() throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        Semaphore lock = new Semaphore(1);
        final Flag flag = new Flag();
        singleThreadExecutor.execute(() -> {
            try {
                lock.acquire();
                flag.sumRunning = true;
                result = sum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.release();
            }
        });

        // 等线程池先拿到锁；
        while (!flag.sumRunning) { }


        lock.acquire();
        try {
            singleThreadExecutor.shutdown();
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        } finally {
            lock.release();
        }
    }

    private void casDemo() throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        final Flag flag = new Flag();
        singleThreadExecutor.execute(() -> {
            try {
                result = sum();
            } finally {
                flag.sumDone = true;
            }
        });

        while(!flag.sumDone) {
            TimeUnit.MILLISECONDS.sleep(150);
        }

        singleThreadExecutor.shutdown();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }

    private void cyclicBarrierDemo() throws BrokenBarrierException, InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        singleThreadExecutor.execute(() -> {
            try {
                result = sum();
            } finally {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        cyclicBarrier.await();
        singleThreadExecutor.shutdown();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }

    private void countDownLatchDemo() throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        singleThreadExecutor.execute(() -> {
            try {
                result = sum();
            } finally {
                countDownLatch.countDown();
            }
        });


        countDownLatch.await();
        singleThreadExecutor.shutdown();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }


    private void reentrantReadWriteLockDemo() throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        final Flag flag = new Flag();

        singleThreadExecutor.execute(() -> {
            lock.writeLock().lock();
            try {
                result = sum();
                flag.sumRunning = true;
            } finally {
                lock.writeLock().unlock();
            }
        });

        // 等线程池先拿到锁；
        while (!flag.sumRunning) { }

        lock.readLock().lock();
        try {
            singleThreadExecutor.shutdown();
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        } finally {
            lock.readLock().unlock();
        }

    }

    private void lockSupportDemo() {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        final Thread thread = Thread.currentThread();

        singleThreadExecutor.execute(() -> {
            try {
                result = sum();
            } finally {
                LockSupport.unpark(thread);
            }
        });

        LockSupport.park();

        singleThreadExecutor.shutdown();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private void notifyDemo() throws InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        singleThreadExecutor.execute(() -> {
            synchronized (Homework03.class) {
                result = sum();
                Homework03.class.notifyAll();
            }
        });

        synchronized (Homework03.class) {
            Homework03.class.wait();
        }

        singleThreadExecutor.shutdown();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }

    private void joinDemo() throws InterruptedException {
        long start = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            result = sum();
        });
        thread.start();

        thread.join();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private void completableFutureDemo() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        Integer result = CompletableFuture.supplyAsync(Homework03::sum).get();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private void futureTaskDemo() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        FutureTask<Integer> futureTask = new FutureTask<>(Homework03::sum);
        futureTask.run();

        System.out.println("异步计算结果为：" + futureTask.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }


    private void futureDemo() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


        Future<Integer> result = singleThreadExecutor.submit(Homework03::sum);
        // 确保  拿到result 并输出
        singleThreadExecutor.shutdown();
        System.out.println("异步计算结果为：" + result.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
