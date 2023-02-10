package io.smallrye.faulttolerance.async.additional.nonblocking.fallback;

import static io.smallrye.faulttolerance.core.util.CompletionStages.failedFuture;
import static java.util.concurrent.CompletableFuture.completedFuture;

import java.util.concurrent.CompletionStage;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.faulttolerance.Fallback;

import io.smallrye.common.annotation.NonBlocking;

@ApplicationScoped
public class FallbackNonblockingHelloService {
    private volatile Thread helloThread;
    private volatile StackTraceElement[] helloStackTrace;

    private volatile Thread fallbackThread;

    @NonBlocking
    @Fallback(fallbackMethod = "fallback")
    public CompletionStage<String> hello() {
        helloThread = Thread.currentThread();
        helloStackTrace = new Throwable().getStackTrace();
        return failedFuture(new RuntimeException());
    }

    public CompletionStage<String> fallback() {
        fallbackThread = Thread.currentThread();
        return completedFuture("hello");
    }

    Thread getHelloThread() {
        return helloThread;
    }

    StackTraceElement[] getHelloStackTrace() {
        return helloStackTrace;
    }

    Thread getFallbackThread() {
        return fallbackThread;
    }
}
